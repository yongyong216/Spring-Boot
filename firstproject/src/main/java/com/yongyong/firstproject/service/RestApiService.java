package com.yongyong.firstproject.service;

public interface RestApiService {
    // 요구사항에 대한 기능 이름을 선언한것
    public String getMethod();

    public String postMethod();

    public String patchMethod();

    public String deleteMethod();
}
