package cn.com.chinaebi.dz.object.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.Hlog;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.InstInfoPK;
import cn.com.chinaebi.dz.object.MerBasic;
import cn.com.chinaebi.dz.object.base.BaseHlogDAO;
import cn.com.chinaebi.dz.util.DYDataUtil;
import cn.com.chinaebi.dz.util.DesEncrypto;
import cn.com.chinaebi.dz.util.PoundageCalculate;
import cn.com.chinaebi.dz.util.StringPingJie;


public class HlogDAO extends BaseHlogDAO implements cn.com.chinaebi.dz.object.dao.iface.HlogDAO {

	public HlogDAO () {}
	
	public HlogDAO (Session session) {
		super(session);
	}
	
	private Log logger = LogFactory.getLog(getClass());
	
	/**
	 * 通过交易时间，删除线上的交易数据
	 * @param tradeTime
	 * @return
	 */
	public boolean deleteOnlineData(String tradeTime,String tableName){
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		try{
			session = this.getSession();
			if(session != null){
				if(StringUtils.isNotBlank(tradeTime)){
					tradeTime = tradeTime.replaceAll("-", "");
				}
				transaction = session.beginTransaction();
				StringBuffer sql = new StringBuffer("");
				sql.append("select count(*) from ");
				sql.append(tableName);
				sql.append(" where sys_date = ?");
				SQLQuery query = session.createSQLQuery(sql.toString());
				query.setParameter(0, tradeTime);
				Integer count = Integer.valueOf(query.uniqueResult().toString());
				if(count == 0){
					logger.info("不存在交易日期为"+tradeTime+"的融易通数据，不需要删除");
					flag = true;
				}else{
					StringBuffer sql_ = new StringBuffer("");
					sql_.append("delete from ");
					sql_.append(tableName);
					sql_.append(" where sys_date = ?");
					SQLQuery delete = session.createSQLQuery(sql_.toString());
					delete.setParameter(0, tradeTime);
					int d_count = delete.executeUpdate();
					if(d_count ==0){
						logger.info("删除交易日期为"+tradeTime+"的融易通数据失败");
					}else{
						flag = true;
						transaction.commit();
					}
				}
			}
		}catch(Exception e){
			logger.error(e);
			transaction.rollback();
		}finally{
			closeSession(session);
		}
		return flag;
	}
	
	
	public int[] saveRytData(List<String> sqlList,Connection conn){
		Statement stmt = null;
		int[] counts = null;
		try{
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for(int index = 0;index<sqlList.size();index++){
				if(sqlList.get(index) != null && !"".equals(sqlList.get(index))){
					stmt.addBatch(sqlList.get(index));
				}
			}
			counts = stmt.executeBatch();
		}catch(Exception e){
			logger.error(e);
		}
		return counts;
	}
	
	/**
	 * 
	 * 
	 * "insert ignore into hlog(tseq,version,ip, mdate,mid,bid,oid,amount,pay_amt,type,gate,sys_date,init_sys_date," +
									"sys_time,batch,fee_amt, bank_fee,tstat,bk_flag,org_seq,ref_seq,refund_amt,mer_priv," +
									"bk_send,bk_recv,bk_url,fg_url,bk_chk,bk_date,bk_seq1,bk_seq2,bk_resp,mobile_no,trans_period,card_no,error_code,author_type,phone_no," +
									"oper_id,gid,pre_amt,bk_fee_model,pre_amt1,error_msg,p1,p2,p3,p4,p5,p6,p7," +
									"p8,p9,p10,p11,p12,is_liq,is_notice,data_source,againPay_status,againPay_date,currency,exchange_rate,p13,p14,p15) " +
									"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
	 	添加商户手续费计算保存
	 */
	@SuppressWarnings("unchecked")
	public int getRytHlog(String tradeTime,int deduct_sys_id,String columnName,String properties_gid){
		logger.info("开始同步时间为 "+tradeTime+", 渠道ID "+deduct_sys_id+" 的融易付收款交易数据");
		Session ryt_session = null;
		Session duiz_session = null;
		Connection duiz_conn = null;
		int count = 0;
		try {
			if(StringUtils.isNotBlank(tradeTime)){
				tradeTime = tradeTime.replaceAll("-", "");
			}
			ryt_session = this.getSession("ryt_hibernate.xml");
			duiz_session = this.getSession();
			if(ryt_session  != null && duiz_session != null){
				StringBuffer buffer = new StringBuffer();
//				boolean flag = false;
//				if(StringUtils.isNotBlank(properties_gid)){
					Integer gid_int = Integer.valueOf(properties_gid);
//					if(deduct_sys_id == gid_int){
						buffer.append("select a.*,o.out_user_id,o.in_user_id,o.bind_mid from (select h.tseq,h.version, h.mdate,h.mid,h.bid,h.oid,h.amount,h.pay_amt,h.type,h.gate,h.sys_date,h.init_sys_date,");
						buffer.append("h.sys_time,h.batch,h.fee_amt, h.bank_fee,h.tstat,h.bk_flag,h.org_seq,h.ref_seq,h.refund_amt,h.mer_priv,");
						buffer.append("h.bk_send,h.bk_recv,h.bk_chk,h.bk_date,h.bk_seq1,h.bk_seq2,h.bk_resp,h.mobile_no,h.trans_period,h.card_no,h.error_code,h.author_type,h.phone_no,");
						buffer.append("h.oper_id,h.gid,h.pre_amt,h.bk_fee_model,h.pre_amt1,h.error_msg,h.p1,h.p2,h.p3,h.p4,h.p5,h.p6,h.p7,");
						buffer.append("h.p8,h.p9,h.p10,h.p11,h.p12,h.is_liq,h.is_notice,h.data_source,h.againPay_status,h.againPay_date,h.currency,h.exchange_rate,h.p13,h.p14,h.p15 from hlog h where ");
						buffer.append(" h.");
						buffer.append(columnName);
						buffer.append(" = ? and h.gid = ?) a left join order_ext o on a.tseq = o.tseq");
//						flag = true;
//					}else{
//						buffer.append("select h.tseq,h.version, h.mdate,h.mid,h.bid,h.oid,h.amount,h.pay_amt,h.type,h.gate,h.sys_date,h.init_sys_date,");
//						buffer.append("h.sys_time,h.batch,h.fee_amt, h.bank_fee,h.tstat,h.bk_flag,h.org_seq,h.ref_seq,h.refund_amt,h.mer_priv,");
//						buffer.append("h.bk_send,h.bk_recv,h.bk_chk,h.bk_date,h.bk_seq1,h.bk_seq2,h.bk_resp,h.mobile_no,h.trans_period,h.card_no,h.error_code,h.author_type,h.phone_no,");
//						buffer.append("h.oper_id,h.gid,h.pre_amt,h.bk_fee_model,h.pre_amt1,h.error_msg,h.p1,h.p2,h.p3,h.p4,h.p5,h.p6,h.p7,");
//						buffer.append("h.p8,h.p9,h.p10,h.p11,h.p12,h.is_liq,h.is_notice,h.data_source,h.againPay_status,h.againPay_date,h.currency,h.exchange_rate,h.p13,h.p14,h.p15 from hlog h where ");
//						buffer.append(" h.");
//						buffer.append(columnName);
//						buffer.append(" = ? and h.gid = ?");
//					}
//				}
				SQLQuery rytSQLQuery = ryt_session.createSQLQuery(buffer.toString());
				rytSQLQuery.setInteger(0, Integer.valueOf(tradeTime));
				rytSQLQuery.setInteger(1, deduct_sys_id);
				List<Object> list = rytSQLQuery.list();
				if(list != null){
					if(list.size() > 0){
						Double mer_fee = 0d; //商户手续费
						duiz_conn = duiz_session.connection();
						duiz_conn.setAutoCommit(false);
						buffer.setLength(0);
						buffer.append("insert ignore into hlog(tseq,version, mdate,mid,bid,oid,amount,pay_amt,type,gate,sys_date,init_sys_date,");
						buffer.append("sys_time,batch,fee_amt, bank_fee,tstat,bk_flag,org_seq,ref_seq,refund_amt,mer_priv,");
						buffer.append("bk_send,bk_recv,bk_chk,bk_date,bk_seq1,bk_seq2,bk_resp,mobile_no,trans_period,card_no,error_code,author_type,phone_no,");
						buffer.append("oper_id,gid,pre_amt,bk_fee_model,pre_amt1,error_msg,p1,p2,p3,p4,p5,p6,p7,");
						buffer.append("p8,p9,p10,p11,p12,is_liq,is_notice,data_source,againPay_status,againPay_date,currency,exchange_rate,p13,p14,p15,mer_fee,out_user_id,in_user_id,bind_mid) ");
						buffer.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						PreparedStatement statement = duiz_conn.prepareStatement(buffer.toString());
						for (Object object : list) {
							Object[] rytArr = (Object[])object;
							mer_fee = Double.valueOf(rytArr[14].toString())/100;
//							SQLQuery dyuzSqlQuery = duiz_session.createSQLQuery(buffer.toString());
							statement.setObject(1, rytArr[0]);
							statement.setObject(2, rytArr[1]);
							statement.setObject(3, rytArr[2]);
							statement.setObject(4, rytArr[3]);
							statement.setObject(5, rytArr[4]);
							statement.setObject(6, rytArr[5]);
							statement.setObject(7, rytArr[6]);
							statement.setObject(8, rytArr[7]);
							if(rytArr[36] != null && Integer.valueOf(rytArr[36].toString()).equals(gid_int)){//gid
								statement.setObject(9, 30);//账户支付
							}else{
								statement.setObject(9, rytArr[8]);
							}
						
							statement.setObject(10, rytArr[9]);
							statement.setObject(11,rytArr[10]);
							statement.setObject(12,rytArr[11]);
							statement.setObject(13,rytArr[12]);
							statement.setObject(14,rytArr[13]);
							statement.setObject(15,rytArr[14]);
							statement.setObject(16,rytArr[15]);
							statement.setObject(17,rytArr[16]);
							statement.setObject(18,rytArr[17]);
							statement.setObject(19,rytArr[18]);
							statement.setObject(20,rytArr[19]);
							statement.setObject(21,rytArr[20]);
							statement.setObject(22,rytArr[21]);
							statement.setObject(23,rytArr[22]);
							statement.setObject(24,rytArr[23]);
							statement.setObject(25,rytArr[24]);
							statement.setObject(26,rytArr[25]);
							statement.setObject(27,rytArr[26]);
							statement.setObject(28,rytArr[27]);
							statement.setObject(29,rytArr[28]);
							statement.setObject(30,rytArr[29]);
							statement.setObject(31,rytArr[30]);
							statement.setObject(32,rytArr[31]);
							statement.setObject(33,rytArr[32]);
							
							if (rytArr[33] != null && !"null".equals(rytArr[33]) && !"".equals(rytArr[33])) {
								String cardNo = rytArr[33].toString();
								// 使用正则表达式验证卡号
								Pattern pattern = Pattern.compile("[0-9]*");
								Matcher isNum = pattern.matcher(cardNo);
								if (!isNum.matches()) {
									String str = DesEncrypto.decrypt(cardNo, "iFv5x6Cu");
									statement.setObject(34,str);
								} else {
									statement.setObject(34,cardNo);
								}
							} else {
								statement.setObject(34,rytArr[33]);
							}
							
							statement.setObject(35,rytArr[34]);
							statement.setObject(36,rytArr[35]);
							statement.setObject(37,rytArr[36]);
							statement.setObject(38,rytArr[37]);
							statement.setObject(39,rytArr[38]);
							statement.setObject(40,rytArr[39]);
							statement.setObject(41,rytArr[40]);
							statement.setObject(42,rytArr[41]);
							statement.setObject(43,rytArr[42]);
							statement.setObject(44,rytArr[43]);
							statement.setObject(45,rytArr[44]);
							statement.setObject(46,rytArr[45]);
							statement.setObject(47,rytArr[46]);
							statement.setObject(48,rytArr[47]);
							statement.setObject(49,rytArr[48]);
							statement.setObject(50,rytArr[49]);
							statement.setObject(51,rytArr[50]);
							statement.setObject(52,rytArr[51]);
							statement.setObject(53,rytArr[52]);
							statement.setObject(54,rytArr[53]);
							statement.setObject(55,rytArr[54]);
							statement.setObject(56,rytArr[55]);
							statement.setObject(57,rytArr[56]);
							statement.setObject(58,rytArr[57]);
							statement.setObject(59,rytArr[58]);
							statement.setObject(60,rytArr[59]);
							statement.setObject(61,rytArr[60]);
							statement.setObject(62,rytArr[61]);
							statement.setObject(63,rytArr[62]);
							statement.setObject(64,mer_fee);
							statement.setObject(65,rytArr[63]);
							statement.setObject(66,rytArr[64]);
							statement.setObject(67,rytArr[65]);
							count++;
							statement.addBatch();
							if(count % 100 == 0){
								statement.executeBatch();
							}
						}
						statement.executeBatch();
						duiz_conn.commit();
					}
					logger.warn("融易通线上收款交易数据获取条数为 :"+count);
				}
			}
		} catch (Exception e) {
			try {
				if(duiz_conn != null)
					duiz_conn.rollback();
			} catch (SQLException e1) {
				logger.error("回滚异常:"+e1);
			}
			logger.error("融易通线上收款交易数据同步异常:"+e);
		}finally{
			try {
				if(duiz_conn != null)
				duiz_conn.close();
			} catch (SQLException e) {
				logger.error("duiz_conn关闭异常"+e);
			}
			closeSession(duiz_session);
			closeSession(ryt_session);
		}
		return count;
	}
			
