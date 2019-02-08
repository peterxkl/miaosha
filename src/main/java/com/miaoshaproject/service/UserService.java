package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.UserModel;


/**
 * telphone为用户手机号，
 * password为用户加密的密码
 */
public interface UserService {
    //通过用户id对象的方法;
    UserModel getUserById(Integer id);

    void register(UserModel userModel) throws BusinessException;

    UserModel validateLoing(String telphone,String encrptPassword) throws BusinessException;
}
