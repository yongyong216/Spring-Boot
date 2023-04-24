package com.yongyong.firstproject.service.implement;

import org.springframework.stereotype.Service;

import com.yongyong.firstproject.service.RestApiService;

@Service
public class RestApiServiceImplement implements RestApiService {

    public String getMethod() {
        return "Return to Service Layer";
    }

}
