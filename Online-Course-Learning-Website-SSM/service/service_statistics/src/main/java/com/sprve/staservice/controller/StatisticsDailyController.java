package com.sprve.staservice.controller;
import com.sprve.common.utils.R;
import com.sprve.staservice.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@Api(description = "统计管理模块")
@RestController
@RequestMapping("/staservice/sta")
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService staService;
    @ApiOperation(value = "统计某一天注册人数，生成统计数据")
    @PostMapping("allCount/{day}")
    public R allCount(@PathVariable String day) {
        staService.allCount(day);
        return R.ok();
    }
    @ApiOperation(value = "图表显示，返回两个部分数据，日期json数组，数量json数据")
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,@PathVariable String begin,@PathVariable String end) {
        Map<String,Object> map = staService.getShowData(type,begin,end);
        return R.ok().data(map);
    }
}
