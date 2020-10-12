package com.twitter.handle.twitterhandle.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.messaging.saaj.util.Base64;
import com.twitter.handle.twitterhandle.config.TwitterConfig;
import com.twitter.handle.twitterhandle.constants.ApplicationConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Service
public class TwitterTokenService {
    private RestTemplate restTemplate;
   private TwitterConfig twitterConfig;
    private static final Logger logger = LoggerFactory.getLogger(TweetService.class);

    @Autowired
    public TwitterTokenService(RestTemplate restTemplate, TwitterConfig twitterConfig) {
        this.restTemplate = restTemplate;
        this.twitterConfig = twitterConfig;
    }

    public String getToken() {
        String bearerToken = "";
        try {
            ResponseEntity<String> response;
            String encodedCredentials = encodeKeys(twitterConfig.getConsumerKey(), twitterConfig.getConsumerSecret());
            HttpHeaders headers = new HttpHeaders();
            headers.add(ApplicationConstants.AUTHORIZATION_KEY, "Basic " + encodedCredentials);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
            MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
            body.add("grant_type", "client_credentials");
            HttpEntity<?> entity = new HttpEntity<Object>(body, headers);
            response = restTemplate.exchange(
                    ApplicationConstants.TOKEN_END_POINT, HttpMethod.POST, entity, String.class);
            if(response.getStatusCode()==HttpStatus.OK){
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode json = objectMapper.readTree(response.getBody());
                bearerToken = json.get("access_token").asText();
                logger.error("response::{}", bearerToken);
            } else {
                logger.error("response::{}", response.getBody());
            }
        } catch (RestClientException e) {
            logger.error("Exception at {{}}", e.getStackTrace()[0].getLineNumber(), e);
        } catch (JsonMappingException e) {
            logger.error("Exception at {{}}", e.getStackTrace()[0].getLineNumber(), e);
        } catch (JsonProcessingException e) {
            logger.error("Exception at {{}}", e.getStackTrace()[0].getLineNumber(), e);
        }
        return bearerToken;
    }

    private String encodeKeys(String consumerKey, String consumerSecret) {
        try {
            String encodedConsumerKey = URLEncoder.encode(consumerKey, "UTF-8");
            String encodedConsumerSecret = URLEncoder.encode(consumerSecret, "UTF-8");

            String fullKey = encodedConsumerKey + ":" + encodedConsumerSecret;
            byte[] encodedBytes = Base64.encode(fullKey.getBytes());
            return new String(encodedBytes);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
