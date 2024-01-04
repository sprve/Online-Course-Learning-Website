package com.sprve.ai.controller;
import com.sprve.common.utils.JwtUtils;
import com.sprve.common.utils.R;
import com.sprve.ai.service.AiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
@Api(description = "智能客服")
@RestController
@RequestMapping("/eduai/ai")
public class AiController {
    @Autowired
    private AiService aiService;
    @ApiOperation(value = "根据角色和对话回答问题")
    @GetMapping("question/{role}/{question}")
    public R question(@PathVariable String role, @PathVariable String question, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)) {
            return R.error().code(80000).message("请登录");
        }
        String answer;
        System.out.println(role+":"+question);
        answer=aiService.getMessage(role,question);
        System.out.println(answer);
        return R.ok().data("answer",answer);
    }
}
