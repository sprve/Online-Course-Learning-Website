package com.sprve.eduservice.entity.subject;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
@ApiModel(value = "Subject添加数据", description = "课程一级分类")
@Data
public class OneSubject {
    private String id;
    private String title;
    private List<TwoSubject> children = new ArrayList<>();
}
