package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUsername(String username);

    int checkEmail(String email);

    int checkAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    int checkPassword(@Param("password") String password, @Param("userId") Integer userId);

    int checkEmailByUserid(@Param("email") String email, @Param("userId") Integer userId);

    User selectLogin(@Param("username") String username, @Param("password") String password);

    String selectQuestionByUsername(String username);

    int updatePasswordByUsername(@Param("username") String username, @Param("newPassword") String newPassword);

}