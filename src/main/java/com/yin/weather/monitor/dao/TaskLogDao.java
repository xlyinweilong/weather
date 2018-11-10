package com.yin.weather.monitor.dao;

import com.yin.weather.monitor.entity.TaskLogEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 任务日志DAO
 *
 * @author yin.weilong
 * @date 2018.11.07
 */
@Repository
public interface TaskLogDao extends PagingAndSortingRepository<TaskLogEntity, Long> {

}
