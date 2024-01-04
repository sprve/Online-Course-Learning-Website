package com.sprve.staservice.client;
import com.sprve.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
@Component
@FeignClient("service-edu")
public interface EduClient {
    @GetMapping("/eduservice/course/countBuy")
    public R countBuy();
    @GetMapping("/eduservice/video/countView")
    public R countView();
}
