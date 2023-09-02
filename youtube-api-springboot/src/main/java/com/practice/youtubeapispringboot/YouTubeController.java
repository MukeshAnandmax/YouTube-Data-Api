package com.practice.youtubeapispringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class YouTubeController {

    @Autowired
    YouTubeRepository youTubeRepository;


    @GetMapping("/get_videos")
    public List<Video> getVideos(@RequestParam("keyword") String keyword,
                                 @RequestParam(value = "pageNo",required = false,defaultValue = "1")  int pageNo,
                                 @RequestParam(value = "pageSize",required = false,defaultValue = "10") int pageSize ){
        return youTubeRepository.getVideos(keyword,pageNo,pageSize);
    }
}
