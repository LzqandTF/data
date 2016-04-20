package com.chinaebi.serviceImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import cn.com.chinaebi.dz.webservice.ManualSummaryOriginalDocument;
import cn.com.chinaebi.dz.webservice.ManualSummaryOriginalType;

import com.chinaebi.service.DataLstService;
import com.chinaebi.utils.HttpUtil;
import com.chinaebi.utils.RequestUrlConf;
import com.chinaebi.utils.ReturnXmlHandler;

/**
 * 汇总数据ServiceImpl
 * @author admin
 *
 */
@Service(value="dataLstService")
public class DataLstServiceImpl implements DataLstService {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@Override
	public boolean manualSummaryData(int bank_id,int inst_id,String summaryDate,int inst_type){
		String xmlString = "";
		try {
			ManualSummaryOriginalDocument document = ManualSummaryOriginalDocument.Factory.newInstance();
			ManualSummaryOriginalType type = document.addNewManualSummaryOriginal();
			type.setBankGate(bank_id);
			type.setInstId(inst_id);
			type.setInstType(inst_type);
			type.setSummaryDate(summaryDate);
			type.setTrace("0099");
			xmlString = HttpUtil.sendPostRequest(RequestUrlConf.url, "xmlString="+document.toString(), "utf-8");
			boolean flag = ReturnXmlHandler.xmlStringHandling(xmlString);
			if(flag){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			log.error(e);
		}
		return false;
	}
}

