package com.ghy.springbootsharding.mapper;


import com.ghy.springbootsharding.entity.Config;

public interface ConfigMapper {

    int insert(Config config);
    Config selectByPrimaryKey(Integer configId);
    int updateByPrimaryKey(Config record);
}