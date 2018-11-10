package com.yin.weather.future.dao;

import com.yin.weather.future.entity.DictProvinceEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 省份字典
 *
 * @author yin.weilong
 * @date 2018.11.07
 */
@Repository
public interface DictProvinceDao extends PagingAndSortingRepository<DictProvinceEntity, Long> {
}
