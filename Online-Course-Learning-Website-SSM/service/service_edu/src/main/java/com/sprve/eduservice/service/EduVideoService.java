package com.sprve.eduservice.service;
import com.sprve.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;
public interface EduVideoService extends IService<EduVideo> {
    void removeVideoByCourseId(String courseId);
    void videoCount(String id);
    String courseId(String id);
    Integer countView();
}