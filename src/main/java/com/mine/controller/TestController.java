package com.mine.controller;

import com.mine.dao.UserInfoMapper;
import com.mine.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 杜晓鹏
 * @create 2019-01-04 15:51
 */
@RestController
public class TestController {
    @Autowired
    UserInfoMapper userInfoMapper;

    @RequestMapping("user/{userid}")
    public UserInfo test(@PathVariable Integer userid){

        return userInfoMapper.selectByPrimaryKey(userid);
    }
}
