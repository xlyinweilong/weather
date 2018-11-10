package com.yin.weather.old.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 地区字典
 *
 * @author yin.weilong
 * @date 2018.11.07
 */
@Entity
@Table(name = "w_dict_old_station")
@Getter
@Setter
public class DictOldStationEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="station")
    private String station;

    @Column(name="city")
    private String city;

    @Column(name="province")
    private String province;

    @Column(name="url")
    private String url;

    @Column(name="station_code")
    private String stationCode;
}
