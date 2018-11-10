package com.yin.weather.future.dao;

import com.yin.weather.future.entity.WeatherLogErrorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 天气错误日志
 *
 * @author yin.weilong
 * @date 2018.11.07
 */
@Repository
public interface WeatherLogErrorDao extends PagingAndSortingRepository<WeatherLogErrorEntity, Long> {

    Page<WeatherLogErrorEntity> findByLogId(Long logId, Pageable pageable);
}
