package com.sprve.eduservice.mapper;
import com.sprve.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
public interface EduVideoMapper extends BaseMapper<EduVideo> {
    Integer countView();
}
