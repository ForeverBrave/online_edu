package com.online.edu.ucenter.controller;

import cn.hutool.core.util.IdUtil;
import com.online.edu.common.R;
import com.online.edu.ucenter.entity.Member;
import com.online.edu.ucenter.entity.dto.LoginDto;
import com.online.edu.ucenter.service.MemberService;
import com.online.edu.ucenter.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/2/11 14:23
 */
@Controller
@RequestMapping("/ucenter/member")
@CrossOrigin
public class UserController {

    @Autowired
    private MemberService memberService;

    /**
     * 用户注册
     * @param member
     * @return
     */
    @ResponseBody
    @PostMapping("registerUser")
    public R registerUser(@RequestBody Member member){
        try {
            //加盐
            String salt = IdUtil.simpleUUID().toUpperCase();
            member.setSalt(salt);
            //加盐加密并散列2次
            member.setPassword(new Md5Hash(member.getPassword(),salt,2).toString());
            //设置是否逻辑删除，true为已删除   false为未删除
            member.setIsDeleted(false);
            //设置是否禁用，true为已禁用   false为未禁用
            member.setIsDisabled(false);
            this.memberService.save(member);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 根据电话、密码登录
     * @param loginDto
     * @return
     */
    @PostMapping("login")
    public String loginUser(@RequestBody LoginDto loginDto){
        //创建一个subject
        Subject subject = SecurityUtils.getSubject();
        //将用户名和密码进行封装
        AuthenticationToken token = new UsernamePasswordToken(loginDto.getMobile(), loginDto.getPassword());

        Member member = null;
        try {
            //调用login方法
            subject.login(token);
            //得到用户对象
            member = (Member) subject.getPrincipal();
            //将用户对象封装进token中
            String userToken = JwtUtils.genJsonWebToken(member);
            //因为端口号不同存在跨域问题，cookie不能跨域，所以这里使用url重写

            return "redirect:http://localhost:3000?token=" + userToken;

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据token信息jwt令牌，获取用户信息并返回
     * @param token
     * @return
     */
    @ResponseBody
    @GetMapping("userInfo/{token}")
    public R getUserInfoToken(@PathVariable String token){
        //调用工具类获取用户信息
        Claims claims = JwtUtils.checkJwt(token);
        String nickname = (String)claims.get("nickname");
        String avatar = (String)claims.get("avatar");
        String id = (String)claims.get("id");

        Member member = new Member();
        member.setId(id);
        member.setAvatar(avatar);
        member.setNickname(nickname);

        return R.ok().data("member",member);
    }

}
