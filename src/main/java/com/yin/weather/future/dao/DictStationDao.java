package com.yin.weather.future.dao;

import com.yin.weather.future.entity.DictStationEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 城市字典
 *
 * @author yin.weilong
 * @date 2018.11.07
 */
@Repository
public interface DictStationDao extends PagingAndSortingRepository<DictStationEntity, Long> {
}
