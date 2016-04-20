package cn.com.chinaebi.dz.object.dao;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.dz.object.base.BaseRefundLogDAO;

public class RefundLogDAO extends BaseRefundLogDAO implements cn.com.chinaebi.dz.object.dao.iface.RefundLogDAO {
	
	private Log log = LogFactory.getLog(getClass());
	public RefundLogDAO () {}
	
	public RefundLogDAO (Session session) {
		super(session);
	}

	@Override
	public Object getRytRefundLog(String id) {
		log.info("开始同步主键ID为 " + id + "的融易付退款交易数据");
		Object object = null;
		Session refund_session = null;
		Session duiz_session = null;
		Transaction duiz_transaction = null;
		try {
			refund_session = this.getSession("ryt_hibernate.xml");
			duiz_session = this.getSession();
			if(refund_session  != null && duiz_session != null){
				StringBuffer buffer = new StringBuffer();
				buffer.append("select id,tseq,mdate,mid,oid,org_mdate,org_oid,ref_amt,sys_date,gate,");
				buffer.append(" card_no,user_name,req_date,pro_date,ref_date,stat,reason,etro_reason,");
				buffer.append(" refund_reason,batch,bgRetUrl,vstate,author_type,mer_fee,bk_fee,");
				buffer.append(" bk_fee_real,org_amt,org_bk_seq,gid,pre_amt,org_pay_amt,pre_amt1,");
				buffer.append(" mer_priv,p1,online_refund_id,online_refund_state,");
				buffer.append(" online_refund_reason,refund_type,sys_time from refund_log where id = ?");
				SQLQuery refundSqlQuery = refund_session.createSQLQuery(buffer.toString());
				refundSqlQuery.setInteger(0, Integer.valueOf(id));
				object = refundSqlQuery.uniqueResult();
				
				if (object != null){
					buffer.setLength(0);
					duiz_transaction = duiz_session.beginTransaction();
					Object[] refundArr = (Object[]) object;
					
					//计算商户手续费
//					Integer refAmt = Integer.valueOf(refundArr[7].toString());
//					String mid = refundArr[3].toString();
//					Integer gate = Integer.valueOf(refundArr[9].toString());
//					Integer gid = Integer.valueOf(refundArr[28].toString());
//					double mer_fee = PoundageCalculate.getTradeMerFee(refAmt, gate,mid,gid);
					
					buffer.append("replace into refund_log (id,tseq,mdate,mid,oid,org_mdate,org_oid,ref_amt,sys_date,gate,");
					buffer.append("card_no,user_name,req_date,pro_date,ref_date,stat,reason,etro_reason,");
					buffer.append("refund_reason,batch,bgRetUrl,vstate,author_type,mer_fee,bk_fee,");
					buffer.append("bk_fee_real,org_amt,org_bk_seq,gid,pre_amt,org_pay_amt,pre_amt1,");
					buffer.append("mer_priv,p1,online_refund_id,online_refund_state,");
					buffer.append("online_refund_reason,refund_type,sys_time) values(?,?,?,?,?,");
					buffer.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
					
					SQLQuery duizSqlQuery = duiz_session.createSQLQuery(buffer.toString());
					duizSqlQuery.setParameter(0,refundArr[0]);
					duizSqlQuery.setParameter(1,refundArr[1]);
					duizSqlQuery.setParameter(2,refundArr[2]);
					duizSqlQuery.setParameter(3,refundArr[3]);
					duizSqlQuery.setParameter(4,refundArr[4]);
					duizSqlQuery.setParameter(5,refundArr[5]);
					duizSqlQuery.setParameter(6,refundArr[6]);
					duizSqlQuery.setParameter(7,refundArr[7]);//退款金额
					duizSqlQuery.setParameter(8,refundArr[8]);
					duizSqlQuery.setParameter(9,refundArr[9]);
					duizSqlQuery.setParameter(10,refundArr[10]);
					duizSqlQuery.setParameter(11,refundArr[11]);
					duizSqlQuery.setParameter(12,refundArr[12]);
					duizSqlQuery.setParameter(13,refundArr[13]);
					duizSqlQuery.setParameter(14,refundArr[14]);
					duizSqlQuery.setParameter(15,refundArr[15]);
//					duizSqlQuery.setParameter(16,StringUtils.isNotBlank(refundArr[16].toString()) ? refundArr[16] : "");
					duizSqlQuery.setParameter(16,refundArr[16]);
					duizSqlQuery.setParameter(17,refundArr[17]);
					duizSqlQuery.setParameter(18,refundArr[18]);
					duizSqlQuery.setParameter(19,refundArr[19]);
					duizSqlQuery.setParameter(20,StringUtils.isNotBlank(refundArr[20].toString()) ? refundArr[20] : "");
					duizSqlQuery.setParameter(21,refundArr[21]);
					duizSqlQuery.setParameter(22,refundArr[22]);
					duizSqlQuery.setParameter(23,refundArr[23]);//商户手续费
					duizSqlQuery.setParameter(24,refundArr[24]);
					duizSqlQuery.setParameter(25,refundArr[25]);
					duizSqlQuery.setParameter(26,refundArr[26]);
					duizSqlQuery.setParameter(27,refundArr[27]);
					duizSqlQuery.setParameter(28,refundArr[28]);
					duizSqlQuery.setParameter(29,refundArr[29]);
					duizSqlQuery.setParameter(30,refundArr[30]);
					duizSqlQuery.setParameter(31,refundArr[31]);
					duizSqlQuery.setParameter(32,refundArr[32]);
					duizSqlQuery.setParameter(33,StringUtils.isNotBlank(refundArr[33].toString()) ? refundArr[33] : "");
					duizSqlQuery.setParameter(34,refundArr[34]);
					duizSqlQuery.setParameter(35,refundArr[35]);
					duizSqlQuery.setParameter(36,refundArr[36]);
					duizSqlQuery.setParameter(37,refundArr[37]);
					duizSqlQuery.setParameter(38,refundArr[38]);
//					duizSqlQuery.setParameter(39,mer_fee);//退款商户手续费
					int count = duizSqlQuery.executeUpdate();
					if(count > 0){
						duiz_transaction.commit();
						log.info("主键为" + id + "的融易付线上退款交易数据同步成功");
					}
				}else{
					log.warn(id+" ：数据不存在refund_log表中");
				}
			}
		} catch (Exception e) {
			log.error("融易通线上退款交易数据同步异常:" + e.getMessage());
		} finally {
			if (duiz_session != null) {
				duiz_session.flush();
			}
			if (refund_session != null) {
				refund_session.flush();
			}
			closeSession(duiz_session);
			closeSession(refund_session);
		}
		return object;
	}

