package com.sprve.eduorder.service;
import com.sprve.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;
public interface PayLogService extends IService<PayLog> {
    Map<String,String> createNative(String orderNo);
    Map<String,String> queryPayStatus(String orderNo);
    void updateOrdersStatus(Map<String, String> map);
}
