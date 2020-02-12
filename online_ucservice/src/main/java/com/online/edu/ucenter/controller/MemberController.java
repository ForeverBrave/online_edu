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
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Brave
 * @since 2020-02-08
 */
@RestController
@RequestMapping("/ucenter/member")
@CrossOrigin
public class MemberController {

    @Autowired
    private MemberService memberService;

    /**
     * 统计某一天里注册的人数
     * @param day
     * @return
     */
    @GetMapping("countRegisterNum/{day}")
    public R countRegisterNum(@PathVariable String day){
        Integer result = memberService.countRegisterNum(day);
        return R.ok().data("registerCount",result);
    }



    /**
     * 根据token信息jwt令牌，获取用户信息并返回
     * @param token
     * @return
     */
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

