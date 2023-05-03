package com.yongyong.firstproject.provider;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
// component 등록으로 autowired 를 통해서 Ioc를 할 수 있다.
public class JwtTokenProvider {
    // jwt 생성 혹은 검증에 사용될 시크릿 키
    // 시크릿 키 같은 데이터는 보안에 유의 해야하기 때문에
    // application.properties 또는 환경변수로 등록해서 사용
    @Value("${jwt.secret-key}") // properties에 값을 불러오기 위함.
    public String SECRET_KEY;

    // jwt를 생성 Method
    public String creat(String subject) {
        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS)); // 만료시간
        // 현재시간 + 1시간

        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .compact();
        // SignatureAlgorithm.HS256 암호화
        // setSubject(subject) 값 지정
        // setIssuedAt(new Date()) 생성시간
        // setExpiration(expiredDate).compact(); 만료시간
        return jwt;
    }

    // 받은 jwt를 증명할 수 있는지에 대해 검증해주는 Method
    public String validate(String jwt) { // jwt 를 받아서
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(jwt).getBody(); // claim으로 바꿔주고 body를 가져옴

        return claims.getSubject();
    }

}