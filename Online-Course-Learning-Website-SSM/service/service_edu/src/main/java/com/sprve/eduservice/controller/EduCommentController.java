package com.sprve.eduservice.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sprve.common.utils.JwtUtils;
import com.sprve.common.utils.R;
import com.sprve.common.utils.commentvo.UcenterMemberVo;
import com.sprve.eduservice.client.UcenterClient;
import com.sprve.eduservice.entity.EduComment;
import com.sprve.eduservice.entity.vo.CommentQuery;
import com.sprve.eduservice.service.EduCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Api(description = "评论")
@RestController
@RequestMapping("/eduservice/comment")
public class EduCommentController {
    @Autowired
    private EduCommentService commentService;
    @Autowired
    private UcenterClient ucenterClient;
    @ApiOperation(value = "分页查询所有评论")
    @GetMapping("getCommentList/{page}/{limit}")
    public R getCommentList(@PathVariable long page,@PathVariable long limit,String courseId) {
        Page<EduComment> pageParam = new Page<>(page,limit);
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        commentService.page(pageParam,wrapper);
        List<EduComment> commentList = pageParam.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());
        return R.ok().data(map);
    }
    @ApiOperation(value = "添加评论")
    @PostMapping("saveComment")
    public R saveComment(@RequestBody EduComment comment, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)) {
            return R.error().code(80000).message("请登录");
        }
        comment.setMemberId(memberId);
        UcenterMemberVo ucenterInfo  = ucenterClient.getInfoUc(memberId);
        comment.setNickname(ucenterInfo.getNickname());
        comment.setAvatar(ucenterInfo.getAvatar());
        commentService.save(comment);
        return R.ok();
    }
    @ApiOperation(value = "条件查询带分页课程列表")
    @PostMapping("pageComment/{current}/{limit}")
    public R pageCommentCondition(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable Long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @RequestBody(required = false) CommentQuery commentQuery){
        Page<EduComment> pageComment =new Page<>(current,limit);
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        String courseId = commentQuery.getCourseId();
        String nickName = commentQuery.getNickName();
        if (!org.apache.commons.lang3.StringUtils.isEmpty(courseId)) {
            wrapper.like("course_id",courseId);
        }
        if (!org.apache.commons.lang3.StringUtils.isEmpty(nickName)) {
            wrapper.like("nickname",nickName);
        }
        commentService.page(pageComment,wrapper);
        long total = pageComment.getTotal();
        List<EduComment> commentList = pageComment.getRecords();
        return R.ok().data("total",total).data("items",commentList);
    }
    @ApiOperation(value = "删除评论")
    @PostMapping("removeComment/{id}")
    public R removeComment(@PathVariable String id) {
        commentService.removeById(id);
        return R.ok();
    }
}
