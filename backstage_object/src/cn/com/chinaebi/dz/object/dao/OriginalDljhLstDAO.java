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

import cn.com.chinaebi.dz.object.OriginalCupsLst;
import cn.com.chinaebi.dz.object.OriginalDljhLst;
import cn.com.chinaebi.dz.object.base.BaseOriginalDljhLstDAO;
import cn.com.chinaebi.dz.util.DYDataUtil;


public class OriginalDljhLstDAO extends BaseOriginalDljhLstDAO implements cn.com.chinaebi.dz.object.dao.iface.OriginalDljhLstDAO {
	private Log log = LogFactory.getLog(getClass());
	public OriginalDljhLstDAO () {}
	
	public OriginalDljhLstDAO (Session session) {
		super(session);
	}

	@Override
	public OriginalDljhLst queryWhetherDzSuccess(String reqSysStance,
			String deductStlmDate, boolean deductRollBk) {
		Session session = null;
		OriginalDljhLst originalDljhLst = null;
		try {
			
			Date startTime = DYDataUtil.getformatConversionDate3(deductStlmDate);
			Date endTime = DYDataUtil.getformatConversionDate4(deductStlmDate);
			log.info("大连交行对账核对原笔对账结果开始时间 ："+startTime);
			log.info("大连交行对账核对原笔对账结果开始时间 ："+endTime);
			
			session = this.getSession();
			Query query = session.createQuery("select Id,BkChk from OriginalDljhLst where ReqSysStance = ? and DeductStlmDate BETWEEN ? and ? and DeductRollBk = ?");
			query.setParameter(0, reqSysStance);
			query.setParameter(1, startTime);
			query.setParameter(2, endTime);
			query.setParameter(3, deductRollBk);
			Object obj = query.uniqueResult();
			if(obj != null){
				Object[] objects = (Object[])obj;
				originalDljhLst = new OriginalDljhLst();
				originalDljhLst.setId(objects[0].toString());
				originalDljhLst.setBkChk(Integer.valueOf(objects[1].toString()));
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return originalDljhLst;
	}

	@Override
	public OriginalDljhLst queryWhetherDzSuccessAll(String reqSysStance,
			String deductStlmDate, boolean deductRollBk) {
		Session session = null;
		OriginalDljhLst originalDljhLst = null;
		try {
			
			Date startTime = DYDataUtil.getformatConversionDate3(deductStlmDate);
			Date endTime = DYDataUtil.getformatConversionDate4(deductStlmDate);
			log.info("大连交行对账核对原笔对账结果开始时间 ："+startTime);
			log.info("大连交行对账核对原笔对账结果开始时间 ："+endTime);
			
			session = this.getSession();
			Query query = session.createQuery("from OriginalDljhLst where ReqSysStance = ? and DeductStlmDate BETWEEN ? and ? and DeductRollBk = ?");
			query.setParameter(0, reqSysStance);
			query.setParameter(1, startTime);
			query.setParameter(2, endTime);
			query.setParameter(3, deductRollBk);
			Object obj = query.uniqueResult();
			if(obj != null){
				originalDljhLst = (OriginalDljhLst)obj;
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return originalDljhLst;
	}

	@Override
	public List<OriginalDljhLst> findOriginalDljhLst(String originalReqTime) {
		Session session = null;
		List<OriginalDljhLst> list = null; 
		if(originalReqTime == null){
			log.error("OriginalDljhLstDAO findOriginalDljhLst deductStlmDate param is not null");
			return null;
		}
		try {
			Date startTime = DYDataUtil.getformatConversionDate3(originalReqTime);
			Date endTime = DYDataUtil.getformatConversionDate4(originalReqTime);
			log.info("大连交行对账查询原始交易开始时间 ："+startTime);
			log.info("大连交行对账查询原始交易开始时间 ："+endTime);
			
			session = this.getSession();
			Query query = session.createQuery("from OriginalDljhLst where TradeTime BETWEEN ? and ? and WhetherValid = 1 order by ReqTime,TrademsgType asc");
			query.setParameter(0, startTime);
			query.setParameter(1, endTime);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}else{
				log.warn("from OriginalDljhLst where TradeTime BETWEEN ? and ? and WhetherValid = 1 order by ReqTime,TrademsgType asc is not data");
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
	public boolean updateDataRiqie(String reqSysStance, boolean whetherRiqie,
			String originalReqTime) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try{
				Date startTime = DYDataUtil.getformatConversionDate3(originalReqTime);
				Date endTime = DYDataUtil.getformatConversionDate4(originalReqTime);
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery query = session.createSQLQuery("update original_dljh_lst set whetherRiqie = ?,bk_chk = 0,whetherErroeHandle = 0 where deduct_sys_stance = ? and trade_time BETWEEN ? and ?");
				query.setBoolean(0, whetherRiqie);
				query.setString(1, reqSysStance);
				query.setParameter(2, startTime);
				query.setParameter(3, endTime);
				int count = query.executeUpdate();
				if(count > 0){
					flag = true;
					transaction.commit();
				}
			}catch(Exception e){
				log.error(e);
				transaction.rollback();
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn("流水号不能为空");
		}
		return flag;
	}

	@Override
	public boolean updateNoNeedHandle(String reqSysStance, boolean clean,
			Integer bkchk, Integer tradeMsgType, String originalReqTime) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				
				Date startTime = DYDataUtil.getformatConversionDate3(originalReqTime);
				Date endTime = DYDataUtil.getformatConversionDate4(originalReqTime);
				
				SQLQuery sqlQuery = session.createSQLQuery("update original_dljh_lst set whetherQs = ?,bk_chk = ? where deduct_sys_stance = ? and trademsg_type = ? and trade_time BETWEEN ? and ?");
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
	public boolean updateClean(String reqSysStance, boolean clean,
			Integer bkchk, Integer tradeMsgType, boolean deductRollBk,
			String originalReqTime) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				
				Date startTime = DYDataUtil.getformatConversionDate3(originalReqTime);
				Date endTime = DYDataUtil.getformatConversionDate4(originalReqTime);
				
				session = this.getSession();
				transaction = session.beginTransaction();
				StringBuffer buffer = new StringBuffer();
				buffer.append("update original_dljh_lst set whetherQs = ?,bk_chk = ? where deduct_sys_stance = ? and trademsg_type = ? and deduct_roll_bk = ? and trade_time BETWEEN ? and ?");
				SQLQuery sqlQuery = session.createSQLQuery(buffer.toString());
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, bkchk);
				sqlQuery.setString(2, reqSysStance);
				sqlQuery.setInteger(3, tradeMsgType);
				sqlQuery.setBoolean(4, deductRollBk);
				sqlQuery.setParameter(5,  startTime);
				sqlQuery.setParameter(6,  endTime);
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
	public boolean updateClean(String reqSysStance, boolean clean,
			Integer bkchk, boolean deductRollBk, String originalReqTime) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				
				Date startTime = DYDataUtil.getformatConversionDate3(originalReqTime);
				Date endTime = DYDataUtil.getformatConversionDate4(originalReqTime);
				
				session = this.getSession();
				transaction = session.beginTransaction();
				StringBuffer buffer = new StringBuffer();
				buffer.append("update original_dljh_lst set whetherQs = ?,bk_chk = ? where deduct_sys_stance = ? and deduct_roll_bk = ? and trade_time BETWEEN ? and ?");
				SQLQuery sqlQuery = session.createSQLQuery(buffer.toString());
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, bkchk);
				sqlQuery.setString(2, reqSysStance);
				sqlQuery.setBoolean(3, deductRollBk);
				sqlQuery.setParameter(4,  startTime);
				sqlQuery.setParameter(5,  endTime);
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
	public boolean updateSettleInfo(String reqSysStance,
			boolean whetherTk,Double zf_fee, String zf_file_fee, Integer tradeMsgType,
			boolean deductRollBk, String originalReqTime,Integer whetherReturnFee) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				Date startTime = DYDataUtil.getformatConversionDate3(originalReqTime);
				Date endTime = DYDataUtil.getformatConversionDate4(originalReqTime);
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
				SQLQuery sqlQuery = session.createSQLQuery("update original_dljh_lst set whetherTk = ?,zf_file_fee = ?,zf_fee = ?,zf_fee_bj = ? where deduct_sys_stance = ? and trademsg_type = ? and deduct_roll_bk = ? and trade_time BETWEEN ? and ?");
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
			String originalReqTime) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				Date startTime = DYDataUtil.getformatConversionDate3(originalReqTime);
				Date endTime = DYDataUtil.getformatConversionDate4(originalReqTime);
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update original_dljh_lst set whetherQs = ?,bk_chk = ?,whetherErroeHandle = ? where deduct_sys_stance = ? and deduct_stlm_date BETWEEN ? and ? and deduct_roll_bk = ?");
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
			boolean deductRollBk, String originalReqTime) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				Date startTime = DYDataUtil.getformatConversionDate3(originalReqTime);
				Date endTime = DYDataUtil.getformatConversionDate4(originalReqTime);
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update original_dljh_lst set whetherQs = ?,bk_chk = ?,whetherErroeHandle = ? where deduct_sys_stance = ? and deduct_stlm_date BETWEEN ? and ? and deduct_roll_bk = ? and trademsg_type = ?");
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
	public boolean updateCleanRiqie(String reqSysStance, boolean clean,
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
				SQLQuery sqlQuery = session.createSQLQuery("update original_dljh_lst set whetherQs = ?,bk_chk = ?,whetherErroeHandle = ? where deduct_sys_stance = ? and trademsg_type = ? and deduct_roll_bk = ? and deduct_stlm_date BETWEEN ? and ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, bkchk);
				sqlQuery.setInteger(2, whetherError);
				sqlQuery.setString(3, reqSysStance);
				sqlQuery.setInteger(4, tradeMsgType);
				sqlQuery.setBoolean(5, deductRollBk);
				sqlQuery.setParameter(6,  startTime);
				sqlQuery.setParameter(7,  endTime);
				int count =  sqlQuery.executeUpdate();
				if(count > 0 ){
					transaction.commit();
					flag = true;
				}
			} catch (Exception e) {
				log.error(e);
				transaction.rollback();
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
	public boolean updateCleanRiqie(String reqSysStance, boolean clean,
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
				SQLQuery sqlQuery = session.createSQLQuery("update original_dljh_lst set whetherQs = ?,bk_chk = ?,whetherErroeHandle = ? where deduct_sys_stance = ? and deduct_roll_bk = ? and deduct_stlm_date BETWEEN ? and ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, bkchk);
				sqlQuery.setInteger(2, whetherError);
				sqlQuery.setString(3, reqSysStance);
				sqlQuery.setBoolean(4, deductRollBk);
				sqlQuery.setParameter(5,  startTime);
				sqlQuery.setParameter(6,  endTime);
				int count =  sqlQuery.executeUpdate();
				if(count > 0 ){
					transaction.commit();
					flag = true;
				}
			} catch (Exception e) {
				log.error(e);
				transaction.rollback();
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
	public boolean updateSettleInfoRiqie(String reqSysStance, 
			boolean whetherTk,Double zf_fee, String zf_file_fee, Integer tradeMsgType,
			boolean deductRollBk, String originalReqTime,Integer whetherReturnFee) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				Date startTime = DYDataUtil.getformatConversionDate3(originalReqTime);
				Date endTime = DYDataUtil.getformatConversionDate4(originalReqTime);
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
				SQLQuery sqlQuery = session.createSQLQuery("update original_dljh_lst set whetherTk = ?,zf_file_fee = ?,zf_fee = ?,zf_fee_bj = ? where deduct_sys_stance = ? and trademsg_type = ? and deduct_roll_bk = ? and deduct_stlm_date BETWEEN ? and ?");
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
	public List<OriginalDljhLst> queryDljhDzSucessOfNotRollBk(String date) {
		Session session = null;
		List<OriginalDljhLst> list = null; 
		if(date == null){
			log.error("OriginalDljhLstDAO queryDljhDzSucessOfNotRollBk date param is not null");
			return null;
		}
		try {
			session = this.getSession();
			Date startTime = DYDataUtil.getformatConversionDate3(date);
			Date endTime = DYDataUtil.getformatConversionDate4(date);
			Query query = session.createQuery("from OriginalDljhLst where  DeductStlmDate BETWEEN ? and ? and WhetherQs = ? and DeductRollBk = ?");
			query.setParameter(0, startTime);
			query.setParameter(1, endTime);
			query.setBoolean(2, true);
			query.setBoolean(3, false);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}else{
				log.warn("from OriginalDljhLst where  DeductStlmDate like "+date +" and WhetherQs = true and DeductRollBk = false is not data");
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
	public List<OriginalDljhLst> quertDljgDzSucessOfRollBk(String date) {
		Session session = null;
		List<OriginalDljhLst> list = null;
		if(StringUtils.isNotBlank(date)){
			try{
				session = this.getSession();
				Date startTime = DYDataUtil.getformatConversionDate3(date);
				Date endTime = DYDataUtil.getformatConversionDate4(date);
				Query query = session.createQuery("from OriginalDljhLst where DeductStlmDate BETWEEN ? and ? and DeductRollBk = true and DeductRollBkResponse in ('00','N1') and WhetherQs = true group by ReqSysStance HAVING count(*) >=1 order by ReqSysStance");
				query.setParameter(0, startTime);
				query.setParameter(1, endTime);
				list = query.list();
				if(list == null || list.size() == 0){
					log.info("日期为"+date+"的大连交行冲正成功且对账成功数据为0");
				}
			}catch(Exception e){
				log.error(e);
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.warn("日期参数不能为空");
		}
		return list;
	}


}