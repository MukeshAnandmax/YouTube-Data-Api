package com.practice.youtubeapispringboot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.json.JsonObject;
import org.json.simple.JSONObject;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Video {

    private String videoId;
    private String title;
    private  String description;

    private JSONObject thumbnailUrl;
    private String channelTitle;
    private String channelId;



}
