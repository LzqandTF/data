package cn.com.chinaebi.dz.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.MerFundStance;
import cn.com.chinaebi.dz.util.DercStatusConvert;

public class MerFundStanceService {
	private static MerFundStanceService merFundStanceHandleService = null;
	private static Log log = LogFactory.getLog(MerFundStanceService.class);
	
	private cn.com.chinaebi.dz.object.dao.iface.MerFundStanceDAO merFundStanceDAO = cn.com.chinaebi.dz.object.dao.MerFundStanceDAO.getInstance();
	
	public static MerFundStanceService getInstance(){
		if(merFundStanceHandleService == null)
			merFundStanceHandleService = new MerFundStanceService();
		return merFundStanceHandleService;
	}
	
	public Object queryCountAndMoney(String merCode, String startDate, String endDate) {
		Object object = null;
		try {
			object = merFundStanceDAO.queryCountAndMoney(merCode, startDate, endDate);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return object;
	}
	
	public String queryMerFundStanceByMerCode(String merCode, String startDate, String endDate, int startRow, int endRow) {
		StringBuffer buffer = new StringBuffer();
		try {
			List<MerFundStance> list = merFundStanceDAO.queryMerFundStanceByMerCode(merCode, startDate, endDate, startRow, endRow);
			if (list != null && list.size() > 0) {
				int i = 0;
				for (MerFundStance merFundStance : list) {
					//---------mer_code(商户号)
					buffer.append(merFundStance.getMerCode().getId());
					buffer.append(",");
					//---------trade_time(交易日期)
					buffer.append(merFundStance.getTradeTime().toString().substring(0, 10));
					buffer.append(",");
					//---------mer_fee(交易金额)
					buffer.append(String.format("%.2f", merFundStance.getTradeAmount()));
					buffer.append(",");
					//---------change_amount(变动金额)
					buffer.append(String.format("%.2f", merFundStance.getChangeAmount()));
					buffer.append(",");
					//---------account_amount(账户余额)
					buffer.append(String.format("%.2f", merFundStance.getAccountAmount()));
					buffer.append(",");
					//---------derc_status(简单说明)
					buffer.append(DercStatusConvert.getDercStatus(merFundStance.getDercStatus()));
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
