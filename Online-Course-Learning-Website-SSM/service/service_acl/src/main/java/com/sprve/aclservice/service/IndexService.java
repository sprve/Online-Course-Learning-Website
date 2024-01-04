package com.sprve.aclservice.service;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;
public interface IndexService {
    Map<String, Object> getUserInfo(String username);
    List<JSONObject> getMenu(String username);
}
