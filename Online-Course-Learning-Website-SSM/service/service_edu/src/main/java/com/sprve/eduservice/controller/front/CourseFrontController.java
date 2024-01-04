package com.sprve.eduservice.controller.front;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sprve.common.utils.JwtUtils;
import com.sprve.common.utils.R;
import com.sprve.common.utils.ordervo.CourseWebVoOrder;
import com.sprve.eduservice.client.OrdersClient;
import com.sprve.eduservice.entity.EduCourse;
import com.sprve.eduservice.entity.chapter.ChapterVo;
import com.sprve.eduservice.entity.frontVo.CourseFrontVo;
import com.sprve.eduservice.entity.frontVo.CourseWebVo;
import com.sprve.eduservice.service.EduChapterService;
import com.sprve.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
@Api(description = "课程管理前端")
@RestController
@RequestMapping("/eduservice/coursefront")
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private OrdersClient ordersClient;
    @ApiOperation(value = "条件查询带分页的课程")
    @PostMapping("getCourseFrontList/{page}/{limit}")
    public R getCourseFrontList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<>(page, limit);
        Map<String,Object> map = courseService.getCourseFrontList(pageCourse,courseFrontVo);
        return R.ok().data(map);
    }
    @ApiOperation(value = "课程详情方法")
    @GetMapping("/getCourseFrontInfo/{courseId}")
    public R getCourseFrontInfo(@PathVariable String courseId, HttpServletRequest request) {
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        String memberIdToken = JwtUtils.getMemberIdByJwtToken(request);
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);
        if(StringUtils.isEmpty(memberIdToken)){
            if(courseWebVo.getPrice().intValue()==0){
                return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList);
            }
            chapterVideoList = chapterService.check(chapterVideoList);
            return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList);
        }
        boolean buyCourse = ordersClient.isBuyCourse(courseId, memberIdToken);
        if(!buyCourse)
            chapterVideoList = chapterService.check(chapterVideoList);
        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("isBuy",buyCourse);
    }
    @ApiOperation(value = "根据课程id查询课程信息")
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id) {
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }
}
