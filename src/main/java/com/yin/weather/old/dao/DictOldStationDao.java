package com.yin.weather.old.dao;

import com.yin.weather.old.entity.DictOldStationEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 地区字典
 *
 * @author yin.weilong
 * @date 2018.11.07
 */
@Repository
public interface DictOldStationDao extends PagingAndSortingRepository<DictOldStationEntity, Long> {

}
