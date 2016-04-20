package com.chinaebi.utils.mybaits;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.util.ReflectionUtils;

/**
 * Mybatis分页查询工具类,为分页查询增加传递:
 * startRow,endRow : 用于oracle分页使用,从1开始
 * offset,limit : 用于mysql 分页使用,从0开始
 * 
 * @author badqiu
 *
 */
@SuppressWarnings("unchecked")
public class MyBatisDao extends SqlSessionDaoSupport {

    public <T> Page<T> selectPage(Page<T> page, String statementName, Object parameter) {
        String countStatementName = statementName + ".count";
        return selectPage(page, statementName, countStatementName, parameter);
    }

    /**
     * 分页
     * @param page
     * @param statementName 查询集合名称
     * @param countStatementName 统计名称
     * @param parameter
     * @return
     */
    @SuppressWarnings("rawtypes")
    public <T> Page<T> selectPage(Page<T> page, String listStatementName, String countStatementName, Object parameter) {

        Number totalItems = (Number) getSqlSession().selectOne(countStatementName, toParameterMap(parameter));

        if (totalItems != null) {
            int totalCount = totalItems.intValue();
            page.setTotalItems(totalCount);
            Map parameterMap = toParameterMap(parameter, page);
            List list = getSqlSession().selectList(listStatementName, parameterMap);
            page.setResult(list);
            page.setReturnMap(parameterMap);
        }
        return page;
    }

    @SuppressWarnings("rawtypes")
    protected static Map toParameterMap(Object parameter, Page p) {
        Map map = toParameterMap(parameter);
        map.put("startRow", p.getStartRow() - 1);
        map.put("endRow", p.getEndRow());
        map.put("offset", p.getOffset());
        map.put("limit", p.getPageSize());
        return map;
    }

    @SuppressWarnings("rawtypes")
    protected static Map toParameterMap(Object parameter) {
        if (parameter instanceof Map) {
            return (Map) parameter;
        } else {
            try {
                return PropertyUtils.describe(parameter);
            } catch (Exception e) {
                ReflectionUtils.handleReflectionException(e);
                return null;
            }
        }
    }
}
