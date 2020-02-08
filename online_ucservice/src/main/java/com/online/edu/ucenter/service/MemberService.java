package com.online.edu.ucenter.service;

import com.online.edu.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Brave
 * @since 2020-02-08
 */
public interface MemberService extends IService<Member> {

    /**
     * 统计某一天里注册的人数
     * @param day
     * @return
     */
    Integer countRegisterNum(String day);
}
