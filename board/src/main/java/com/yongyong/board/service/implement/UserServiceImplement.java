package com.yongyong.board.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yongyong.board.dto.request.user.PostUserRequestDto;
import com.yongyong.board.dto.response.ResponseDto;
import com.yongyong.board.entity.UserEntity;
import com.yongyong.board.repository.UserRepository;
import com.yongyong.board.service.UserService;

@Service
public class UserServiceImplement implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImplement(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<ResponseDto> postUser(PostUserRequestDto dto) {
        // TODO 적어놓고 할일 적고 하나씩 하는게 좋음
        // 우선 반환할 것을 만들어줌

        ResponseDto responseBody = null;

        String email = dto.getUserEmail();
        String nickName = dto.getUserNickname();
        String phoneNumber = dto.getUserPhoneNumber();

        try {
            // TODO : 이메일 중복 반환
            // 이메일 중복 체크를 하려면 유저테이블에서 해당 테이블에 값존재확인하고 찾아와야함
            // 데이터 접근을 위해 Repository 사용해야함 .
            boolean hasEmail = userRepository.existsByEmail(email); // 존재하면 true로 반환됨
            if (hasEmail) {
                responseBody = new ResponseDto("EU", "Existent User Email");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
            }
            // TODO : 닉네임 중복
            boolean hasNickname = userRepository.existsByNickname(nickName);
            if (hasNickname) {
                responseBody = new ResponseDto("EN", "Existent User Nickname");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
            }

            // TODO : 휴대폰번호 중복
            boolean hasPhoneNumber = userRepository.existsByPhoneNumber(phoneNumber);
            if (hasPhoneNumber) {
                responseBody = new ResponseDto("ET", "Existent User PhoneNumber");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
            }
            // 다됐으면 삽입하고 저장하고 반영해야함
            // 유저 respository를 생성해줄것이다. 여기다가 하면 너무 길어짐 그래서 UserEntity에서 작업해줌
            // 유저 레코드 삽입
            UserEntity userEntity = new UserEntity(dto);
            userRepository.save(userEntity);
            responseBody = new ResponseDto("SU", "Success");

        } catch (Exception exception) {
            // TODO : 데이터베이스 오류
            exception.printStackTrace();
            responseBody = new ResponseDto("DE", "Database Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }

        // TODO : 성공 반환

        return ResponseEntity.status(HttpStatus.OK).body(null);

    }
}