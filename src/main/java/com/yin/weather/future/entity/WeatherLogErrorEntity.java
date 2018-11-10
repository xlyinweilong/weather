package com.yin.weather.future.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 天气收录错误日志
 *
 * @author yin.weilong
 * @date 2018.11.07
 */
@Entity
@Table(name = "w_log_error")
@Getter
@Setter
public class WeatherLogErrorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weather_date")
    private Date weatherDate = new Date();

    @Column(name = "log_id")
    private Long logId;

    @Column(name = "station_code")
    private String stationCode;

}
