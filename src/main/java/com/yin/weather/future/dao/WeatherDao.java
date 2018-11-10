package com.yin.weather.future.dao;

import com.yin.weather.future.entity.WeatherEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 天气
 *
 * @author yin.weilong
 * @date 2018.11.07
 */
@Repository
public interface WeatherDao extends PagingAndSortingRepository<WeatherEntity, Long> {
}