	@Override
	public int updateRytRefundLogDataStatus(String id, int stat)throws Exception {
		int effectNum = 0;
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			SQLQuery updateSql = session.createSQLQuery("update refund_log set stat = ? where id = ?");
			updateSql.setParameter(0, stat);
			updateSql.setParameter(1, Integer.valueOf(id));
			effectNum = updateSql.executeUpdate();
			if (effectNum > 0) {
				transaction.commit();
				log.info("根据主键" + id + "修改线上融易付退款交易数据的数据状态成功");
			}
		} catch (Exception e) {
			transaction.rollback();
			log.error("根据主键" + id + "修改线上融易付退款交易数据的数据状态出现异常：" + e.getMessage());
			throw e;
		} finally {
			closeSession(session);
		}
		return effectNum;
	}

	@Override
	public Object queryDataById(String id) {
		Object object = null;
		Session session = null;
		try {
			session = this.getSession();
			StringBuilder sb = new StringBuilder();
			sb.append("select id,tseq,mdate,mid,oid,org_mdate,org_oid,ref_amt,sys_date,gate,");
			sb.append(" card_no,user_name,req_date,pro_date,ref_date,stat,reason,etro_reason,");
			sb.append(" refund_reason,batch,bgRetUrl,vstate,author_type,mer_fee,bk_fee,");
			sb.append(" bk_fee_real,org_amt,org_bk_seq,gid,pre_amt,org_pay_amt,pre_amt1,");
			sb.append(" mer_priv,p1,online_refund_id,online_refund_state,");
			sb.append(" online_refund_reason,refund_type,sys_time from refund_log where id = ?");
			SQLQuery query = session.createSQLQuery(sb.toString());
			query.setInteger(0, Integer.valueOf(id));
			object = query.uniqueResult();
		} catch (Exception e) {
			log.error("根据id" + "获取退款表中的数据出现异常：" + e.getMessage());
		} finally {
			closeSession(session);
		}
		return object;
	}

	@Override
	public boolean updateRytRefundLogColumn(String id,Object ... obj) throws Exception{
		boolean flag = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			StringBuffer buffer = new StringBuffer();
			buffer.append("update refund_log set ");
			int len = obj.length;
			for (int i=0;i<len;i++) {
				if(i%2==0){
					buffer.append(obj[i]);
					buffer.append("=");
					if(obj[i+1] instanceof String){
						buffer.append("'");
						buffer.append(obj[i+1]);
						buffer.append("'");
					}else
						buffer.append(obj[i+1]);
					if(len-i!=2)
						buffer.append(",");
				}
			}
			buffer.append(" where id = ?");
			log.info(buffer.toString());
			SQLQuery updateSql = session.createSQLQuery(buffer.toString());
			updateSql.setLong(0, Long.parseLong(id));
			int effectNum = updateSql.executeUpdate();
			if (effectNum > 0) {
				transaction.commit();
				flag = true;
			}
		} catch (Exception e) {
			transaction.rollback();
			throw e;
		} finally {
			closeSession(session);
		}
		return flag;
	}

	@Override
	public Integer getRefundLog(String id) {
		Integer stat = null;
		Session session = null;
		try {
			session = this.getSession();
			StringBuilder sb = new StringBuilder();
			sb.append("select stat from refund_log where id = ?");
			SQLQuery query = session.createSQLQuery(sb.toString());
			query.setInteger(0, Integer.valueOf(id));
			Object obj = query.uniqueResult();
			if(obj != null){
				stat = Integer.valueOf(obj.toString());
			}
		} catch (Exception e) {
			log.error("根据id" + "获取退款表中的数据出现异常：" + e.getMessage());
		} finally {
			closeSession(session);
		}
		return stat;
	}

	@Override
	public Long addRefundLog(
			String tseq,
			int author_type,
			String mdate,
			String mid,
			String oid,
			int org_mdate,
			String org_oid,
			Long ref_amt,
			int sys_date,
			int gate,
			String card_no,
			String user_name,
			int req_date,
			int vstate,
			int stat,
			String refund_reason,
			int mer_fee,
			int bk_fee,
			int bk_fee_real,
			Long org_amt,
			String org_bk_seq,
			int gid,
			Long org_pay_amt,
			int pre_amt,
			int pre_amt1,
			String mer_priv,
			String p1,
			int sys_time,
			int refund_type) throws Exception{
		Long id = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			StringBuffer buffer = new StringBuffer();
			buffer.append("insert into refund_log(tseq,author_type,mdate,mid,oid,org_mdate,org_oid,ref_amt,sys_date,gate,card_no,user_name,req_date,");
			buffer.append("vstate,stat,refund_reason,mer_fee,bk_fee,bk_fee_real,org_amt,org_bk_seq,gid,org_pay_amt,pre_amt,pre_amt1,mer_priv,p1,sys_time,refund_type) ");
			buffer.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			SQLQuery query = session.createSQLQuery(buffer.toString());
			query.setParameter(0, tseq);
			query.setParameter(1, author_type);
			query.setParameter(2, mdate);
			query.setParameter(3, mid);
			query.setParameter(4, oid);
			query.setParameter(5, org_mdate);
			query.setParameter(6, org_oid);
			query.setParameter(7, ref_amt);
			query.setParameter(8, sys_date);
			query.setParameter(9, gate);
			query.setParameter(10, card_no);
			query.setParameter(11, user_name);
			query.setParameter(12, req_date);
			query.setParameter(13, vstate);
			query.setParameter(14, stat);
			query.setParameter(15, refund_reason);
			query.setParameter(16, mer_fee);
			query.setParameter(17, bk_fee);
			query.setParameter(18, bk_fee_real);
			query.setParameter(19, org_amt);
			query.setParameter(20, org_bk_seq);
			query.setParameter(21, gid);
			query.setParameter(22, org_pay_amt);
			query.setParameter(23, pre_amt);
			query.setParameter(24, pre_amt1);
			query.setParameter(25, mer_priv);
			query.setParameter(26, p1);
			query.setParameter(27, sys_time);
			query.setParameter(28, refund_type);
			int count = query.executeUpdate();
			if(count > -1){
				transaction.commit();
				SQLQuery sqlQuery = session.createSQLQuery("select max(id) from refund_log where tseq = ?");
				sqlQuery.setParameter(0, Long.valueOf(tseq));
				id = Long.valueOf(sqlQuery.uniqueResult().toString());
			}
		} catch (Exception e) {
			log.error(e);
			transaction.rollback();
			throw e;
		}finally{
			closeSession(session);
		}
		return id;
	}

	@Override
	public Long queryOriTseqTkAmt(String tseq) throws Exception {
		Long oriAmt = 0l;
		Session session = null;
		try {
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select sum(ref_amt) from refund_log where tseq = ?");
			query.setParameter(0, Long.valueOf(tseq));
			Object obj = query.uniqueResult();
			if(obj != null){
				oriAmt = Long.valueOf(obj.toString());
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return oriAmt;
	}
	
	
	public static void main(String[] args) {
		Object[] obj = {"stat",7,"etro_reason","测试","etro_reason1","测试1","etro_reason2","测试2"};
		StringBuffer buffer = new StringBuffer();
		buffer.append("update refund_log set ");
		int len = obj.length;
		for (int i=0;i<len;i++) {
			if(i%2==0){
				buffer.append(obj[i]);
				buffer.append("=");
				if(obj[i+1] instanceof String){
					buffer.append("'");
					buffer.append(obj[i+1]);
					buffer.append("'");
				}else
					buffer.append(obj[i+1]);
				if(len-i!=2)
					buffer.append(",");
			}
		}
		buffer.append(" where id = ?");
		System.out.println(buffer.toString());
	}
}