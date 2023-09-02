package com.practice.youtubeapispringboot;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.SearchResultSnippet;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VideoThread extends  Thread {


   private String keyword;
    private List<Video> videos;
    private String api_key;
    private YouTube youTube;
    private YouTubeRepository youTubeRepository;
    private String pageToken;

    public VideoThread(String keyword, YouTubeRepository youTubeRepository,YouTube youTube,String api_key) {
        this.keyword = keyword;
        this.youTubeRepository = youTubeRepository;
        this.youTube = youTube;
        this.api_key=api_key;
    }

    @Override
    public void run() {
        //fetching the data from youtube &saving the data

        YouTube.Search.List searchList = null;
        try {
            searchList = this.youTube.search().list("id,snippet");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        searchList.setKey(this.api_key);
        searchList.setQ(keyword);
        searchList.setMaxResults(5L);
        searchList.setPageToken(this.pageToken);

        SearchListResponse response = null;
        try {
            response = searchList.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // logger.warn("the response is{}"+response);

        List<SearchResult> items = response.getItems();

        //logger.warn("the item in the response are{}"+items);

        List<Video> videos = new ArrayList<>();

        for (SearchResult result : items) {

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

            JSONObject thumbnails = null;
            try {
                thumbnails = (JSONObject) JSONValue.parseWithException(snippet.getThumbnails().toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            DateTime publishedAt = snippet.getPublishedAt();

            Video video = new Video();

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
        this.pageToken =  response.getNextPageToken();
    }
}
