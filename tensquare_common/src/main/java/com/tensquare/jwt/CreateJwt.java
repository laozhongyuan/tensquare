package com.tensquare.jwt;

import io.jsonwebtoken.*;

import java.util.Date;

public class CreateJwt {
    public static void main(String[] args) {
       JwtBuilder jwtBuilder = Jwts.builder()
               .setId("666")
               .setSubject("小九")
               .setIssuedAt(new Date())
               .signWith(SignatureAlgorithm.HS256,"itheima");
               System.out.println(jwtBuilder.compact());

    }
}
