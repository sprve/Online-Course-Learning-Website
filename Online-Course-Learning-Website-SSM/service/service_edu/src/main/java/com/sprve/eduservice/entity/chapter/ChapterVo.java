package com.sprve.eduservice.entity.chapter;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
@ApiModel(value = "章节封装类",description = "章节封装类")
@Data
public class ChapterVo {
    private String id;
    private String title;
    private List<VideoVo> children = new ArrayList<>();
}
