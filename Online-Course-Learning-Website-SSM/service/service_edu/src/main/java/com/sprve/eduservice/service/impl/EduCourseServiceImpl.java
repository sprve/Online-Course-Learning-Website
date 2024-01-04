package com.sprve.eduservice.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sprve.eduservice.entity.EduCourse;
import com.sprve.eduservice.entity.EduCourseDescription;
import com.sprve.eduservice.entity.frontVo.CourseFrontVo;
import com.sprve.eduservice.entity.frontVo.CourseWebVo;
import com.sprve.eduservice.entity.vo.CourseInfoVo;
import com.sprve.eduservice.entity.vo.CoursePublishVo;
import com.sprve.eduservice.mapper.EduCourseMapper;
import com.sprve.eduservice.service.EduChapterService;
import com.sprve.eduservice.service.EduCourseDescriptionService;
import com.sprve.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sprve.eduservice.service.EduVideoService;
import com.sprve.common.base.ExceptionHandler.SprveException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    @Autowired
    private EduVideoService videoService;
    @Autowired
    private EduChapterService chapterService;
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse =new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            throw new SprveException(40000,"添加课程信息失败");
        }
        String cid = eduCourse.getId();
        EduCourseDescription courseDescription =new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);
        return cid;
    }
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo =new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update ==0) {
            throw new SprveException(40000,"修改课程信息失败");
        }
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(description);
    }
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo publicCourseInfo = baseMapper.getPublicCourseInfo(id);
        return publicCourseInfo;
    }
    @Override
    public void removeCourse(String courseId) {
        videoService.removeVideoByCourseId(courseId);
        chapterService.removeChapterByCourseId(courseId);
        courseDescriptionService.removeById(courseId);
        int result = baseMapper.deleteById(courseId);
        if (result == 0){
            throw new SprveException(40000,"删除失败");
        }
    }
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("status","Normal");
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(pageParam,wrapper);
        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();
        Map<String,Object> map = new HashMap<>();
        map.put("items",records);
        map.put("pages",pages);
        map.put("current",current);
        map.put("size",size);
        map.put("total",total);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);
        return map;
    }
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
    @Override
    public void courseAction(String courseId,String action){
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("id",courseId);
        EduCourse eduCourse=baseMapper.selectOne(wrapper);
        if(action.equals("buy")){
            eduCourse.setBuyCount(eduCourse.getBuyCount()+1);
            baseMapper.updateById(eduCourse);
        }
        else if (action.equals("view")){
            eduCourse.setViewCount(eduCourse.getViewCount()+1);
            baseMapper.updateById(eduCourse);
        }
    }
    @Override
    public Integer countBuy(){
        Integer count = baseMapper.countBuy();
        return count;
    }
}
