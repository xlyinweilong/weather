package com.yin.weather.old.dao;

import com.yin.weather.old.entity.OldWeatherEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 天气
 *
 * @author yin.weilong
 * @date 2018.11.07
 */
@Repository
public interface OldWeatherDao extends PagingAndSortingRepository<OldWeatherEntity, Long> {

    OldWeatherEntity findByStationCodeAndWeatherDate(String stationCode, Date weatherDate);

    @Query("delete from OldWeatherEntity t where t.stationCode = :stationCode AND t.weatherDate BETWEEN :startDate  AND :endDate")
    int delete4StationCodeAndWeatherDate(@Param("stationCode") String stationCode,@Param("startDate") Date startDate,@Param("endDate") Date endDate);
}
