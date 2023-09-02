package com.practice.youtubeapispringboot;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class VideoService {

    private Logger logger = LoggerFactory.getLogger(VideoService.class);


    @Value("${app.youtube.api-key}")
    private String api_key;

    public void loadVideosInDB(String keyword) throws IOException {


        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        YouTube youTube = new YouTube.Builder(httpTransport, jsonFactory, new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest httpRequest) throws IOException {
            }
        }).build();


        YouTube.Search.List searchList = youTube.search().list("id,snippet");

        //searchList.setKey(this.api_key);
        searchList.setKey("AIzaSyDZF7n-nonqqlFKZx1zQY8DrwSzBvQkFBc");
        searchList.setQ(keyword);
        searchList.setMaxResults(5L);

        SearchListResponse response = searchList.execute();
        logger.warn("the response is{}"+response);

        List<SearchResult> items = response.getItems();

        logger.warn("the item in the response are{}"+items);

        for(SearchResult result :items){

        }

    }


}
