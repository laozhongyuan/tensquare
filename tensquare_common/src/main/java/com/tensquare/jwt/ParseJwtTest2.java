package com.tensquare.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseJwtTest2 {
    public static void main(String[] args) {
        String
                compactJws="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1NTY0NDM0OTMsImV4cCI6MTU1NjQ0MzU1Mywi5aeT5ZCNIjoi5Yqz5b-g5rqQIn0.RZO5Wo8k9Kqbqe40sdXLa5a04PzosR8MhzO3wZ_lNGc";
        Claims claims =Jwts.parser().setSigningKey("itcast").parseClaimsJws(compactJws).getBody( );
        System.out.println("id:"+claims.getId());
        System.out.println("subject:"+claims.getSubject());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy‐MM‐dd hh:mm:ss");
        System.out.println("签发时间:"+sdf.format(claims.getIssuedAt()));
        System.out.println("过期时间:"+sdf.format(claims.getExpiration()));
        System.out.println("当前时间:"+sdf.format(new Date()) );
        System.out.println("姓名："+claims.get("姓名"));
    }
    }
