package com.sprve.oss.utils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
@Component
public class ConstantPropertiesUtils implements InitializingBean {
    private String endpoint="oss-cn-beijing.aliyuncs.com";
    private String keyId="LTAI5tJQRu2aQEo6otAxgaV8";
    private String keySecret="9pTrmeQVvQ3XehJ5wMykJQjTcarsqv";
    private String bucketName="sprve";
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;
    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
    }
}
