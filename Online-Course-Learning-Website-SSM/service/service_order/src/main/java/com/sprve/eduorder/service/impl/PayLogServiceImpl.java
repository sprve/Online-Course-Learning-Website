package com.sprve.eduorder.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sprve.eduorder.client.EduClient;
import com.sprve.eduorder.entity.Order;
import com.sprve.eduorder.entity.PayLog;
import com.sprve.eduorder.mapper.PayLogMapper;
import com.sprve.eduorder.service.OrderService;
import com.sprve.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sprve.eduorder.utils.ConstantAlipay;
import com.sprve.common.base.ExceptionHandler.SprveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private EduClient eduClient;
    @Override
    public Map createNative(String orderNo) {
        try {
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no",orderNo);
            Order order = orderService.getOne(wrapper);
            AlipayClient alipayClient = new DefaultAlipayClient(
                    ConstantAlipay.ACCESS_GATEAY_URL,
                    ConstantAlipay.ACCESS_APP_ID,
                    ConstantAlipay.ACCESS_MERCHANT_PRIVATE_KEY,
                    ConstantAlipay.ACCESS_FORMAT,
                    ConstantAlipay.ACCESS_CHAR_SET,
                    ConstantAlipay.ACCESS_ALIPAY_PUBLIC_KEY,
                    ConstantAlipay.ACCESS_SIGN_TYPE);
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            request.setReturnUrl(ConstantAlipay.ACCESS_RETURN_URL+order.getCourseId());
            String totalAmount =order.getTotalFee().multiply(new BigDecimal("1")).longValue()+"";
            String subject = order.getCourseId();
            Long currentTime = System.currentTimeMillis() + 10* 60 * 1000;
            Date date = new Date(currentTime);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String endTime = dateFormat.format(date);
            JSONObject json = new JSONObject();
            json.put("out_trade_no",orderNo);
            json.put("total_amount",totalAmount);
            json.put("subject",subject);
            json.put("product_code","FAST_INSTANT_TRADE_PAY");
            json.put("time_expire",endTime);
            request.setBizContent(json.toString());
            String form = alipayClient.pageExecute(request).getBody();
            Map map=new HashMap<>();
            map.put("data",form);
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            return map;
        }catch(Exception e) {
            throw new SprveException(40000,"生成二维码失败");
        }
    }
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no",orderNo);
            Order order = orderService.getOne(wrapper);
            AlipayClient alipayClient = new DefaultAlipayClient(
                    ConstantAlipay.ACCESS_GATEAY_URL,
                    ConstantAlipay.ACCESS_APP_ID,
                    ConstantAlipay.ACCESS_MERCHANT_PRIVATE_KEY,
                    ConstantAlipay.ACCESS_FORMAT,
                    ConstantAlipay.ACCESS_CHAR_SET,
                    ConstantAlipay.ACCESS_ALIPAY_PUBLIC_KEY,
                    ConstantAlipay.ACCESS_SIGN_TYPE);
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            String outTradeNo = orderNo;
            JSONObject json = new JSONObject();
            json.put("out_trade_no",outTradeNo);
            request.setBizContent(json.toString());
            AlipayTradeQueryResponse data = alipayClient.execute(request);
            Map map=new HashMap<>();
            map.put("out_trade_no",data.getOutTradeNo());
            map.put("trade_no",data.getTradeNo());
            map.put("trade_state",data.getTradeStatus());
            return map;
        }catch(Exception e) {
            return null;
        }
    }
    @Override
    public void updateOrdersStatus(Map<String, String> map) {
        String orderNo = map.get("out_trade_no");
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);
        if (order.getStatus().intValue() == 1) {
            return;
        }
        order.setStatus(1);
        orderService.updateById(order);
        eduClient.setCourseAction(order.getCourseId(),"buy");
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo);
        payLog.setPayTime(new Date());
        payLog.setPayType(2);
        payLog.setTotalFee(order.getTotalFee());
        payLog.setTradeState(map.get("trade_state"));
        payLog.setTransactionId(map.get("trade_no"));
        payLog.setAttr(JSONObject.toJSONString(map));
        baseMapper.insert(payLog);
    }
}
