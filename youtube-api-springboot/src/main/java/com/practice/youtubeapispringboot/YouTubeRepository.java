package com.practice.youtubeapispringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;

public class YouTubeRepository  {

    @Autowired
    MongoOperations mongoOperations;

    public  void  save(Video video){
        mongoOperations.save(video);
    }


}
