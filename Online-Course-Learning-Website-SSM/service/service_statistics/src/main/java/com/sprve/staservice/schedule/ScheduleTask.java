package com.sprve.staservice.schedule;
import com.sprve.staservice.service.StatisticsDailyService;
import com.sprve.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;
@Component
public class ScheduleTask {
    @Autowired
    private StatisticsDailyService staService;
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {
        staService.allCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
