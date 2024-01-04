package com.sprve.staservice.service;
import com.sprve.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;
public interface StatisticsDailyService extends IService<StatisticsDaily> {
    void allCount(String day);
    Map<String,Object> getShowData(String type, String begin, String end);
}
