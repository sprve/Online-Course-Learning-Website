package com.sprve.serurity.filter;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprve.common.utils.R;
import com.sprve.common.utils.ResponseUtil;
import com.sprve.common.utils.ResultCode;
import com.sprve.serurity.entity.SecurityUser;
import com.sprve.serurity.entity.User;
import com.sprve.serurity.exception.CustomerAuthenticationException;
import com.sprve.serurity.security.TokenManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;
    public TokenLoginFilter(AuthenticationManager authenticationManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/acl/login","POST"));
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(req.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        SecurityUser user = (SecurityUser) auth.getPrincipal();
        String token = tokenManager.createToken(user.getCurrentUserInfo().getUsername());
        redisTemplate.opsForValue().set(user.getCurrentUserInfo().getUsername(), user.getPermissionValueList(),1800000, TimeUnit.SECONDS);
        System.out.println("token = " + token);
        ResponseUtil.out(res, R.ok().data("token", token));
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        ServletOutputStream outputStream = response.getOutputStream();
        String message = null;
        int code = ResultCode.ERROR;
        if (exception instanceof AccountExpiredException) {
            message = "账户过期，登录失败！";
        } else if (exception instanceof BadCredentialsException) {
            message = "用户名或密码错误，登录失败！";
        } else if (exception instanceof CredentialsExpiredException) {
            message = "密码过期，登录失败！";
        } else if (exception instanceof DisabledException) {
            message = "账户被禁用，登录失败！";
        } else if (exception instanceof LockedException) {
            message = "账户被锁，登录失败！";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            message = "账户不存在，登录失败！";
        }else if(exception instanceof CustomerAuthenticationException){
            message = exception.getMessage();
            code = ResultCode.FORBIDDEN;
        } else {
            message = "登录失败！";
        }
        String result = JSON.toJSONString(R.error().code(code).message(message));
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
