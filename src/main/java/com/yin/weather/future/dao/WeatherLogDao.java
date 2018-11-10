package com.yin.weather.future.dao;

import com.yin.weather.future.entity.WeatherLogEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 天气日志
 *
 * @author yin.weilong
 * @date 2018.11.07
 */
@Repository
public interface WeatherLogDao extends PagingAndSortingRepository<WeatherLogEntity, Long> {
}
