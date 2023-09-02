package com.practice.youtubeapispringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class YouTubeRepository  {

    @Autowired
    MongoOperations mongoOperations;

    public  void  save(Video video){
        mongoOperations.save(video);
    }

    public void saveAll(List<Video> videos){
        mongoOperations.insertAll(videos);
    }

    public  List<Video> getVideos(String query,int pageNo,int pageSize){
        return null;
    }


}
