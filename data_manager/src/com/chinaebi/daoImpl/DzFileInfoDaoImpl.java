package com.chinaebi.daoImpl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.DzFileInfoDao;
import com.chinaebi.entity.DzFileInfo;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;
@Component(value = "dzFileInfoDao")
public class DzFileInfoDaoImpl extends MyBatisDao implements DzFileInfoDao{
	/**
	 * 分页查询对账文件生成
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<DzFileInfo> queryPageDzFileInfo(Page<DzFileInfo> page,Map<String,Object> map){
		return selectPage(page, "DzFileInfo.queryPageDzFileInfo","DzFileInfo.selectCount", map);
	}
	public DzFileInfo queryDzFileInfoById(int id){
		return (DzFileInfo)getSqlSession().selectOne("DzFileInfo.queryDzFileInfoById",id);
	}
}
