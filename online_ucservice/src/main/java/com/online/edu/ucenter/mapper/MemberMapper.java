package com.online.edu.ucenter.mapper;

import com.online.edu.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author Brave
 * @since 2020-02-08
 */
public interface MemberMapper extends BaseMapper<Member> {

    /**
     * 统计某一天里注册的人数
     * @param day
     * @return
     */
    Integer countRegisterNum(String day);
}
