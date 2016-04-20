package cn.com.chinaebi.dz.object.dao;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.dz.object.MerchantSettleStatistics;
import cn.com.chinaebi.dz.object.base.BaseMerchantSettleStatisticsDAO;


public class MerchantSettleStatisticsDAO extends BaseMerchantSettleStatisticsDAO implements cn.com.chinaebi.dz.object.dao.iface.MerchantSettleStatisticsDAO {

	private Log log = LogFactory.getLog(getClass());
	public MerchantSettleStatisticsDAO () {}
	
	public MerchantSettleStatisticsDAO (Session session) {
		super(session);
	}

	@Override
	public MerchantSettleStatistics queryMerchantSettleStatistics(String mer_code,
			int deduct_stlm_date, int inst_id,int inst_type, int dataStatus) {
		MerchantSettleStatistics merchantSettleStatistics = null;
		Session session = null;
		try {
			session = this.getSession();
			Query query = session.createQuery("from MerchantSettleStatistics where MerCode = ? and DeductStlmDate = ? and InstId = ? and InstType = ? and DataStatus = ? ");
			query.setParameter(0, mer_code);
			query.setParameter(1, deduct_stlm_date);
			query.setParameter(2, inst_id);
			query.setParameter(3, inst_type);
			query.setParameter(4, dataStatus);
			Object object = query.uniqueResult();
			if (object != null) {
				merchantSettleStatistics = (MerchantSettleStatistics) object;
				log.info("根据商户号：" + mer_code + " 清算日期：" + deduct_stlm_date + "渠道ID：" + inst_id + "获取商户T+1统计表数据成功");
			} else {
				log.warn("根据商户号：" + mer_code + " 清算日期：" + deduct_stlm_date + "渠道ID：" + inst_id + "获取商户T+1统计表数据为空");
			}
		} catch (Exception e) {
			log.error("根据商户号：" + mer_code + " 清算日期：" + deduct_stlm_date + "渠道ID：" + inst_id + "获取商户T+1统计表数据出现异常：" + e.getMessage());
		} finally {
			closeSession(session);
		}
		return merchantSettleStatistics;
	}

