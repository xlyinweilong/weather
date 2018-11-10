package com.yin.weather.monitor.dao;

import com.yin.weather.monitor.entity.TaskLogDetailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 任务日志详情DAO
 *
 * @author yin.weilong
 * @date 2018.11.07
 */
@Repository
public interface TaskLogDetailDao extends PagingAndSortingRepository<TaskLogDetailEntity, Long> {

    Page<TaskLogDetailEntity> findByTaskLogId(Long taskLogId, Pageable pageable);
}
