package com.sprve.educenter.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sprve.educenter.entity.UcenterMember;
import com.sprve.educenter.entity.vo.RegisterVo;
import com.sprve.educenter.entity.vo.ResetPasswordVo;
public interface UcenterMemberService extends IService<UcenterMember> {
    String login(UcenterMember member);
    void register(RegisterVo registerVo);
    void reset(ResetPasswordVo resetPasswordVo);
    Integer countRegisterDay(String day);
}