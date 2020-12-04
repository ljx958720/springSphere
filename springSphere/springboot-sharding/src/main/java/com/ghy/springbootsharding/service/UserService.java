package com.ghy.springbootsharding.service;


import com.ghy.springbootsharding.entity.UserInfo;
import com.ghy.springbootsharding.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    UserInfoMapper userInfoMapper;

    public static Long userId = 1L;

    public void insert() {
        System.out.println("11111111111111111111111111111111111111");
        for (int i = 1; i <= 100; i++) {
            System.out.println("进来");
            UserInfo userInfo = new UserInfo();
            // userInfo.setUserId(userId);
            userInfo.setAccount("account" + i);
            userInfo.setPassword("password" + i);
            userInfo.setUserName("name" + i);
            userId++;
            userInfoMapper.insert(userInfo);
        }
    }

    public UserInfo getUserInfoByUserId(Long id){
        return userInfoMapper.selectByPrimaryKey(id);
    }

    public List<UserInfo> selectByRange(Long firstId, Long lastId){
        return userInfoMapper.selectByRange(firstId, lastId);
    }
}