	 public int dataCollectHanler(String tradeTime,String tableName,int deduct_sys_id,int bank_id){
		Session session = null;
		int count = -1;
		try {
			session = this.getSession();
			if (session != null) {
				if (StringUtils.isNotBlank(tradeTime)) {
					tradeTime = tradeTime.replaceAll("-", "");
				}
				CallableStatement cs = (CallableStatement) session.connection().prepareCall("call proce_original_ryt_hlog(?,?,?,?,?)");
				cs.setObject(1, tradeTime);
				cs.setObject(2, tableName);
				cs.setInt(3, deduct_sys_id);
				cs.setInt(4, bank_id);
				cs.registerOutParameter(5, Types.INTEGER);
				cs.execute();
				count = cs.getInt(5) <= 0 ? 0 : cs.getInt(5);
			} else {
				logger.error("RytUpmpDAO.dataCollectHanler() 获取session为空");
			}
		} catch (Exception e) {
			logger.error("融易通线上交易数据汇总抛出异常:" + e);
		} finally {
			closeSession(session);
		}
		return count;
	}
	
	@Override
	public boolean rytDispenseWithHandle(String tradeTime,String rytTab,int handle_type,int deduct_sys_id){
		Session session = null;
		boolean flag = false;
		try {
			logger.info("清算日期："+tradeTime);
			logger.info("收款表名："+rytTab);
			logger.info("处理类型："+handle_type);
			session = this.getSession();
			if(session != null){
				if(StringUtils.isNotBlank(tradeTime)){
					tradeTime = tradeTime.replaceAll("-", "");
					CallableStatement cs = (CallableStatement) session.connection().prepareCall("call proce_ryt_dispense_with_handle(?,?,?,?)");
					cs.setInt(1, Integer.valueOf(tradeTime));
					cs.setObject(2, rytTab);
					cs.setInt(3, handle_type);
					cs.setInt(4, deduct_sys_id);
					cs.execute();
					flag = true;
				}
			}else{
				logger.error("HlogDAO.rytDispenseWithHandle() 获取session为空");
			}
		} catch (Exception e) {
			logger.error("HlogDAO.rytDispenseWithHandle():"+e);
		}finally{
			closeSession(session);
		}
		return flag;
	}
	/**
	 * 汇总线上退款交易数据
	 * @param tradeTime 交易时间
	 * @param tableName 交易数据表名称
	 * @param deduct_sys_id	渠道ID
	 * @return
	 */
	public int refundDataCollectHanler(String tradeTime,String tableName,int deduct_sys_id){
		Session session = null;
		int count = -1;
		try {
			session = this.getSession();
			if(session  != null){
				if(StringUtils.isNotBlank(tradeTime)){
					tradeTime = tradeTime.replaceAll("-", "");
				}
				CallableStatement cs = (CallableStatement) session.connection().prepareCall("call proce_original_ryt_refund_log(?,?,?,?)");
				cs.setObject(1, tradeTime);
				cs.setObject(2, tableName);
				cs.setInt(3, deduct_sys_id);
				cs.registerOutParameter(4, Types.INTEGER);
				cs.execute();
				count = cs.getInt(4) <= 0 ? 0 : cs.getInt(4);
			}else{
				logger.error("RytRefundLogDAO.dataCollectHanler() 获取session为空");
			}
		} catch (Exception e) {
			logger.error("融易通线上退货交易数据汇总抛出异常:"+e);
		}finally{
			closeSession(session);
		}
		return count;
	}
	
	/**
	 * 获取线上对账成功交易数据总条数
	 * @param date
	 * @param codeArr
	 * @param generateNumber
	 * @return
	 */
	public int getTotalCountOfDzSucessDataOfOnLine(String date,List<String> codeArr,int generateNumber,boolean whethercreatefilebyrange){
		Session session = null;
		Integer totalCount = 0;
		try{
			session = this.getSession();
			
			if(StringUtils.isNotBlank(date)){
				date = date.replaceAll("-", "");
			}
			
			StringBuffer sql = new StringBuffer("");
			sql.append(" where sys_date = ? and whetherQs = ? ");
			if(generateNumber == 2){
				if(whethercreatefilebyrange){ //true 按接口数据范围生成数据
					sql.append("and mid in (");
					if(codeArr != null && codeArr.size() > 0){
						for(int i=0;i<codeArr.size();i++){
							sql.append("'");
							sql.append(codeArr.get(i));
							sql.append("'");
							if(i != codeArr.size()-1){
								sql.append(",");
							}
						}
					}else{
						sql.append("''");
					}
					sql.append(") ");
				}else{
					sql.append("and mid not in (");
					if(codeArr != null && codeArr.size() > 0){
						for(int i=0;i<codeArr.size();i++){
							sql.append("'");
							sql.append(codeArr.get(i));
							sql.append("'");
							if(i != codeArr.size()-1){
								sql.append(",");
							}
						}
					}else{
						sql.append("''");
					}
					sql.append(") ");
				}
			}
			logger.info("整理好的sql语句为"+sql.toString());
			SQLQuery query = session.createSQLQuery("select count(*) from ryt_upmp "+sql.toString());
			query.setParameter(0, Integer.valueOf(date));
			query.setInteger(1, 1);
			totalCount = totalCount + (query.uniqueResult()==null?0:((BigInteger) query.uniqueResult()).intValue());
			sql.delete(0, sql.length());
			
			
			sql.append(" where ref_date = ? and bk_chk = ? ");
			if(generateNumber == 2){
				if(whethercreatefilebyrange){ //true 按接口数据范围生成数据
					sql.append("and mid in (");
					if(codeArr != null && codeArr.size() > 0){
						for(int i=0;i<codeArr.size();i++){
							sql.append("'");
							sql.append(codeArr.get(i));
							sql.append("'");
							if(i != codeArr.size()-1){
								sql.append(",");
							}
						}
					}else{
						sql.append("''");
					}
					sql.append(") ");
				}else{
					sql.append("and mid not in (");
					if(codeArr != null && codeArr.size() > 0){
						for(int i=0;i<codeArr.size();i++){
							sql.append("'");
							sql.append(codeArr.get(i));
							sql.append("'");
							if(i != codeArr.size()-1){
								sql.append(",");
							}
						}
					}else{
						sql.append("''");
					}
					sql.append(") ");
				}
			}
			SQLQuery query_ = session.createSQLQuery("select count(*) from ryt_refund_log "+sql.toString());
			query_.setParameter(0, Integer.valueOf(date));
			query_.setInteger(1, 1);
			totalCount = totalCount + (query.uniqueResult()==null?0:((BigInteger) query_.uniqueResult()).intValue());
		}catch(Exception e){
			logger.error(e);
			return 0;
		} finally {
			closeSession(session);
		}
		return totalCount;
	}
	
