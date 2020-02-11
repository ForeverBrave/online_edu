package com.online.edu.ucenter.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.edu.ucenter.entity.Member;
import com.online.edu.ucenter.service.MemberService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;


/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2019/12/23 12:48
 */
public class MemberRealm extends AuthorizingRealm {

    @Autowired
    @Lazy   //懒加载(只有使用的时候才会加载)
    private MemberService memberService;

    @Override
    public String getName(){
        //获取底层类简称
        return this.getClass().getSimpleName();
    }

    /**
     * 做认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //new 查询构建器
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        //登录名为传来的token中的身份信息
        memberQueryWrapper.eq("mobile",token.getPrincipal().toString());
        //根据查询条件查询用户
        Member member = memberService.getOne(memberQueryWrapper);
        if(null!=member){
            //获取盐   转为ByteSource类型
            ByteSource salt = ByteSource.Util.bytes(member.getSalt());
            //因为需要返回AuthenticationInfo类型，所以使用他的实现类SimpleAuthenticationInfo
            //参数说明:1、任意类型（一般传Member、Roles、Permission的包装类） 2、密码 3、盐 4、类名
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(member,member.getPassword(),salt,this.getName());
            return info;
        }
        return null;
    }

    /**
     * 做授权
     * @param principal
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        /*SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        ActiverMember activerMember = (ActiverMember) principal.getPrimaryPrincipal();
        Member member = activerMember.getMember();
        List<String> permissions = activerMember.getPermissions();
        if(member.getType()==Constast.USER_TYPE_SUPER){
            authorizationInfo.addStringPermission("*:*");
        }else {
            if(null!=permissions&&permissions.size()>0){
                authorizationInfo.addStringPermissions(permissions);
            }
        }
        return authorizationInfo;*/
        return null;
    }


}
