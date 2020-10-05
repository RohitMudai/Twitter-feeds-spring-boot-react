package com.twitter.handle.twitterhandle.controllers;

import com.twitter.handle.twitterhandle.services.TweetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class TwitterStreamController {

    private TweetService tweetService;
    Logger logger = LoggerFactory.getLogger(TwitterStreamController.class);

    @Autowired
    public TwitterStreamController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping("/fetch/tweets/{id}")
    public ResponseEntity<String> fetchTweets(@PathVariable String id) {
        logger.trace("Getting Started!!");
        return tweetService.getTweetsById(id);
    }
//
//    @GetMapping("/fetch/stream")
//    public ResponseEntity<String> fetchLiveStream() {
//        System.out.println("sending request....");
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAAs5%2FAAAAAAA%2BFhxtLDRr2AuKh5zdIHTczhg0Jg%3DltF0dqGzLFlmXH9wjI8HkO1gEzGlnCYUegwIOVVu1Umn8Yi1sX");
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        HttpEntity<String> entity = new HttpEntity<String>(headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                "https://api.twitter.com/labs/1/tweets/stream/filter?format=detailed", HttpMethod.GET, entity, String.class);
//        String body = response.getBody();
//        System.out.println(body);
//        return ResponseEntity.ok(body);
//    }
}

