package com.sprve.eduservice.controller;
import com.sprve.common.utils.R;
import com.sprve.eduservice.client.VodClient;
import com.sprve.eduservice.entity.EduVideo;
import com.sprve.eduservice.service.EduVideoService;
import com.sprve.common.base.ExceptionHandler.SprveException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
@Api(description = "小节")
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;
    @Autowired
    private VodClient vodClient;
    @ApiOperation(value = "添加小节")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }
    @ApiOperation(value = "根据小节id查询")
    @GetMapping("getVideoInfo/{id}")
    public R getVideoInfo(@PathVariable String id){
        EduVideo eduVideo = videoService.getById(id);
        return R.ok().data("eduVideo",eduVideo);
    }
    @ApiOperation(value = "修改小节")
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }
    @ApiOperation(value = "删除小节")
    @DeleteMapping("deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)) {
            R result = vodClient.removeAliyVideo(videoSourceId);
            if (result.getCode() == 40000) {
                throw new SprveException(40000,"删除视频失败，熔断器...");
            }
        }
        videoService.removeById(id);
        return R.ok();
    }
    @PostMapping("addVideoCount/{id}")
    public void addVideoCount(@PathVariable("id") String id){
        videoService.videoCount(id);
    }
    @ApiOperation(value = "根据视频id查找课程id")
    @PostMapping("findId/{id}")
    public String getCourseId(@PathVariable("id") String id){
        String videoId=videoService.courseId(id);
        return videoId;
    }
    @ApiOperation(value = "统计观看数")
    @GetMapping("countView")
    public R getCountView(){
        Integer count = videoService.countView();
        return R.ok().data("countView",count);
    }
}
