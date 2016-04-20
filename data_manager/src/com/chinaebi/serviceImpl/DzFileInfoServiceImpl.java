package com.chinaebi.serviceImpl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileDocument;
import cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType;
import com.chinaebi.exception.SelectException;

import com.chinaebi.dao.DzFileInfoDao;
import com.chinaebi.entity.DzFileInfo;
import com.chinaebi.service.DzFileInfoService;
import com.chinaebi.utils.HttpUtil;
import com.chinaebi.utils.RequestUrlConf;
import com.chinaebi.utils.ReturnXmlHandler;
import com.chinaebi.utils.mybaits.Page;

@Service(value = "dzFileInfoService")
public class DzFileInfoServiceImpl implements DzFileInfoService{
	private static Logger log = LoggerFactory.getLogger(DzFileInfoServiceImpl.class);
	@Autowired
	@Qualifier(value = "dzFileInfoDao")
	private DzFileInfoDao dzFileInfoDao;
	/**
	 * 分页查询对账文件生成
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<DzFileInfo> queryPageDzFileInfo(Page<DzFileInfo> page,Map<String,Object> map){
		Page<DzFileInfo> page1 = null;
		try{
			page1 = dzFileInfoDao.queryPageDzFileInfo(page, map);
			if(page1 == null){
				throw new SelectException("dzFileInfoDao.queryPageDzFileInfo(page, map)  查询结果为NULL ");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return page1;
	}
	/**
	 * 根据渠道ID 和 交易时间 手动生成对账总表 
	 * @param instId
	 * @param tradeTime
	 * @return
	 */
	public int createDzFile(int instId , String tradeTime,Integer fileType,int object_id){
		try{
			ReductionGenerateDzFileDocument document = ReductionGenerateDzFileDocument.Factory.newInstance();
			ReductionGenerateDzFileType type = document.addNewReductionGenerateDzFile();
			type.setInstId(0);
			type.setGenerateDate(tradeTime);
			type.setTrace("30000000");
			type.setFileType(fileType);
			type.setCustomId(object_id);
			String xmlString = HttpUtil.sendPostRequest(RequestUrlConf.url, "xmlString="+document.toString(), "utf-8");
			boolean flag = ReturnXmlHandler.xmlStringHandling(xmlString);
			if(flag){
				log.info("手动生成对账总表成功");
				return 1;
			}else{
				log.info("手动生成对账总表失败");
				return 0;
			}
		}catch(Exception e){
			log.error(e.getMessage());
			return 0;
		}
	}
	public DzFileInfo queryDzFileInfoById(int id){
		DzFileInfo dzFileInfo = null;
		try{
			dzFileInfo = dzFileInfoDao.queryDzFileInfoById(id);
			if(dzFileInfo == null){
				throw new SelectException("通过"+id+"未查到对应的对账文件信息对象");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return dzFileInfo;
	}
}
