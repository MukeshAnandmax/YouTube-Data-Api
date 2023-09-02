package com.practice.youtubeapispringboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class YouTubeController {


    @GetMapping("/get_videos")
    public List<Video> getVideos(){
        return null;
    }
}
