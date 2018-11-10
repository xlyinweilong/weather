package com.yin.weather.future.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 天气
 *
 * @author yin.weilong
 * @date 2018.11.07
 */
@Entity
@Table(name = "w_weather")
@Getter
@Setter
public class WeatherEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="station_code")
    private String stationCode;

    @Column(name="temp_highest")
    private BigDecimal tempHighest;

    @Column(name="temp_lowest")
    private BigDecimal tempLowest;

    @Column(name="weather")
    private String weather;

    @Column(name="weather_date")
    private Date weatherDate = new Date();
}
