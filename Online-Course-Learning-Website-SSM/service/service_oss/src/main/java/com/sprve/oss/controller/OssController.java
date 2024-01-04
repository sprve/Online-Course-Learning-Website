package com.sprve.oss.controller;
import com.sprve.common.utils.R;
import com.sprve.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@Api(description="阿里云OSS管理")
@RestController
@RequestMapping("/eduoss")
public class OssController {
    @Autowired
    private OssService ossService;
    @ApiOperation(value = "文件上传")
    @PostMapping("/fileoss")
    public R uploadOssFile(@ApiParam(name = "file", value = "文件", required = true)
                           @RequestParam("file") MultipartFile file){
        String url = ossService.uploadFileAvator(file);
        return R.ok().data("url",url);
    }
    @ApiOperation(value = "文件删除")
    @DeleteMapping("/deletefile/{fileUrl}")
    public R deleteOssFile(@PathVariable String fileUrl){
        ossService.deleteFileAvator(fileUrl);
        return R.ok();
    }
}