package com.practice.youtubeapispringboot;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.SearchResultSnippet;
import com.google.api.services.youtube.model.ThumbnailDetails;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService {

    private Logger logger = LoggerFactory.getLogger(VideoService.class);


    @Value("${app.youtube.api-key}")
    private String api_key;
    @Autowired
    YouTubeRepository youTubeRepository;

    private  YouTube youTube;

    public VideoService() {

        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        this.youTube = new YouTube.Builder(httpTransport, jsonFactory, new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest httpRequest) throws IOException {
            }
        }).build();
    }


    public void fetchVideos(List<String> keywords){

        for(int i=0;i<keywords.size();i++){
            VideoThread thread = new VideoThread(keywords.get(i),youTubeRepository,this.youTube,this.api_key);
            thread.start();
        }

        return ;
    }
    public void loadVideosInDB(String keyword) throws IOException, ParseException {

        YouTube.Search.List searchList = this.youTube.search().list("id,snippet");

        searchList.setKey(this.api_key);
        searchList.setQ(keyword);
        searchList.setMaxResults(5L);

        SearchListResponse response = searchList.execute();
        logger.warn("the response is{}"+response);

        List<SearchResult> items = response.getItems();

        logger.warn("the item in the response are{}"+items);

        List<Video> videos = new ArrayList<>();

        for(SearchResult result :items){

            SearchResultSnippet snippet = result.getSnippet();
            String videoId = result.getId().getVideoId();
            String channelId = snippet.getChannelId();
            String channelTitle = snippet.getChannelTitle();
            String title = snippet.getTitle();
            String description = snippet.getDescription();


           /* ThumbnailDetails thumbnails = snippet.getThumbnails();
            String lowurl = thumbnails.getDefault().getUrl();
            String mediumurl = thumbnails.getMedium().getUrl();
            String highurl = thumbnails.getHigh().getUrl();

            JSONObject thumbnailses = new JSONObject();
            thumbnailses.put("low",lowurl);
            thumbnailses.put("medium",mediumurl);
            thumbnailses.put("high",highurl);
            */

            JSONObject thumbnails=  (JSONObject)JSONValue.parseWithException( snippet.getThumbnails().toString());

            DateTime publishedAt = snippet.getPublishedAt();

            Video video= new Video();

            video.setVideoId(videoId);
            video.setChannelId(channelId);
            video.setChannelTitle(channelTitle);
            video.setThumbnailUrl(thumbnails);
            video.setPublishedDate(publishedAt);
            video.setDescription(description);
            video.setTitle(title);


            //youTubeRepository.save(video);
            videos.add(video);
        }

        youTubeRepository.saveAll(videos);

    }


}
