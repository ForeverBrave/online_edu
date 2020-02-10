package com.online.edu.ucenter.controller;

import com.online.edu.ucenter.utils.ConstantPropertiesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/2/10 14:27
 */
@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WxApiController {

    //生成微信登录扫描的二维码
    //调用微信提供的固定的地址，拼接需要的参数，请求这个地址生成的二维码
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
        String state = "atonline";

        //4、拼接出来最终生成二维码地址
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                state);

        //5、重定向到一个地址
        return "redirect:/" + qrcodeUrl;
    }
}
