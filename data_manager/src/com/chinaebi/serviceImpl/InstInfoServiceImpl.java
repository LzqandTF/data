package com.chinaebi.serviceImpl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.com.chinaebi.dz.webservice.DeductChannelOpenOrCloseDocument;
import cn.com.chinaebi.dz.webservice.DeductChannelOpenOrCloseType;
import cn.com.chinaebi.dz.webservice.DeductChannelUpdateRamDocument;
import cn.com.chinaebi.dz.webservice.DeductChannelUpdateRamType;
import cn.com.chinaebi.dz.webservice.impl.OpenOrcloseImpl;

import com.chinaebi.dao.InstInfoDao;
import com.chinaebi.entity.InstInfo;
import com.chinaebi.exception.DeleteException;
import com.chinaebi.exception.InsertException;
import com.chinaebi.exception.PageException;
import com.chinaebi.exception.SelectException;
import com.chinaebi.exception.UpdateException;
import com.chinaebi.service.InstInfoService;
import com.chinaebi.utils.HttpUtil;
import com.chinaebi.utils.RequestUrlConf;
import com.chinaebi.utils.ReturnXmlHandler;
import com.chinaebi.utils.mybaits.Page;

@Service(value ="instInfoService")
public class InstInfoServiceImpl implements InstInfoService {
	private static final Logger log = LoggerFactory.getLogger(InstInfoServiceImpl.class);
	
	@Autowired
	@Qualifier(value = "instInfoDao")
	private InstInfoDao instInfoDao;

	@Override
	public int addInstInfo(InstInfo instInfo) {
		int effectNum = 0;
		try{
			effectNum = instInfoDao.addInstInfo(instInfo);
			if(!(effectNum > 0)){
				throw new InsertException("instInfoDao.addInstInfo(instInfo)  添加机构信息失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public int updateInstInfo(InstInfo instInfo) {
		int effectNum = 0;
		try{
			effectNum = instInfoDao.updateInstInfo(instInfo);
			if(!(effectNum > 0)){
				throw new UpdateException("instInfoDao.updateInstInfo(instInfo)  机构信息修改失败");
			}
			
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return instInfoDao.updateInstInfo(instInfo);
	}
	@Override
	public boolean deleteInstInfo(int instId){
		try{
			int effectNum = instInfoDao.deleteInstInfo(instId);
			if(effectNum >0){
				return true;
			}else{
				throw new DeleteException("instInfoDao.deleteInstInfo(instId)   机构信息删除失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return false;
	}

	@Override
	public Page<InstInfo> queryPageInstInfo(Page<InstInfo> page ,Map<String, Object> map) {
		Page<InstInfo> pageList = null;
		try{
			pageList = instInfoDao.queryPageInstInfo(page,map);
			if(pageList.getResult() ==null){
				throw new PageException("instInfoDao.queryPageInstInfo(page,map)  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return pageList;
	}

	@Override
	public List<InstInfo> queryAll() {
		List<InstInfo> list = null;
		try{
			list =  instInfoDao.queryAll();
			if(list == null){
				throw new SelectException("instInfoDao.queryAll()  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}
	/**
	 * 根据参数的值，禁用或者激活渠道
	 * @param valueOf
	 * @return
	 */
	public boolean updateInstInfoStatus(Map<String,Object> map){
		boolean flag = false;
		try{
			int effectNum = instInfoDao.updateInstInfoStatus(map);
			if(effectNum > 0){
				flag = true;
			}else{
				throw new UpdateException("instInfoDao.lockOrActivateInstInfo(active)  渠道信息禁用或激活失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return flag;
	}
	/**
	 * 根据渠道id查询渠道个数
	 * @param instId
	 * @return
	 */
	public int queryInstInfoById(int instId){
		int effectNum = 0;
		try{
			effectNum = instInfoDao.queryInstInfoById(instId);
			if(effectNum == 0){
				log.info("没有"+instId+"对应的渠道信息");
			}else{
				log.info("存在"+instId+"对应的渠道信息");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}
	/**
	 * 通过渠道ID查询渠道信息
	 * @param instId
	 * @return
	 */
	public InstInfo queryInstInfoByInstId(int instId,int instType){
		InstInfo instInfo = null;
		try{
			Map<String,Integer> map = new HashMap<String,Integer>();
			map.put("inst_id", instId);
			map.put("inst_type", instType);
			instInfo = instInfoDao.queryInstInfoByInstId(map);
			if(instInfo == null){
				throw new SelectException("instInfoDao.queryInstInfoByInstId(instId) 查询结果为null");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return instInfo;
	}
	
	public boolean updateRamDeductChannelInfo(int instId,int instType){
		try{
			DeductChannelUpdateRamDocument document = DeductChannelUpdateRamDocument.Factory.newInstance();
			DeductChannelUpdateRamType type = document.addNewDeductChannelUpdateRam();
			type.setInstId(instId);
			type.setInstType(instType);
			type.setTrace("011");
			log.info("向后台发送更新内存中扣款渠道信息请求");
			String xmlString = HttpUtil.sendPostRequest(RequestUrlConf.url, "xmlString="+document.toString(), "utf-8");
			log.info("后台更新内存中扣款渠道信息请求信息："+xmlString);
			boolean flag = ReturnXmlHandler.xmlStringHandling(xmlString);
			if(flag){
				log.info("更新内存中扣款渠道信息成功");
				return true;
			}else{
				log.info("更新内存中扣款渠道信息失败");
				return false;
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return false;
	}
	
	public boolean handleInstInfoTimingTask(Map<String,Object> map){
		try{
			DeductChannelOpenOrCloseDocument document = DeductChannelOpenOrCloseDocument.Factory.newInstance();
			DeductChannelOpenOrCloseType type = document.addNewDeductChannelOpenOrClose();
			type.setInstId(Integer.valueOf(map.get("instId").toString()));
			type.setInstType(Integer.valueOf(map.get("inst_type").toString()));
			if("0".equals(map.get("active").toString())){
				type.setOpenOrclose(OpenOrcloseImpl.CLOSE);
			}else{
				type.setOpenOrclose(OpenOrcloseImpl.OPEN);
			}
			log.info("向后台发送更新渠道定时任务配置信息请求");
			String xmlString = HttpUtil.sendPostRequest(RequestUrlConf.url, "xmlString="+document.toString(), "utf-8");
			log.info("后台发送更新渠道定时任务配置信息请求信息："+xmlString);
			boolean flag = ReturnXmlHandler.xmlStringHandling(xmlString);
			if(flag){
				log.info("更新渠道定时任务配置信息成功");
				return true;
			}else{
				log.info("更新渠道定时任务配置信息失败");
				return false;
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return false;
	}
	
	@Override
	public int queryInstInfoCountByBankId(String bank_id) {
		int count = 0;
		try {
			count = instInfoDao.queryInstInfoCountByBankId(bank_id);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return count;
	}
	
	@Override
	public int deleteInstInfoByBankId(String bank_id) {
		int effectNum = 0;
		try {
			effectNum = instInfoDao.deleteInstInfoByBankId(bank_id);
			if (effectNum > 0) {
				return effectNum;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return effectNum;
	}
}
