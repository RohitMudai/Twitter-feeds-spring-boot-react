package com.twitter.handle.twitterhandle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
public class TwitterStreamController {

    @Autowired
    protected RestTemplate restTemplate;

    @GetMapping("/fetch/tweets")
    public ResponseEntity<String> fetchTweets() {
        System.out.println("sending request....");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "OAuth oauth_consumer_key=\"NPyPp7ut0xD2ZN8WTJtxrQ0xQ\",oauth_token=\"1311012258894340096-SZnJVLZm0uyFQLcElMUbcndiU3Osip\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"1601488496\",oauth_nonce=\"8zsLDCKs2GQ\",oauth_version=\"1.0\",oauth_signature=\"TcizuNlPw2Aa%2B0rLqkxYtUWF5Zk%3D\"");
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.twitter.com/2/tweets/1311032637507678215", HttpMethod.GET, entity, String.class);
        String body = response.getBody();
        System.out.println(body);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/fetch/stream")
    public ResponseEntity<String> fetchLiveStream() {
        System.out.println("sending request....");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAAs5%2FAAAAAAA%2BFhxtLDRr2AuKh5zdIHTczhg0Jg%3DltF0dqGzLFlmXH9wjI8HkO1gEzGlnCYUegwIOVVu1Umn8Yi1sX");
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.twitter.com/labs/1/tweets/stream/filter?format=detailed", HttpMethod.GET, entity, String.class);
        String body = response.getBody();
        System.out.println(body);
        return ResponseEntity.ok(body);
    }
}

