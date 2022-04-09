package com.example.lab3_behind.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.lab3_behind.vo.JwtUserData;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;


public class JwtUtil {

    private static long passTime = 1000*60*60*1;//一小时后过期
    private static final String signature = "husivhjsdkvnksdjvnsdhvwehe88*&^%";

    /*
    * 生成token
    * */
    public static String createToken(JwtUserData jwtUserData){
        JWTCreator.Builder jwtBuilder = JWT.create();
        jwtBuilder.withClaim("stu_number",jwtUserData.getStu_number());
        jwtBuilder.withClaim("role",jwtUserData.getRole());

        Calendar calendarInstance = Calendar.getInstance();
        jwtBuilder.withExpiresAt(new Date(calendarInstance.getTimeInMillis()+passTime));
        return jwtBuilder.sign(Algorithm.HMAC256(signature)).toString();
    }

    public static void verify(String token){
        JWT.require(Algorithm.HMAC256(signature)).build().verify(token);
    }

    public static JwtUserData getToken(String token){
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(signature)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            // e.printStackTrace();
            // token 校验失败, 抛出Token验证非法异常
        }
        JwtUserData userData=new JwtUserData();
        userData.setStu_number(jwt.getClaims().get("stu_number").toString());
        userData.setRole(jwt.getClaims().get("role").toString());
        return userData;
    }

}
