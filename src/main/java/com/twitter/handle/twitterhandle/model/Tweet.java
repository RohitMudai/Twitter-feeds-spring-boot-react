package com.twitter.handle.twitterhandle.model;

import lombok.Data;

@Data
public class Tweet {
    protected String id;
    protected String tweet;
    protected String userName;
    protected String fullName;
}
