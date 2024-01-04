package com.sprve.eduorder.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sprve.common.utils.JwtUtils;
import com.sprve.common.utils.R;
import com.sprve.eduorder.entity.Order;
import com.sprve.eduorder.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Api(description = "查询支付订单模块")
@RestController
@RequestMapping("/eduorder/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @ApiOperation(value = "生成订单的方法")
    @PostMapping("/createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)) {
            return R.error().code(80000).message("请登录");
        }
        String orderNo = orderService.createOrders(courseId, memberId);
        return R.ok().data("orderId", orderNo);
    }
    @ApiOperation(value = "根据订单id查询订单信息")
    @GetMapping("/getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item", order);
    }
    @ApiOperation(value = "根据课程id和用户id查询订单中订单状态")
    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId, @PathVariable String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status", 1);
        int count = orderService.count(wrapper);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
    @ApiOperation(value = "带条件分页的订单列表")
    @PostMapping("/pageOrder/{current}/{limit}")
    public R pageOrder(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable Long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @RequestBody(required = false) Order order) {
        Page<Order> orderPage = new Page<>(current, limit);
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(order.getOrderNo())) {
            wrapper.like("order_no", order.getOrderNo());
        }
        wrapper.orderByDesc("gmt_create");
        orderService.page(orderPage, wrapper);
        long total = orderPage.getTotal();
        List<Order> records = orderPage.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }
    @ApiOperation(value = "用户查询已支付订单的方法")
    @PostMapping("/infoOrder")
    public R saveOrder(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)) {
            return R.error().code(80000).message("请登录");
        }
        List<Order> orderList = orderService.infoOrder(memberId);
        return R.ok().data("orderlist", orderList);
    }
}
