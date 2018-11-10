package com.yin.weather.monitor.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 任务日志
 *
 * @author yin.weilong
 * @date 2018.11.09
 */
@Entity
@Table(name = "t_task_log")
@Getter
@Setter
public class TaskLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_start_time")
    private LocalDateTime taskStartTime;

    @Column(name = "task_end_time")
    private LocalDateTime taskEndTime;

    @Column(name = "task_id")
    private Long taskId;

    @JoinColumn(name = "task_id", updatable = false, insertable = false)
    @ManyToOne
    private TaskEntity task;

    @Column(name = "task_status")
    private String taskStatus;

    @Column(name = "task_log")
    private String taskLog;
}
