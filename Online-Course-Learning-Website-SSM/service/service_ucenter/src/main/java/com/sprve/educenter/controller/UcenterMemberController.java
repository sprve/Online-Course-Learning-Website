package com.sprve.educenter.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sprve.common.utils.JwtUtils;
import com.sprve.common.utils.R;
import com.sprve.common.utils.commentvo.UcenterMemberVo;
import com.sprve.common.utils.ordervo.UcenterMemberOrder;
import com.sprve.educenter.entity.UcenterMember;
import com.sprve.educenter.entity.vo.RegisterVo;
import com.sprve.educenter.entity.vo.ResetPasswordVo;
import com.sprve.educenter.service.UcenterMemberService;
import com.sprve.educenter.utils.MD5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Api(description = "前端登录注册接口")
@RestController
@RequestMapping("/educenter/member")
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;
    @ApiOperation(value = "登录")
    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember member) {
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }
    @ApiOperation(value = "注册")
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }
    @ApiOperation(value = "更新学员密码")
    @PostMapping("reset")
    public R resetUser(@RequestBody ResetPasswordVo resetPassword){
        memberService.reset(resetPassword);
        return R.ok();
    }
    @ApiOperation(value = "根据token获取用户信息")
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }
    @ApiOperation(value = "查询某一天注册人数")
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day) {
        Integer count = memberService.countRegisterDay(day);
        return R.ok().data("countRegister",count);
    }
    @ApiOperation(value = "条件查询带分页学员列表")
    @PostMapping("/pageStudentCondition/{current}/{limit}")
    public R pageStudentCondition(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable Long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @RequestBody(required = false) UcenterMember ucenterMember){
        Page<UcenterMember> memberPage = new Page<>(current,limit);
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(ucenterMember.getMobile())){
            wrapper.like("mobile",ucenterMember.getMobile());
        }
        wrapper.orderByDesc("gmt_create");
        memberService.page(memberPage, wrapper);
        long total = memberPage.getTotal();
        List<UcenterMember> records = memberPage.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }
    @ApiOperation(value = "添加学员信息")
    @PostMapping("/addStudent")
    public R addStudent(@ApiParam(name = "ucenterMember", value = "学员对象", required = true)
                        @RequestBody UcenterMember ucenterMember){
        ucenterMember.setPassword(MD5.encrypt(ucenterMember.getPassword()));
        boolean save = memberService.save(ucenterMember);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @ApiOperation(value = "根据id查询学员")
    @GetMapping("/getStudent/{id}")
    public R getStudent(@ApiParam(name = "id", value = "学员ID", required = true)
                        @PathVariable String id){
        UcenterMember ucenterMember = memberService.getById(id);
        return R.ok().data("data",ucenterMember);
    }
    @ApiOperation(value = "更新学员")
    @PostMapping("/updateStudent")
    public R updateStudent(@ApiParam(name = "ucenterMember", value = "学员对象", required = true)
                           @RequestBody UcenterMember ucenterMember){
        boolean flag = memberService.updateById(ucenterMember);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @ApiOperation(value = "逻辑删除学员")
    @DeleteMapping("/{id}")
    public R removeById(
            @ApiParam(name = "id", value = "学员ID",required = true)
            @PathVariable String id){
        boolean flag = memberService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @ApiOperation(value = "根据用户id获取用户信息")
    @PostMapping("getInfoUc/{id}")
    public UcenterMemberVo getInfoUc(@PathVariable String id) {
        UcenterMember ucenterMember = memberService.getById(id);
        UcenterMemberVo memberVo = new UcenterMemberVo();
        BeanUtils.copyProperties(ucenterMember,memberVo);
        return memberVo;
    }
    @ApiOperation(value = "根据用户id获取用户信息")
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id) {
        UcenterMember member = memberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }
}
