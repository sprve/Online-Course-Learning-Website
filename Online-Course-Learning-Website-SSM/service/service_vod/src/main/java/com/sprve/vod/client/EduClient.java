package com.sprve.vod.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
@Component
@FeignClient("service-edu")
public interface EduClient {
    @PostMapping("/eduservice/course/{courseId}/{action}")
    public void setCourseAction(@PathVariable("courseId") String courseId,
                                @PathVariable("action") String action);
    @PostMapping("/eduservice/video/findId/{id}")
    public String getCourseId(@PathVariable("id") String id);
    @PostMapping("/eduservice/video/addVideoCount/{id}")
    public void addVideoCount(@PathVariable("id") String id);
}