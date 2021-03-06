package com.twitter.handle.twitterhandle.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.handle.twitterhandle.constants.ApplicationConstants;
import com.twitter.handle.twitterhandle.model.Tweet;
import com.twitter.handle.twitterhandle.response.TweetsWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class TweetService {

    private RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(TweetService.class);

    @Autowired
    public TweetService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TweetsWrapper getTweetsByQuery(final String tweetId) {
        ResponseEntity<String> response = null;
        TweetsWrapper tweetsWrapper = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(ApplicationConstants.AUTHORIZATION_KEY, "Bearer AAAAAAAAAAAAAAAAAAAAAMKmIAEAAAAAjHF%2FjxxNcIM3pn16zZFKFA2QnEI%3Dgtb6Eo595mEnLMKEGuPRSQjY9dSQXwUWD0NAcbLMUkwRXvIKr4");
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<String>(headers);
            response = restTemplate.exchange(
                    ApplicationConstants.SINGLE_TWEET_END_POINT + tweetId, HttpMethod.GET, entity, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode json = objectMapper.readTree(response.getBody());
            Tweet[] tweets = objectMapper.readValue(json.get("data").toString(), Tweet[].class);
            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("response size::" + tweets.length);
                tweetsWrapper = new TweetsWrapper();
                tweetsWrapper.setTotalCount(tweets.length);
                tweetsWrapper.setTweets(Arrays.asList(tweets));
            } else {
                logger.warn("No data found for the given query::{}", tweetId);
            }
        } catch (RestClientException e) {
            logger.error("Exception at {{}}", e.getStackTrace()[0].getLineNumber(), e);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return tweetsWrapper;
    }
}
