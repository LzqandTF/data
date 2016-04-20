package cn.com.chinaebi.dz.object.dao;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import cn.com.chinaebi.dz.object.ChannelTradeCollect;
import cn.com.chinaebi.dz.object.Hlog;
import cn.com.chinaebi.dz.object.base.BaseChannelTradeCollectDAO;
import cn.com.chinaebi.dz.util.DYDataUtil;


public class ChannelTradeCollectDAO extends BaseChannelTradeCollectDAO implements cn.com.chinaebi.dz.object.dao.iface.ChannelTradeCollectDAO {
	
	private Log logger = LogFactory.getLog(getClass());

	public ChannelTradeCollectDAO () {}
	
	public ChannelTradeCollectDAO (Session session) {
		super(session);
	}
	

	@Override
	public List<ChannelTradeCollect> getHistoryChannelTradeCollectPageData(
			Map<String, String[]> map) throws Exception {
		List<ChannelTradeCollect> list = null;
		Session session = null;
		String inst_type = "";
		try{
			
			inst_type = map.get("query_type")[0];
			
			session = this.getSession();
			StringBuffer sb = new StringBuffer("");
			sb.append("SELECT * FROM channel_trade_collect WHERE ");
			
			if("1".equals(inst_type)){
				sb.append(" sys_date between ");
				sb.append(map.get("start_date")[0]);
				sb.append(" and ");
				sb.append(map.get("end_date")[0]);
			}else if("0".equals(inst_type)){
				sb.append(" substring(deduct_sys_time,1,8) between ");
				sb.append(map.get("start_date")[0]);
				sb.append(" and ");
				sb.append(map.get("end_date")[0]);
			}else{
				logger.debug("渠道类型"+inst_type+",参数不匹配");
				return null;
			}
			
			sb.append(" and settle_code = ");
			sb.append(map.get("mid")[0]);
			sb.append(" and instType = ");
			sb.append(map.get("query_type")[0]);
			
			for(Map.Entry<String,String[]> entry:map.entrySet()){
				if("pageNo".equals(entry.getKey()) || "pageNum".equals(entry.getKey()) || "version".equals(entry.getKey()) 
						|| "tranCode".equals(entry.getKey()) || "query_type".equals(entry.getKey()) || "merPriv".equals(entry.getKey())
						|| "start_date".equals(entry.getKey()) || "end_date".equals(entry.getKey()) || "mid".equals(entry.getKey())){
					continue;
				}else{
					sb.append(" and ");
					sb.append(entry.getKey());
					sb.append(" = ");
					sb.append(entry.getValue()[0]);
				}
			}
			
			sb.append(" order by trade_time desc limit ");
			
			sb.append((Integer.valueOf(map.get("pageNo")[0])-1)*(Integer.valueOf(map.get("pageNum")[0])));
			sb.append(",");
			sb.append(map.get("pageNum")[0]);
			
			SQLQuery query = session.createSQLQuery(sb.toString()).addEntity(ChannelTradeCollect.class);
			list = query.list();
		}catch(Exception e){
			logger.error(e);
			throw e;
		}
		return list;
	}

	@Override
	public List<ChannelTradeCollect> getHistoryChannelTradeCollectDataList(
			Map<String, String[]> map) throws Exception {
		List<ChannelTradeCollect> list = null;
		Session session = null;
		String inst_type = "";
		try{
			session = this.getSession();
			
			inst_type = map.get("query_type")[0];
			
			StringBuffer sb = new StringBuffer("");
			sb.append("SELECT * FROM channel_trade_collect WHERE ");
			
			if("1".equals(inst_type)){
				sb.append(" sys_date between ");
				sb.append(map.get("start_date")[0]);
				sb.append(" and ");
				sb.append(map.get("end_date")[0]);
			}else if("0".equals(inst_type)){
				sb.append(" substring(deduct_sys_time,1,8) between ");
				sb.append(map.get("start_date")[0]);
				sb.append(" and ");
				sb.append(map.get("end_date")[0]);
			}else{
				logger.debug("渠道类型"+inst_type+",参数不匹配");
				return null;
			}
			
			sb.append(" and settle_code = ");
			sb.append(map.get("mid")[0]);
			sb.append(" and instType = ");
			sb.append(map.get("query_type")[0]);
			
			for(Map.Entry<String,String[]> entry:map.entrySet()){
				if("version".equals(entry.getKey()) || "tranCode".equals(entry.getKey()) || "query_type".equals(entry.getKey()) 
						|| "merPriv".equals(entry.getKey()) || "start_date".equals(entry.getKey()) 
						|| "end_date".equals(entry.getKey()) || "mid".equals(entry.getKey())){
					continue;
				}else{
					logger.info("查询交易数据参数------"+entry.getKey()+"------"+entry.getValue()[0]);
					sb.append(" and ");
					sb.append(entry.getKey());
					sb.append(" = ");
					sb.append(entry.getValue()[0]);
				}
			}
			
			sb.append(" order by trade_time desc ");
			
			SQLQuery query = session.createSQLQuery(sb.toString()).addEntity(ChannelTradeCollect.class);
			list = query.list();
		}catch(Exception e){
			logger.error(e);
			throw e;
		}
		return list;
	}
	
