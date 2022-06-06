package me.jwjun.scrapimages.rest;

public class RestException extends RuntimeException{
    public String errorMsg;

    public RestException(String errorMsg) {
        super(errorMsg);
    }
}