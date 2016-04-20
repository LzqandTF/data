package cn.com.chinaebi.dz.object.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.dz.object.RiqieCupsLst;
import cn.com.chinaebi.dz.object.RiqieDljhLst;
import cn.com.chinaebi.dz.object.base.BaseRiqieDljhLstDAO;
import cn.com.chinaebi.dz.util.DYDataUtil;


public class RiqieDljhLstDAO extends BaseRiqieDljhLstDAO implements cn.com.chinaebi.dz.object.dao.iface.RiqieDljhLstDAO {
	private Log log = LogFactory.getLog(getClass());
	public RiqieDljhLstDAO () {}
	
	public RiqieDljhLstDAO (Session session) {
		super(session);
	}

	@Override
	public List<RiqieDljhLst> findRiqieDljhLst(String deductStlmDate) {
		Session session = null;
		List<RiqieDljhLst> list = null; 
		if(deductStlmDate == null){
			log.error("RiqieDljhLstDAO findRiqieDljhLst deductStlmDate param is not null");
			return null;
		}
		try {
			
			Date startTime = DYDataUtil.getformatConversionDate3(deductStlmDate);
			Date endTime = DYDataUtil.getformatConversionDate4(deductStlmDate);
			log.info("大连交行对账查询日切原始交易开始时间 ："+startTime);
			log.info("大连交行对账查询日切原始交易开始时间 ："+endTime);
			
			session = this.getSession();
			Query query = session.createQuery("from RiqieDljhLst where DeductStlmDate BETWEEN ? and ? and WhetherValid = 1 order by ReqTime,TrademsgType asc");
			query.setParameter(0, startTime);
			query.setParameter(1, endTime);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}else{
				log.warn("from RiqieDljhLst where DeductStlmDate BETWEEN ? and ? and WhetherValid = 1 order by ReqTime,TrademsgType asc is not data");
			}
		}catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}

	@Override
	public boolean updateClean(String reqSysStance, boolean clean,
			Integer bkchk, Integer tradeMsgType, boolean deductRollBk,
			String deductStmlDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				Date startTime = DYDataUtil.getformatConversionDate3(deductStmlDate);
				Date endTime = DYDataUtil.getformatConversionDate4(deductStmlDate);
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update riqie_dljh_lst set whetherQs = ?,bk_chk = ? where deduct_sys_stance = ? and trademsg_type = ? and deduct_roll_bk = ? and deduct_stlm_date BETWEEN ? and ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, bkchk);
				sqlQuery.setString(2, reqSysStance);
				sqlQuery.setInteger(3, tradeMsgType);
				sqlQuery.setBoolean(4, deductRollBk);
				sqlQuery.setParameter(5, startTime);
				sqlQuery.setParameter(6, endTime);
				int count =  sqlQuery.executeUpdate();
				if(count > 0 ){
					transaction.commit();
					flag = true;
				}
			} catch (Exception e) {
				transaction.rollback();
				log.error(e);
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn(reqSysStance +" 流水号不存在");
		}
		return flag;
	}

	@Override
	public boolean updateClean(String reqSysStance, boolean clean,
			Integer bkchk, boolean deductRollBk, String deductStmlDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				Date startTime = DYDataUtil.getformatConversionDate3(deductStmlDate);
				Date endTime = DYDataUtil.getformatConversionDate4(deductStmlDate);
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update riqie_dljh_lst set whetherQs = ?,bk_chk = ? where deduct_sys_stance = ? and deduct_roll_bk = ? and deduct_stlm_date BETWEEN ? and ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, bkchk);
				sqlQuery.setString(2, reqSysStance);
				sqlQuery.setBoolean(3, deductRollBk);
				sqlQuery.setParameter(4, startTime);
				sqlQuery.setParameter(5, endTime);
				int count =  sqlQuery.executeUpdate();
				if(count > 0 ){
					transaction.commit();
					flag = true;
				}
			} catch (Exception e) {
				transaction.rollback();
				log.error(e);
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn(reqSysStance +" 流水号不存在");
		}
		return flag;
	}

	@Override
	public boolean updateNoNeedHandle(String reqSysStance, boolean clean,
			Integer bkchk, Integer tradeMsgType, String deductStmlDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				Date startTime = DYDataUtil.getformatConversionDate3(deductStmlDate);
				Date endTime = DYDataUtil.getformatConversionDate4(deductStmlDate);
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update riqie_dljh_lst set whetherQs = ?,bk_chk = ? where deduct_sys_stance = ? and trademsg_type = ? and deduct_stlm_date BETWEEN ? and ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, bkchk);
				sqlQuery.setString(2, reqSysStance);
				sqlQuery.setInteger(3, tradeMsgType);
				sqlQuery.setParameter(4, startTime);
				sqlQuery.setParameter(5, endTime);
				int count =  sqlQuery.executeUpdate();
				if(count > 0 ){
					transaction.commit();
					flag = true;
				}
			} catch (Exception e) {
				transaction.rollback();
				log.error(e);
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn(reqSysStance +" 流水号不存在");
		}
		return flag;
	}

	@Override
	public boolean updateSettleInfo(String reqSysStance,
			boolean whetherTk,Double zf_fee, String zf_file_fee, Integer tradeMsgType,
			boolean deductRollBk, String deductStmlDate,Integer whetherReturnFee) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				Date startTime = DYDataUtil.getformatConversionDate3(deductStmlDate);
				Date endTime = DYDataUtil.getformatConversionDate4(deductStmlDate);
				Integer zfFeeBj = 2;//0:无需比对、1:比对成功、2:比对失败  ->默认比对失败
