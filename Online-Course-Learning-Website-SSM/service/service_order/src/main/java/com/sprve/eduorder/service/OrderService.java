package com.sprve.eduorder.service;
import com.sprve.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
public interface OrderService extends IService<Order> {
    String createOrders(String courseId, String memberIdByJwtToken);
    List<Order> infoOrder(String memberId);
}
