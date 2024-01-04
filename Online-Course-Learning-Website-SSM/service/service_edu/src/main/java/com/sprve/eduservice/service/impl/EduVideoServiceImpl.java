package com.sprve.eduservice.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sprve.eduservice.client.VodClient;
import com.sprve.eduservice.entity.EduVideo;
import com.sprve.eduservice.mapper.EduVideoMapper;
import com.sprve.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    private VodClient vodClient;
    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        wrapperVideo.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapperVideo);
        List<String> videoIds = new ArrayList<>();
        for (int i = 0; i < eduVideoList.size(); i++) {
            EduVideo eduVideo = eduVideoList.get(i);
            String videoSourceId = eduVideo.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                videoIds.add(videoSourceId);
            }
        }
        if (videoIds.size() >0){
            vodClient.deleteBatch(videoIds);
        }
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
    @Override
    public void videoCount(String id){
        EduVideo eduVideo;
        eduVideo=baseMapper.selectById(id);
        eduVideo.setPlayCount(eduVideo.getPlayCount()+1);
        baseMapper.updateById(eduVideo);
    }
    @Override
    public String courseId(String id){
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("video_source_id",id);
        EduVideo eduVideo = baseMapper.selectOne(wrapper);
        return eduVideo.getCourseId();
    }
    @Override
    public Integer countView(){
       Integer count = baseMapper.countView();
        return count;
    }
}
