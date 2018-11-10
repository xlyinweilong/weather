package com.yin.weather.monitor.dao;

import com.yin.weather.monitor.entity.TaskEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 任务DAO
 *
 * @author yin.weilong
 * @date 2018.11.07
 */
@Repository
public interface TaskDao extends PagingAndSortingRepository<TaskEntity, Long> {

}
