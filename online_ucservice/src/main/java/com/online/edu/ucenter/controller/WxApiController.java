package com.online.edu.ucenter.controller;

import com.google.gson.Gson;
import com.online.edu.ucenter.entity.Member;
import com.online.edu.ucenter.service.MemberService;
import com.online.edu.ucenter.utils.ConstantPropertiesUtil;
import com.online.edu.ucenter.utils.HttpClientUtils;
import com.online.edu.ucenter.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/2/12 19:04
 */
@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WxApiController {

    @Autowired
    private MemberService memberService;

    //扫描二维码进行回调的方法
    @GetMapping("callback")
    public String callback(String code, String state) {
        System.out.println("code: "+code);
        System.out.println("state: "+state);
        //1 获取扫描之后票据  code

        //2 使用返回票据请求地址获取 凭证（为了获取微信扫描人的信息）
        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        //拼接出来请求地址
        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code
        );

        //发送httpclient请求地址，获取凭证
        String result = null;
        try {
            result = HttpClientUtils.get(accessTokenUrl);
            System.out.println("result:"+result);
        }catch(Exception e) {

        }

        //把返回result使用Gson进行json数据转换
        Gson gson = new Gson();
        HashMap map = gson.fromJson(result, HashMap.class);
        //通过转换之后map集合，根据map里面的key获取value值
        String access_token = (String)map.get("access_token");
        String openid = (String)map.get("openid");

        //根据openid判断数据库表是否已经存在当前扫描微信用户
        Member member = memberService.existWxUser(openid);
        if(member == null) {//表没有扫描微信数据，获取用户信息，添加到数据库里面
            //根据凭证和微信id 获取扫描人的信息（用户名称，头像等）
            //拼接获取微信用户信息的地址
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl,access_token,openid);

            //发送httpclient请求
            String userInfo = null;
            try {
                userInfo = HttpClientUtils.get(userInfoUrl);
            }catch(Exception e) {
            }

            //转换json
            HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
            String nickname = (String)userInfoMap.get("nickname");//微信昵称
            String headimgurl = (String)userInfoMap.get("headimgurl");//微信头像

            System.out.println("nickname: "+nickname);
            System.out.println("headimgurl: "+headimgurl);

            //把微信信息添加到数据库里面
            member = new Member();
            member.setOpenid(openid);
            member.setNickname(nickname);
            member.setAvatar(headimgurl);
            memberService.save(member);
        }

        //根据member对象生成jwt令牌
        String token = JwtUtils.genJsonWebToken(member);

        //重定向到前台页面中
        return "redirect:http://localhost:3000?token="+token;
    }

    //生成微信登录扫描的二维码
    //调用微信提供的固定的地址，拼接需要的参数，请求这个地址生成二维码
    @GetMapping("login")
    public String genQrConnect(HttpSession session) {

        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 回调地址
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (Exception e) {

        }

        //TODO 为了测试：这个值传递是注册内网穿透的域名名称，实现域名跳转
        //为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        String state = "itonline";

        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                state);

        return "redirect:" + qrcodeUrl;
    }
}
