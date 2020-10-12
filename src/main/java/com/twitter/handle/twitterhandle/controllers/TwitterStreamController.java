package com.twitter.handle.twitterhandle.controllers;

import com.twitter.handle.twitterhandle.constants.ApplicationConstants;
import com.twitter.handle.twitterhandle.model.Tweet;
import com.twitter.handle.twitterhandle.response.Response;
import com.twitter.handle.twitterhandle.response.TweetsWrapper;
import com.twitter.handle.twitterhandle.services.TweetService;
import com.twitter.handle.twitterhandle.services.TwitterTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@Api(value = "Tweet fetch APIs")
@CrossOrigin(origins = "*")
public class TwitterStreamController {

    private TweetService tweetService;
    private TwitterTokenService twitterTokenService;
    Logger logger = LoggerFactory.getLogger(TwitterStreamController.class);

    @Autowired
    public TwitterStreamController(TweetService tweetService, TwitterTokenService twitterTokenService) {
        this.tweetService = tweetService;
        this.twitterTokenService = twitterTokenService;
    }

    @GetMapping("/fetch/tweets/{id}")
    @ApiOperation(value = "This API fetches tweets")
    @ResponseBody
    public ResponseEntity<Response> fetchTweets(@PathVariable String id) {
        logger.trace("Getting Started!!");
        ResponseEntity<Response> responseEntity = null;
        Response response = Response.getSuccessResponse();
        try {
            TweetsWrapper tweets = tweetService.getTweetsByQuery(id);
            if(Objects.nonNull(tweets)){
                response.setStatusCode(HttpStatus.OK);
                response.setMessage(ApplicationConstants.SUCCESS);
                response.setData(tweets);
            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND);
                response.setMessage("no data found");
                response.setData(null);
            }
            responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(ApplicationConstants.FAILURE);
            responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  responseEntity;
    }

    @GetMapping("/token")
    public ResponseEntity<Response> getToken() {
        ResponseEntity<Response> responseEntity = null;
        Response response = Response.getSuccessResponse();
        try {
            String token = twitterTokenService.getToken();
            if(!StringUtils.isEmpty(token)){
                response.setData(token);
                responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND);
                response.setMessage(ApplicationConstants.FAILURE);
                responseEntity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(ApplicationConstants.FAILURE);
            responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
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

