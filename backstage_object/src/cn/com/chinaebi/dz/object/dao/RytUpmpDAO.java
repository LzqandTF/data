package cn.com.chinaebi.dz.object.dao;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.CallableStatement;

import cn.com.chinaebi.dz.object.RytUpmp;
import cn.com.chinaebi.dz.object.base.BaseRytUpmpDAO;


public class RytUpmpDAO extends BaseRytUpmpDAO implements cn.com.chinaebi.dz.object.dao.iface.RytUpmpDAO {

	private Log log = LogFactory.getLog(getClass());
	
	public RytUpmpDAO () {}
	
	public RytUpmpDAO (Session session) {
		super(session);
	}

	/**
	 * 汇总融易通的线上交易数据
	 * @param tradeTime 交易时间
	 * @param tableName 交易数据表名称
	 * @param deduct_sys_id	渠道ID
	 * @return
	 */
	public int dataCollectHanler(String tradeTime,String tableName,int deduct_sys_id){
		Session session = null;
		int count = -1;
		try {
			session = this.getSession();
			if(session  != null){
				if(StringUtils.isNotBlank(tradeTime)){
					tradeTime = tradeTime.replaceAll("-", "");
				}
				CallableStatement cs = (CallableStatement) session.connection().prepareCall("call proce_original_ryt_hlog(?,?,?,?)");
				cs.setObject(1, tradeTime);
				cs.setObject(2, tableName);
				cs.setInt(3, deduct_sys_id);
				cs.registerOutParameter(4, Types.INTEGER);
				cs.execute();
				count = cs.getInt(4) <= 0 ? 0 : cs.getInt(4);
			}else{
				log.error("RytUpmpDAO.dataCollectHanler() 获取session为空");
			}
		} catch (Exception e) {
			log.error("融易通线上交易数据汇总抛出异常:"+e);
		}finally{
			closeSession(session);
		}
		return count;
	}
	
