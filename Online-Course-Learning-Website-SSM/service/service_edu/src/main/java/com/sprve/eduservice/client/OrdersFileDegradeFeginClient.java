package com.sprve.eduservice.client;
import org.springframework.stereotype.Component;
@Component
public class OrdersFileDegradeFeginClient implements OrdersClient{
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        return false;
    }
}
