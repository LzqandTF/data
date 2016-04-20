package cn.com.chinaebi.dz.object.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.CallableStatement;

import cn.com.chinaebi.dz.object.CreditcardpayTradeLst;
import cn.com.chinaebi.dz.object.base.BaseCreditcardpayTradeLstDAO;
import cn.com.chinaebi.dz.util.DYDataUtil;


public class CreditcardpayTradeLstDAO extends BaseCreditcardpayTradeLstDAO implements cn.com.chinaebi.dz.object.dao.iface.CreditcardpayTradeLstDAO {

	private Log log = LogFactory.getLog(getClass());
	public CreditcardpayTradeLstDAO () {}
	
	public CreditcardpayTradeLstDAO (Session session) {
		super(session);
	}

	@Override
	public boolean getCreditcardpayTradeData(String tradeTime) {
		Session session = null;
		Transaction ts = null;
		boolean flag = false;
		try {
			session = this.getSession();
			if(session  != null){
				ts = session.beginTransaction();
				CallableStatement cs = (CallableStatement) session.connection().prepareCall("call proce_creditcardpay_trade_lst(?)");
				cs.setObject(1, tradeTime);
				cs.execute();
				ts.commit();
				flag = true;
			}else 
				log.info("getCreditcardpayTradeData 获取session 为null");
		} catch (Exception e) {
			ts.rollback();
			log.error("信用卡还款交易数据汇总出错：" + e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return flag;
	}

	public List<CreditcardpayTradeLst> queryCreditcardpayClearInnerData(String date,int instId,String status) {
		Session  session = null;
		List<CreditcardpayTradeLst> list = null;
		try{
			session = this.getSession();
			Query query = session.createQuery("from CreditcardpayTradeLst where DeductSysId = ? and substring(TradeTime,1,10) = ?  and TradeResult = ?  order by TradeTime asc");
			query.setInteger(0, instId);
			query.setString(1, date);
			query.setString(2, status);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}else{
				log.warn("from CreditcardpayTradeLst where DeductSysId = ? and substring(TradeTime,1,10) = '"+date+"' and TradeResult = ? is not data");
			}
		}catch(Exception e){
			log.error(e);
		} finally {
			closeSession(session);
		}
		return list;
	}
	/**
	 * 查询信用卡成功交易记录
	 * @param date
	 * @param status
	 * @return
	 */
	public List<CreditcardpayTradeLst> queryCreditcardpaySucessData(String date,String status) {
		Session  session = null;
		List<CreditcardpayTradeLst> list = null;
		try{
			session = this.getSession();
			Date startTime = DYDataUtil.getformatConversionDate3(date);
			Date endTime = DYDataUtil.getformatConversionDate4(date);
			Query query = session.createQuery("from CreditcardpayTradeLst where TradeTime BETWEEN ? and ?  and TradeResult = ?  order by TradeTime asc");
			query.setParameter(0, startTime);
			query.setParameter(1, endTime);
			query.setString(2, status);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}else{
				log.warn("from CreditcardpayTradeLst where TradeTime BETWEEN '"+startTime+"' and '"+endTime+"' and TradeResult =  '"+status+"' is not data");
			}
		}catch(Exception e){
			log.error(e);
		} finally {
			closeSession(session);
		}
		return list;
	}
	
	public boolean insertDzFileData(String date,String file_name,String file_type,String create_last_time,String path,int object_id,String object_name){
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		if(StringUtils.isNotBlank(date)){
			try{
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery query = session.createSQLQuery("select count(*) from dz_file_tab where deduct_sys_date = ? and file_type = ? and file_name = ? and object_id = ? ");
				query.setString(0, date);
				query.setString(1, file_type);
				query.setString(2, file_name);
				query.setInteger(3, object_id);
				Integer num = Integer.valueOf(query.uniqueResult().toString());
				log.info("根据清算时间、文件类型与文件名称查询的记录条数为"+num+",进行"+ (num>0?"修改操作":"添加操作"));
				if(num > 0){
					SQLQuery update = session.createSQLQuery("update dz_file_tab set create_last_time = ?,file_path = ? where deduct_sys_date = ? and file_type = ? and file_name = ?  and object_id = ? ");
					update.setString(0, create_last_time);
					update.setString(1, path);
					update.setString(2, date);
					update.setString(3, file_type);
					update.setString(4, file_name);
					update.setInteger(5, object_id);
					int count = update.executeUpdate();
					if(count > 0){
						transaction.commit();
						flag = true;
					}
				}else{
					SQLQuery insert = session.createSQLQuery("INSERT INTO dz_file_tab(deduct_sys_date,file_type,file_name,create_last_time,file_path,object_id,object_name) VALUES(?,?,?,?,?,?,?)");
					insert.setString(0, date);
					insert.setString(1, file_type);
					insert.setString(2, file_name);
					insert.setString(3, create_last_time);
					insert.setString(4, path);
					insert.setInteger(5, object_id);
					insert.setString(6, object_name);
					int count = insert.executeUpdate();
					if(count > 0){
						transaction.commit();
						flag = true;
					}
				}
			}catch(Exception e){
				transaction.rollback();
				log.error(e);
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}
		return flag;
	}
	
	public boolean deleteCreditcardPayLst(String tradeTime){
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		if(StringUtils.isNotBlank(tradeTime)){
			try{
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery query = session.createSQLQuery("select count(*) from creditcardpay_trade_lst where trade_time like ?");
				query.setString(0, tradeTime + "%");
				Integer c = Integer.valueOf(query.uniqueResult().toString());
				if(c > 0){
					SQLQuery delete = session.createSQLQuery("delete from creditcardpay_trade_lst where trade_time like ?");
					delete.setString(0, tradeTime + "%");
					int count = delete.executeUpdate();
					if(count > 0){
						flag = true;
						transaction.commit();
					}
				}else{
					flag = true;
				}
			}catch(Exception e){
				transaction.rollback();
				log.error(e);
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}
		return flag;
	}
	/**
	 * 统计
	 * @param date
	 * @param codeArr
	 * @param generateNumber
	 * @return
	 */
	public int getTotalCountOfDzSucessDataOfCreditCard(String date,List<String> codeArr,int generateNumber){
		Session session = null;
		Integer totalCount = 0;
		try{
			session = this.getSession();
			Date startTime = DYDataUtil.getformatConversionDate3(date);
			Date endTime = DYDataUtil.getformatConversionDate4(date);
			StringBuffer sql = new StringBuffer("");
			sql.append("select count(*) from creditcardpay_trade_lst where trade_time BETWEEN ? and ? and trade_result = ? ");
			if(generateNumber == 2){
				sql.append("and req_mer_code in (");
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
			}else if(generateNumber == 3){
				sql.append(" and IF(CHAR_LENGTH(terminal_info) = 58 or CHAR_LENGTH(terminal_info) = 51 ,substring(terminal_info,27, 3) ,substring(terminal_info,35, 3) ) in (");
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
			log.info("整理好的sql语句为"+sql.toString());
			SQLQuery query = session.createSQLQuery(sql.toString());
			query.setParameter(0, startTime);
			query.setParameter(1, endTime);
			query.setString(2, "0");
			totalCount = totalCount + (query.uniqueResult()==null?0:((BigInteger) query.uniqueResult()).intValue());
		}catch(Exception e){
			log.error(e);
			return 0;
		} finally {
			closeSession(session);
		}
		return totalCount;
	}
}