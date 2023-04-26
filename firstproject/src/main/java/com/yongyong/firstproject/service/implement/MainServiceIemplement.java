package com.yongyong.firstproject.service.implement;

import org.springframework.stereotype.Component;

import com.yongyong.firstproject.service.MainService;

@Component
public class MainServiceIemplement implements MainService {

    @Override
    public String hello() {
        return "Hello";
    }

}
