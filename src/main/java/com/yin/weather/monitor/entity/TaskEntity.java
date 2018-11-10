package com.yin.weather.monitor.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 任务
 *
 * @author yin.weilong
 * @date 2018.11.09
 */
@Entity
@Table(name = "t_task")
@Getter
@Setter
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "task_finish_date")
    private LocalDate taskFinishDate;

    @Column(name = "task_time")
    private LocalTime taskTime;

    @Column(name = "task_erp_db")
    private String taskErpDb;

    @Column(name = "task_dw_db")
    private String taskDwDb;

    @Column(name = "task_remarks")
    private String taskRemarks;

    @Column(name = "task_last_success_date")
    private LocalDateTime taskLastSuccessDate;
}
