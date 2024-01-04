package com.sprve.eduorder.client;
import com.sprve.common.utils.ordervo.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
@Component
@FeignClient("service-edu")
public interface EduClient {
    @PostMapping("/eduservice/coursefront/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id);
    @PostMapping("/eduservice/course/{courseId}/{action}")
    public void setCourseAction(@PathVariable("courseId") String courseId,
                                @PathVariable("action") String action);
}
