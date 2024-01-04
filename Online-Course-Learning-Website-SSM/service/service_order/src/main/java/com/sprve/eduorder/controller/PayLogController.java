package com.sprve.eduorder.controller;
import com.sprve.common.utils.R;
import com.sprve.eduorder.service.PayLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.concurrent.TimeUnit;
@Api(description = "支付宝管理")
@RestController
@RequestMapping("/eduorder/paylog")
public class PayLogController {
    @Autowired
    private PayLogService payLogService;
    @ApiOperation(value = "生成支付宝二维码接口")
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo) {
        Map map = payLogService.createNative(orderNo);
        System.out.println("****返回二维码map集合"+map);
        return R.ok().data(map);
    }
    @ApiOperation(value = "监听订单支付状态")
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {
        Map<String,String> map;
        for(int i=0;i<60;i++){
            try{
                TimeUnit.MILLISECONDS.sleep(10000);}
            catch (Exception e){
                e.printStackTrace();
            }
            map = payLogService.queryPayStatus(orderNo);
            System.out.println("****查询订单状态："+map);
            if (map == null) {
                return R.error().message("支付出错了");
            }
            if(map.get("trade_state")==null)
                continue;
            if (map.get("trade_state").equals("TRADE_SUCCESS")) {
               payLogService.updateOrdersStatus(map);
               return R.ok().message("支付成功");
            }
        }
        return R.error().message("订单支付超时");
    }
}