//				if(tradeMsgType == 20 && whetherTk && whetherReturnFee == 0){//退货交易
//					zf_fee = 0d;
//				}
				Double zf_file_fee_double = Double.valueOf(zf_file_fee);
				if(zf_fee.equals(Double.valueOf(zf_file_fee_double))){
					zfFeeBj = 1;
				}
				StringBuffer sb = new StringBuffer();
				if(whetherTk){
					zf_fee = 0-zf_fee;
					if(zf_file_fee_double == 0){
						sb.append(zf_file_fee);
					}else{
						sb.append("-");
						sb.append(zf_file_fee);
					}
				}else{
					//消费or预授权完成or消费撤销冲正or预授权完成撤销冲正or电子现金(脱机消费)
					if(tradeMsgType == 2 || tradeMsgType == 56 || tradeMsgType == 28 || tradeMsgType == 82 || tradeMsgType == 110){
						sb.append(zf_file_fee);
					}else{
						zf_fee = 0-zf_fee;
						if(zf_file_fee_double == 0){
							sb.append(zf_file_fee);
						}else{
							sb.append("-");
							sb.append(zf_file_fee);
						}
					}
				}
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update riqie_dljh_lst set whetherTk = ?,zf_file_fee = ?,zf_fee = ?,zf_fee_bj = ? where deduct_sys_stance = ? and trademsg_type = ? and deduct_roll_bk = ? and deduct_stlm_date BETWEEN ? and ?");
				sqlQuery.setBoolean(0, whetherTk);
				sqlQuery.setString(1, sb.toString());
				sqlQuery.setDouble(2, zf_fee);
				sqlQuery.setInteger(3, zfFeeBj);
				sqlQuery.setString(4, reqSysStance);
				sqlQuery.setInteger(5, tradeMsgType);
				sqlQuery.setBoolean(6, deductRollBk);
				sqlQuery.setParameter(7, startTime);
				sqlQuery.setParameter(8, endTime);
				int count =  sqlQuery.executeUpdate();
				if(count > 0 ){
					transaction.commit();
					flag = true;
				}
			} catch (Exception e) {
				transaction.rollback();
				log.error(e);
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn(reqSysStance +" 流水号不存在");
		}
		return flag;
	}


	@Override
	public boolean updateCleanAndError(String reqSysStance, boolean clean,
			Integer bkchk, Integer whetherError, boolean deductRollBk,
			String deductStmlDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				Date startTime = DYDataUtil.getformatConversionDate3(deductStmlDate);
				Date endTime = DYDataUtil.getformatConversionDate4(deductStmlDate);
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update riqie_dljh_lst set whetherQs = ?,bk_chk = ?,whetherErroeHandle = ? where deduct_sys_stance = ? and deduct_stlm_date BETWEEN ? and ? and deduct_roll_bk = ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, bkchk);
				sqlQuery.setInteger(2, whetherError);
				sqlQuery.setString(3, reqSysStance);
				sqlQuery.setParameter(4, startTime);
				sqlQuery.setParameter(5, endTime);
				sqlQuery.setBoolean(6, deductRollBk);
				int count =  sqlQuery.executeUpdate();
				if(count > 0 ){
					transaction.commit();
					flag = true;
				}
			} catch (Exception e) {
				transaction.rollback();
				log.error(e);
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn(reqSysStance +", 流水号不存在");
		}
		return flag;
	}

	@Override
	public boolean updateCleanAndError(String reqSysStance, boolean clean,
			Integer bkchk, Integer whetherError, Integer tradeMsgType,
			boolean deductRollBk, String deductStmlDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				Date startTime = DYDataUtil.getformatConversionDate3(deductStmlDate);
				Date endTime = DYDataUtil.getformatConversionDate4(deductStmlDate);
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update riqie_dljh_lst set whetherQs = ?,bk_chk = ?,whetherErroeHandle = ? where deduct_sys_stance = ? and deduct_stlm_date BETWEEN ? and ? and deduct_roll_bk = ? and trademsg_type = ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, bkchk);
				sqlQuery.setInteger(2, whetherError);
				sqlQuery.setString(3, reqSysStance);
				sqlQuery.setParameter(4, startTime);
				sqlQuery.setParameter(5, endTime);
				sqlQuery.setBoolean(6, deductRollBk);
				sqlQuery.setInteger(7, tradeMsgType);
				int count =  sqlQuery.executeUpdate();
				if(count > 0 ){
					transaction.commit();
					flag = true;
				}
			} catch (Exception e) {
				transaction.rollback();
				log.error(e);
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn(reqSysStance +", 流水号不存在");
		}
		return flag;
	}

	@Override
	public boolean saveRiqieDljhLst(RiqieDljhLst riqieDljhLst,
			Integer flagStatus) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			SQLQuery sqlQuery = session.createSQLQuery("select count(*) from riqie_dljh_lst where trade_id = ?");
			sqlQuery.setString(0, riqieDljhLst.getId());
			Integer count = Integer.valueOf(sqlQuery.uniqueResult().toString());
			if(count > 0 ){
				if(flagStatus == 0){//0：表示自动对账 、那么当手动对账时不修改原先已经对账处理好的日切数据状态
					update(riqieDljhLst);
					transaction.commit();
					flag = true;
				}
			}else{
				save(riqieDljhLst);
				transaction.commit();
				flag = true;
			}
		} catch (Exception e) {
			transaction.rollback();
			log.error(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return flag;
	}


}