package com.yin.weather.monitor.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 用户
 *
 * @author yin.weilong
 * @date 2018.11.09
 */
@Entity
@Table(name = "u_user")
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "account")
    private String account;

    @Column(name = "passwd")
    private String password;
}
