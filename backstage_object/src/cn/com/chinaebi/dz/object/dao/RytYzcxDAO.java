package cn.com.chinaebi.dz.object.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.dz.object.RytYzcx;
import cn.com.chinaebi.dz.object.base.BaseRytYzcxDAO;


public class RytYzcxDAO extends BaseRytYzcxDAO implements cn.com.chinaebi.dz.object.dao.iface.RytYzcxDAO {

	private Log log = LogFactory.getLog(getClass());
	public RytYzcxDAO () {}
	
	public RytYzcxDAO (Session session) {
		super(session);
	}

	@Override
	public Object[] selectTradeAmount(String tseq){
		Session session = null;
		Object[] obj = null;
		try {
			session = this.getSession();
			SQLQuery sqlQuery = session.createSQLQuery("select sys_date,sys_time,tstat,tseq,card_no,amount,mer_fee,mid,gid,gate from ryt_yzcx where tseq = ?");
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
	public List findRytYzcxList(String sysDate,Integer gid,int bk_chk){
		Session session = null;
		List list = null;
		try {
			session = this.getSession();
			Integer date = Integer.valueOf(sysDate.replaceAll("-", ""));
			SQLQuery sqlQuery = session.createSQLQuery("select sys_date,sys_time,tstat,tseq,card_no,amount,mer_fee,mid,gid,gate,oid from ryt_jsyh where sys_date = ? and gid = ? and bk_chk = ?");
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
	public List<RytYzcx> selectRytYzcx(String sysDate,Integer tstat,Integer bk_chk){
		List<RytYzcx> list = null;
		Session session = null;
		try {
			Integer date = Integer.valueOf(sysDate.replaceAll("-", ""));
			session = this.getSession();
			Query query = session.createQuery("from RytYzcx where SysDate = ? and Tstat = ? and BkChk = ?");
			query.setInteger(0, date);
			query.setInteger(1, tstat);
			query.setInteger(2, bk_chk);
			list = query.list();
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return list;
	}
	
	@Override
	public Integer selectRytYzcxCount(String sysDate){
		Session session = null;
		Integer count = 0;
		try {
			Integer date = Integer.valueOf(sysDate.replaceAll("-", ""));
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select count(*) from ryt_yzcx where sys_date = ?");
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
	public boolean updateRytYzcx(String tseq,int bk_chk,boolean whetherQs,double zf_fee,String zf_file_fee,double mer_fee,Integer whetherReturnFee,Integer deductStlmDate) {
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
			SQLQuery query = session.createSQLQuery("update ryt_yzcx set bk_chk = ?,whetherQs = ?,zf_fee = ?,zf_file_fee = ?,mer_fee = ?,zf_fee_bj = ?,deduct_stlm_date = ? where tseq = ?");
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
	public boolean updateNoNeedHandle(String sysDate,Integer bk_chk,Integer tstat,boolean whetherQs) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		try {
			Integer date = Integer.valueOf(sysDate);
			session = this.getSession();
			transaction = session.beginTransaction();
			SQLQuery query = session.createSQLQuery("update ryt_yzcx set bk_chk = ?,whetherQs = ? where sys_date = ? and tstat = ?");
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
			log.error(e);
			transaction.rollback();
		}finally{
			closeSession(session);
		}
		return flag;
	}

}