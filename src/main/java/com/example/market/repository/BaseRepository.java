package com.example.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Author:ZhuQing
 * Date:2017/11/14 15:35
 */
@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T,String>,JpaSpecificationExecutor<T> {
}
