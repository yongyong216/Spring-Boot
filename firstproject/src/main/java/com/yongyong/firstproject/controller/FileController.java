package com.yongyong.firstproject.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yongyong.firstproject.common.constant.RequestPattern;
import com.yongyong.firstproject.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(RequestPattern.FILE_API) // 패키지 만들어서 하나로 관리 할 수 있는게 더 편하다!
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    private final String UPLOAD_URL = "upload";

    private final String GET_URL = "{fileName}";

    // 파일 업로드(우리는 이미지로만 할 꺼임!)
    @PostMapping(UPLOAD_URL)
    public String upload(
            @RequestParam("file") MultipartFile file) {
        return fileService.upload(file);
    }

    // 파일 불러오기
    @GetMapping(value = GET_URL, produces = { MediaType.ALL_VALUE }) // ALL_VALUE로 안뜨면
                                                                     // MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE로
                                                                     // 설정
    public Resource getFile(
            @PathVariable("fileName") String fileName) {

        return fileService.getFile(fileName);
    }

}