	/**
	 * 删除hlog表中是否存在数据
	 * @param date
	 * @param gid
	 * @return
	 */
	@Override
	public int deleteHlogDataCount(String date,int gid){
		Session session = null;
		Integer totalCount = 0;
		Transaction transaction = null;
		try{
			session = this.getSession();
			transaction = session.beginTransaction();
			if(StringUtils.isNotBlank(date)){
				date = date.replaceAll("-", "");
			}
			SQLQuery query = session.createSQLQuery("delete from hlog where sys_date = ? and gid = ?");
			query.setParameter(0, Integer.valueOf(date));
			query.setParameter(1, gid);
			int deleteCount = query.executeUpdate();
			if(deleteCount > -1){
				transaction.commit();
				totalCount = deleteCount;
			}
		}catch(Exception e){
			logger.error(e);
			transaction.rollback();
		}finally{
			closeSession(session);
		}
		return totalCount;
	}
	
	/**
	 * 汇总数据进入商户余额、商户资金流水、商户T+1统计
	 * @param sysDate ： 日期
	 * @param dateColumn ：日期数据库字段名称
	 * @param gid ：渠道ID
	 * @param tableName : 渠道表名称
	 * @return boolean
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean handlerRytSuccessData(String tradeDate,String dateColumn, int gid, String tableName,int bank_id) {
		logger.info("根据交易日期：" + tradeDate + "扣款渠道：" + gid + "处理线上成功交易数据");
		boolean flag = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			if(StringUtils.isNotBlank(tradeDate)){
				tradeDate = tradeDate.replaceAll("-", "");
			}
			logger.info(StringPingJie.getInstance().getStringPingJie("开始根据交易日期：" ,tradeDate , "扣款渠道：" , gid , "查询线上成功交易数据"));
			StringBuffer sqlBuffer = new StringBuffer("");
			sqlBuffer.append("select tseq, mid, oid, amount, sys_date, sys_time, mer_fee, bk_date from ");
			sqlBuffer.append(tableName);
			sqlBuffer.append(" where ");
			sqlBuffer.append(dateColumn);
			sqlBuffer.append(" = ");
			sqlBuffer.append(tradeDate);
			sqlBuffer.append(" and tstat = 2");
			sqlBuffer.append(" and gid = ");
			sqlBuffer.append(gid);
			sqlBuffer.append(" and whetherAccessStance = 0");
			logger.info(StringPingJie.getInstance().getStringPingJie("查询线上交易数据的SQL语句是：" , sqlBuffer.toString()));
			
			SQLQuery query = session.createSQLQuery(sqlBuffer.toString());
			List resultList = query.list();
			
			if (resultList != null && resultList.size() > 0) {
				logger.info(StringPingJie.getInstance().getStringPingJie("根据交易日期：" ,tradeDate ,"扣款渠道：" ,gid , "查询线上交易成功数据的条数是：", resultList.size()));
				
				String tseq = null;//交易流水号
				String mid = null;//商户号
				double amount = 0d;//交易金额
				Integer sysDate = null;//交易日期
				Integer sysTime = null;//交易时间
				double mer_fee = 0d;//商户手续费
				double change_amount = 0d;//账户余额
				String bkDate = null;//清算日期
				MerBasic merBasic = null;
				Integer bilWay = 0;
				
				//循环交易数据插入到商户余额表、商户资金流水表
				for (Object object : resultList) {
					sqlBuffer.setLength(0);
					Object[] obj = (Object[]) object;
					tseq = String.valueOf(obj[0]);
					mid = String.valueOf(obj[1]);
					amount = Double.valueOf(String.valueOf(obj[3]))/100;
					sysDate = Integer.valueOf(obj[4].toString());
					sysTime = Integer.valueOf(obj[5].toString());
					mer_fee = Double.valueOf(String.valueOf(obj[6]));
					bkDate = obj[7] != null && Integer.valueOf(obj[7].toString()) == 0 ? obj[4].toString() : obj[7].toString();
					String tradeTime = DYDataUtil.getRyfDateHandler(sysDate, sysTime);
					String deductStlmDate = DYDataUtil.getRyfDateHandler(Integer.valueOf(bkDate), 0);
					logger.info(StringPingJie.getInstance().getStringPingJie("根据商户号" , mid , "进入商户基本信息查询..."));
					
					SQLQuery queryMer = session.createSQLQuery("select a.mer_name, a.mer_category, a.mer_state, a.mer_type,b.bil_way from mer_basic a,mer_billing b where a.mer_code = b.mer_code and a.mer_code = ?");
					queryMer.setParameter(0, mid);
					Object merObj = queryMer.uniqueResult();
					merBasic = new MerBasic();
					if (merObj != null) {
						Object[] objects = (Object[])merObj;
						merBasic.setMerName(objects[0].toString());
						merBasic.setMerCategory(Integer.valueOf(objects[1].toString()));
						merBasic.setMerState(Integer.valueOf(objects[2].toString()));
						merBasic.setMerType(Integer.valueOf(objects[3].toString()));
						if (objects[4] != null) {
							bilWay = Integer.valueOf(objects[4].toString());
						}
						
						if(bilWay == 1){//全额
							change_amount = amount;//变动金额
						}else {
							change_amount = amount- mer_fee ;//变动金额
						}
						
						logger.info(StringPingJie.getInstance().getStringPingJie("根据商户号" , mid , "进入商户余额表中查询数据..."));
						SQLQuery queryMerBalance = session.createSQLQuery("select mer_balance from mer_balance where mer_code = ?");
						queryMerBalance.setParameter(0, mid);
						Object merBalanceObj = queryMerBalance.uniqueResult();
						String balance = "0";
						if (merBalanceObj != null) {//如果该商户在商户余额表中存在，就根据商户号修改数据
							balance = merBalanceObj.toString(); 
							String addMerBalance = PoundageCalculate.addHlog(balance, change_amount);
							logger.info(StringPingJie.getInstance().getStringPingJie("商户原有余额为：" , balance ,",商户调整余额为：" , String.format("%.2f", change_amount),",商户调整后的余额为：" , addMerBalance));
							SQLQuery merBalanceUpdateSql = session.createSQLQuery("update mer_balance set mer_balance = ? where mer_code = ?");
							merBalanceUpdateSql.setParameter(0, addMerBalance);
							merBalanceUpdateSql.setParameter(1, mid);
							int merBalanceUpdateResult = merBalanceUpdateSql.executeUpdate();
							if (merBalanceUpdateResult > 0) {
								flag = true;
							} else {
								flag = false;
								logger.error("商户余额修改失败");
							}
						} else {//如果该商户在商户余额表中不存在，就插入数据
							SQLQuery merBalanceInsertSql = session.createSQLQuery("insert into mer_balance(mer_code, mer_category, mer_balance, mer_state)" +
									" values(?,?,?,?)");
							merBalanceInsertSql.setParameter(0, mid);
							merBalanceInsertSql.setParameter(1, merBasic.getMerCategory());
							merBalanceInsertSql.setParameter(2, String.format("%.2f", change_amount));
							merBalanceInsertSql.setParameter(3, merBasic.getMerState());
							int merBalanceInsertResult = merBalanceInsertSql.executeUpdate();
							if (merBalanceInsertResult > 0) {
								flag = true;
								logger.info(StringPingJie.getInstance().getStringPingJie("向商户余额表中插入数据成功——>" , "商户号是：" , mid , "商户余额是：" , String.format("%.2f", change_amount)));
							} else {
								flag = false;
								logger.error("向商户余额表中插入数据失败");
							}
						}
						
						logger.info("开始向资金流水表中插入数据...");
						sqlBuffer.append("insert into mer_fund_stance(id, mer_code, trade_time, trade_amount, mer_fee, change_amount, account_amount, trade_stance, derc_status, mer_state, mer_category, mer_name, inst_id, deduct_stlm_date, inst_type,stance_time,bank_id)");
						sqlBuffer.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						SQLQuery merFundStanceInsertSql = session.createSQLQuery(sqlBuffer.toString());
						merFundStanceInsertSql.setParameter(0, UUID.randomUUID().toString().replaceAll("-", ""));//主键ID
						merFundStanceInsertSql.setParameter(1, mid);//商户号
						merFundStanceInsertSql.setParameter(2, tradeTime);//交易时间
						merFundStanceInsertSql.setParameter(3, String.format("%.2f", amount));//交易金额
						merFundStanceInsertSql.setParameter(4, String.format("%.2f", mer_fee));//商户手续费
						merFundStanceInsertSql.setParameter(5, change_amount);//变动金额
						//账户余额=商户余额+变动金额
						merFundStanceInsertSql.setParameter(6, PoundageCalculate.add(balance, change_amount));//账户余额
						merFundStanceInsertSql.setParameter(7, tseq);//交易流水号
						merFundStanceInsertSql.setParameter(8, 1);//简单说明1：消费2：退款3：差错退款
						merFundStanceInsertSql.setParameter(9, merBasic.getMerState());//商户状态
						merFundStanceInsertSql.setParameter(10, merBasic.getMerCategory());//商户类别
						merFundStanceInsertSql.setParameter(11, merBasic.getMerName());//商户名称
						merFundStanceInsertSql.setParameter(12, gid);//渠道号
						merFundStanceInsertSql.setParameter(13, deductStlmDate);//清算日期
						merFundStanceInsertSql.setParameter(14, 1);//渠道类型inst_type
						merFundStanceInsertSql.setParameter(15, DYDataUtil.getCurrentTime());//stance_time
						merFundStanceInsertSql.setParameter(16, bank_id);
						int merFundStanceInsertResult = merFundStanceInsertSql.executeUpdate();
						if (merFundStanceInsertResult > 0) {
							flag = true;
						} else {
							flag = false;
							logger.error("向资金流水表中插入数据失败");
						}
						
						if(flag){
							sqlBuffer.setLength(0);
							sqlBuffer.append("update ");
							sqlBuffer.append(tableName);
							sqlBuffer.append(" set whetherAccessStance = ? where tseq = ?");
							SQLQuery updateWhetherAccessStanceSql = session.createSQLQuery(sqlBuffer.toString());
							updateWhetherAccessStanceSql.setParameter(0, true);
							updateWhetherAccessStanceSql.setParameter(1, Long.valueOf(tseq));
							updateWhetherAccessStanceSql.executeUpdate();
						}
					} else {
						logger.warn(StringPingJie.getInstance().getStringPingJie("商户号为：" , mid , "的交易数据在商户基本信息中不存在，不需要将该条数据插入余额表、资金流水表"));
					}
				}
				transaction.commit();
			} else {
				logger.warn(StringPingJie.getInstance().getStringPingJie("根据交易日期：" ,tradeDate , "扣款渠道：" , gid , "查询线上成功交易数据为空"));
				flag = true;
			}
		} catch (Exception e) {
			transaction.rollback();
			flag = false;
			logger.error(StringPingJie.getInstance().getStringPingJie("根据交易日期：" , tradeDate , "扣款渠道：" , gid , "处理线上成功交易数据出现异常：" , e.getMessage()));
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return flag;
	}

	@Override
	public boolean saveMerchantSettleStatistics(String tableName,String dateColumn,int gid,int deductStlmDate,int instType,Map<String, Object> mapMerBasic,int bank_id) throws Exception{
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		try {
			session = this.getSession();
			StringBuffer sqlBuffer = new StringBuffer();
			//查询有成功交易的商户
			sqlBuffer.append("select DISTINCT mid from ");
			sqlBuffer.append(tableName);
			sqlBuffer.append(" where ");
			sqlBuffer.append(dateColumn);
			sqlBuffer.append(" = ");
			sqlBuffer.append(deductStlmDate);
			sqlBuffer.append(" and whetherQs = 1 ");
			sqlBuffer.append(" and gid = ");
			sqlBuffer.append(gid);
			SQLQuery selectMid = session.createSQLQuery(sqlBuffer.toString());
			List midlist = selectMid.list();
			if(midlist != null && midlist.size() > 0){
				for (Object object : midlist) {
					String mid = object.toString();
					sqlBuffer.setLength(0);
					sqlBuffer.append("select IFNULL(sum(amount)*0.01,0),count(*),IFNULL(sum(mer_fee),0),IFNULL(sum(CONVERT(zf_file_fee,DECIMAL(20,2))),0),IFNULL(sum(CONVERT(zf_fee,DECIMAL(20,2))),0) from ");
					sqlBuffer.append(tableName);
					sqlBuffer.append(" where ");
					sqlBuffer.append(dateColumn);
					sqlBuffer.append(" = ");
					sqlBuffer.append(deductStlmDate);
					sqlBuffer.append(" and whetherQs = 1 ");
					sqlBuffer.append(" and gid = ");
					sqlBuffer.append(gid);
					sqlBuffer.append(" and mid = '");
					sqlBuffer.append(mid);
					sqlBuffer.append("'");
					SQLQuery selectMidTotal = session.createSQLQuery(sqlBuffer.toString());
					Object midTotalObj = selectMidTotal.uniqueResult();
					if(midTotalObj != null){
						Object[] midTotal = (Object[])midTotalObj;
						String trade_amount = midTotal[0].toString();
						Integer trade_count = Integer.valueOf(midTotal[1].toString());
						String merFee = midTotal[2].toString();
						String systemFee = midTotal[3].toString();
						String zfFee = midTotal[4].toString();
						
//						SQLQuery queryMer = session.createSQLQuery("select mer_category from mer_basic where mer_code = ?");
//						queryMer.setParameter(0, mid);
//						Object merObj = queryMer.uniqueResult();
						Object merObj = mapMerBasic.get(mid);
						if (merObj != null) {
							MerBasic merBasic = (MerBasic)merObj;
							Integer merType = merBasic.getMerCategory();
							
							SQLQuery selSqlQuery = session.createSQLQuery("select trade_amount,trade_count,mer_fee,system_fee,zf_fee from merchant_settle_statistics where mer_code = ? and deduct_stlm_date = ? and inst_id = ? and inst_type = ? and data_status = 0");
							selSqlQuery.setParameter(0, mid);
							selSqlQuery.setParameter(1, deductStlmDate);
							selSqlQuery.setParameter(2, gid);
							selSqlQuery.setParameter(3, instType);
							Object selectObject = selSqlQuery.uniqueResult();
							transaction = session.beginTransaction();
							if(selectObject == null){
								logger.info("开始向商户T+1统计表中插入数据...");
								SQLQuery merchantSettleStatisticsInsertSql = session.createSQLQuery("insert into merchant_settle_statistics(inst_id, mer_code, mer_type, deduct_stlm_date, trade_amount, trade_count, refund_amount, refund_count, mer_fee, system_fee, mer_refund_fee, settle_amount, system_refund_fee, inst_type,zf_fee,refund_zf_fee,bank_id,js_date,trade_gc_count) " +
										"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
								merchantSettleStatisticsInsertSql.setParameter(0, gid);//渠道号
								merchantSettleStatisticsInsertSql.setParameter(1, mid);//商户号
								merchantSettleStatisticsInsertSql.setParameter(2, merType);//商户类型
								merchantSettleStatisticsInsertSql.setParameter(3, deductStlmDate);//清算日期
								merchantSettleStatisticsInsertSql.setParameter(4, trade_amount);//交易金额
								merchantSettleStatisticsInsertSql.setParameter(5, trade_count);//交易笔数
								merchantSettleStatisticsInsertSql.setParameter(6, "0.00");//退款金额
								merchantSettleStatisticsInsertSql.setParameter(7, 0);//退款笔数
								merchantSettleStatisticsInsertSql.setParameter(8, merFee);//商户手续费
								merchantSettleStatisticsInsertSql.setParameter(9, systemFee);//系统手续费
								merchantSettleStatisticsInsertSql.setParameter(10, "0.00");//退回商户手续费
								merchantSettleStatisticsInsertSql.setParameter(11, "0");//应结算金额
								merchantSettleStatisticsInsertSql.setParameter(12, "0.00");//退回系统手续费
								merchantSettleStatisticsInsertSql.setParameter(13, instType);//渠道类型inst_type
								merchantSettleStatisticsInsertSql.setParameter(14, zfFee);//支付手续费zf_fee
								merchantSettleStatisticsInsertSql.setParameter(15, "0.00");//退回支付手续费refund_zf_fee
								merchantSettleStatisticsInsertSql.setParameter(16, bank_id);//银行网关
								merchantSettleStatisticsInsertSql.setParameter(17, deductStlmDate);//结算日期
								merchantSettleStatisticsInsertSql.setParameter(18, trade_count);//收款交易轧差笔数=交易笔数
								int merchantSettleStatisticsInsertResult = merchantSettleStatisticsInsertSql.executeUpdate();
								if (merchantSettleStatisticsInsertResult > 0) {
									flag = true;
									transaction.commit();
									logger.info(StringPingJie.getInstance().getStringPingJie("商户号为："  , mid, "向商户T+1统计表中插入数据成功"));
								} else {
									flag = false;
									logger.error(StringPingJie.getInstance().getStringPingJie("商户号为："  , mid, "向商户T+1统计表中插入数据失败"));
								}
							}else{
								sqlBuffer.setLength(0);
//								Object[] objectResult = (Object[])selectObject;
//								
//								String trade_amount_ = PoundageCalculate.add(objectResult[0].toString(), trade_amount).toString();
//								String trade_count_ = PoundageCalculate.add(objectResult[1].toString(), trade_count).toString();
//								String merFee_ = PoundageCalculate.add(objectResult[2].toString(), merFee).toString();
//								String systemFee_ = PoundageCalculate.add(objectResult[3].toString(), merFee).toString();
//								String zfFee_ = PoundageCalculate.add(objectResult[4].toString(), zfFee).toString();
								
								sqlBuffer.append("update merchant_settle_statistics set ");
								sqlBuffer.append("mer_type = ?,");
								sqlBuffer.append("trade_amount = ?,");
								sqlBuffer.append("trade_count = ?,");
								sqlBuffer.append("mer_fee = ?,");
								sqlBuffer.append("system_fee = ?,");
								sqlBuffer.append("zf_fee = ?,");
								sqlBuffer.append("trade_gc_count = ?");
								sqlBuffer.append(" where mer_code = ? and deduct_stlm_date = ? and inst_id = ? and inst_type = ? and data_status = 0");
								SQLQuery merchantSettleStatisticsUpdateSql = session.createSQLQuery(sqlBuffer.toString());
								merchantSettleStatisticsUpdateSql.setParameter(0, merType);//商户类别
								merchantSettleStatisticsUpdateSql.setParameter(1, trade_amount);//交易金额
								merchantSettleStatisticsUpdateSql.setParameter(2, trade_count);//交易笔数
								merchantSettleStatisticsUpdateSql.setParameter(3, merFee);//商户手续费
								merchantSettleStatisticsUpdateSql.setParameter(4, systemFee);//系统手续费(银行手续费)
								merchantSettleStatisticsUpdateSql.setParameter(5, zfFee);//支付手续费
								merchantSettleStatisticsUpdateSql.setParameter(6, trade_count);//收款交易轧差笔数=交易笔数
								//where
								merchantSettleStatisticsUpdateSql.setParameter(7, mid);//商户号
								merchantSettleStatisticsUpdateSql.setParameter(8, deductStlmDate);//清算日期
								merchantSettleStatisticsUpdateSql.setParameter(9, gid);//渠道号
								merchantSettleStatisticsUpdateSql.setParameter(10, instType);//渠道类型
								int merchantSettleStatisticsInsertResult = merchantSettleStatisticsUpdateSql.executeUpdate();
								if (merchantSettleStatisticsInsertResult > 0) {
									flag = true;
									transaction.commit();
									logger.info(StringPingJie.getInstance().getStringPingJie("根据商户号" , mid ,"修改商户T+1统计表中的数据成功"));
								} else {
									flag = false;
									logger.error(StringPingJie.getInstance().getStringPingJie("根据商户号" , mid , "修改商户T+1统计表中的数据失败"));
								}
							}
						} else {
							logger.info(StringPingJie.getInstance().getStringPingJie("商户号" , mid , "在商户基本信息表中不存在，不需要将分组统计后的数据插入T+1表中"));
							
						}
					}else{
						logger.info(StringPingJie.getInstance().getStringPingJie(sqlBuffer.toString()," 查询数据为空"));
					}
				}
			}
		} catch (Exception e) {
			transaction.rollback();
			logger.error(e);
			throw e;
		}finally{
			closeSession(session);
		}
		return flag;
	}

	@Override
	public boolean saveSingleMerchantSettleStatistics(String mid,int deductStlmDate,int gid,int instType,double amount,double merFee,String system_fee,double zf_fee,Map<String, Object> mapMerBasic,int bank_id)throws Exception {
		boolean flag = false;
		StringBuffer sqlBuffer = new StringBuffer();
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
//			SQLQuery queryMer = session.createSQLQuery("select mer_category from mer_basic where mer_code = ?");
//			queryMer.setParameter(0, mid);
//			Object merObj = queryMer.uniqueResult();
			Object merObj = mapMerBasic.get(mid);
			if (merObj != null) {
				MerBasic merBasic = (MerBasic)merObj;
				Integer merType = merBasic.getMerCategory();
				
				SQLQuery selSqlQuery = session.createSQLQuery("select trade_amount,trade_count,mer_fee,system_fee,zf_fee from merchant_settle_statistics where mer_code = ? and deduct_stlm_date = ? and inst_id = ? and inst_type = ? and data_status = 0");
				selSqlQuery.setParameter(0, mid);
				selSqlQuery.setParameter(1, deductStlmDate);
				selSqlQuery.setParameter(2, gid);
				selSqlQuery.setParameter(3, instType);
				Object obj = selSqlQuery.uniqueResult();
				String trade_amount = null;
				Integer trade_count = 0;
				String mer_fee = null;
				String zf_fee_ = null;
				String system_fee_ = null;
				if(obj != null){
					Object[] objArr = (Object[])obj;
					trade_amount = PoundageCalculate.addHlog(objArr[0].toString(), amount);
					trade_count = Integer.valueOf(objArr[1].toString())+1;
					mer_fee = PoundageCalculate.addHlog(objArr[2].toString(), merFee);
					system_fee_ = PoundageCalculate.addHlog(objArr[3].toString(), Double.valueOf(system_fee));
					zf_fee_ = PoundageCalculate.addHlog(objArr[4].toString(), zf_fee);
				}else{
					trade_amount = String.format("%.2f", amount);
					trade_count = 1;
					mer_fee = String.format("%.2f", mer_fee);
					system_fee_ = String.format("%.2f", Double.valueOf(system_fee));
					zf_fee_ = String.format("%.2f", zf_fee);
				}
				if(obj == null){
					logger.info("开始向商户T+1统计表中插入数据...");
					SQLQuery merchantSettleStatisticsInsertSql = session.createSQLQuery("insert into merchant_settle_statistics(inst_id, mer_code, mer_type, deduct_stlm_date, trade_amount, trade_count, refund_amount, refund_count, mer_fee, system_fee, mer_refund_fee, settle_amount, system_refund_fee, inst_type,zf_fee,refund_zf_fee,bank_id) " +
							"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					merchantSettleStatisticsInsertSql.setParameter(0, gid);//渠道号
					merchantSettleStatisticsInsertSql.setParameter(1, mid);//商户号
					merchantSettleStatisticsInsertSql.setParameter(2, merType);//商户类型
					merchantSettleStatisticsInsertSql.setParameter(3, deductStlmDate);//清算日期
					merchantSettleStatisticsInsertSql.setParameter(4, trade_amount);//交易金额
					merchantSettleStatisticsInsertSql.setParameter(5, trade_count);//交易笔数
					merchantSettleStatisticsInsertSql.setParameter(6, "0.00");//退款金额
					merchantSettleStatisticsInsertSql.setParameter(7, 0);//退款笔数
					merchantSettleStatisticsInsertSql.setParameter(8, merFee);//商户手续费
					merchantSettleStatisticsInsertSql.setParameter(9, "0.00");//系统手续费
					merchantSettleStatisticsInsertSql.setParameter(10, "0.00");//退回商户手续费
					merchantSettleStatisticsInsertSql.setParameter(11, "0");//应结算金额
					merchantSettleStatisticsInsertSql.setParameter(12, "0.00");//退回系统手续费
					merchantSettleStatisticsInsertSql.setParameter(13, instType);//渠道类型inst_type
					merchantSettleStatisticsInsertSql.setParameter(14, zf_fee_);//支付手续费
					merchantSettleStatisticsInsertSql.setParameter(15, "0.00");//退回支付手续费
					merchantSettleStatisticsInsertSql.setParameter(16, bank_id);//银行网关
					int merchantSettleStatisticsInsertResult = merchantSettleStatisticsInsertSql.executeUpdate();
					if (merchantSettleStatisticsInsertResult > 0) {
						flag = true;
						transaction.commit();
						logger.info(StringPingJie.getInstance().getStringPingJie("商户号为："  , mid, "向商户T+1统计表中插入数据成功"));
					} else {
						flag = false;
						logger.error(StringPingJie.getInstance().getStringPingJie("商户号为："  , mid, "向商户T+1统计表中插入数据失败"));
					}
				}else{
					sqlBuffer.append("update merchant_settle_statistics set ");
					sqlBuffer.append("mer_type = ?,");
					sqlBuffer.append("trade_amount = ?,");
					sqlBuffer.append("trade_count = ?,");
					sqlBuffer.append("mer_fee = ?,");
					sqlBuffer.append("system_fee = ?,");
					sqlBuffer.append("zf_fee = ?");
					sqlBuffer.append(" where mer_code = ? and deduct_stlm_date = ? and inst_id = ? and inst_type = ? and data_status = 0");
					SQLQuery merchantSettleStatisticsUpdateSql = session.createSQLQuery(sqlBuffer.toString());
					merchantSettleStatisticsUpdateSql.setParameter(0, merType);//商户类别
					merchantSettleStatisticsUpdateSql.setParameter(1, trade_amount);//交易金额
					merchantSettleStatisticsUpdateSql.setParameter(2, trade_count);//交易笔数
					merchantSettleStatisticsUpdateSql.setParameter(3, system_fee_);//系统手续费(对账文件手续费)
					merchantSettleStatisticsUpdateSql.setParameter(4, merFee);//商户手续费
					merchantSettleStatisticsUpdateSql.setParameter(5, zf_fee_);//商户手续费
					//where
					merchantSettleStatisticsUpdateSql.setParameter(6, mid);//商户号
					merchantSettleStatisticsUpdateSql.setParameter(7, deductStlmDate);//清算日期
					merchantSettleStatisticsUpdateSql.setParameter(8, gid);//渠道号
					merchantSettleStatisticsUpdateSql.setParameter(9, instType);//渠道类型
					int merchantSettleStatisticsInsertResult = merchantSettleStatisticsUpdateSql.executeUpdate();
					if (merchantSettleStatisticsInsertResult > 0) {
						flag = true;
						transaction.commit();
						logger.info("根据商户号" + mid +"修改商户T+1统计表中的数据成功");
					} else {
						flag = false;
						logger.error("根据商户号" + mid + "修改商户T+1统计表中的数据失败");
					}
				}
			} else {
				logger.info("商户号" + mid + "在商户基本信息表中不存在，不需要将分组统计后的数据插入T+1表中");
			}
		} catch (Exception e) {
			transaction.rollback();
			logger.error(e);
			throw e;
		}finally{
			closeSession(session);
		}
		return flag;
	}
	@Override
	public int getOnLineDataCountFromRYF(String date, int gid,String column) {
		int result = 0;
		Session session = null;
		try{
			session = this.getSession("ryt_hibernate.xml");
			
			date = date.replaceAll("-", "");
			
			logger.info("日期参数:"+date);
			logger.info("渠道ID:"+gid);
			logger.info("日期属性值:"+column);
			
			StringBuffer sql = new StringBuffer("");
			sql.append("select count(*) from hlog where ");
			sql.append(column);
			sql.append(" = ?");
			sql.append(" and gid = ?");
			SQLQuery query = session.createSQLQuery(sql.toString());
			query.setParameter(0, date);
			query.setParameter(1, gid);
			logger.info(StringPingJie.getInstance().getStringPingJie("查询SQL",sql.toString()));
			
			result = query.uniqueResult()==null?0:Integer.valueOf(query.uniqueResult().toString());
		}catch(Exception e){
			logger.error("统计融易付数据库中渠道交易数据数量操作抛出异常:"+e.getMessage());
		}finally{
			closeSession(session);
		}
		return result;
	}
	
	@Override
	public int getOnlineDataCountFromDzSys(String date,int gid,String column){
		int result = 0;
		Session session = null;
		try{
			session = this.getSession();
			
			date = date.replaceAll("-", "");
			
			logger.info("日期参数:"+date);
			logger.info("渠道ID:"+gid);
			logger.info("日期属性值:"+column);
			
			StringBuffer sql = new StringBuffer("");
			sql.append("select count(*) from hlog where ");
			sql.append(column);
			sql.append(" = ?");
			sql.append(" and gid = ?");
			SQLQuery query = session.createSQLQuery(sql.toString());
			query.setParameter(0, date);
			query.setParameter(1, gid);
			logger.info(StringPingJie.getInstance().getStringPingJie("查询SQL",sql.toString()));
			
			result = query.uniqueResult()==null?0:Integer.valueOf(query.uniqueResult().toString());
		}catch(Exception e){
			logger.error("统计对账系统数据库中渠道交易数据数量操作抛出异常:"+e.getMessage());
		}finally{
			closeSession(session);
		}
		return result;
	}

	@Override
	public List<?> queryQsData(Class<?> clas, String tableName, String date,int inst_id) {
		Session session = null;
		List<?> list = null;
		String table_name = "";
		if(StringUtils.isNotBlank(date)){
			date = date.replaceAll("-", "");
			if(tableName.contains(",")){
				table_name = tableName.split(",")[0];
			}else{
				table_name = tableName;
			}
			logger.info("参数：清算日期---"+date+",原始交易数据表名---"+table_name);
			try{
				session = this.getSession();
				StringBuffer sb = new StringBuffer("");
				sb.append("SELECT * FROM ");
				sb.append(table_name);
				sb.append(" WHERE deduct_stlm_date = ? AND gid = ? AND whetherQs = 1 order by deduct_stlm_date");
				SQLQuery query = session.createSQLQuery(sb.toString()).addEntity(clas);
				query.setParameter(0, date);
				query.setInteger(1, inst_id);
				list = query.list();
				if(list == null || list.size() == 0){
					logger.info("日期为"+date+"的"+table_name+"对账成功数据为0条");
				}
			}catch(Exception e){
				logger.error(e);
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			logger.warn("日期参数不能为空");
		}
		return list;
	}
	
	@Override
	public List<Object> queryDuizhangDataList(String dzDataTableName, String deductStmlDate ,boolean isTk,int bk_chk) {
		Session session = null;
		List<Object> list = null;
		if(StringUtils.isNotBlank(deductStmlDate)){
			try{
				session = this.getSession();
				StringBuffer sb = new StringBuffer("");
				sb.append("SELECT id,tradeAmount,tradeFee,reqSysStance,orderId,deduct_stlm_date,reqTime,outAccount,dz_file_name FROM ");
				sb.append(dzDataTableName);
				sb.append(" WHERE deduct_stlm_date = ? and bk_chk = ? and whetherTk = ?");
				SQLQuery query = session.createSQLQuery(sb.toString());
				query.setString(0, deductStmlDate);
				query.setInteger(1, bk_chk);
				query.setBoolean(2, isTk);
				list = query.list();
			}catch(Exception e){
				logger.error(e);
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			logger.warn("清算日期参数不能为空");
		}
		return list;
	}
	
	@Override
	public boolean updateDuizhangDataBkchk(String dzDataTableName,String id,int bk_chk) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		try{
			session = this.getSession();
			transaction = session.beginTransaction();
			StringBuffer buffer = new StringBuffer();
			buffer.append("update ");
			buffer.append(dzDataTableName);
			buffer.append(" set bk_chk = ? where id = ?");
			SQLQuery sqlQuery = session.createSQLQuery(buffer.toString());
			sqlQuery.setParameter(0, bk_chk);
			sqlQuery.setParameter(1, id);
			int update_flag = sqlQuery.executeUpdate();
			if(update_flag > -1){
				transaction.commit();
				flag = true;
			}
		}catch(Exception e){
			if(transaction != null)transaction.rollback();
			logger.error(e);
		}finally {
			closeSession(session);
		}
		return flag;
	}

	@Override
	public boolean updateOriginalDataBkchk(String originalTableName,String id,int bk_chk,boolean whetherQs,double zf_fee,String zf_file_fee,String dz_deductStlmDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		try{
			session = this.getSession();
			transaction = session.beginTransaction();
			StringBuffer buffer = new StringBuffer();
			Integer zfFeeBj = 2;//0:无需比对、1:比对成功、2:比对失败  ->默认比对失败
			zf_file_fee = Double.valueOf(zf_file_fee).toString();
			if(StringUtils.equals(String.valueOf(zf_fee), zf_file_fee)){
				zfFeeBj = 1;
			}
			buffer.append("update ");
			buffer.append(originalTableName);
			buffer.append(" set bk_chk = ?,whetherQs = ?,zf_fee = ?,zf_file_fee = ?,zf_fee_bj = ?,deduct_stlm_date = ? where tseq = ?");
			SQLQuery sqlQuery = session.createSQLQuery(buffer.toString());
			sqlQuery.setParameter(0, bk_chk);
			sqlQuery.setParameter(1, whetherQs);
			sqlQuery.setParameter(2, zf_fee);
			sqlQuery.setParameter(3, zf_file_fee);
			sqlQuery.setParameter(4, zfFeeBj);
			sqlQuery.setParameter(5, Integer.valueOf(dz_deductStlmDate));
			sqlQuery.setParameter(6, id);
			int update_flag = sqlQuery.executeUpdate();
			if(update_flag > -1){
				transaction.commit();
				flag = true;
			}
		}catch(Exception e){
			if(transaction != null)transaction.rollback();
			logger.error(e);
		}finally {
			closeSession(session);
		}
		return flag;
	}
	
	@SuppressWarnings("unchecked")
	public Map<Integer,Integer> getInstDzResult(String tradeDate,String tableName,int bk_chk,int inst_id){
		Session session = null;
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		try{
			session = this.getSession();
			StringBuffer sqlBuffer = new StringBuffer("");
			sqlBuffer.append("select gid,count(*) from ");
			sqlBuffer.append(tableName);
			sqlBuffer.append(" where sys_date = ");
			sqlBuffer.append(tradeDate);
			if(bk_chk != -1){
				sqlBuffer.append(" and bk_chk = ");
				sqlBuffer.append(bk_chk);
			}
			if(inst_id != 0){
				sqlBuffer.append(" and gid = ");
				sqlBuffer.append(inst_id);
			}
			sqlBuffer.append(" group by gid ");
			SQLQuery query = session.createSQLQuery(sqlBuffer.toString());
			List<Object> resultList = query.list();
			if(resultList != null && resultList.size() > 0){
				for (Object object : resultList) {
					Object[] obj = (Object[]) object;
					if(obj != null && obj.length > 1){
						if(obj[0] != null && obj[1] != null){
							map.put(Integer.valueOf(obj[0].toString()), Integer.valueOf(obj[1].toString()));
						}
					}
				}
			}
		}catch(Exception e){
			logger.error(e);
		}finally{
			closeSession(session);
		}
		return map;
	}

	@Override
	public int getBankTotalDataNum(String deduct_stlm_date,String tableName) {
		int result = 0;
		Session session = null;
		try{
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select count(*) from "+ tableName + " where deduct_stlm_date = "+ deduct_stlm_date);
			Object object = query.uniqueResult();
			if(object != null){
				result = Integer.valueOf(object.toString());
			}
		}catch(Exception e){
			logger.error(e);
		}
		return result;
	}
	
	@Override
	public int getOriTotalDataNum(int startDate,int endDate,String tableName,int gid){
		int result = 0;
		Session session = null;
		try{
		   session = this.getSession();
		   StringBuffer sqlBuffer = new StringBuffer("");
		   sqlBuffer.append("select count(*) from ");
		   sqlBuffer.append(tableName);
		   sqlBuffer.append(" where sys_date between ");
		   sqlBuffer.append(startDate);
		   sqlBuffer.append(" and ");
		   sqlBuffer.append(endDate);
		   if(gid != 0){
			   sqlBuffer.append(" and gid = ");
			   sqlBuffer.append(gid);
		   }
		   SQLQuery query = session.createSQLQuery(sqlBuffer.toString());
		   Object object = query.uniqueResult();
		   if(object != null){
			   result = Integer.valueOf(object.toString());
		   }
		}catch(Exception e){
			logger.error(e);
		}
		return result;
	}
	
	@Override
	public List<Object> queryNoDzJsOriginalData(String originalTableName,
			int sys_date, int bk_chk, boolean is_qs) {
		Session session = null;
		List<Object> list = null;
		try {
			session = this.getSession();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select sys_date,sys_time,tstat,tseq,oid,card_no,amount,mer_fee,zf_fee,zf_file_fee,mid,gid from ");
			buffer.append(originalTableName);
			buffer.append(" where sys_date = ? and bk_chk = ? and whetherQs = ?");
			SQLQuery sqlQuery = session.createSQLQuery(buffer.toString());
			sqlQuery.setInteger(0, sys_date);
			sqlQuery.setInteger(1, bk_chk);
			sqlQuery.setBoolean(2, is_qs);
			list = sqlQuery.list();
		} catch (Exception e) {
			logger.error(e);
		}finally{
			closeSession(session);
		}
		return list;
	}

	@Override
	public List<Object> queryNoDzJsIsSuccessOriginalData(
			String originalTableName, int sys_date, int bk_chk, boolean is_qs,
			int tstat) {
		Session session = null;
		List<Object> list = null;
		try {
			session = this.getSession();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select sys_time,tseq from ");
			buffer.append(originalTableName);
			buffer.append(" where sys_date = ? and bk_chk = ? and whetherQs = ? and tstat = ?");
			SQLQuery sqlQuery = session.createSQLQuery(buffer.toString());
			sqlQuery.setInteger(0, sys_date);
			sqlQuery.setInteger(1, bk_chk);
			sqlQuery.setBoolean(2, is_qs);
			sqlQuery.setInteger(3, tstat);
			list = sqlQuery.list();
		} catch (Exception e) {
			logger.error(e);
		}finally{
			closeSession(session);
		}
		return list;
	}

	@Override
	public boolean updateBkChkOrWhetherQs(String originalTableName,String id,
			boolean whetherQs,int deduct_stlm_date) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			StringBuffer buffer = new StringBuffer();
			buffer.append("update ");
			buffer.append(originalTableName);
			buffer.append(" set whetherQs = ?,deduct_stlm_date = ? where tseq = ?");
			SQLQuery query = session.createSQLQuery(buffer.toString());
			query.setBoolean(0, whetherQs);
			query.setInteger(1, deduct_stlm_date);
			query.setLong(2, Long.valueOf(id));
			int update_flag = query.executeUpdate();
			if(update_flag > -1){
				transaction.commit();
				flag = true;
			}
		} catch (Exception e) {
			logger.error(e);
			if(transaction != null )transaction.rollback();
		}finally{
			closeSession(session);
		}
		return flag;
	}

	@Override
	public boolean updateDZFNoHandle(String originalTableName,String sysDate, int update_bk_chk,
			int select_bk_chk) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			StringBuffer buffer = new StringBuffer();
			buffer.append("update ");
			buffer.append(originalTableName);
			buffer.append(" set bk_chk = ? ");
			buffer.append("where sys_date = ? and bk_chk = ?");
			SQLQuery query = session.createSQLQuery(buffer.toString());
			query.setParameter(0, update_bk_chk);
			query.setParameter(1, Integer.valueOf(sysDate));
			query.setParameter(2, select_bk_chk);
			int update_flag = query.executeUpdate();
			if(update_flag > -1){
				transaction.commit();
				flag = true;
			}
		} catch (Exception e) {
			logger.error(e);
			if(transaction != null )transaction.rollback();
		}finally{
			closeSession(session);
		}
		return flag;
	}

	@Override
	public Map<String, Object[]> queryColumnMap(String originalTableName,
			Integer instId, int startDate, int endDate, String column) {
		Session session = null;
		Map<String, Object[]> map = null;
		try {
			session = this.getSession();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select ");
			buffer.append("sys_date,sys_time,tstat,tseq,card_no,amount,mer_fee,mid,gid,gate,oid,deduct_stlm_date,");
			buffer.append(column);
			buffer.append(" from ");
			buffer.append(originalTableName);
			buffer.append(" where sys_date >= ? and sys_date <= ? and bk_chk = 0 and ");
			buffer.append(column);
			buffer.append(" is not null");
			SQLQuery query = session.createSQLQuery(buffer.toString());
			query.setParameter(0, startDate);
			query.setParameter(1, endDate);
			List<Object> list = query.list();
			if(list != null && list.size() > 0){
				map = new HashMap<String, Object[]>();
				for (Object object : list) {
					Object[] objArr = (Object[])object;
					String column_str = objArr[12].toString();
					map.put(column_str, objArr);
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}finally{
			closeSession(session);
		}
		return map;
	}

	@Override
	public Object[] queryAllMap(String originalTableName,
			Integer instId, int startDate, int endDate) {
		Session session = null;
		Object[] objMap = null;
		try {
			session = this.getSession();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select sys_date,sys_time,tstat,tseq,card_no,amount,mer_fee,mid,gid,gate,oid,deduct_stlm_date from ");
			buffer.append(originalTableName);
			buffer.append(" where sys_date >= ? and sys_date <= ? and bk_chk = 0");
			SQLQuery query = session.createSQLQuery(buffer.toString());
			query.setParameter(0, startDate);
			query.setParameter(1, endDate);
			List<Object> list = query.list();
			if(list != null && list.size() > 0){
				objMap = new Object[2];
				Map<String, Object[]> tseqMap = new HashMap<String, Object[]>();
				Map<String, Object[]> oidMap = new HashMap<String, Object[]>();
				for (Object object : list) {
					Object[] objArr = (Object[])object;
					String oid = objArr[3].toString();
					tseqMap.put(oid, objArr);
					if(objArr[10] != null && StringUtils.isNotBlank(objArr[10].toString())){
						oidMap.put(objArr[10].toString(), objArr);
					}
				}
				objMap[0] = tseqMap;
				objMap[1] = oidMap;
			}
		} catch (Exception e) {
			logger.error(e);
		}finally{
			closeSession(session);
		}
		return objMap;
	}
	/**
	 *保存银行对账数据到数据库中
	 * 
	 */
	@Override
	public boolean saveBankData(String[] columnData,PreparedStatement stmt) throws Exception{
		try{
			if(columnData != null){
				if(columnData != null && columnData.length > 0){
					for(int i = 0;i<columnData.length;i++){
						stmt.setObject(i+1, trimMySelf(columnData[i]));
					}
					stmt.addBatch();
					return true;
				}
			}
		}catch(Exception e){
			logger.error(e);
			throw e;
		}
		return false;
	}
	
