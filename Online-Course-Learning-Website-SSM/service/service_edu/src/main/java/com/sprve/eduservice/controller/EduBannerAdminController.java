package com.sprve.eduservice.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sprve.common.utils.R;
import com.sprve.eduservice.entity.EduBanner;
import com.sprve.eduservice.service.EduBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@Api(description = "后台轮播图接口")
@RestController
@RequestMapping("/eduservice/banneradmin")
public class EduBannerAdminController {
    @Autowired
    private EduBannerService bannerService;
    @ApiOperation(value = "获取Banner分页列表")
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page,@PathVariable long limit) {
        Page<EduBanner> pageBanner = new Page<>(page,limit);
        bannerService.page(pageBanner,null);
        return R.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }
    @ApiOperation(value = "添加Banner")
    @PostMapping("addBanner")
    public R addBanner(@RequestBody EduBanner EduBanner) {
        bannerService.save(EduBanner);
        return R.ok();
    }
    @ApiOperation(value = "获取banner")
    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        EduBanner banner = bannerService.getById(id);
        return R.ok().data("item",banner);
    }
    @ApiOperation(value = "更新Banner")
    @PostMapping("updateBanner")
    public R updateBanner(@RequestBody EduBanner EduBanner) {
        bannerService.updateById(EduBanner);
        return R.ok();
    }
    @ApiOperation(value = "删除Banner")
    @PostMapping("removeBanner/{id}")
    public R removeBanner(@PathVariable String id) {
        bannerService.removeById(id);
        return R.ok();
    }
}
