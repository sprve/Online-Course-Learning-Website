package com.sprve.vod.utils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
@Component
public class ConstantVodUtils implements InitializingBean {
    private String keyId="LTAI5tJQRu2aQEo6otAxgaV8";
    private String keySecret="9pTrmeQVvQ3XehJ5wMykJQjTcarsqv";
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
    }
}