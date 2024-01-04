package com.sprve.eduservice.entity.subject;
import io.swagger.annotations.ApiModel;
import lombok.Data;
@ApiModel(value = "Subject添加数据", description = "课程二级分类")
@Data
public class TwoSubject {
    private String id;
    private String title;
}
