package cn.com.chinaebi.dz.object.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.dz.object.MerFundStance;
import cn.com.chinaebi.dz.object.base.BaseMerFundStanceDAO;
import cn.com.chinaebi.dz.util.DYDataUtil;
import cn.com.chinaebi.dz.util.DataCollect;


public class MerFundStanceDAO extends BaseMerFundStanceDAO implements cn.com.chinaebi.dz.object.dao.iface.MerFundStanceDAO {

	private Log log = LogFactory.getLog(getClass()); 
	public MerFundStanceDAO () {}
	
	public MerFundStanceDAO (Session session) {
		super(session);
	}

	@Override
	public boolean saveMerFundStance(String mer_code, Date trade_time,
			double trade_amount, double mer_fee, double change_amount,
			double account_amount, String trade_stance, Integer derc_status,
			Integer mer_state, Integer mer_category, String mer_name,Integer inst_id,String deduct_stlm_date,Integer inst_type,int bank_id) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		try {
//			if(derc_status == 2){//2:退款(冲正)
//				mer_fee = 0-mer_fee;
//			}
			session = this.getSession();
			transaction = session.beginTransaction();
			SQLQuery sqlQuery = session.createSQLQuery("insert into mer_fund_stance(id,mer_code,trade_time,trade_amount,mer_fee,change_amount,account_amount,trade_stance,derc_status,mer_state,mer_category,mer_name,inst_id,deduct_stlm_date,inst_type,stance_time,bank_id)" +
					"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			sqlQuery.setParameter(0, UUID.randomUUID().toString().replaceAll("-", ""));
			sqlQuery.setParameter(1, mer_code);
			sqlQuery.setParameter(2, trade_time);
			sqlQuery.setParameter(3, trade_amount);
			sqlQuery.setParameter(4, mer_fee);
			sqlQuery.setParameter(5, change_amount);
			sqlQuery.setParameter(6, account_amount);
			sqlQuery.setParameter(7, trade_stance);//717411
			sqlQuery.setParameter(8, derc_status);
			sqlQuery.setParameter(9, mer_state);
			sqlQuery.setParameter(10, mer_category);
			sqlQuery.setParameter(11, mer_name);
			sqlQuery.setParameter(12, inst_id);
			sqlQuery.setParameter(13, deduct_stlm_date);
			sqlQuery.setParameter(14, inst_type);
			sqlQuery.setParameter(15, DYDataUtil.getCurrentTime());
			sqlQuery.setParameter(16, bank_id);
			int count = sqlQuery.executeUpdate();
			
			int mer_balanceCount = 0;
			SQLQuery mer_balanceSelect = session.createSQLQuery("select mer_balance from mer_balance where mer_code = ?");
			mer_balanceSelect.setString(0, mer_code);
			Object obj = mer_balanceSelect.uniqueResult();
			if(obj != null){
				SQLQuery mer_balanceUpdate = session.createSQLQuery("update mer_balance set mer_balance = ? where mer_code = ?");
				mer_balanceUpdate.setString(0, DataCollect.keepPrecision(String.valueOf(account_amount), 2));
				mer_balanceUpdate.setString(1, mer_code);
				mer_balanceCount = mer_balanceUpdate.executeUpdate();
			}else{
				SQLQuery mer_balanceInsert = session.createSQLQuery("insert into mer_balance(mer_code,mer_category,mer_balance,mer_state) values(?,?,?,?)");
				mer_balanceInsert.setParameter(0, mer_code);
				mer_balanceInsert.setParameter(1, mer_category);
				mer_balanceInsert.setParameter(2, DataCollect.keepPrecision(String.valueOf(account_amount), 2));
				mer_balanceInsert.setParameter(3, mer_state);
				mer_balanceCount = mer_balanceInsert.executeUpdate();
			}
			
			if(count > 0 && mer_balanceCount > 0){
				transaction.commit();
				flag = true;
			}else{
				transaction.rollback();
			}
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error(e);
		}finally{
			closeSession(session);
		}
		return flag;
	}