	@Override
	public Session getCurrentSession(){
		return this.getSession();
	}
	
	private static String trimMySelf(String str){
    	return "".equals(str)||str==null||"".equals(str.trim()) ?null:str.trim();
    }
	
	public List<Hlog> getHlogPageData(Map<String,String[]> map) throws Exception{
		List<Hlog> list = null;
		Session session = null;
		try{
			session = this.getSession();
			StringBuffer sb = new StringBuffer("");
			sb.append("SELECT * FROM hlog WHERE 1=1 ");
			for(Map.Entry<String,String[]> entry:map.entrySet()){
				if("pageNo".equals(entry.getKey()) || "pageNum".equals(entry.getKey()) || "version".equals(entry.getKey()) 
						|| "tranCode".equals(entry.getKey()) || "query_type".equals(entry.getKey()) || "merPriv".equals(entry.getKey())
						|| "start_date".equals(entry.getKey()) || "end_date".equals(entry.getKey())){
					continue;
				}else{
					sb.append(" and ");
					sb.append(entry.getKey());
					sb.append("=");
					sb.append(entry.getValue()[0]);
				}
			}
			
			sb.append(" and sys_date between ");
			sb.append(map.get("start_date")[0]);
			sb.append(" and ");
			sb.append(map.get("end_date")[0]);
			
			sb.append(" order by sys_date desc limit ");
			sb.append((Integer.valueOf(map.get("pageNo")[0])-1)*(Integer.valueOf(map.get("pageNum")[0])));
			sb.append(",");
			sb.append(map.get("pageNum")[0]);
			
			SQLQuery query = session.createSQLQuery(sb.toString()).addEntity(Hlog.class);
			list = query.list();
		}catch(Exception e){
			logger.error(e);
			throw e;
		}
		return list;
	}
	
