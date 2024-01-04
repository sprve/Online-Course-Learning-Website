package com.sprve.eduservice.controller;
import com.sprve.common.utils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
@Api(description = "后台登录")
@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {
    @PostMapping("/login")
    public R login() {
        return R.ok().data("token","admin");
    }
    @GetMapping("/info")
    public R info() {
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
