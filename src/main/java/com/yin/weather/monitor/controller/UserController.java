package com.yin.weather.monitor.controller;

import com.yin.weather.monitor.common.BaseMessage;
import com.yin.weather.monitor.dao.UserDao;
import com.yin.weather.monitor.entity.UserEntity;
import com.yin.weather.monitor.vo.in.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 用户控制器
 *
 * @author yin.weilong
 * @date 2018.11.09
 */
@RestController()
@RequestMapping(value = "/user", produces = "application/json")
public class UserController {

    @Autowired
    private UserDao userDao;

    /**
     * 登录
     *
     * @param loginVo
     * @param session
     * @return
     */
    @PostMapping(value = "/login", headers = "Content-Type=application/json")
    public BaseMessage login(@RequestBody LoginVo loginVo, HttpSession session) {
        UserEntity user = userDao.findByAccount(loginVo.getUsername());
        if (user == null) {
            return BaseMessage.error("账号错误");
        }
        if (!user.getPassword().equals(loginVo.getPassword())) {
            return BaseMessage.error("密码错误");
        }
        session.setAttribute("user", user);
        return BaseMessage.success("登录成功", session.getId());
    }

    /**
     * 获取用户权限等信息
     *
     * @param session
     * @return
     */
    @GetMapping(value = "/info")
    public BaseMessage info(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        return BaseMessage.success(user);
    }

    /**
     * 登出
     *
     * @param session
     * @return
     */
    @PostMapping(value = "/logout")
    public BaseMessage logout(HttpSession session) {
        session.removeAttribute("user");
        return BaseMessage.success();
    }
}