	public List<Hlog> getHlogDataList(Map<String,String[]> map) throws Exception{
		List<Hlog> list = null;
		Session session = null;
		try{
			session = this.getSession();
			StringBuffer sb = new StringBuffer("");
			sb.append("SELECT * FROM hlog WHERE 1=1 ");
			for(Map.Entry<String,String[]> entry:map.entrySet()){
				if("version".equals(entry.getKey()) || "tranCode".equals(entry.getKey()) || "query_type".equals(entry.getKey()) 
						|| "merPriv".equals(entry.getKey()) || "start_date".equals(entry.getKey()) || "end_date".equals(entry.getKey())){
					continue;
				}else{
					sb.append(" and ");
					sb.append(entry.getKey());
					sb.append("=");
					sb.append(entry.getValue()[0]);
				}
			}
			
			sb.append(" and sys_date between ");
			sb.append(map.get("start_date")[0]);
			sb.append(" and ");
			sb.append(map.get("end_date")[0]);
			
			sb.append(" order by sys_date desc ");
			
			SQLQuery query = session.createSQLQuery(sb.toString()).addEntity(Hlog.class);
			list = query.list();
		}catch(Exception e){
			logger.error(e);
			throw e;
		}
		return list;
	}
	
	
	@Override
	public boolean saveSynDzfTrade(String tseq, String version, String mdate,
			String mid, String bid, String oid, String amount, String type,
			String gate, String author_type, String sys_date,
			String init_sys_date, String sys_time, String fee_amt,
			String bank_fee, String tstat, String mobile_no, String phone_no,
			String card_no, BankInst bankInst,InstInfo instInfo, String pre_amt, String pre_amt1,
			String bk_fee_model, String pay_amt, String currency,
			String exchange_rate, String out_user_id, String in_user_id,
			String bind_mid,MerBasic merBasic)throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			StringBuffer buffer = new StringBuffer();
			InstInfoPK infoPK = instInfo.getId();
			buffer.append("insert into ");
			buffer.append(bankInst.getOriginalDataTableName());
			buffer.append("(tseq,version,mdate,mid,bid,oid,amount,type,gate,author_type,");
			buffer.append("sys_date,init_sys_date,sys_time,fee_amt,bank_fee,tstat,");
			buffer.append("mobile_no,phone_no,card_no,gid,pre_amt,pre_amt1,bk_fee_model,");
			buffer.append("pay_amt,currency,exchange_rate,out_user_id,in_user_id,bind_mid,bank_id) ");
			buffer.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			//logger.info(buffer.toString());
			SQLQuery sqlQuery = session.createSQLQuery(buffer.toString());
			sqlQuery.setParameter(0, Long.valueOf(tseq));
			sqlQuery.setParameter(1, Integer.valueOf(version));
			sqlQuery.setParameter(2, Integer.valueOf(mdate));
			sqlQuery.setParameter(3, mid);
			sqlQuery.setParameter(4, bid);
			sqlQuery.setParameter(5, oid);
			sqlQuery.setParameter(6, Long.valueOf(amount));
			sqlQuery.setParameter(7, Integer.valueOf(type));
			sqlQuery.setParameter(8, Integer.valueOf(gate));
			sqlQuery.setParameter(9, author_type == null ? null : Integer.valueOf(author_type));
			sqlQuery.setParameter(10, Integer.valueOf(sys_date));
			sqlQuery.setParameter(11, Integer.valueOf(init_sys_date));
			sqlQuery.setParameter(12, Integer.valueOf(sys_time));
			sqlQuery.setParameter(13, Integer.valueOf(fee_amt));
			sqlQuery.setParameter(14, Integer.valueOf(bank_fee));
			sqlQuery.setParameter(15, Integer.valueOf(tstat));
			sqlQuery.setParameter(16, mobile_no);
			sqlQuery.setParameter(17, phone_no);
			sqlQuery.setParameter(18, card_no);
			sqlQuery.setParameter(19, infoPK.getInstId());
			sqlQuery.setParameter(20, Integer.valueOf(pre_amt));
			sqlQuery.setParameter(21, Integer.valueOf(pre_amt1));
			sqlQuery.setParameter(22, bk_fee_model);
			sqlQuery.setParameter(23, Integer.valueOf(pay_amt));
			sqlQuery.setParameter(24, currency);
			sqlQuery.setParameter(25, new BigDecimal(exchange_rate));
			sqlQuery.setParameter(26, out_user_id);
			sqlQuery.setParameter(27, in_user_id);
			sqlQuery.setParameter(28, bind_mid);
			sqlQuery.setParameter(29, bankInst.getId());
			int count = sqlQuery.executeUpdate();
			if(count > 0){
				flag = true;
			}
			buffer.setLength(0);
			Long tradeTime = DYDataUtil.getStringTimeyyyyMMddHHmmss(sys_date, sys_time);
			buffer.append("insert into channel_trade_collect(trade_id,out_account,trade_amount,");
			buffer.append("trade_currency,trade_currency_name,trade_result,req_sys_stance,req_mer_code,deduct_sys_id,deduct_sys_stance,");
			buffer.append("deduct_sys_time,deduct_stlm_date,trade_type,instType,mer_fee,zf_fee,zf_file_fee,zf_fee_bj,");
			buffer.append("gate,settle_code,inst_name,dy_mer_name,deduct_mer_code,trade_time,oid,sys_date,bank_id,out_user_id,in_user_id,bind_mid) ");
			buffer.append("value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			SQLQuery query = session.createSQLQuery(buffer.toString());
			query.setParameter(0, tseq);
			query.setParameter(1, card_no);
			query.setParameter(2, amount);
			if(StringUtils.isNotBlank(currency)){
				query.setParameter(3, currency);
				int trade_currency = Integer.valueOf(currency);
				switch (trade_currency) {
				case 1:
					query.setParameter(4, "人民币");
					break;
				case 2:
					query.setParameter(4, "英镑");
					break;
				case 3:
					query.setParameter(4, "英镑");
					break;
				case 4:
					query.setParameter(4, "美元");
					break;
				case 5:
					query.setParameter(4, "瑞士法郎");
					break;
				case 6:
					query.setParameter(4, "日元");
					break;
				case 7:
					query.setParameter(4, "加拿大元");
					break;
				case 8:
					query.setParameter(4, "澳大利亚元");
					break;
				case 9:
					query.setParameter(4, "澳大利亚元");
					break;
				default:
					query.setParameter(4, "人民币");
					break;
				}
			}else{
				query.setParameter(3, "01");
				query.setParameter(4, "人民币");
			}
			query.setParameter(5, Integer.valueOf(tstat));
			query.setParameter(6, tseq);
			query.setParameter(7, mid);
			query.setParameter(8, infoPK.getInstId());
			query.setParameter(9, tseq);
			query.setParameter(10, tradeTime);
			query.setParameter(11, 0);
			query.setParameter(12, type);
			query.setParameter(13, infoPK.getInstType());
			query.setParameter(14, Double.valueOf(fee_amt)/100);
			query.setParameter(15, 0d);
			query.setParameter(16, 0d);
			query.setParameter(17, 0);
			query.setParameter(18, gate);
			query.setParameter(19, mid);
			query.setParameter(20, instInfo.getName());
			query.setParameter(21, merBasic == null ? null : merBasic.getMerName());
			query.setParameter(22, mid);
			query.setParameter(23, tradeTime);
			query.setParameter(24, oid);
			query.setParameter(25, Integer.valueOf(sys_date));
			query.setParameter(26, bankInst.getId());
			query.setParameter(27, out_user_id);
			query.setParameter(28, in_user_id);
			query.setParameter(29, bind_mid);
			int count_trade = query.executeUpdate();
			if(count_trade > 0 && flag){
				transaction.commit();
			}else{
				transaction.rollback();
				flag = false;
			}
		} catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			logger.error(e);
			throw e;
		}finally{
			session.flush();
			session.clear();
			closeSession(session);
		}
		return flag;
	}

	@Override
	public boolean updateSynOkTrade(String tseq, String gid, String tstat,
			String bk_flag, String bk_date, String bk_seq1, String bk_seq2,
			String bk_resp, String error_msg,BankInst bankInst) throws Exception {
		Session session = null;
		boolean flag = false;
		Transaction transaction = null;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			StringBuffer buffer = new StringBuffer();
			buffer.append("update ");
			buffer.append(bankInst.getOriginalDataTableName());
			buffer.append(" set tstat = ?,bk_flag = ?,bk_date = ?,bk_seq1 = ?,bk_seq2 = ?,bk_resp = ?,error_msg = ?");
			buffer.append(" where tseq = ?");
			SQLQuery updateQuery = session.createSQLQuery(buffer.toString());
			updateQuery.setParameter(0, Integer.valueOf(tstat));
			updateQuery.setParameter(1, Integer.valueOf(bk_flag));
			updateQuery.setParameter(2, Integer.valueOf(bk_date));
			updateQuery.setParameter(3, bk_seq1);
			updateQuery.setParameter(4, bk_seq2);
			updateQuery.setParameter(5, bk_resp);
			updateQuery.setParameter(6, error_msg);
			updateQuery.setParameter(7, Long.valueOf(tseq));
			int update_flag = updateQuery.executeUpdate();
			if(update_flag > 0){
				flag = true;
			}
			buffer.setLength(0);
			buffer.append("update channel_trade_collect set trade_result = ? where trade_id = ?");
			SQLQuery query = session.createSQLQuery(buffer.toString());
			query.setParameter(0, Integer.valueOf(tstat));
			query.setParameter(1, tseq);
			int count = query.executeUpdate();
			if(count > 0 && flag){
				transaction.commit();
			}else{
				transaction.rollback();
				flag = false;
			}
		} catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			logger.error(e);
		}finally{
			closeSession(session);
		}
		return flag;
	}

	@Override
	public int queryOringinalTableCount(Long tseq, String oringinalTable) {
		Session session = null;
		int count = 0;
		try {
			if(StringUtils.isNotBlank(oringinalTable)){
				session = this.getSession();
				StringBuffer buffer = new StringBuffer();
				buffer.append("select count(*) from ");
				buffer.append(oringinalTable);
				buffer.append(" where tseq = ?");
				SQLQuery query = session.createSQLQuery(buffer.toString());
				query.setParameter(0, tseq);
				count = Integer.valueOf(query.uniqueResult().toString());
			}else{
				logger.error("queryOringinalTableCount 参数oringinalTable 不能为空");
			}
		} catch (Exception e) {
			logger.error(e);
		}finally{
			closeSession(session);
		}
		return count;
	}

	@Override
	public Object[] queryOnlineTableData(String tableName, Object... obj) {
		Session session = null;
		Object[] objArr = new Object[2];
		try {
			session = this.getSession();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select currency,mid,tseq,oid,mdate,amount,mer_fee from ");
			buffer.append(tableName);
			if(obj != null && obj.length > 0){
				int len = obj.length;
				for(int i=0;i<len;i++){
					if(i%2==0){
						buffer.append(obj[i]);
						buffer.append("=");
						if(obj[i+1] instanceof String){
							buffer.append("'");
							buffer.append(obj[i+1]);
						}else
							buffer.append(obj[i+1]);
						if(len-i!=2)
							buffer.append("' and ");
					}
				}
				SQLQuery query = session.createSQLQuery(buffer.toString());
				Object result_obj = query.uniqueResult();
				if(result_obj != null){
					objArr[0] = "000";
					objArr[1] = (Object[])result_obj;
				}else{
					objArr[0] = "001";//没有查询到原笔
				}
			}else{
				logger.error("hlogDao.queryOnlineTableData方法 obj 参数不能为空");
			}
		} catch (Exception e) {
			logger.error(e);
		}finally{
			closeSession(session);
		}
		return objArr;
	}
	
	public static void main(String[] args) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from ");
		buffer.append("hlog ");
		Object[] obj = {"tseq","1234","sys_date",20151231,"mdate",20151231,"init_sys_date",20151231};
		int len = obj.length;
		for (int i=0;i<len;i++) {
			if(i%2==0){
				buffer.append(obj[i]);
				buffer.append("=");
				if(obj[i+1] instanceof String){
					buffer.append("'");
					buffer.append(obj[i+1]);
				}else
					buffer.append(obj[i+1]);
				if(len-i!=2)
					buffer.append("' and ");
			}
		}
		System.out.println(buffer.toString());
	}
}