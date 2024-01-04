package com.sprve.eduservice.controller;
import com.sprve.common.utils.R;
import com.sprve.eduservice.entity.EduHtml;
import com.sprve.eduservice.service.EduHtmlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@Api(description = "Html")
@RestController
@RequestMapping("/eduservice/Html")
public class EduHtmlController {
    @Autowired
    private EduHtmlService eduHtmlService;
    @ApiOperation(value = "保存Html")
    @PostMapping("saveHtml")
    public R saveHtml(@RequestBody EduHtml eduHtml){
        eduHtml.setHtmlPage(StringEscapeUtils.unescapeHtml(eduHtml.getHtmlPage()));
        eduHtmlService.updateById(eduHtml);
        return R.ok();
    }
    @ApiOperation(value = "根据Htmlid查询")
    @GetMapping("getHtmlInfo/{id}")
    public R getHtmlInfo(@PathVariable String id){
        EduHtml eduHtml = eduHtmlService.getById(id);
        return R.ok().data("eduHtml",eduHtml);
    }
}