	@Override
	public List<ChannelTradeCollect> getTodayChannelTradeCollectPageData(
			Map<String, String[]> map) throws Exception {
		List<ChannelTradeCollect> list = null;
		Session session = null;
		String inst_type = "";
		try{
			session = this.getSession();
			
			Calendar calendar = Calendar.getInstance();//系统当前时间
    		
			String deduct_stlm_date = DYDataUtil.getSimpleDateFormat("yyyyMMdd").format(calendar.getTime());
			
			inst_type = map.get("query_type")[0];
			
			StringBuffer sb = new StringBuffer("");
			sb.append("SELECT * FROM channel_trade_collect WHERE ");
			
			if("1".equals(inst_type)){
				sb.append(" sys_date = ");
				sb.append(deduct_stlm_date);
			}else if("0".equals(inst_type)){
				sb.append(" substring(deduct_sys_time,1,8) = ");
				sb.append(deduct_stlm_date);
			}else{
				logger.debug("渠道类型"+inst_type+",参数不匹配");
				return null;
			}
			
			sb.append(" and settle_code = ");
			sb.append(map.get("mid")[0]);
			sb.append(" and instType = ");
			sb.append(map.get("query_type")[0]);
			
			for(Map.Entry<String,String[]> entry:map.entrySet()){
				if("pageNo".equals(entry.getKey()) || "pageNum".equals(entry.getKey()) || "version".equals(entry.getKey()) 
						|| "tranCode".equals(entry.getKey()) || "query_type".equals(entry.getKey()) || "merPriv".equals(entry.getKey())
						|| "start_date".equals(entry.getKey()) || "end_date".equals(entry.getKey()) || "mid".equals(entry.getKey())){
					continue;
				}else{
					sb.append(" and ");
					sb.append(entry.getKey());
					sb.append(" = ");
					sb.append(entry.getValue()[0]);
				}
			}
			
			sb.append(" order by trade_time desc limit ");
			
			sb.append((Integer.valueOf(map.get("pageNo")[0])-1)*(Integer.valueOf(map.get("pageNum")[0])));
			sb.append(",");
			sb.append(map.get("pageNum")[0]);
			
			SQLQuery query = session.createSQLQuery(sb.toString()).addEntity(ChannelTradeCollect.class);
			list = query.list();
		}catch(Exception e){
			logger.error(e);
			throw e;
		}
		return list;
	}

	@Override
	public List<ChannelTradeCollect> getTodayChannelTradeCollectDataList(
			Map<String, String[]> map) throws Exception {
		List<ChannelTradeCollect> list = null;
		Session session = null;
		String inst_type = ""; 
		try{
			session = this.getSession();
			
			Calendar calendar = Calendar.getInstance();//系统当前时间
    		
			String deduct_stlm_date = DYDataUtil.getSimpleDateFormat("yyyyMMdd").format(calendar.getTime());
			
			inst_type = map.get("query_type")[0];
			
			StringBuffer sb = new StringBuffer("");
			sb.append("SELECT * FROM channel_trade_collect WHERE ");
			
			if("1".equals(inst_type)){
				sb.append(" sys_date = ");
				sb.append(deduct_stlm_date);
			}else if("0".equals(inst_type)){
				sb.append(" substring(deduct_sys_time,1,8) = ");
				sb.append(deduct_stlm_date);
			}else{
				logger.debug("渠道类型"+inst_type+",参数不匹配");
				return null;
			}
			sb.append(" and settle_code = ");
			sb.append(map.get("mid")[0]);
			sb.append(" and instType ");
			sb.append(map.get("query_type")[0]);
			
			for(Map.Entry<String,String[]> entry:map.entrySet()){
				if("version".equals(entry.getKey()) || "tranCode".equals(entry.getKey()) || "query_type".equals(entry.getKey()) 
						|| "merPriv".equals(entry.getKey()) || "start_date".equals(entry.getKey()) 
						|| "end_date".equals(entry.getKey()) || "mid".equals(entry.getKey())){
					continue;
				}else{
					logger.info("查询交易数据参数------"+entry.getKey()+"------"+entry.getValue()[0]);
					sb.append(" and ");
					sb.append(entry.getKey());
					sb.append(" = ");
					sb.append(entry.getValue()[0]);
				}
			}
			
			sb.append(" order by trade_time desc ");
			
			SQLQuery query = session.createSQLQuery(sb.toString()).addEntity(ChannelTradeCollect.class);
			list = query.list();
		}catch(Exception e){
			logger.error(e);
			throw e;
		}
		return list;
	}


}