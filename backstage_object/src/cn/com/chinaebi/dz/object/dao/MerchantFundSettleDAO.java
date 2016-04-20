package cn.com.chinaebi.dz.object.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.dz.object.Hlog;
import cn.com.chinaebi.dz.object.MerchantFundSettle;
import cn.com.chinaebi.dz.object.base.BaseMerchantFundSettleDAO;

public class MerchantFundSettleDAO extends BaseMerchantFundSettleDAO implements cn.com.chinaebi.dz.object.dao.iface.MerchantFundSettleDAO {

	public MerchantFundSettleDAO () {}
	
	public MerchantFundSettleDAO (Session session) {
		super(session);
	}

	private static Log log = LogFactory.getLog(MerchantFundSettleDAO.class);
	
	@Override
	public List<MerchantFundSettle> queryMerchantSettleData(int startDate,
			int endDate, int settleAccountType) {
		List<MerchantFundSettle> list = null;
		Session session = null;
		Transaction transaction = null;
		try{
			session = this.getSession();
			transaction = session.beginTransaction();
			StringBuffer sql = new StringBuffer("");
			sql.append("FROM MerchantFundSettle WHERE EndDate ");
			sql.append(" BETWEEN ");
			sql.append(startDate);
			sql.append(" AND ");
			sql.append(endDate);
			sql.append(" AND SettleState = 2 AND SettleWay = ");
			sql.append(settleAccountType);
			transaction.commit();
			Query query = session.createQuery(sql.toString());
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}else{
				log.warn(" not data");
			}
		}catch(Exception e){
			log.error("查询商户结算信息时抛出异常:"+e);
		} finally {
			closeSession(session);
		}
		return list;
	}
	
	/**
	 * 根据ID获得需要代付的
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<MerchantFundSettle> queryNeedDeductedMerList(String id) {
		List<MerchantFundSettle> list = null;
		Session session = null;
		try{
			session = this.getSession();
			if (id != null || id != "") {
				Query query = session.createQuery("from MerchantFundSettle where Id = ?");
				query.setString(0, id);
				List listResult = query.list();
				if(listResult != null && listResult.size() > 0){
					list = listResult;
				}
			}
		}catch(Exception e){
			log.error("查询商户代付数据信息时抛出异常:"+e.getMessage());
		} finally {
			closeSession(session);
		}
		return list;
	}

	@Override
	public boolean updateMerchantFundSettleDfResult(Integer id, Integer df_result,String errorMsg) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		try{
			session = this.getSession();
			transaction = session.beginTransaction();
			SQLQuery query = session.createSQLQuery("update merchant_fund_settle set syn_result = ?, error_msg = ? where id = ?");
			query.setParameter(0, df_result);
			query.setParameter(1, errorMsg);
			query.setParameter(2, id);
			int count = query.executeUpdate();
			if(count > 0){
				transaction.commit();
				flag = true;
			}
		} catch(Exception e) {
			log.error("根据商户ID修改商户划款状态时出现异常:"+e.getMessage());
			transaction.rollback();
		} finally {
			closeSession(session);
		}
		return flag;
	}

	@Override
	public int queryCheckEndDate(Integer deductStlmDate, String mid) {
		Session session = null;
		Transaction transaction = null;
		int endDate = 0;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			SQLQuery sqlQuery = session.createSQLQuery("select end_date from merchant_fund_settle where end_date <= ? and mer_code = ? ORDER BY end_date desc LIMIT 1");
			sqlQuery.setParameter(0, deductStlmDate);
			sqlQuery.setParameter(1, mid);
			Object obj = sqlQuery.uniqueResult();
			if(obj != null ){
				endDate = Integer.valueOf(obj.toString());
			}
		}catch(Exception e) {
			log.error(e.getMessage());
			transaction.rollback();
		} finally {
			closeSession(session);
		}
		return endDate;
	}

	@Override
	public List<MerchantFundSettle> getMerchantFundSettlePageData(
			Map<String, String[]> map) throws Exception {
		List<MerchantFundSettle> list = null;
		Session session = null;
		try{
			session = this.getSession();
			StringBuffer sb = new StringBuffer("");
			sb.append("SELECT * FROM merchant_fund_settle WHERE 1=1 ");
			for(Map.Entry<String,String[]> entry:map.entrySet()){
				if("pageNo".equals(entry.getKey()) || "pageNum".equals(entry.getKey()) || "version".equals(entry.getKey()) 
						|| "tranCode".equals(entry.getKey()) || "merPriv".equals(entry.getKey())
						|| "start_date".equals(entry.getKey()) || "end_date".equals(entry.getKey())){
					continue;
				}else{
					sb.append(" and ");
					sb.append(entry.getKey());
					sb.append("=");
					sb.append(entry.getValue()[0]);
				}
			}
			
			sb.append(" and end_date between ");
			sb.append(map.get("start_date")[0]);
			sb.append(" and ");
			sb.append(map.get("end_date")[0]);
			
			sb.append(" order by end_date desc limit ");
			sb.append((Integer.valueOf(map.get("pageNo")[0])-1)*(Integer.valueOf(map.get("pageNum")[0])));
			sb.append(",");
			sb.append(map.get("pageNum")[0]);
			
			SQLQuery query = session.createSQLQuery(sb.toString()).addEntity(MerchantFundSettle.class);
			list = query.list();
		}catch(Exception e){
			log.error(e);
			throw e;
		}
		return list;
	}

	@Override
	public List<MerchantFundSettle> getMerchantFundSettleDataList(
			Map<String, String[]> map) throws Exception {
		List<MerchantFundSettle> list = null;
		Session session = null;
		try{
			session = this.getSession();
			StringBuffer sb = new StringBuffer("");
			sb.append("SELECT * FROM merchant_fund_settle WHERE 1=1 ");
			for(Map.Entry<String,String[]> entry:map.entrySet()){
				if("version".equals(entry.getKey()) || "tranCode".equals(entry.getKey()) 
						|| "merPriv".equals(entry.getKey()) || "start_date".equals(entry.getKey()) || "end_date".equals(entry.getKey())){
					continue;
				}else{
					sb.append(" and ");
					sb.append(entry.getKey());
					sb.append("=");
					sb.append(entry.getValue()[0]);
				}
			}
			
			sb.append(" and end_date between ");
			sb.append(map.get("start_date")[0]);
			sb.append(" and ");
			sb.append(map.get("end_date")[0]);
			
			sb.append(" order by end_date desc ");
			
			SQLQuery query = session.createSQLQuery(sb.toString()).addEntity(MerchantFundSettle.class);
			list = query.list();
		}catch(Exception e){
			log.error(e);
			throw e;
		}
		return list;
	}
}