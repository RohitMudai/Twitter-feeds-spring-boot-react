package com.twitter.handle.twitterhandle.services;

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
    private static final String SINGLE_TWEET_END_POINT = "https://api.twitter.com/2/tweets/";

    private RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(TweetService.class);

    @Autowired
    public TweetService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> getTweetsById(final String tweetId) {
        ResponseEntity<String> response = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "OAuth oauth_consumer_key=\"NPyPp7ut0xD2ZN8WTJtxrQ0xQ\",oauth_token=\"1311012258894340096-SZnJVLZm0uyFQLcElMUbcndiU3Osip\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"1601488496\",oauth_nonce=\"8zsLDCKs2GQ\",oauth_version=\"1.0\",oauth_signature=\"TcizuNlPw2Aa%2B0rLqkxYtUWF5Zk%3D\"");
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            response = restTemplate.exchange(
                    SINGLE_TWEET_END_POINT + tweetId, HttpMethod.GET, entity, String.class);
        } catch (RestClientException e) {
            logger.error("Exception at {{}}", e.getStackTrace()[0].getLineNumber(), e);
        }
        return response;
    }
}
