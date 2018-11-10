package com.yin.weather.old.dao;

import com.yin.weather.old.entity.OldWeatherEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 天气
 *
 * @author yin.weilong
 * @date 2018.11.07
 */
@Repository
public interface OldWeatherDao extends PagingAndSortingRepository<OldWeatherEntity, Long> {
}
