package com.online.edu.ucenter.controller;

import com.google.gson.Gson;
import com.online.edu.ucenter.utils.ConstantPropertiesUtil;
import com.online.edu.ucenter.utils.HttpClientUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/2/10 14:27
 */
@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WxApiController {

    /**
     * 扫描二维码进行回调的方法
     * @param code
     * @param state
     * @return
     */
    @GetMapping("callback")
    public String callback(String code,String state){
        System.out.println("code:"+code);
        System.out.println("state:"+state);
        //1、获取扫描之后票据

        //2、使用返回票据请求地址获取凭证（为了获取微信扫描人的信息）

        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        System.out.println("ConstantPropertiesUtil.WX_OPEN_APP_SECRET="+ConstantPropertiesUtil.WX_OPEN_APP_SECRET);
        //拼接地址
        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code);

        //发送httpclient请求地址，获取凭证
        String result = null;
        try {
            result = HttpClientUtils.get(accessTokenUrl);
            System.out.println("result="+result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //把返回result使用gson进行json数据转换
        Gson gson = new Gson();
        HashMap map = gson.fromJson(result, HashMap.class);
        String access_token = (String)map.get("access_token");
        String openid = (String)map.get("openid");

        //访问微信的资源服务器，获取用户信息
        String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                "?access_token=%s" +
                "&openid=%s";
        //拼接地址
        String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);

        //发送httppclient请求
        String userInfo = null;
        try {
            userInfo = HttpClientUtils.get(userInfoUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //解析json
        HashMap mapUserInfo = gson.fromJson(userInfo, HashMap.class);
        //微信昵称
        String nickname = (String)mapUserInfo.get("nickname");
        //微信头像
        String headimgurl = (String)mapUserInfo.get("headimgurl");

        System.out.println("nickname="+nickname);
        System.out.println("headimgurl="+headimgurl);

        return null;
    }

    /**
     * 生成微信登录扫描的二维码
     * 调用微信提供的固定的地址，拼接需要的参数，请求这个地址生成的二维码
     * @param session
     * @return
     */
    @GetMapping("login")
    public String genQrConnect(HttpSession session) {

        // 1、定义微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //2、获取扫描二维码之后回调的地址(扫描之后，调用controller中的方法)
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;

        //3、对回调地址进行url编码
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //传递注册内网穿透的域名名称，实现域名跳转
        String state = "itonline";

        //4、拼接出来最终生成二维码地址
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                state);

        //5、重定向到一个地址
        return "redirect:" + qrcodeUrl;
    }
}
