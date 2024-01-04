package com.sprve.msmService.utils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
@Component
public class ConstantPropertiesUtils implements InitializingBean {
    private String regionId="cn-shanghai";
    private String accessKeyId="LTAI5tJQRu2aQEo6otAxgaV8";
    private String secret="9pTrmeQVvQ3XehJ5wMykJQjTcarsqv";
    private String signName="Sprve云";
    private String templateCode="SMS_276400670";
    public static String REGION_ID;
    public static String ACCESS_KEY_ID;
    public static String SECRECT;
    public static String SIGN_NAME;
    public static String TEMPLATE_CODE;
    @Override
    public void afterPropertiesSet() throws Exception {
        REGION_ID = regionId;
        ACCESS_KEY_ID = accessKeyId;
        SECRECT = secret;
        SIGN_NAME = signName;
        TEMPLATE_CODE = templateCode;
    }
}
