package com.practice.youtubeapispringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class YoutubeApiSpringbootApplication implements CommandLineRunner {

	@Autowired
	VideoService videoService;

	public static void main(String[] args) {
		SpringApplication.run(YoutubeApiSpringbootApplication.class, args);
	}


	@Override
	public void run(String... args) throws IOException {

		videoService.loadVideosInDB("cricket");


	}
}
