package com.yin.weather.future.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 省份字典
 *
 * @author yin.weilong
 * @date 2018.11.07
 */
@Entity
@Table(name = "dict_province")
@Getter
@Setter
public class DictProvinceEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="code")
    private String code;

    @Column(name="name")
    private String name;

}
