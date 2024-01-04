package com.sprve.eduorder.utils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
@Component
public class ConstantAlipay implements InitializingBean {
    public String appId = "2021000122681592";
    public String merchantPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCZm2CdFb/kHUJk69Spw/4v/nKL4QdoOS/tUHNsk8U7R4mAIKqMnZAE8ye5v4zLxVqeyHN/ml53DJckvvjQ3kYEcYwdO3BdYLEJfIM5Lz/MY5LEUVgIYIEo9VipCZXolW7p6lcXMbDp8fY/be2Fr5c/MXvPHRxJp8/6K7zWzt52GptQKwMyFV/CDcHi9Jo/6hQeR7wBJE8JbK2AOWIVezf34bY3AYwSbyvEUsNOFgo5bgT1f4FumVQHYh8b2HJ/+UBcxTq1+ygT8p4sWnljI1GXPf3+ROgGWybr+/04Gft6zMptLiihr+2npay8ZvS7hb5BB4gSyy5gnlcXszx11kURAgMBAAECggEAdVRMoNA7Kbf7vfCxGRde8jPuNNAnA0IjTKbV84qP7a6/5YGOJCg6GL3Oi0A4AHH3vS8pob89Brw0OtowXgJaAwzkVHPRaVHxCGj2JtjjZ3Bra88iagukvK9jEIME4Yo7FSttaIcJNjkeueZytlmgMqHOiy4WLGClKNb9wCp1nEmZUx4umxuxUa8ZSyZ/kzzi1SJDXNipVfAQpJf/oKT9IxrrpWJfVFzLq8aESF/6DssJTBtwIgBVE8s3/zWaklSQ140qLyEpXYC0i3ATh1THDYXRi9bEU25F1djJbIR4FtwzCP87gr5R5/QE4isY2UQZH75GE84re6LBIYG3w/aHPQKBgQDNnSy4h+Y3vuP0FV1cMfcBGsPSq163AhwW3TzgTAGM8AhUOZq+KdPhON+8LKrR3kduZhmOdlqSqbNoQRTYxXXz7vM53MD73AzjqP+280Y1YBZZTj5JBpwZbvBPloLXux7K+aJPwF3IytpmixHsEOtHBb39DzzzhHtUyUYxrlMccwKBgQC/P6KQWe8wSjmOF49sr1qoQ8RDWGu3CV5aLQ4WcQvpzIrNh6W2cnVA2x+7NYvLPEWqTKBlN/PZxjXeAo7QtYqZtJzoB2owubhbcujyyZTg7C9NveDERMGWfbEhBWuwM76o4ZJmk/pmfmP/nSkEksm04ZoEbeOV7N+g63YVC3HbawKBgCp7sDLMeYfJHEZhZ7HV7QhAZHeRyvcbK3yZ56scaTGaos3sPQnPkKS3YJr7y6ujNrUbyWoDEEG/aH9A+zKy0eU92Ol6sDEZzVv4niVJ7Ja4rMV1Ai+KexRSNLrtCUnTstATdjLuNzJ2n1uSnnhdIo/BIvk3fct+8xCYqbU4JJn7AoGADbBtX23AjDFT61LOZlYq5A5ttvBWTXSzNzswXfTbiHGmoc2K1ANfVzQYEltj1TlMN0Ni27vkjXr9khWWyWOlNxlRfd5CAJjalFkmSXKaYHsna6Yit4nAXZhDnHx519PsWsRCqWsAKmPznnFOZVBodbUMRDLlbm7VFvyjqB/4l18CgYEAm1vWI3cGE2AHbT3BN9E0rhUoKAXyMFLqkVG8rLlaE+OBxIxxh10NHZpMn6IeLG9fLygPuSgJtmrb8r4VZ3DlcRTXSarllizmD6WLMeQbQ1KZyleNpern2wv4Vth64O1FBh3Y98xn5u+73zdiUcHUOHtV1egglZsPCe1Zceb0jtc=";
    public String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiNBVTOjEl0jDi2qwSyrqSVwRxLSLFnNgmoWZca/PV0/vBxqF8aYbp0rPC2KLkt1E1AZDRZ9FyypuVpLhR+8VQ0AQFjcF02Jg1oqGwU26Eic1m3IijNLjLEL38Nbszpjrgoeh3m6UAF7awbjoGj+Svycm5bOSf8qLe35t316uAgHmXfSRE3Pne7vEFG9ai9gWQDjqJ5ZfmG9587IyB/pchf0mncQO4QphxXJ97TNXEumD2VFWJV0xcwju1mmxC7D+ItFSCBvsE298r1ebFH8mobWUUUR255h0vQa7WcxWJwrX/cJ5aoY2j3MLadtwcJzk1cwpbbGUAWzYH1SsZAdeQwIDAQAB";
    public String notifyUrl = "";
    public String returnUrl = "http://101.42.171.11:2000/course/";
    public String signType = "RSA2";
    public String charSet = "utf-8";
    public String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
    public String format="JSON";
    public static String ACCESS_APP_ID;
    public static String ACCESS_MERCHANT_PRIVATE_KEY;
    public static String ACCESS_ALIPAY_PUBLIC_KEY;
    public static String ACCESS_NOTIFY_URL;
    public static String ACCESS_RETURN_URL;
    public static String ACCESS_SIGN_TYPE;
    public static String ACCESS_CHAR_SET;
    public static String ACCESS_GATEAY_URL;
    public static String ACCESS_FORMAT;
    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_APP_ID = appId;
        ACCESS_MERCHANT_PRIVATE_KEY = merchantPrivateKey;
        ACCESS_ALIPAY_PUBLIC_KEY=alipayPublicKey;
        ACCESS_NOTIFY_URL=notifyUrl;
        ACCESS_RETURN_URL=returnUrl;
        ACCESS_SIGN_TYPE=signType;
        ACCESS_CHAR_SET=charSet;
        ACCESS_GATEAY_URL=gatewayUrl;
        ACCESS_FORMAT=format;
    }
}
