package com.online.edu.ucenter.controller;


import com.online.edu.common.R;
import com.online.edu.ucenter.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

}

