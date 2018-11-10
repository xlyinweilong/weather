package com.yin.weather.monitor.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 任务日志详情
 *
 * @author yin.weilong
 * @date 2018.11.09
 */
@Entity
@Table(name = "t_task_log_detail")
@Getter
@Setter
public class TaskLogDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "task_log_id")
    private Long taskLogId;

    @Column(name = "log_detail")
    private String logDetail;

    @Column(name = "startTime")
    private LocalDateTime startTime;

    @Column(name = "endTime")
    private LocalDateTime endTime;

    @Column(name = "logStatus")
    private String logStatus;

    @Column(name = "logCount")
    private Integer logCount;
}
