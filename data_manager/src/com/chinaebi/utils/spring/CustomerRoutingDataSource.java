/*********************************************************************
 * 
 * Copyright (C) 2012, Shanghai Chinaebi
 * All rights reserved.
 * http://www.chinaebi.com.cn/
 * 
 *********************************************************************/
package com.chinaebi.utils.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 自定义的多数据源路由器.
 * 使用方法：先调用CustomerDBContextHolder.setDBType("1")即可完成数据源切换
 * @author dong.rui
 */
public class CustomerRoutingDataSource extends AbstractRoutingDataSource {
    private final transient Logger log = LoggerFactory.getLogger(CustomerRoutingDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        final String dbType = CustomerDBContextHolder.getDataSourceType();
        if (log.isInfoEnabled()) {
            log.info("获取了KEY={}的数据源", dbType);
        }
        return dbType;
    }
}
