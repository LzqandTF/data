package cn.com.chinaebi.dz.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.ChannelDzCollect;
import cn.com.chinaebi.dz.util.Ryt_trade_type;

public class ChannelDzCollectService {
	private static ChannelDzCollectService channelDzCollectService = null;
	private static Log log = LogFactory.getLog(ChannelDzCollectService.class);
	
	private cn.com.chinaebi.dz.object.dao.iface.ChannelDzCollectDAO channelDzCollectDAO = cn.com.chinaebi.dz.object.dao.ChannelDzCollectDAO.getInstance();
	
	public static ChannelDzCollectService getInstance(){
		if(channelDzCollectService == null)
			channelDzCollectService = new ChannelDzCollectService();
		return channelDzCollectService;
	}
	
	public Object queryCountAndMoney(String merCode, String startDate, String endDate) {
		Object object = null;
		try {
			object = channelDzCollectDAO.queryCountAndMoney(merCode, startDate, endDate);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return object;
	}
	
	public String queryManualRecLst(String merCode, String startDate, String endDate, int startRow, int endRow) {
		StringBuffer buffer = new StringBuffer();
		try {
			List<ChannelDzCollect> list = channelDzCollectDAO.queryChannelDzCollectLst(merCode, startDate, endDate, startRow, endRow);
			if (list != null && list.size() > 0) {
				int i = 0;
				for (ChannelDzCollect channelDzCollect : list) {
				    //---------mid(商户号)
					buffer.append(channelDzCollect.getReqMerCode());
					buffer.append(",");
					//---------sys_date(交易时间)
					buffer.append(channelDzCollect.getTradeTime().toString().substring(0, 8));
					buffer.append(",");
					//---------oid(商户订单号)
					buffer.append(channelDzCollect.getAdditionalResponseData() == null ? "" : channelDzCollect.getAdditionalResponseData());
					buffer.append(",");
					//---------mdate(商户日期)
					buffer.append(channelDzCollect.getTradeTime().toString().substring(0, 8));
					buffer.append(",");
					//---------type(交易类型)
					buffer.append(Ryt_trade_type.getRytTradeName(channelDzCollect.getTradeType()));
					buffer.append(",");
					//---------amount(支付金额)
					buffer.append(String.format("%.2f", channelDzCollect.getTradeAmount()));
					buffer.append(",");
					//---------mer_fee(商户手续费)
					buffer.append(String.format("%.2f", channelDzCollect.getMerFee()));
					buffer.append(",");
					//---------tseq(电银流水)
					buffer.append(channelDzCollect.getReqSysStance());
					buffer.append(",");
					buffer.append(channelDzCollect.getTradeTime().toString().substring(0, 8));
					buffer.append(",");
					//---------gid(渠道ID)
					buffer.append(channelDzCollect.getDeductSysId());
					buffer.append(",");
					//---------gate(网关号)
					buffer.append(channelDzCollect.getGate());
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
