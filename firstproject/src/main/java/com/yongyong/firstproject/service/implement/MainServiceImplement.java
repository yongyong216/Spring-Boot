package com.yongyong.firstproject.service.implement;

import org.springframework.stereotype.Service;

import com.yongyong.firstproject.provider.JwtTokenProvider;
import com.yongyong.firstproject.provider.UserRole;
import com.yongyong.firstproject.service.MainService;

@Service
public class MainServiceImplement implements MainService {

    private JwtTokenProvider jwtTokenProvider;

    public MainServiceImplement(JwtTokenProvider jwtoJwtTokenProvider) {
        this.jwtTokenProvider = jwtoJwtTokenProvider;
    }

    @Override
    public String hello() {
        return "Hello kjy";
    }

    @Override
    public String getJwt(String data) {
        String jwt = jwtTokenProvider.creat(data);
        return jwt;
    }

    @Override
    public UserRole validJwt(String jwt) {
        UserRole subject = jwtTokenProvider.validate(jwt);
        return subject;
    }

}