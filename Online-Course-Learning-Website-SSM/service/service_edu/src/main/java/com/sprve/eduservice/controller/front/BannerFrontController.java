package com.sprve.eduservice.controller.front;
import com.sprve.common.utils.R;
import com.sprve.eduservice.entity.EduBanner;
import com.sprve.eduservice.service.EduBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Api(description = "前端轮播图接口")
@RestController
@RequestMapping("/eduservice/bannerfront")
public class BannerFrontController {
    @Autowired
    private EduBannerService bannerService;
    @ApiOperation(value = "查询所有banner")
    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<EduBanner> list = bannerService.selectBanner();
        return R.ok().data("list",list);
    }
}
