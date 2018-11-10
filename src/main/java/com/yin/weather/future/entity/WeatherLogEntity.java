package com.yin.weather.future.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 天气收录日志
 *
 * @author yin.weilong
 * @date 2018.11.07
 */
@Entity
@Table(name = "w_log")
@Getter
@Setter
public class WeatherLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weather_date")
    private Date weatherDate = new Date();

    @Column(name = "start_time")
    private Date startTime = new Date();

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "station_count")
    private Long stationCount;

    @Column(name = "success_count")
    private Integer successCount;

    @Column(name = "error_count")
    private Integer errorCount;
}
