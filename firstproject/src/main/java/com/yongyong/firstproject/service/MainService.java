package com.yongyong.firstproject.service;

import org.springframework.stereotype.Component;

import com.yongyong.firstproject.provider.UserRole;

@Component
public interface MainService {
    public String hello();

    public String getJwt(String data);

    public UserRole validJwt(String jwt);
}
