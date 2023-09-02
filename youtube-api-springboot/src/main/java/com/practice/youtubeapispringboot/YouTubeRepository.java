package com.practice.youtubeapispringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class YouTubeRepository  {


    YouTubeRepository(){
        createIndex();
    }

    @Autowired
    MongoOperations mongoOperations;

    public  void  save(Video video){
        mongoOperations.save(video);
    }

    public void saveAll(List<Video> videos){
        mongoOperations.insertAll(videos);
    }

    public  List<Video> getVideos(String keyword,int pageNo,int pageSize){

        Criteria criteria =  Criteria.where("tag").is(keyword);
        Query query = new Query();
        query.addCriteria(criteria).skip((pageNo-1)*pageSize).limit(pageSize);
        return mongoOperations.find(query,Video.class);
    }public  void createIndex(){
        mongoOperations.indexOps(Video.class).ensureIndex(new Index().on("videoId", Sort.Direction.ASC).unique());
    }


}
