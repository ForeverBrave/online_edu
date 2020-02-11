package com.online.edu.ucenter.utils;

import com.online.edu.ucenter.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2019/12/23 12:48
 */
public class JwtUtils {


	public static final String APPSECRET = "online";

	/**
	 * 1 生成jwt令牌的方法
	 * @param member
	 * @return
	 */
	public static String genJsonWebToken(Member member){

	if(member == null
			|| StringUtils.isEmpty(member.getId())
			|| StringUtils.isEmpty(member.getNickname())
			|| StringUtils.isEmpty(member.getAvatar())){
		return null;
	}

		String token = Jwts.builder().setSubject("edu")
				.claim("id", member.getId())
				.claim("nickname", member.getNickname())
				.claim("avatar", member.getAvatar())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				.signWith(SignatureAlgorithm.HS256, APPSECRET).compact();

		return token;
	}

	/**
	 * 2 根据jwt令牌获取里面用户信息
	 * @param token
	 * @return
	 */
	public static Claims checkJwt(String token){
		Claims claims = Jwts.parser().setSigningKey(APPSECRET).parseClaimsJws(token).getBody();
		return claims;
	}

	//TODO 这个方法也是为了测试使用的
	/**
	 * 测试jwt的校验
	 */
	public static void testCheckJwt(){

		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndWxpIiwiaWQiOiI5OTkiLCJuaWNrbmFtZSI6ImphdmF0ZXN0IiwiYXZhdGFyIjoiaHR0cDovL2hhaGFoYS9oYWhhaC5wbmciLCJpYXQiOjE1NTY0MzM1ODUsImV4cCI6MTU1NjQzNTM4NX0.Chs0vqfJ7Y9sZ2PAaizYSpPj-yhNiO-pd5FHG3FTMdM";

		Claims claims = JwtUtils.checkJwt(token);
		String id = (String)claims.get("id");
		String nickname = (String)claims.get("nickname");
		String avatar = (String)claims.get("avatar");
		System.out.println(id);
		System.out.println(nickname);
		System.out.println(avatar);
	}


	//TODO  这个方法为了测试生成jwt使用的
	/**
	 * 测试jwt的生成
	 */
	private static void testGenJsonWebToken(){
		Member member = new Member();
		member.setId("999");
		member.setNickname("javatest");
		member.setAvatar("http://hahaha/hahah.png");

		String token = JwtUtils.genJsonWebToken(member);
		System.out.println(token);
	}

	public static void main(String[] args){
//	    testGenJsonWebToken();

	    testCheckJwt();
	}
}
