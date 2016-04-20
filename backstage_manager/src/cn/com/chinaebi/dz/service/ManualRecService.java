package cn.com.chinaebi.dz.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.ManualRec;
import cn.com.chinaebi.dz.util.StringUtils;

public class ManualRecService {
	private static ManualRecService manualRecService = null;
	private static Log log = LogFactory.getLog(ManualRecService.class);
	
	private cn.com.chinaebi.dz.object.dao.iface.ManualRecDAO manualRecDAO = cn.com.chinaebi.dz.object.dao.ManualRecDAO.getInstance();
	
	public static ManualRecService getInstance(){
		if(manualRecService == null)
			manualRecService = new ManualRecService();
		return manualRecService;
	}
	
	public Object queryCountAndMoney(String merCode, String startDate, String endDate) {
		Object object = null;
		try {
			object = manualRecDAO.queryCountAndMoney(merCode, startDate, endDate);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return object;
	}
	
	public String queryManualRecLst(String merCode, String startDate, String endDate, int startRow, int endRow) {
		StringBuffer buffer = new StringBuffer();
		try {
			List<ManualRec> list = manualRecDAO.queryManualRecLst(merCode, startDate, endDate, startRow, endRow);
			if (list != null && list.size() > 0) {
				int i = 0;
				String handlerDate = null;
				String handlerTime = null;
				String auditDate = null;
				String auditTime = null;
				for (ManualRec manualRec : list) {
				    //---------mer_code(商户号)
					buffer.append(manualRec.getMerCode().getId());
					buffer.append(",");
					//---------mer_abbreviation(商户简称)
					buffer.append(manualRec.getMerAbbreviation());
					buffer.append(",");
					//---------handler_time(调账处理时间)
					handlerDate = manualRec.getHandlerTime().toString().substring(0, 10).replace("-", "");
					handlerTime = manualRec.getHandlerTime().toString().substring(11, 19).replace(":", "");
					buffer.append(handlerDate);
					buffer.append(handlerTime);
					buffer.append(",");
					//---------rec_amount(调账金额)
					buffer.append(manualRec.getRecAmount());
					buffer.append(",");
					//---------addorsub(调账类型)
					if (manualRec.getAddorsub() == 1) {
						buffer.append("调增");
					} else {
						buffer.append("调减");
					}
					buffer.append(",");
					//---------data_status(调账状态)
					if (manualRec.getDataStatus() == 1) {
						buffer.append("调账提交");
					} else if (manualRec.getDataStatus() == 2) {
						buffer.append("审核成功");
					} else {
						buffer.append("审核失败");
					}
					buffer.append(",");
					//---------audit_time(调账审核时间)
					if (StringUtils.isNotBlank(manualRec.getAuditTime().toString())) {
						auditDate = manualRec.getAuditTime().toString().substring(0, 10).replace("-", "");
						auditTime = manualRec.getAuditTime().toString().substring(11, 19).replace(":", "");
						buffer.append(auditDate);
						buffer.append(auditTime);
					} else {
						buffer.append("");
					}
					buffer.append(",");
					//---------request_desc(调账原因)
					buffer.append(manualRec.getRequestDesc());
					i++;
					if (i != list.size()) {
						buffer.append(",");
						buffer.append("|");
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return buffer.toString();
	}
}