	@Override
	public List<Object[]> queryMerFundStance(String deduct_stlm_date,int inst_id, int inst_type) {
		Session session = null;
		List<Object[]> list = null;
		try{
			session = this.getSession();
			Query query = session.createQuery("select sum(a.ChangeAmount),a.MerCode from MerFundStance a where a.DeductStlmDate = ? and a.InstId = ? and a.InstType = ? and a.DercStatus not in(5,6,7) group by a.MerCode");
			query.setString(0, deduct_stlm_date);
			query.setInteger(1, inst_id);
			query.setInteger(2, inst_type);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}else{
				log.warn("select sum(ChangeAmount),MerCode from MerFundStance where DeductStlmDate = "+deduct_stlm_date+" and InstId = "+inst_id+" and a.InstType = " + inst_type + " and a.DercStatus not in(5,6,7) group by MerCode not data");
			}
			
		}catch(Exception e){
			log.error(e);
		}finally{
			closeSession(session);
		}
		return list;
	}
	@Override
	public List<Object[]> queryMerFundStanceOfErrorAdjust(String deduct_stlm_date,int inst_id, int inst_type) {
		Session session = null;
		List<Object[]> list = null;
		try{
			session = this.getSession();
			Query query = session.createQuery("select sum(a.ChangeAmount),a.MerCode from MerFundStance a where a.DeductStlmDate = ? and a.InstId = ? and a.InstType = ? and a.DercStatus in(3,4) group by a.MerCode");
			query.setString(0, deduct_stlm_date);
			query.setInteger(1, inst_id);
			query.setInteger(2, inst_type);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}else{
				log.warn("select sum(ChangeAmount),MerCode from MerFundStance where DeductStlmDate = "+deduct_stlm_date+" and InstId = "+inst_id+" and a.InstType = " + inst_type + " and a.DercStatus in(3,4) group by MerCode not data");
			}
			
		}catch(Exception e){
			log.error(e);
		}finally{
			closeSession(session);
		}
		return list;
	}

	@Override
	public boolean deleteMerFundStance(String deduct_stlm_date, int inst_id, int inst_type) {
		boolean flag = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			SQLQuery query = session.createSQLQuery("select count(*) from mer_fund_stance where deduct_stlm_date = ? and inst_id = ? and inst_type = ? and derc_status not in(5,6,7)");
			query.setParameter(0, deduct_stlm_date);
			query.setParameter(1, inst_id);
			query.setParameter(2, inst_type);
			Integer count = Integer.valueOf(query.uniqueResult().toString());
			if (count > 0) {
				SQLQuery sql = session.createSQLQuery("delete from mer_fund_stance where deduct_stlm_date = ? and inst_id = ? and inst_type = ? and derc_status not in(5,6,7)");
				sql.setParameter(0, deduct_stlm_date);
				sql.setParameter(1, inst_id);
				sql.setParameter(2, inst_type);
				int effectNum = sql.executeUpdate();
				if (effectNum > 0) {
					transaction.commit();
					flag = true;
					log.info("删除商户资金流水数据成功");
				} else {
					flag = false;
					log.info("删除资金流水数据失败");
				}
			} else {
				log.info("不存在清算日期为" + deduct_stlm_date + "渠道ID为" + inst_id + "渠道类型 "+ inst_type + "的资金流水数据，不需要执行删除操作");
			}
		} catch (Exception e) {
			transaction.rollback();
			log.error("商户资金还原出现异常：" + e.getMessage());
		} finally{
			closeSession(session);
		}
		return flag;
	}
	@Override
	public boolean deleteMerFundStanceOfErrorAdjust(String deduct_stlm_date, int inst_id, int inst_type) {
		boolean flag = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			SQLQuery sql = session.createSQLQuery("delete from mer_fund_stance where deduct_stlm_date = ? and inst_id = ? and inst_type = ? and derc_status in(3,4)");
			sql.setParameter(0, deduct_stlm_date);
			sql.setParameter(1, inst_id);
			sql.setParameter(2, inst_type);
			int effectNum = sql.executeUpdate();
			if (effectNum >= 0) {
				transaction.commit();
				flag = true;
				log.info("删除商户资金流水数据成功");
			} else {
				flag = false;
				log.info("删除资金流水数据失败");
			}
		} catch (Exception e) {
			transaction.rollback();
			log.error("商户资金还原出现异常：" + e.getMessage());
		} finally{
			closeSession(session);
		}
		return flag;
	}

	@Override
	public boolean addMerFundStance(MerFundStance merFundStance) {
		boolean flag = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			SQLQuery sqlQuery = session.createSQLQuery("insert into mer_fund_stance(id,mer_code,trade_time,trade_amount,mer_fee,change_amount,account_amount,trade_stance,derc_status,mer_state," +
					"mer_category,mer_name,inst_id,deduct_stlm_date,inst_type,stance_time,bank_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			sqlQuery.setParameter(0, merFundStance.getId());
			sqlQuery.setParameter(1, merFundStance.getMerCode());
			sqlQuery.setParameter(2, merFundStance.getTradeTime());
			sqlQuery.setParameter(3, merFundStance.getTradeAmount());
			sqlQuery.setParameter(4, merFundStance.getMerFee());
			sqlQuery.setParameter(5, merFundStance.getChangeAmount());
			sqlQuery.setParameter(6, merFundStance.getAccountAmount());
			sqlQuery.setParameter(7, merFundStance.getTradeStance());
			sqlQuery.setParameter(8, merFundStance.getDercStatus());
			sqlQuery.setParameter(9, merFundStance.getMerState());
			sqlQuery.setParameter(10, merFundStance.getMerCategory());
			sqlQuery.setParameter(11, merFundStance.getMerName());
			sqlQuery.setParameter(12, merFundStance.getInstId());
			sqlQuery.setParameter(13, merFundStance.getDeductStlmDate());
			sqlQuery.setParameter(14, merFundStance.getInstType());
			sqlQuery.setParameter(15, merFundStance.getStanceTime());
			sqlQuery.setParameter(16, merFundStance.getBankId());
			int count = sqlQuery.executeUpdate();
			if (count > 0) {
				transaction.commit();
				flag = true;
				log.info("向商户资金流水中插入数据成功");
			}
		} catch (Exception e) {
			transaction.rollback();
			log.error("向商户资金流水中插入数据出现异常：" + e.getMessage());
		} finally {
			closeSession(session);
		}
		return flag;
	}
	
	@Override
	public Object queryCountAndMoney(String merCode, String startDate,String endDate) {
		Session session = null;
		Object object = null;
		try{
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select count(*), SUM(trade_amount) from mer_fund_stance where mer_code = ? and REPLACE(substring(trade_time,1,10),'-','') BETWEEN ? and ?");
			query.setParameter(0, merCode);
			query.setParameter(1, startDate);
			query.setParameter(2, endDate);
			object = query.uniqueResult();
		}catch(Exception e){
			log.error(e);
		}finally{
			closeSession(session);
		}
		return object;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<MerFundStance> queryMerFundStanceByMerCode(String merCode,String startDate, String endDate, int startRow, int endRow) {
		Session session = null;
		List<MerFundStance> list = null;
		try{
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select * from mer_fund_stance where mer_code = ? and REPLACE(substring(trade_time,1,10),'-','') between ? and ? limit ?, ?").addEntity(MerFundStance.class);
			query.setParameter(0, merCode);
			query.setParameter(1, startDate);
			query.setParameter(2, endDate);
			query.setParameter(3, startRow);
			query.setParameter(4, endRow);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}
		}catch(Exception e){
			log.error(e);
		}finally{
			closeSession(session);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<MerFundStance> queryMerFundStanceDataLst(String merCode,String startDate, String endDate) {
		Session session = null;
		List<MerFundStance> list = null;
		try{
			session = this.getSession();
			Query query = session.createQuery("from MerFundStance where MerCode = ? and REPLACE(substring(TradeTime,1,10),'-','') between ? and ?");
			query.setString(0, merCode);
			query.setString(1, startDate);
			query.setString(2, endDate);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}
		}catch(Exception e){
			log.error(e);
		}finally{
			closeSession(session);
		}
		return list;
	}
}