	@Override
	public int updateMerchantSettleStatistics(MerchantSettleStatistics merchantSettleStatistics) {
		int effectNum = 0;
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			SQLQuery updateSql = session.createSQLQuery("update merchant_settle_statistics set refund_amount = ?, refund_count = ?, mer_refund_fee = ? " +
					"where mer_code = ? and deduct_stlm_date = ? and inst_id = ? and inst_type = ? and data_status = ?");
			updateSql.setParameter(0, merchantSettleStatistics.getRefundAmount());
			updateSql.setParameter(1, merchantSettleStatistics.getRefundCount());
			updateSql.setParameter(2, merchantSettleStatistics.getMerRefundFee());
			updateSql.setParameter(3, merchantSettleStatistics.getMerCode());
			updateSql.setParameter(4, merchantSettleStatistics.getDeductStlmDate());
			updateSql.setParameter(5, merchantSettleStatistics.getInstId());
			updateSql.setParameter(6, merchantSettleStatistics.getInstType());
			updateSql.setParameter(7, merchantSettleStatistics.getDataStatus());
			effectNum = updateSql.executeUpdate();
			if (effectNum > 0) {
				transaction.commit();
				log.info("根据商户号：" + merchantSettleStatistics.getMerCode() + " 清算日期：" + merchantSettleStatistics.getDeductStlmDate() + "渠道ID：" + 
						merchantSettleStatistics.getInstId() + "修改商户T+1统计表数据成功");
			}
		} catch (Exception e) {
			transaction.rollback();
			log.error("根据商户号：" + merchantSettleStatistics.getMerCode() + " 清算日期：" + merchantSettleStatistics.getDeductStlmDate() + "渠道ID：" + 
						merchantSettleStatistics.getInstId() + "修改商户T+1统计表数据出现异常：" + e.getMessage());
		} finally {
			closeSession(session);
		}
		return effectNum;
	}

	@Override
	public int addMerchantSettleStatistics(
			MerchantSettleStatistics merchantSettleStatistics) {
		int effectNum = 0;
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			SQLQuery insertSql = session.createSQLQuery("insert ignore into merchant_settle_statistics(inst_id, mer_code, mer_type, deduct_stlm_date, trade_amount, trade_count," +
					" refund_amount, refund_count, mer_fee, system_fee, mer_refund_fee, settle_amount, system_refund_fee, whetherJs, data_status, inst_type, refund_zf_fee, bank_id,js_date) values(" +
					"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			insertSql.setParameter(0, merchantSettleStatistics.getInstId());
			insertSql.setParameter(1, merchantSettleStatistics.getMerCode());
			insertSql.setParameter(2, merchantSettleStatistics.getMerType());
			insertSql.setParameter(3, merchantSettleStatistics.getDeductStlmDate());
			insertSql.setParameter(4, merchantSettleStatistics.getTradeAmount());
			insertSql.setParameter(5, merchantSettleStatistics.getTradeCount());
			insertSql.setParameter(6, merchantSettleStatistics.getRefundAmount());
			insertSql.setParameter(7, merchantSettleStatistics.getRefundCount());
			insertSql.setParameter(8, merchantSettleStatistics.getMerFee());
			insertSql.setParameter(9, merchantSettleStatistics.getSystemFee());
			insertSql.setParameter(10, merchantSettleStatistics.getMerRefundFee());
			insertSql.setParameter(11, merchantSettleStatistics.getSettleAmount());
			insertSql.setParameter(12, merchantSettleStatistics.getSystemRefundFee());
			insertSql.setParameter(13, merchantSettleStatistics.isWhetherJs());
			insertSql.setParameter(14, merchantSettleStatistics.getDataStatus());
			insertSql.setParameter(15, merchantSettleStatistics.getInstType());
			insertSql.setParameter(16, merchantSettleStatistics.getRefundZfFee());
			insertSql.setParameter(17, merchantSettleStatistics.getBankId());
			insertSql.setParameter(18, merchantSettleStatistics.getJsDate());
			effectNum = insertSql.executeUpdate();
			if (effectNum > 0) {
				transaction.commit();
				log.info("向商户T+1统计表中插入数据成功");
			}
		} catch (Exception e) {
			log.error("向商户T+1统计表中插入数据出现异常：" + e.getMessage());
		} finally {
			closeSession(session);
		}
		return effectNum;
	}

	@Override
	public boolean deleteMerchantSettleStatistics(String deduct_stlm_date, int inst_id, int inst_type) {
		boolean flag = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			if (StringUtils.isNotBlank(deduct_stlm_date)) {
				deduct_stlm_date = deduct_stlm_date.replaceAll("-", "");
			}
			SQLQuery query = session.createSQLQuery("select count(*) from merchant_settle_statistics where data_status = 0 and deduct_stlm_date = ? and inst_id = ? and inst_type = ?");
			query.setParameter(0, deduct_stlm_date);
			query.setParameter(1, inst_id);
			query.setParameter(2, inst_type);
			Integer count = Integer.valueOf(query.uniqueResult().toString());
			if (count > 0) {
				SQLQuery sql = session.createSQLQuery("delete from merchant_settle_statistics where data_status = 0 and deduct_stlm_date = ? and inst_id = ? and inst_type = ?");
				sql.setParameter(0, deduct_stlm_date);
				sql.setParameter(1, inst_id);
				sql.setParameter(2, inst_type);
				int effectNum = sql.executeUpdate();
				if (effectNum > 0) {
					transaction.commit();
					flag = true;
					log.info("删除商户T+1数据成功");
				} else {
					flag = false;
					log.info("删除T+1数据失败");
				}
			} else {
				log.info("不存在清算日期为" + deduct_stlm_date + "渠道ID为" + inst_id + "渠道类型：" + inst_type + "的T+1数据，不需要执行删除操作");
			}
		} catch (Exception e) {
			transaction.rollback();
			log.error("商户T+1数据还原出现异常：" + e.getMessage());
		} finally {
			closeSession(session);
		}
		return flag;
	}
}