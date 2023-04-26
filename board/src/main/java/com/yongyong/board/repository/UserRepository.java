package com.yongyong.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yongyong.board.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    public boolean existsByEmail(String email);

    public boolean existsByNickname(String Nickname);

    public boolean existsByPhoneNumber(String PhoneNumber);

}
