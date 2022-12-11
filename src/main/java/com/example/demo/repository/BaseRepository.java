package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface BaseRepository<T> extends PagingAndSortingRepository<T, Long>, JpaSpecificationExecutor<T>, CrudRepository<T, Long> {
}
