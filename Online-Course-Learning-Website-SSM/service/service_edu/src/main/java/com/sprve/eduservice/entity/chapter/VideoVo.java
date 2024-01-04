package com.sprve.eduservice.entity.chapter;
import io.swagger.annotations.ApiModel;
import lombok.Data;
@ApiModel(value = "小节封装类",description = "小节封装类")
@Data
public class VideoVo {
    private String id;
    private String title;
    private String videoSourceId;
}
