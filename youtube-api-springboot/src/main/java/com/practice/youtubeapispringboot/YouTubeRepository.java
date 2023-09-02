package com.practice.youtubeapispringboot;

import com.mongodb.DuplicateKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    /*YouTubeRepository(){
        createIndex();
    }*/
    private Logger logger = LoggerFactory.getLogger(YouTubeRepository.class);
    @Autowired
    MongoOperations mongoOperations;

    public  void  save(Video video) throws Exception {
        try {
            mongoOperations.save(video);
            logger.warn("Saving single Video{}");
        }catch (org.springframework.dao.DuplicateKeyException e){
            e.printStackTrace();
        }catch (Exception e){
            throw  new Exception("Some other Exception while saving videos");
        }

    }

    public void saveAll(List<Video> videos) throws Exception {
        try {
            logger.warn("Saving Multiple Video{}");
            mongoOperations.insertAll(videos);
        }catch (org.springframework.dao.DuplicateKeyException e){
            for (Video video:videos){
                logger.warn("Saving single Video11{}");
                save(video);
            }
        }

    }

    public  List<Video> getVideos(String keyword,int pageNo,int pageSize){

        Criteria criteria =  Criteria.where("tag").is(keyword);
        Query query = new Query();
        query.addCriteria(criteria).skip((pageNo-1)*pageSize).limit(pageSize);
        return mongoOperations.find(query,Video.class);
    }

  /*  public  void createIndex(){
        mongoOperations.indexOps(Video.class).ensureIndex(new Index().on("videoId", Sort.Direction.ASC).unique());
    }*/


}
