/*********************************************************************
 * 
 * Copyright (C) 2012, Shanghai Chinaebi
 * All rights reserved.
 * http://www.chinaebi.com.cn/
 * 
 *********************************************************************/
package com.chinaebi.utils.spring;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

/**
 * 自定义的上下文储存器
 * @author dong.rui
 */
public class CustomerDBContextHolder {

    //线程安全的ThreadLocal  
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setDataSourceType(String dbType) {
        Assert.notNull(dbType, "DBType cannot be null");
        contextHolder.set(dbType);
    }

    public static String getDataSourceType() {
        String str = (String) contextHolder.get();
        if (StringUtils.isBlank(str))
            str = DataSourceInstances.WRITE_DATASOURCE;
        return str;
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }

}
