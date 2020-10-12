package com.twitter.handle.twitterhandle.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sun.xml.internal.messaging.saaj.util.Base64;
import com.twitter.handle.twitterhandle.constants.ApplicationConstants;
import com.twitter.handle.twitterhandle.model.Tweet;
import com.twitter.handle.twitterhandle.model.TwitterTemplate;
import com.twitter.handle.twitterhandle.response.TweetsWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;

@Service
public class TwitterTokenService {
    private RestTemplate restTemplate;
    private TwitterTemplate twitterTemplate;
    private static final Logger logger = LoggerFactory.getLogger(TweetService.class);

    @Autowired
    public TwitterTokenService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TweetsWrapper getToken() {
        try {
            String encodedCredentials = encodeKeys(twitterTemplate.getConsumerKey(), twitterTemplate.getConsumerSecret());
            HttpHeaders headers = new HttpHeaders();
            headers.add(ApplicationConstants.AUTHORIZATION_KEY, "Bearer AAAAAAAAAAAAAAAAAAAAAMKmIAEAAAAAjHF%2FjxxNcIM3pn16zZFKFA2QnEI%3Dgtb6Eo595mEnLMKEGuPRSQjY9dSQXwUWD0NAcbLMUkwRXvIKr4");
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            HttpEntity<String> entity = new HttpEntity<String>(headers);
            response = restTemplate.exchange(
                    ApplicationConstants.SINGLE_TWEET_END_POINT + tweetId, HttpMethod.GET, entity, String.class);

        } catch (RestClientException e) {
            logger.error("Exception at {{}}", e.getStackTrace()[0].getLineNumber(), e);
        }
        return tweetsWrapper;
    }

    private String encodeKeys(String consumerKey, String consumerSecret) {
        try {
            String encodedConsumerKey = URLEncoder.encode(consumerKey, "UTF-8");
            String encodedConsumerSecret = URLEncoder.encode(consumerSecret, "UTF-8");

            String fullKey = encodedConsumerKey + ":" + encodedConsumerSecret;
            byte[] encodedBytes = Base64.encode(fullKey.getBytes());
            return new String(encodedBytes);
        }
        catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
