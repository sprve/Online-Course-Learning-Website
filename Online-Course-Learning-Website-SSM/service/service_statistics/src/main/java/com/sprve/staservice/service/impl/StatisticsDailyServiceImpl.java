package com.sprve.staservice.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sprve.common.utils.R;
import com.sprve.staservice.client.EduClient;
import com.sprve.staservice.client.UcenterClient;
import com.sprve.staservice.entity.StatisticsDaily;
import com.sprve.staservice.mapper.StatisticsDailyMapper;
import com.sprve.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.*;
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    private UcenterClient ucenterClient;
    @Autowired
    private EduClient eduClient;
    @Override
    public void allCount(String day) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        try{
           date= dateFormat.parse(day);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Calendar cal =Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE,-1);
        String yesterday=dateFormat.format(cal.getTime());
        R registerR = ucenterClient.countRegister(day);
        Integer countRegister = (Integer) registerR.getData().get("countRegister");
        R viewR = eduClient.countView();
        Integer countView = (Integer) viewR.getData().get("countView");
        R buyR =eduClient.countBuy();
        Integer countBuy = (Integer) buyR.getData().get("countBuy");
        QueryWrapper<StatisticsDaily> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("date_calculated",yesterday);
        StatisticsDaily sta;
        sta=baseMapper.selectOne(wrapper2);
        if(sta==null){
            sta=new StatisticsDaily();
            sta.setVideoViewNum(0);
            sta.setBuyNum(0);
        }
        sta.setDateCalculated(day);
        sta.setRegisterNum(countRegister);
        sta.setId(null);
        sta.setVideoViewNum(countView-sta.getVideoViewNum());
        sta.setBuyNum(countBuy-sta.getBuyNum());
        baseMapper.insert(sta);
    }
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);
        List<String> date_calculatedList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();
        for (int i = 0; i < staList.size(); i++) {
            StatisticsDaily daily = staList.get(i);
            date_calculatedList.add(daily.getDateCalculated());
            switch (type) {
                case "register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case "buy_num":
                    numDataList.add(daily.getBuyNum());
                    break;
                default:
                    break;
            }
        }
        Map<String, Object> map =new HashMap<>();
        map.put("date_calculatedList",date_calculatedList);
        map.put("numDataList",numDataList);
        return map;
    }
}
