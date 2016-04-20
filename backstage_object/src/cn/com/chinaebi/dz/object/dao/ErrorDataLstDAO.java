package cn.com.chinaebi.dz.object.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.dz.object.ErrorDataLst;
import cn.com.chinaebi.dz.object.base.BaseErrorDataLstDAO;
import cn.com.chinaebi.dz.util.ConnectionUtil;
import cn.com.chinaebi.dz.util.DYDataUtil;


public class ErrorDataLstDAO extends BaseErrorDataLstDAO implements cn.com.chinaebi.dz.object.dao.iface.ErrorDataLstDAO {

	private static Log log = LogFactory.getLog(ErrorDataLstDAO.class);
	public ErrorDataLstDAO () {}
	
	public ErrorDataLstDAO (Session session) {
		super(session);
	}
	public boolean deleteErrorCupsDataByTradeTime(String tradeTime){
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(tradeTime)){
			try{
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery query = session.createSQLQuery("delete from error_data_lst where substring(trade_time,0,10) = ?");
				query.setString(0, tradeTime);
				int count = query.executeUpdate();
				if(count > 0){
					flag = true;
					transaction.commit();
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

	@Override
	public List<ErrorDataLst> findErrorDataCupsbankLst(String errorDataReqTime) {
		Session session = null;
		List<ErrorDataLst> list = null; 
		if(StringUtils.isEmpty(errorDataReqTime)){
			log.warn("ErrorDataLstDAO findErrorDataCupsbankLst errorDataReqTime param is not null");
			return null;
		}
		try {
			session = this.getSession();
			Query query = session.createQuery("from ErrorDataLst where substring(TradeTime,1,10) = ? and handling_status = 2 order by TradeTime asc");
			query.setString(0, errorDataReqTime);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}else{
				log.warn("from ErrorDataLst where (substring(TradeTime,1,10) = '"+errorDataReqTime+"' and handling_status = 2 is not data");
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
	public boolean updateClean(String reqSysStance, boolean clean,int duizhangFlag) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update error_data_lst set whetherJs = ?,bk_chk = ? where deduct_sys_stance = ?");
				sqlQuery.setBoolean(0, clean);
				sqlQuery.setInteger(1, duizhangFlag);
				sqlQuery.setString(2, reqSysStance);
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
			log.error(reqSysStance +" 流水号不存在");
		}
		return flag;
	}

	public List<ErrorDataLst> queryErrorDzSucessData(String date,List<Integer> ids,List<String> codeArr,int generateNumber) {
		List<ErrorDataLst> list = null;
		Session session = null;
		if(StringUtils.isEmpty(date)){
			log.error("ErrorDataLstDAO queryErrorDzSucessData date param is not null");
			return null;
		}
		try {
			session = this.getSession();
			StringBuffer sql = new StringBuffer("");
			sql.append("from ErrorDataLst  where CheckTime is not NULL and substring(CheckTime,1,10) = ? and ( ( (DeductSysId = 11 or DeductSysId = 55001) and HandlingId = 1 ) or ( DeductSysId != 11 and DeductSysId != 55001 and HandlingId in (1,2) and DeductSysId in("+ids.toString().substring(1, ids.toString().length()-1)+") ) ) and HandlingStatus = 2 and DoubleCheckStatus = 0 ");
			
			if(codeArr != null && codeArr.size() > 0){
				if(generateNumber == 2){
					sql.append(" and ReqMerCode in ( ");
					sql.append(codeArr.toString().substring(1, codeArr.toString().length()-1));
					sql.append(" )");
				}else if(generateNumber == 3){
					sql.append(" IF(CHAR_LENGTH(TerminalInfo) = 58 or CHAR_LENGTH(TerminalInfo) = 51 ,substring(TerminalInfo,27, 3) ,substring(TerminalInfo,35, 3) ) in ( ");
					sql.append(codeArr.toString().substring(1, codeArr.toString().length()-1));
					sql.append(" )");
				}
			}
			
			sql.append(" order by CheckTime asc");
			Query query = session.createQuery(sql.toString());
			query.setString(0, date);
			
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}else{
				log.warn("from ErrorDataLst  where CheckTime is not NULL and substring(CheckTime,1,10) = '"+date+"' and DeductSysId in("+ids.toString().substring(1, ids.toString().length()-1)+") and HandlingStatus = 2 and case when DeductSysId = 11 then (HandlingId = 1) else ( HandlingId in (1,2) ) end and HandlingStatus = 2 and DoubleCheckStatus = 0 order by CheckTime asc  is not data");
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
	
	public String getTradeCodeName(String tradeCode){
		String name = "";
		Session session = null;
		try{
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select trade_name from trade_type where trade_code = '"+tradeCode+"'");
			name = query.uniqueResult()+"";
		}catch(Exception e){
			log.error(e);
		}finally {
			if (session != null) {
				session.close();
			}
		}
		return name;
	}

	@Override
	public boolean saveErrorData(ErrorDataLst errorDataLst) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		try{
			StringBuffer sb = new StringBuffer();
			if(errorDataLst.isWhetherTk()){
				errorDataLst.setMerFee(0-errorDataLst.getMerFee());
				errorDataLst.setZfFee(0-errorDataLst.getZfFee());
				sb.append("-");
				sb.append(errorDataLst.getZfFileFee());
			}else{
				sb.append(errorDataLst.getZfFileFee());
			}
			errorDataLst.setZfFileFee(sb.toString());
			session = this.getSession();
			transaction = session.beginTransaction();
			SQLQuery query = session.createSQLQuery("select handling_status from error_data_lst where trade_id = ?");
			query.setString(0, errorDataLst.getId());
			Object data = query.uniqueResult();
			if(data == null){
				save(errorDataLst);
				transaction.commit();
				flag = true;
			}
		}catch(Exception e){
			log.error(e);
			transaction.rollback();
		}finally{
			closeSession(session);
		}
		return flag;
	}
	/**
	 * 根据内部差错处理方式id查询处理方式中文名称
	 * @param handling_id
	 * @return
	 */
	public String getInnerErrorHandlingName(int handling_id){
		Session session = null;
		String handling_name = "";
		try{
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select handling_name from inner_error_handling where handling_id = ? ");
			query.setInteger(0, handling_id);
			handling_name = query.uniqueResult()+""; 
		}catch(Exception e){
			log.error(e);
		}finally {
			if (session != null) {
				session.close();
			}
		}
		return handling_name;
	}

	@Override
	public boolean deleteErrorData(String id) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(id)){
			try{
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery query = session.createSQLQuery("delete from error_data_lst where trade_id = ?");
				query.setString(0, id);
				int count = query.executeUpdate();
				if(count > 0){
					flag = true;
					transaction.commit();
				}
			}catch(Exception e){
				transaction.rollback();
				log.error(e.getMessage());
			}finally {
				closeSession(session);
			}
		}
		return flag;
	}
	
	
	public int getTotalErrorDzDataNum(String date,List<Integer> ids,List<String> codeArr,int generateNumber){
		Session session = null;
		if(StringUtils.isEmpty(date)){
			log.error("ErrorDataLstDAO queryErrorDzSucessData date param is not null");
			return 0;
		}
		try {
			session = this.getSession();
			StringBuffer sql = new StringBuffer("");
			sql.append("select count(*) from ErrorDataLst where CheckTime is not null and substring(CheckTime,1,10) = ? and ( ((DeductSysId = 11 or DeductSysId = 55001) and HandlingId = 1 ) or ( DeductSysId != 11 and DeductSysId != 55001 and HandlingId in (1,2) and DeductSysId in("+ids.toString().substring(1, ids.toString().length()-1)+") ) ) and HandlingStatus = 2 and DoubleCheckStatus = 0 ");
			if(codeArr != null && codeArr.size() > 0){
				if(generateNumber == 2){
					sql.append(" and ReqMerCode in ( ");
					sql.append(codeArr.toString().substring(1, codeArr.toString().length()-1));
					sql.append(" )");
				}else if(generateNumber == 3){
					sql.append(" IF(CHAR_LENGTH(TerminalInfo) = 58 or CHAR_LENGTH(TerminalInfo) = 51 ,substring(TerminalInfo,27, 3) ,substring(TerminalInfo,35, 3) ) in ( ");
					sql.append(codeArr.toString().substring(1, codeArr.toString().length()-1));
					sql.append(" )");
				}
			}
			
			sql.append(" order by CheckTime asc");
			Query query = session.createQuery(sql.toString());
			query.setString(0, date);
			return query.uniqueResult()==null?0:((Long) query.uniqueResult()).intValue();
		}catch (Exception e) {
			log.error(e);
			return 0;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public boolean saveRytDuizhangFileError(Integer deduct_sys_id,String mer_name,int inst_type,int bank_id,
			List<Object> duizhangNotDzList,SimpleDateFormat yyyyMMddHHmmss,SimpleDateFormat yyyyMMdd) {
		Session session = null;
		Connection conn = null; 
		boolean flag = false;
		try {
			session = this.getSession();
			conn = session.connection();
			conn.setAutoCommit(false);
			StringBuffer buffer = new StringBuffer("insert ignore into error_data_lst(");
			buffer.append("trade_id,");
			buffer.append("trade_time,");
			buffer.append("out_account,");
			buffer.append("out_acc_valid_time,");
			buffer.append("trade_amount,");
			buffer.append("zf_file_fee,");
			buffer.append("trade_result,");
			buffer.append("req_sys_stance,");
			buffer.append("req_response,");
			buffer.append("deduct_sys_id,");
			buffer.append("deduct_sys_stance,");
			buffer.append("deduct_result,");
			buffer.append("deduct_sys_response,");
			buffer.append("deduct_roll_bk,");
			buffer.append("req_time,");
			buffer.append("deduct_stlm_date,");
			buffer.append("deduct_sys_time,");
			buffer.append("trademsg_type,");
			buffer.append("req_process,");
			buffer.append("mer_name,");
			buffer.append("whetherQs,");
			buffer.append("bk_chk,");
			buffer.append("error_resource,");
			buffer.append("inst_type,");
			buffer.append("trade_type,");
			buffer.append("whetherErroeHandle,");
			buffer.append("bank_id,");
			buffer.append("additional_data,");
			buffer.append("nii,");
			buffer.append("custom_define_info)");
			buffer.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			PreparedStatement stmt = conn.prepareStatement(buffer.toString());
			int i = 0;
			for (Object object : duizhangNotDzList) {
				Object[] notDzObj = (Object[])object;
				String id = notDzObj[0].toString();
				String tradeAmount = notDzObj[1].toString();
				tradeAmount = String.format("%.2f",Double.valueOf(tradeAmount)).replace(".", "");
				String zf_file_fee = notDzObj[2].toString();
				String reqSysStance = notDzObj[3].toString();
				String oid = notDzObj[4] == null ? "": notDzObj[4].toString();
				String deductstmldate = notDzObj[5].toString();
				Date deductStmlDate = yyyyMMdd.parse(deductstmldate);
				String reqTime = notDzObj[6].toString();
				Date tradeTime = yyyyMMddHHmmss.parse(reqTime);
				String out_account = notDzObj[7] == null ? "" : notDzObj[7].toString();
				String dz_file_name = notDzObj[8] == null ? "" : notDzObj[8].toString();
				stmt.setObject(1, id);
				stmt.setObject(2, tradeTime);
				stmt.setObject(3, out_account);
				stmt.setObject(4, tradeTime);
				stmt.setObject(5, tradeAmount);
				stmt.setObject(6, zf_file_fee);
				stmt.setObject(7, 0);
				stmt.setObject(8, reqSysStance);
				stmt.setObject(9, "00");
				stmt.setObject(10, deduct_sys_id);
				stmt.setObject(11, reqSysStance);
				stmt.setObject(12, 0);
				stmt.setObject(13, "00");
				stmt.setObject(14, 0);
				stmt.setObject(15, tradeTime);
				stmt.setObject(16, deductStmlDate);
				stmt.setObject(17, tradeTime);
				stmt.setObject(18, 2);
				stmt.setObject(19, "910000");
				stmt.setObject(20, mer_name);
				stmt.setObject(21, 0);
				stmt.setObject(22, 0);
				stmt.setObject(23, 1);
				stmt.setObject(24, inst_type);
				stmt.setObject(25, 2);
				stmt.setObject(26, 1);
				stmt.setObject(27, bank_id);
				stmt.setObject(28, oid);
				stmt.setObject(29, dz_file_name);
				stmt.setObject(30, id);
				stmt.addBatch();
				if(i%100 == 0){
					stmt.executeBatch();
				}
				i++;
			}
			stmt.executeBatch();
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			if(conn != null){
				try {
					if(conn != null){
						conn.rollback();
					}
				} catch (SQLException e1) {
					log.info("事务回滚异常:"+e1);
				}
			}
		}finally{
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				log.info("conn关闭异常:"+e);
			}
			closeSession(session);
		}
		return flag;
	}
	
	@Override
	public boolean saveRytOriginalTradeError(String duizhangId,Integer bk_chk,Integer sys_date,Integer sys_time,Date deduct_stlm_date,Integer tstat,String tseq,String oid,String out_account,Long trade_amount,double mer_fee,double zf_fee,String zf_file_fee,Integer deduct_sys_id,int inst_type,int whetherErroeHandle,String mid,int bank_id){
		boolean flag = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			String reqSysStance = tseq;
			String date = DYDataUtil.getRyfDateHandler(sys_date,sys_time);
			Date trade_Time = DYDataUtil.getSimpleDateFormat(DYDataUtil.DATE_FORMAT_1).parse(date);
			Integer zfFeeBj = 2;//0:无需比对、1:比对成功、2:比对失败  ->默认比对失败
			if(StringUtils.equals(String.valueOf(zf_fee), zf_file_fee)){
				zfFeeBj = 1;
			}
			StringBuffer buffer = new StringBuffer("insert ignore into error_data_lst(");
				buffer.append("trade_id,");
				buffer.append("trade_time,");
				buffer.append("out_account,");
				buffer.append("out_acc_valid_time,");
				buffer.append("trade_amount,");
				buffer.append("mer_fee,");
				buffer.append("trade_result,");
				buffer.append("req_sys_stance,");
				buffer.append("req_response,");
				buffer.append("deduct_sys_id,");
				buffer.append("deduct_sys_stance,");
				buffer.append("deduct_result,");
				buffer.append("deduct_sys_response,");
				buffer.append("deduct_roll_bk,");
				buffer.append("req_time,");
				buffer.append("deduct_stlm_date,");
				buffer.append("deduct_sys_time,");
				buffer.append("trademsg_type,");
				buffer.append("req_process,");
				buffer.append("whetherQs,");
				buffer.append("bk_chk,");
				buffer.append("error_resource,");
				buffer.append("inst_type,");
				buffer.append("zf_fee,");
				buffer.append("zf_file_fee,");
				buffer.append("additional_data,");
				buffer.append("trade_type,");
				buffer.append("zf_fee_bj,");
				buffer.append("whetherErroeHandle,");
				buffer.append("req_mer_code,");
				buffer.append("bank_id,");
				buffer.append("custom_define_info)");
				buffer.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			SQLQuery sqlQuery = session.createSQLQuery(buffer.toString());
			sqlQuery.setParameter(0, tseq);
			sqlQuery.setParameter(1, trade_Time); //trade_time
			sqlQuery.setParameter(2, out_account); //out_account
			sqlQuery.setParameter(3, trade_Time); //out_acc_valid_time
			sqlQuery.setParameter(4, trade_amount);//trade_amount
			sqlQuery.setParameter(5, mer_fee); //mer_fee
			sqlQuery.setParameter(7, reqSysStance);//req_sys_stance
			if(tstat == 2){
				sqlQuery.setParameter(6, 0);//trade_result
				sqlQuery.setParameter(8, "00");//req_response
				sqlQuery.setParameter(11, 0);//deduct_result
				sqlQuery.setParameter(12, "00");//deduct_sys_response
			}else{
				sqlQuery.setParameter(6, 2);
				sqlQuery.setParameter(8, "N2");
				sqlQuery.setParameter(11, 2);
				sqlQuery.setParameter(12, "N2");
			}
			sqlQuery.setParameter(9, deduct_sys_id);//deduct_sys_id
			sqlQuery.setParameter(10, reqSysStance);//deduct_sys_stance
			sqlQuery.setParameter(13, 0);//deduct_roll_bk
			sqlQuery.setParameter(14, trade_Time);//req_time
			sqlQuery.setParameter(15, deduct_stlm_date);//deduct_stlm_date
			sqlQuery.setParameter(16, trade_Time);//deduct_sys_time
			sqlQuery.setParameter(17, 2);//trademsg_type
			sqlQuery.setParameter(18, "910000");//req_process
			sqlQuery.setParameter(19, 0);//whetherQs
			sqlQuery.setParameter(20, bk_chk);//bk_chk
			sqlQuery.setParameter(21, 0);//error_resource 原始交易差错
			sqlQuery.setParameter(22, inst_type);//inst_type
			sqlQuery.setParameter(23, zf_fee);//zf_file
			sqlQuery.setParameter(24, zf_file_fee);//zf_file_fee
			sqlQuery.setParameter(25, oid);//additional_data
			sqlQuery.setParameter(26, 2);//trade_type
			sqlQuery.setParameter(27, zfFeeBj);//zf_fee_bj
			sqlQuery.setParameter(28, whetherErroeHandle);//whetherErroeHandle
			sqlQuery.setParameter(29, mid);//req_mer_code
			sqlQuery.setParameter(30, bank_id);//bank_id
			sqlQuery.setParameter(31, duizhangId);//  对账单主键ID
			int insert_count = sqlQuery.executeUpdate();
			if(insert_count > -1){
				flag = true;
				transaction.commit();
			}
		} catch (Exception e) {
			log.error(e);
			transaction.rollback();
		}finally{
			closeSession(session);
		}
		return flag;
	}
	
	@Override
	public boolean saveRytOriginalTradeQsNoDzError(Integer sys_date,Integer sys_time,Date deduct_stlm_date,Integer tstat,String tseq,String oid,String out_account,Long trade_amount,double mer_fee,double zf_fee,String zf_file_fee,Integer deduct_sys_id,int inst_type,int whetherErroeHandle,String mid,int bank_id){
		boolean flag = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			String reqSysStance = tseq;
			String date = DYDataUtil.getRyfDateHandler(sys_date,sys_time);
			Date trade_Time = DYDataUtil.getSimpleDateFormat(DYDataUtil.DATE_FORMAT_1).parse(date);
			Integer zfFeeBj = 2;//0:无需比对、1:比对成功、2:比对失败  ->默认比对失败
			if(StringUtils.equals(String.valueOf(zf_fee), zf_file_fee)){
				zfFeeBj = 1;
			}
			StringBuffer buffer = new StringBuffer("insert ignore into error_data_lst(");
				buffer.append("trade_id,");
				buffer.append("trade_time,");
				buffer.append("out_account,");
				buffer.append("out_acc_valid_time,");
				buffer.append("trade_amount,");
				buffer.append("mer_fee,");
				buffer.append("trade_result,");
				buffer.append("req_sys_stance,");
				buffer.append("req_response,");
				buffer.append("deduct_sys_id,");
				buffer.append("deduct_sys_stance,");
				buffer.append("deduct_result,");
				buffer.append("deduct_sys_response,");
				buffer.append("deduct_roll_bk,");
				buffer.append("req_time,");
				buffer.append("deduct_stlm_date,");
				buffer.append("deduct_sys_time,");
				buffer.append("trademsg_type,");
				buffer.append("req_process,");
				buffer.append("whetherQs,");
				buffer.append("bk_chk,");
				buffer.append("error_resource,");
				buffer.append("inst_type,");
				buffer.append("zf_fee,");
				buffer.append("zf_file_fee,");
				buffer.append("additional_data,");
				buffer.append("trade_type,");
				buffer.append("zf_fee_bj,");
				buffer.append("whetherErroeHandle,");
				buffer.append("req_mer_code,");
				buffer.append("bank_id)");
				buffer.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			SQLQuery sqlQuery = session.createSQLQuery(buffer.toString());
			sqlQuery.setParameter(0, tseq);
			sqlQuery.setParameter(1, trade_Time); //trade_time
			sqlQuery.setParameter(2, out_account); //out_account
			sqlQuery.setParameter(3, trade_Time); //out_acc_valid_time
			sqlQuery.setParameter(4, trade_amount);//trade_amount
			sqlQuery.setParameter(5, mer_fee); //mer_fee
			sqlQuery.setParameter(7, reqSysStance);//req_sys_stance
			if(tstat == 2){
				sqlQuery.setParameter(6, 0);//trade_result
				sqlQuery.setParameter(8, "00");//req_response
				sqlQuery.setParameter(11, 0);//deduct_result
				sqlQuery.setParameter(12, "00");//deduct_sys_response
			}else{
				sqlQuery.setParameter(6, 2);
				sqlQuery.setParameter(8, "N2");
				sqlQuery.setParameter(11, 2);
				sqlQuery.setParameter(12, "N2");
			}
			sqlQuery.setParameter(9, deduct_sys_id);//deduct_sys_id
			sqlQuery.setParameter(10, reqSysStance);//deduct_sys_stance
			sqlQuery.setParameter(13, 0);//deduct_roll_bk
			sqlQuery.setParameter(14, trade_Time);//req_time
			sqlQuery.setParameter(15, deduct_stlm_date);//deduct_stlm_date
			sqlQuery.setParameter(16, trade_Time);//deduct_sys_time
			sqlQuery.setParameter(17, 2);//trademsg_type
			sqlQuery.setParameter(18, "910000");//req_process
			sqlQuery.setParameter(19, 1);//whetherQs
			sqlQuery.setParameter(20, 0);//bk_chk
			sqlQuery.setParameter(21, 0);//error_resource 原始交易差错
			sqlQuery.setParameter(22, inst_type);//inst_type
			sqlQuery.setParameter(23, zf_fee);//zf_file
			sqlQuery.setParameter(24, zf_file_fee);//zf_file_fee
			sqlQuery.setParameter(25, oid);//additional_data
			sqlQuery.setParameter(26, 2);//trade_type
			sqlQuery.setParameter(27, zfFeeBj);//zf_fee_bj
			sqlQuery.setParameter(28, whetherErroeHandle);//whetherErroeHandle
			sqlQuery.setParameter(29, mid);//req_mer_code
			sqlQuery.setParameter(30, bank_id);//bank_id
//			sqlQuery.setParameter(31, 3);//handling_id
//			sqlQuery.setParameter(32, DYDataUtil.getSimpleDateFormat(DYDataUtil.DATE_FORMAT_1).format(new Date()));//handling_time
//			sqlQuery.setParameter(33, 4);//handling_status
			int insert_count = sqlQuery.executeUpdate();
			if(insert_count > -1){
				flag = true;
				transaction.commit();
			}
		} catch (Exception e) {
			log.error(e);
			transaction.rollback();
		}finally{
			closeSession(session);
		}
		return flag;
	}

	@Override
	public List<ErrorDataLst> findOriErrorChannelData(String deductStmlDate,
			Integer error_resource, Integer instId, Integer instType) {
		Session session = null;
		List<ErrorDataLst> list = null;
		try {
			session = this.getSession();
			Date startTime = DYDataUtil.getformatConversionDate3(deductStmlDate);
			Date endTime = DYDataUtil.getformatConversionDate4(deductStmlDate);
			Query query = session.createQuery("from ErrorDataLst where DeductStlmDate BETWEEN ? and  ? and DeductSysId = ? and InstType = ? and ErrorResource = ?");
			query.setParameter(0, startTime);
			query.setParameter(1, endTime);
			query.setParameter(2, instId);
			query.setParameter(3, instType);
			query.setParameter(4, error_resource);
			list = query.list();
		}catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return list;
	}

	@Override
	public boolean updateErrorHandlerStatus(String trade_id,
			Integer handlerStatus,Integer doubleCheckStatus, String handlerRemark) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			SQLQuery query = session.createSQLQuery("update error_data_lst set handling_status = ?,double_check_status = ?,cdz_remark = ? where trade_id = ?");
			query.setParameter(0, handlerStatus);
			query.setParameter(1, doubleCheckStatus);
			query.setParameter(2, handlerRemark);
			query.setParameter(3, trade_id);
			int count = query.executeUpdate();
			if(count > -1){
				flag = true;
				transaction.commit();
			}
		}catch (Exception e) {
			log.error(e);
			if(transaction != null)
				transaction.rollback();
		}finally{
			closeSession(session);
		}
		return flag;
	}
	
	public static void main(String[] args) {
		SimpleDateFormat yyyyMMddHHmmss = DYDataUtil.getSimpleDateFormat(DYDataUtil.DATE_FORMAT_2);
		java.sql.Date date = null;
		try {
			Date date2 = yyyyMMddHHmmss.parse("20151120235959");
			System.out.println("time : "+date2.getTime());
			date = new java.sql.Date(date2.getTime());
			System.out.println(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}