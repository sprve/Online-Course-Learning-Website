package com.sprve.eduservice.mapper;
import com.sprve.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
public interface EduTeacherMapper extends BaseMapper<EduTeacher> {
    boolean updateAudit(EduTeacher eduTeacher);
}
