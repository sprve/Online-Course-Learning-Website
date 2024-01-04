package com.sprve.educenter.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sprve.common.base.ExceptionHandler.SprveException;
import com.sprve.common.utils.JwtUtils;
import com.sprve.educenter.entity.UcenterMember;
import com.sprve.educenter.entity.vo.RegisterVo;
import com.sprve.educenter.entity.vo.ResetPasswordVo;
import com.sprve.educenter.mapper.UcenterMemberMapper;
import com.sprve.educenter.service.UcenterMemberService;
import com.sprve.educenter.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public String login(UcenterMember member) {
        String mobile = member.getMobile();
        String password = member.getPassword();
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new SprveException(40000,"登录失败");
        }
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        if(mobileMember == null) {
            throw new SprveException(40000,"登录失败");
        }
        if(!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            throw new SprveException(40000,"密码不正确");
        }
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return jwtToken;
    }
    @Override
    public void reset(ResetPasswordVo resetPasswordVo){
        String id =resetPasswordVo.getId();
        String oldpassword=resetPasswordVo.getOldpassword();
        String newpassword=resetPasswordVo.getNewpassword();
        if (StringUtils.isEmpty(oldpassword) || StringUtils.isEmpty(newpassword)){
            throw new SprveException(40000,"重置失败");
        }
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        UcenterMember idMember = baseMapper.selectOne(wrapper);
        if(!MD5.encrypt(oldpassword).equals(idMember.getPassword())) {
            throw new SprveException(40000,"密码错误");
        }
        idMember.setPassword(MD5.encrypt(newpassword));
        baseMapper.updateById(idMember);
    }
    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)) {
            throw new SprveException(40000,"注册失败");
        }
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)) {
            throw new SprveException(40000,"注册失败");
        }
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0) {
            throw new SprveException(40000,"注册失败");
        }
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setAvatar("https://sprve.oss-cn-beijing.aliyuncs.com/pictures/user.png");
        baseMapper.insert(member);
    }
    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
