package com.sprve.msmService.service;
import java.util.Map;
public interface MsmService {
    boolean send(Map<String, Object> param, String phone);
}
