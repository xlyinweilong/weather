package com.yin.weather.future.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 城市字典
 *
 * @author yin.weilong
 * @date 2018.11.07
 */
@Entity
@Table(name = "dict_city")
@Getter
@Setter
public class DictCityEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="code")
    private String code;

    @Column(name="name")
    private String name;

    @Column(name="province_code")
    private String provinceCode;

    @Column(name="province_name")
    private String provinceName;
}
