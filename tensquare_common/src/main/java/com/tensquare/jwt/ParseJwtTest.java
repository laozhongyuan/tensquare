package com.tensquare.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class ParseJwtTest {
    public static void main(String[] args) {
        Claims claims = Jwts.parser().setSigningKey("itheima")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1NTY0OTk4NDQsInJvbGVzIjoidXNlciIsImV4cCI6MTU1NjUwMzQ0NH0.ok4g5awV2f_oLfwxxpAtWt14TFXFG2xGeXytK4Xf-DQ")
                .getBody();
        System.out.println("用户id:"+claims.getId());
        System.out.println("subject:"+claims.getSubject());
        System.out.println("IssuedAt:"+claims.getIssuedAt());
    }
    }
