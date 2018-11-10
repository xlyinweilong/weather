package com.yin.weather.monitor.service;

import com.yin.weather.monitor.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务
 *
 * @author yin.weilong
 * @date 2018.11.09
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

}