	/**
	 * 通过交易时间，删除融易通的交易数据
	 * @param tradeTime
	 * @return
	 */
	public boolean deleteRytUpmpData(String tradeTime){
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
				String sql = "select count(*) from ryt_upmp where sys_date = ?";
				SQLQuery query = session.createSQLQuery(sql);
				query.setParameter(0, Integer.valueOf(tradeTime));
				Integer count = Integer.valueOf(query.uniqueResult().toString());
				if(count == 0){
					log.info("不存在交易日期为"+tradeTime+"的融易通数据，不需要删除");
					flag = true;
				}else{
					String sql_ = "delete from ryt_upmp where sys_date = ?";
					SQLQuery delete = session.createSQLQuery(sql_);
					delete.setParameter(0, Integer.valueOf(tradeTime));
					int d_count = delete.executeUpdate();
					if(d_count ==0){
						log.info("删除交易日期为"+tradeTime+"的融易通数据失败");
					}else{
						flag = true;
						transaction.commit();
					}
				}
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
	 * 查询融易通线上对账成功的交易数据
	 * @param tradeTime
	 * @return
	 */
	public List<RytUpmp> queryRytUpmpDzSucessData(String tradeTime){
		Session session = null;
		List<RytUpmp> list = null; 
		if(StringUtils.isBlank(tradeTime)){
			log.error("RytUpmpDAO queryRytUpmpDzSucessData tradeTime param is not null");
			return null;
		}else{
			tradeTime = tradeTime.replaceAll("-", "");
		}
		try {
			session = this.getSession();
			Query query = session.createQuery("from RytUpmp where SysDate = ? and WhetherQs = true order by SysDate");
			query.setParameter(0, Integer.valueOf(tradeTime));
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}else{
				log.warn("from RytUpmp where SysDate = ? and WhetherQs = true order by SysDate is not data");
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
	public boolean updateRytUpmp(String tseq, int bk_chk, boolean whetherQs,
			double zf_fee, String zf_file_fee,double mer_fee,Integer whetherReturnFee,Integer deductStlmDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			Integer zfFeeBj = 2;//0:无需比对、1:比对成功、2:比对失败  ->默认比对失败
			zf_file_fee = Double.valueOf(zf_file_fee).toString();
			if(StringUtils.equals(String.valueOf(zf_fee), zf_file_fee)){
				zfFeeBj = 1;
			}
			SQLQuery query = session.createSQLQuery("update ryt_upmp set bk_chk = ?,whetherQs = ?,zf_fee = ?,zf_file_fee = ?,mer_fee = ?,zf_fee_bj = ?,deduct_stlm_date = ? where tseq = ?");
			query.setInteger(0, bk_chk);
			query.setBoolean(1, whetherQs);
			query.setDouble(2, zf_fee);
			query.setString(3, zf_file_fee);
			query.setDouble(4, mer_fee);
			query.setInteger(5, zfFeeBj);
			query.setInteger(6, deductStlmDate);
			query.setLong(7, Long.valueOf(tseq));
			int count = query.executeUpdate();
			if(count > -1){
				transaction.commit();
				flag = true;
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
	public Object[] selectTradeAmount(String tseq) {
		Session session = null;
		Object[] obj = null;
		try {
			session = this.getSession();
			SQLQuery sqlQuery = session.createSQLQuery("select sys_date,sys_time,tstat,oid,card_no,amount,mer_fee,mid,gid,gate,tseq from ryt_upmp where tseq = ?");
			sqlQuery.setLong(0, Long.valueOf(tseq));
			Object object = sqlQuery.uniqueResult();
			if(object != null){
				obj = (Object[])object;
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return obj;
	}
	
	@Override
	public List findRytUpmpList(String sysDate,int gid,int bk_chk){
		Session session = null;
		List list = null;
		try {
			session = this.getSession();
			Integer date = Integer.valueOf(sysDate.replaceAll("-", ""));
			SQLQuery sqlQuery = session.createSQLQuery("select sys_date,sys_time,tstat,oid,card_no,amount,mer_fee,mid,gid,gate,tseq from ryt_upmp where sys_date = ? and gid = ? and bk_chk = ?");
			sqlQuery.setParameter(0, date);
			sqlQuery.setParameter(1, gid);
			sqlQuery.setParameter(2, bk_chk);
			list =  sqlQuery.list();
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return list;
	}

	@Override
	public List<RytUpmp> selectRytUpmp(Integer sysDate, Integer bk_chk) {
		List<RytUpmp> list = null;
		Session session = null;
		try {
			session = this.getSession();
			Query query = session.createQuery("from RytUpmp where SysDate = ? and BkChk = ?");
			query.setInteger(0, sysDate);
			query.setInteger(1, bk_chk);
			list = query.list();
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return list;
	}

	@Override
	public List<RytUpmp> selectNoDzJsRytUpmp(Integer sysDate, Integer bk_chk,
			boolean whetherJs) {
		List<RytUpmp> list = null;
		Session session = null;
		try {
			session = this.getSession();
			Query query = session.createQuery("from RytUpmp where SysDate = ? and BkChk = ? and WhetherJs = ?");
			query.setInteger(0, sysDate);
			query.setInteger(1, bk_chk);
			query.setBoolean(2, whetherJs);
			list = query.list();
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return list;
	}

	@Override
	public List<RytUpmp> selectNoDzJsIsSuccessRytUpmp(Integer sysDate,
			Integer bk_chk, boolean whetherJs, int tstat) {
		List<RytUpmp> list = null;
		Session session = null;
		try {
			session = this.getSession();
			Query query = session.createQuery("from RytUpmp where SysDate = ? and BkChk = ? and WhetherJs = ? and Tstat = ?");
			query.setInteger(0, sysDate);
			query.setInteger(1, bk_chk);
			query.setBoolean(2, whetherJs);
			query.setInteger(3, tstat);
			list = query.list();
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return list;
	}

	@Override
	public Integer selectRytUpmpCount(String sysDate) {
		Session session = null;
		Integer count = 0;
		try {
			Integer date = Integer.valueOf(sysDate.replaceAll("-", ""));
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select count(*) from ryt_upmp where sys_date = ?");
			query.setInteger(0, date);
			count = Integer.valueOf(query.uniqueResult().toString());
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return count;
	}

	@Override
	public boolean updateNoNeedHandle(String sysDate, Integer bk_chk,
			Integer tstat, boolean whetherQs) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		try {
			Integer date = Integer.valueOf(sysDate.replaceAll("-", ""));
			session = this.getSession();
			transaction = session.beginTransaction();
			SQLQuery query = session.createSQLQuery("update ryt_upmp set bk_chk = ?,whetherQs = ? where sys_date = ? and tstat != ? and bk_chk = 0");
			query.setInteger(0, bk_chk);
			query.setBoolean(1, whetherQs);
			query.setInteger(2, date);
			query.setInteger(3, tstat);
			int count = query.executeUpdate();
			if(count > -1){
				transaction.commit();
				flag = true;
			}
		} catch (Exception e) {
			log.error("银联手机支付原始交易未对账，交易失败无需对账处理失败"+e);
			transaction.rollback();
		}finally{
			closeSession(session);
		}
		return flag;
	}

}