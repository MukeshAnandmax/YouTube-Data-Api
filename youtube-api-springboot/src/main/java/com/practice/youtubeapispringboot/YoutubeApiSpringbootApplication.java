package com.practice.youtubeapispringboot;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class YoutubeApiSpringbootApplication implements CommandLineRunner {

	@Autowired
	VideoService videoService;

	List<String> keywords = Arrays.asList("cricket","tech","java","FAANG");

	public static void main(String[] args) {
		SpringApplication.run(YoutubeApiSpringbootApplication.class, args);
	}


	@Override
	public void run(String... args) throws IOException, ParseException {

//		videoService.initDB();
//		//videoService.loadVideosInDB("cricket");
//		videoService.fetchVideos(keywords);


	}
}
