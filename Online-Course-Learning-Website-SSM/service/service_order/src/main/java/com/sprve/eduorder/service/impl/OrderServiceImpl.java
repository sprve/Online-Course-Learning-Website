package com.sprve.eduorder.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sprve.common.utils.ordervo.CourseWebVoOrder;
import com.sprve.common.utils.ordervo.UcenterMemberOrder;
import com.sprve.eduorder.client.EduClient;
import com.sprve.eduorder.client.UcenterClient;
import com.sprve.eduorder.entity.Order;
import com.sprve.eduorder.mapper.OrderMapper;
import com.sprve.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sprve.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;
    @Override
    public String createOrders(String courseId, String memberId) {
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherId(courseInfoOrder.getTeacherId());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);
        order.setPayType(2);
        baseMapper.insert(order);
        return order.getOrderNo();
    }
    @Override
    public List<Order> infoOrder(String memberId){
        List<Order> orderList;
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);
        orderList = baseMapper.selectList(wrapper);
        return orderList;
    }
}
