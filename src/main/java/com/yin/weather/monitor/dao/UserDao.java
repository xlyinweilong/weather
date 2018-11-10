package com.yin.weather.monitor.dao;

import com.yin.weather.monitor.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户DAO
 *
 * @author yin.weilong
 * @date 2018.11.07
 */
@Repository
public interface UserDao extends PagingAndSortingRepository<UserEntity, Long> {

    /**
     * 根据账号查询
     *
     * @param account
     * @return
     */
    UserEntity findByAccount(String account);
}
