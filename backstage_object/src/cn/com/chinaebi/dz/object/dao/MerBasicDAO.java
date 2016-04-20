package cn.com.chinaebi.dz.object.dao;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.dz.object.MerBasic;
import cn.com.chinaebi.dz.object.MerBilling;
import cn.com.chinaebi.dz.object.base.BaseMerBasicDAO;
import cn.com.chinaebi.dz.util.DYDataUtil;

public class MerBasicDAO extends BaseMerBasicDAO implements cn.com.chinaebi.dz.object.dao.iface.MerBasicDAO {
	
	private Log log =LogFactory.getLog(getClass());

	public MerBasicDAO () {}
	
	public MerBasicDAO (Session session) {
		super(session);
	}

	public Map<String,Object> saveOrUpdateMerInfo(Map<String,Object> map){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Session session = this.getSession();
			Transaction transaction = null;
			try {
				transaction = session.beginTransaction();
				
				MerBasic merBasic = (MerBasic)map.get("merBasic");
				
				StringBuffer merBasicSql = new StringBuffer("");
				
				if(merBasic == null){
					log.info("新增商户基本信息");
					merBasicSql.append("insert into mer_basic(mer_code,mer_name,mer_category,mer_abbreviation,mer_state,city,mer_type");
					merBasicSql.append(",startDate,endDate,channel,expand,expandno,provinces) VALUES(");
					merBasicSql.append("'");
					merBasicSql.append(map.get("innerMerCode"));
					merBasicSql.append("'");
					merBasicSql.append(",'");
					merBasicSql.append(map.get("merName"));
					merBasicSql.append("',");
					merBasicSql.append(map.get("merCategory"));
					merBasicSql.append(",'");
					merBasicSql.append(map.get("merAbbreviation"));
					merBasicSql.append("',");
					if(map.get("industry") != null && StringUtils.isNotBlank(map.get("industry").toString())){	
						merBasicSql.append(Integer.valueOf(map.get("industry").toString()));
					}else{
						merBasicSql.append(1);
					}
					merBasicSql.append(",'");
					merBasicSql.append(map.get("city"));
					merBasicSql.append("',");
					if(map.get("merType") != null && StringUtils.isNotBlank(map.get("merType").toString())){	
						merBasicSql.append(Integer.valueOf(map.get("merType").toString()));
					}else{
						merBasicSql.append(0);
					}
					
					merBasicSql.append(",");
					merBasicSql.append(map.get("startDateFrom"));
					merBasicSql.append(",");
					merBasicSql.append(map.get("endDateFrom"));
					merBasicSql.append(",'");
					merBasicSql.append(map.get("tradeChannel"));
					merBasicSql.append("','");
					merBasicSql.append(map.get("merExpand"));
					merBasicSql.append("','");
					merBasicSql.append(map.get("merExpandno"));
					merBasicSql.append("','");
					merBasicSql.append(map.get("prov"));
					merBasicSql.append("')");
					merBasic = new MerBasic();
					merBasic.setId(map.get("innerMerCode").toString());
					merBasic.setMerName(map.get("merName").toString());
					if(map.get("merCategory") != null && StringUtils.isNotBlank(map.get("merCategory").toString())){		
						merBasic.setMerCategory(Integer.valueOf(map.get("merCategory").toString()));
					}else{
						merBasic.setMerCategory(0);
					}
					
					merBasic.setMerAbbreviation(map.get("merAbbreviation").toString());
					merBasic.setMerState(Integer.valueOf(map.get("industry").toString()));
					merBasic.setProvinces(map.get("prov").toString());
					merBasic.setCity(map.get("city").toString());
					if(map.get("merType") != null && StringUtils.isNotBlank(map.get("merType").toString())){		
						merBasic.setMerType(Integer.valueOf(map.get("merType").toString()));
					}else{
						merBasic.setMerType(0);
					}
					if(map.get("startDateFrom") != null && StringUtils.isNotBlank(map.get("startDateFrom").toString())){
						merBasic.setStartDate(Integer.valueOf(map.get("startDateFrom").toString()));
					}else{
						merBasic.setStartDate(0);
					}
					if(map.get("endDateFrom") != null && StringUtils.isNotBlank(map.get("endDateFrom").toString())){
						merBasic.setEndDate(Integer.valueOf(map.get("endDateFrom").toString()));
					}else{
						merBasic.setEndDate(0);
					}
					merBasic.setChannel(map.get("tradeChannel").toString());
					merBasic.setExpand(map.get("merExpand").toString());
					merBasic.setExpandno(map.get("merExpandno").toString());
				}else{
					log.info("修改商户基本信息");
					merBasicSql.append("update mer_basic set");
					merBasicSql.append(" mer_name='");
					merBasicSql.append(map.get("merName"));
					merBasicSql.append("',");
					merBasicSql.append(" mer_category=");
					merBasicSql.append(map.get("merCategory"));
					merBasicSql.append(",");
					merBasicSql.append(" mer_abbreviation='");
					merBasicSql.append(map.get("merAbbreviation"));
					merBasicSql.append("',");
					merBasicSql.append(" mer_state=");
					if(map.get("industry") != null && StringUtils.isNotBlank(map.get("industry").toString())){	
						merBasicSql.append(Integer.valueOf(map.get("industry").toString()));
					}else{
						merBasicSql.append(0);
					}
					merBasicSql.append(",");
					merBasicSql.append(" city='");
					merBasicSql.append(map.get("city"));
					merBasicSql.append("',");
					merBasicSql.append(" mer_type=");
					if(map.get("merType") != null && StringUtils.isNotBlank(map.get("merType").toString())){	
						merBasicSql.append(Integer.valueOf(map.get("merType").toString()));
					}else{
						merBasicSql.append(0);
					}
					merBasicSql.append(",");
					merBasicSql.append(" startDate=");
					merBasicSql.append(map.get("startDateFrom"));
					merBasicSql.append(",");
					merBasicSql.append(" endDate=");
					merBasicSql.append(map.get("endDateFrom"));
					merBasicSql.append(",");
					merBasicSql.append(" channel='");
					merBasicSql.append(map.get("tradeChannel"));
					merBasicSql.append("',");
					merBasicSql.append(" expand='");
					merBasicSql.append(map.get("merExpand"));
					merBasicSql.append("',");
					merBasicSql.append(" expandno='");
					merBasicSql.append(map.get("merExpandno"));
					merBasicSql.append("',");
					merBasicSql.append(" provinces='");
					merBasicSql.append(map.get("prov"));
					merBasicSql.append("' where mer_code = '");
					merBasicSql.append(map.get("innerMerCode"));
					merBasicSql.append("'");
					merBasic.setMerName(map.get("merName").toString());
					if(map.get("merCategory") != null && StringUtils.isNotBlank(map.get("merCategory").toString())){	
						merBasic.setMerCategory(Integer.valueOf(map.get("merCategory").toString()));
					}else{
						merBasic.setMerCategory(0);
					}
					merBasic.setMerAbbreviation(map.get("merAbbreviation").toString());
					if(map.get("industry") != null && StringUtils.isNotBlank(map.get("industry").toString())){	
						merBasic.setMerState(Integer.valueOf(map.get("industry").toString()));
					}else{
						merBasic.setMerState(0);
					}
					merBasic.setProvinces(map.get("prov").toString());
					merBasic.setCity(map.get("city").toString());
					if(map.get("merType") != null && StringUtils.isNotBlank(map.get("merType").toString())){	
						merBasic.setMerType(Integer.valueOf(map.get("merType").toString()));
					}else{
						merBasic.setMerType(0);
					}
					if(map.get("startDateFrom") != null && StringUtils.isNotBlank(map.get("startDateFrom").toString())){	
						merBasic.setStartDate(Integer.valueOf(map.get("startDateFrom").toString()));
					}else{
						merBasic.setStartDate(0);
					}
					if(map.get("endDateFrom") != null && StringUtils.isNotBlank(map.get("endDateFrom").toString())){	
						merBasic.setEndDate(Integer.valueOf(map.get("endDateFrom").toString()));
					}else{
						merBasic.setEndDate(0);
					}
					merBasic.setChannel(map.get("tradeChannel").toString());
					merBasic.setExpand(map.get("merExpand").toString());
					merBasic.setExpandno(map.get("merExpandno").toString());
				}
				log.info("整理好的商户基本信息SQL:"+merBasicSql.toString());
				SQLQuery merBasicQuery = session.createSQLQuery(merBasicSql.toString());
				int result = merBasicQuery.executeUpdate();
								
				MerBilling merBilling = (MerBilling)map.get("merBilling");
				
				StringBuffer merBillingSql = new StringBuffer("");
				
				if(merBilling == null){
					log.info("新增商户结算信息");
					String uuid = UUID.randomUUID().toString().replaceAll("-", "");
					merBillingSql.append("insert into mer_billing(id,bil_object,bil_bank,bil_bankname,bil_accountname,bil_bankaccount,bil_way");
					merBillingSql.append(",bil_smallamt,bil_cycle,bil_manual,bil_account,bil_status,mer_poundage,mer_code,bil_type) VALUES('");
					merBillingSql.append(uuid);
					merBillingSql.append("',");
					if(String.valueOf(map.get("clearingObject"))!=null){//结算对象
						merBillingSql.append(Integer.valueOf(map.get("clearingObject").toString()));
					}else{
						merBillingSql.append(0);
					}
					merBillingSql.append(",'");
					merBillingSql.append(map.get("clearingBankNo"));//结算银行联行号
					merBillingSql.append("','");
					merBillingSql.append(map.get("clearingBankName"));//结算银行名称
					merBillingSql.append("','");
					merBillingSql.append(map.get("clearingAccountName"));//结算账户名称
					merBillingSql.append("','");
					merBillingSql.append(map.get("clearingCardNo"));//结算银行账号
					merBillingSql.append("',");
					if(map.get("clearingWay") != null && StringUtils.isNotBlank(map.get("clearingWay").toString())){//结算方式
						merBillingSql.append(Integer.valueOf(map.get("clearingWay").toString()));
					}else{
						merBillingSql.append(0);
					}
					merBillingSql.append(",'");
					merBillingSql.append(map.get("clearingMinMoney"));//结算最少金额(元)
					merBillingSql.append("',");
					if(map.get("clearingPeriod") != null && StringUtils.isNotBlank(map.get("clearingPeriod").toString())){//结算周期 
						merBillingSql.append(Integer.valueOf(map.get("clearingPeriod").toString()));
					}else{
						merBillingSql.append(0);
					}
					merBillingSql.append(",");
					if(map.get("handworkClearing") != null && StringUtils.isNotBlank(map.get("handworkClearing").toString())){//手工结算
						merBillingSql.append(Integer.valueOf(map.get("handworkClearing").toString()));
					}else{
						merBillingSql.append(0);
					}
					merBillingSql.append(",'");
					merBillingSql.append(map.get("dyClearingNo"));//电银结算账号
					merBillingSql.append("',");
					if(map.get("clearingStatus") != null && StringUtils.isNotBlank(map.get("clearingStatus").toString())){//结算状态
						merBillingSql.append(Integer.valueOf(map.get("clearingStatus").toString()));
					}else{
						merBillingSql.append(0);
					}
					merBillingSql.append(",'");
					merBillingSql.append(map.get("merSignedFee"));//商户签约手续费
					merBillingSql.append("','");
					merBillingSql.append(map.get("innerMerCode"));//商户号
					merBillingSql.append("',");
					merBillingSql.append(map.get("bilType"));//结算类型
					merBillingSql.append(")");
					merBilling=new MerBilling();
					merBilling.setId(uuid);
					merBilling.setMerCode(merBasic);
					if(map.get("clearingObject") != null && StringUtils.isNotBlank(map.get("clearingObject").toString())){
						merBilling.setBilObject(Integer.valueOf(map.get("clearingObject").toString()));
					}else{
						merBilling.setBilObject(0);
					}
					merBilling.setBilBank(map.get("clearingBankNo").toString());	
					merBilling.setBilBankname(map.get("clearingBankName").toString());
					merBilling.setBilAccountname(map.get("clearingAccountName").toString());
					merBilling.setBilBankaccount(map.get("clearingCardNo").toString());
					if(map.get("clearingWay") != null && StringUtils.isNotBlank(map.get("clearingWay").toString())){
						merBilling.setBilWay(Integer.valueOf(map.get("clearingWay").toString()));
					}else{
						merBilling.setBilWay(0);
					}
					merBilling.setBilSmallamt(map.get("clearingMinMoney").toString());
					if(map.get("clearingPeriod") != null && StringUtils.isNotBlank(map.get("clearingPeriod").toString())){
						merBilling.setBilCycle(Integer.valueOf(map.get("clearingPeriod").toString()));
					}else{
						merBilling.setBilCycle(0);
					}
					if(map.get("handworkClearing") != null && StringUtils.isNotBlank(map.get("handworkClearing").toString())){
						merBilling.setBilManual(Integer.valueOf(map.get("handworkClearing").toString()));
					}else{
						merBilling.setBilManual(0);
					}
					merBilling.setBilAccount(map.get("dyClearingNo").toString());
					if(map.get("clearingStatus") != null && StringUtils.isNotBlank(map.get("clearingStatus").toString())){
						merBilling.setBilType(Integer.valueOf(map.get("clearingStatus").toString()));
					}else{
						merBilling.setBilType(0);
					}
					merBilling.setMerPoundage(map.get("merSignedFee").toString());
				}else{
					log.info("修改商户结算信息");
					merBilling.setMerCode(merBasic);
					merBilling.setBilObject(Integer.valueOf(map.get("clearingObject").toString()));
					merBilling.setBilBank(map.get("clearingBankNo").toString());	
					merBilling.setBilBankname(map.get("clearingBankName").toString());
					merBilling.setBilAccountname(map.get("clearingAccountName").toString());
					merBilling.setBilBankaccount(map.get("clearingCardNo").toString());
					if(map.get("clearingWay") != null && StringUtils.isNotBlank(map.get("clearingWay").toString())){
						merBilling.setBilWay(Integer.valueOf(map.get("clearingWay").toString()));
					}else{
						merBilling.setBilWay(0);
					}
					merBilling.setBilSmallamt(map.get("clearingMinMoney").toString());
					if(map.get("clearingPeriod") != null && StringUtils.isNotBlank(map.get("clearingPeriod").toString())){
						merBilling.setBilCycle(Integer.valueOf(map.get("clearingPeriod").toString()));
					}else{
						merBilling.setBilCycle(0);
					}
					if(map.get("handworkClearing") != null && StringUtils.isNotBlank(map.get("handworkClearing").toString())){
						merBilling.setBilManual(Integer.valueOf(map.get("handworkClearing").toString()));
					}else{
						merBilling.setBilManual(0);
					}
					if(map.get("clearingStatus") != null && StringUtils.isNotBlank(map.get("clearingStatus").toString())){
						merBilling.setBilType(Integer.valueOf(map.get("clearingStatus").toString()));
					}else{
						merBilling.setBilType(0);
					}
					merBilling.setBilAccount(map.get("dyClearingNo").toString());
					merBilling.setMerPoundage(map.get("merSignedFee").toString());
					merBillingSql.append("update mer_billing set bil_object =");
					if(map.get("clearingObject") != null && StringUtils.isNotBlank(map.get("clearingObject").toString())){
						merBillingSql.append(Integer.valueOf(map.get("clearingObject").toString()));
					}else{
						merBillingSql.append(0);
					}
					merBillingSql.append(",bil_bank =");
					merBillingSql.append(map.get("clearingBankNo"));
					merBillingSql.append(",bil_bankname ='");
					merBillingSql.append(map.get("clearingBankName"));
					merBillingSql.append("',bil_accountname ='");
					merBillingSql.append(map.get("clearingAccountName"));
					merBillingSql.append("',bil_bankaccount =");
					merBillingSql.append(map.get("clearingCardNo"));
					merBillingSql.append(",bil_way =");
					if(map.get("clearingWay") != null && StringUtils.isNotBlank(map.get("clearingWay").toString())){
						merBillingSql.append(Integer.valueOf(map.get("clearingWay").toString()));
					}else{
						merBillingSql.append(0);
					}
					merBillingSql.append(",bil_smallamt =");
					merBillingSql.append(map.get("clearingMinMoney"));
					merBillingSql.append(",bil_cycle =");
					if(map.get("clearingPeriod") != null && StringUtils.isNotBlank(map.get("clearingPeriod").toString())){
						merBillingSql.append(Integer.valueOf(map.get("clearingPeriod").toString()));
					}else{
						merBillingSql.append(0);
					}
					merBillingSql.append(",bil_manual =");
					if(map.get("handworkClearing") != null && StringUtils.isNotBlank(map.get("handworkClearing").toString())){
						merBillingSql.append(Integer.valueOf(map.get("handworkClearing").toString()));
					}else{
						merBillingSql.append(0);
					}
					merBillingSql.append(",bil_account ='");
					merBillingSql.append(map.get("dyClearingNo"));
					merBillingSql.append("',bil_status =");
					if(map.get("clearingStatus") != null && StringUtils.isNotBlank(map.get("clearingStatus").toString())){
						merBillingSql.append(Integer.valueOf(map.get("clearingStatus").toString()));
					}else{
						merBillingSql.append(0);
					}
					merBillingSql.append(",mer_poundage ='");
					merBillingSql.append(map.get("merSignedFee"));
					merBillingSql.append("',bil_type =");
					merBillingSql.append(map.get("bilType"));
					merBillingSql.append(" where mer_code = '");
					merBillingSql.append(map.get("innerMerCode"));
					merBillingSql.append("'");
				}
				
				log.info("整理好的商户结算信息SQL:"+merBillingSql.toString());
				SQLQuery merBillingQuery = session.createSQLQuery(merBillingSql.toString());
				int result1 = merBillingQuery.executeUpdate();
				
				if((result1+result) >=0 ){
					transaction.commit();
					resultMap.put("merBasic", merBasic);
					resultMap.put("merBilling", merBilling);
					resultMap.put("result", true);
				}
			} catch (Exception e) {
				log.info("保存商户信息数据时抛出异常："+e);
				resultMap.put("result", false);
				transaction.rollback();
				throw e;
			}finally{
				if(session != null){
					session.close();
				}
			}
		} catch (Exception e) {
			resultMap.put("result", false);
			log.error("处理管理平台商户信息同步接口抛出异常:"+e);
		}
		return resultMap;
	}

	@Override
	public boolean saveOrUpdateMerAndBasic(MerBasic merBasic,MerBilling merBilling) {
		Session session = null;
		Transaction transaction = null;
		boolean merBasicFlag = false; //更新商户基本信息标识
		boolean merBillingFlag = false; //更新商户结算信息标识
		//因为融易付那边同步的数据有限，所以商户基本信息表中 expand、expandno、city三个字段没有出现在sql中
		//商户结算信息中手续费公司mer_poundage字段没有出现在sql中
		try{
			session = this.getSession();
			transaction = session.beginTransaction();
			SQLQuery query = session.createSQLQuery("select count(*) from mer_basic where mer_code = ?");
			query.setString(0, merBasic.getId());
			Integer count = Integer.valueOf(query.uniqueResult().toString());
			if(count == 0){
				//不存在在insert
				query = session.createSQLQuery("insert into mer_basic(mer_code,mer_name,mer_category,mer_abbreviation,mer_state,mer_type,startDate,endDate,channel,provinces)"
						+ "values (?,?,?,?,?,?,?,?,?,?)");
				query.setString(0, merBasic.getId());
				query.setString(1, merBasic.getMerName());
				query.setInteger(2, merBasic.getMerCategory());
				query.setString(3, merBasic.getMerAbbreviation());
				query.setInteger(4, merBasic.getMerState());
				query.setInteger(5, merBasic.getMerType());
				query.setInteger(6, merBasic.getStartDate());
				query.setInteger(7, merBasic.getEndDate());
				query.setString(8, merBasic.getChannel());
				query.setString(9, merBasic.getProvinces());
				count = query.executeUpdate();
				if(count < 1){
					log.error("插入商户基本信息失败");
					return false;
				}else{
					log.info("插入商户基本信息成功");
					merBasicFlag = true;
				}
			}else{
				//存在就update
				query = session.createSQLQuery("update mer_basic set mer_name = ?,mer_category = ?,mer_abbreviation = ?,mer_state = ?,mer_type = ?,startDate = ?,endDate = ?,channel = ?,provinces = ? where mer_code = ? ");
				query.setString(0, merBasic.getMerName());
				query.setInteger(1, merBasic.getMerCategory());
				query.setString(2, merBasic.getMerAbbreviation());
				query.setInteger(3, merBasic.getMerState());
				query.setInteger(4, merBasic.getMerType());
				query.setInteger(5, merBasic.getStartDate());
				query.setInteger(6, merBasic.getEndDate());
				query.setString(7, merBasic.getChannel());
				query.setString(8, merBasic.getProvinces());
				query.setString(9, merBasic.getId());
				count = query.executeUpdate();
				if(count < 1){
					log.error("更新商户基本信息失败");
					return false;
				}else{
					log.info("更新商户基本信息成功");
					merBasicFlag = true;
				}
			}
			
			query = session.createSQLQuery("select count(*) from mer_billing where mer_code = ?");
			query.setString(0, merBilling.getMerCode().getId());
			count = Integer.valueOf(query.uniqueResult().toString());
			if(count == 0){
				//不存在在insert
				query = session.createSQLQuery("insert into mer_billing(id,bil_object,bil_bank,bil_bankname,bil_accountname,bil_bankaccount,bil_way,bil_smallamt,bil_cycle,bil_manual,bil_account,bil_type,mer_code,bil_status,last_liq_date,bank_branch,refund_fee)"
						+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				query.setString(0, UUID.randomUUID().toString().replaceAll("-", ""));
				query.setInteger(1, merBilling.getBilObject());
				query.setString(2,  merBilling.getBilBank());
				query.setString(3, merBilling.getBilBankname());
				query.setString(4, merBilling.getBilAccountname());
				query.setString(5, merBilling.getBilBankaccount());
				query.setInteger(6, merBilling.getBilWay());
				query.setString(7,merBilling.getBilSmallamt());
				query.setInteger(8, merBilling.getBilCycle());
				query.setInteger(9, merBilling.getBilManual());
				query.setString(10, merBilling.getBilAccount());
				query.setInteger(11, merBilling.getBilType());
				query.setString(12, merBilling.getMerCode().getId());
				query.setInteger(13, merBilling.getBilStatus());
				//新增一条数据的时候，最后结算日期为当前系统的yyyyMMdd格式数据转换成整形处理,当更新的时候就不作处理
				query.setInteger(14, Integer.valueOf(DYDataUtil.getSimpleDateFormat(DYDataUtil.DATE_FORMAT_3).format(Calendar.getInstance().getTime())));
				query.setString(15, merBilling.getBankBranch());
				query.setInteger(16, merBilling.getRefundFee());
				count = query.executeUpdate();
				if(count < 1){
					log.error("插入商户结算信息失败");
					return false;
				}else{
					log.info("插入商户结算信息成功");
					merBillingFlag = true;
				}
			}else{
				//存在就update
				query = session.createSQLQuery("update mer_billing set bil_object = ?,bil_bank = ?,bil_bankname = ?,bil_accountname = ? ,bil_bankaccount = ?,bil_way = ?,bil_smallamt = ?,bil_cycle = ?,bil_manual = ?,bil_account = ?,bil_type = ?,bil_status = ?,bank_branch = ?,refund_fee = ? "
						+ " where mer_code = ?");
				query.setInteger(0, merBilling.getBilObject());
				query.setString(1,  merBilling.getBilBank());
				query.setString(2, merBilling.getBilBankname());
				query.setString(3, merBilling.getBilAccountname());
				query.setString(4, merBilling.getBilBankaccount());
				query.setInteger(5, merBilling.getBilWay());
				query.setString(6,merBilling.getBilSmallamt());
				query.setInteger(7, merBilling.getBilCycle());
				query.setInteger(8, merBilling.getBilManual());
				query.setString(9, merBilling.getBilAccount());
				query.setInteger(10, merBilling.getBilType());
				query.setInteger(11, merBilling.getBilStatus());
				query.setString(12, merBilling.getBankBranch());
				query.setInteger(13, merBilling.getRefundFee());
				query.setString(14, merBilling.getMerCode().getId());
				count = query.executeUpdate();
				if(count < 1){
					log.error("更新商户结算信息失败");
					return false;
				}else{
					log.info("更新入商户结算信息成功");
					merBillingFlag = true;
				}
			}
			if(merBasicFlag && merBillingFlag){
				log.info("更新商户基本信息和商户结算信息成功");
				transaction.commit();
			}
		}catch(Exception e){
			if(transaction != null)
				transaction.rollback();
			log.error("操作数据库异常: " + e.getMessage());
			return false;
		}finally{
			closeSession(session);
		}
		 return merBasicFlag && merBillingFlag;
	}

//	@Override
//	public boolean updateMerDesInfo(List<MerBilling> merBillings) {
//		Session session = null;
//		Transaction transaction = null;
//		int totalCount = merBillings.size();
//		int updateCount = 0;
//		try{
//			//商户结算信息表中，结算手续费mer_poundage没有出现在sql中
//			session = this.getSession();
//			transaction = session.beginTransaction();
//		    SQLQuery query = null;
//		    Integer count = null;
//			for(MerBilling merBilling : merBillings){
//				if(merBilling == null){
//					log.error("商户结算信息为空");
//					return false;
//				}
//				query = session.createSQLQuery("select count(*) from mer_billing where mer_code = ?");
//				query.setString(0, merBilling.getMerCode().getId());
//				count = Integer.valueOf(query.uniqueResult().toString());
//				if(count == 0){
//					//不存在在insert
//					query = session.createSQLQuery("insert into mer_billing(id,bil_object,bil_bank,bil_bankname,bil_accountname,bil_bankaccount,bil_way,bil_smallamt,bil_cycle,bil_manual,bil_account,bil_type,mer_code,bil_status)"
//							+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
//					query.setString(0, UUID.randomUUID().toString().replaceAll("-", ""));
//					query.setInteger(1, merBilling.getBilObject());
//					query.setString(2,  merBilling.getBilBank());
//					query.setString(3, merBilling.getBilBankname());
//					query.setString(4, merBilling.getBilAccountname());
//					//结算银行账号是加密的无法解密只能在程序里面解密 key=iFv5x6Cu 是固定不变的
//					query.setString(5, DesEncrypto.decrypt(merBilling.getBilBankaccount(),"iFv5x6Cu"));
//					query.setInteger(6, merBilling.getBilWay());
//					query.setString(7,merBilling.getBilSmallamt());
//					query.setInteger(8, merBilling.getBilCycle());
//					query.setInteger(9, merBilling.getBilManual());
//					query.setString(10, merBilling.getBilAccount());
//					query.setInteger(11, merBilling.getBilType());
//					query.setString(12, merBilling.getMerCode().getId());
//					query.setInteger(13, merBilling.getBilStatus());
//					count = query.executeUpdate();
//					if(count < 1){
//						log.error("插入商户结算失败");
//						return false;
//					}else{
//						log.info("插入商户结算信息成功");
//						updateCount ++;
//						continue;
//					}
//				}else{
//					//存在就update
//					query = session.createSQLQuery("update mer_billing set bil_object = ?,bil_bank = ?,bil_bankname = ?,bil_accountname = ? ,bil_bankaccount = ?,bil_way = ?,bil_smallamt = ?,bil_cycle = ?,bil_manual = ?,bil_account = ?,bil_type = ?,bil_status = ?"
//							+ " where mer_code = ?");
//					query.setInteger(0, merBilling.getBilObject());
//					query.setString(1,  merBilling.getBilBank());
//					query.setString(2, merBilling.getBilBankname());
//					query.setString(3, merBilling.getBilAccountname());
//					//结算银行账号是加密的无法解密只能在程序里面解密 key=iFv5x6Cu 是固定不变的
//					query.setString(4, DesEncrypto.decrypt(merBilling.getBilBankaccount(),"iFv5x6Cu"));
//					query.setInteger(5, merBilling.getBilWay());
//					query.setString(6,merBilling.getBilSmallamt());
//					query.setInteger(7, merBilling.getBilCycle());
//					query.setInteger(8, merBilling.getBilManual());
//					query.setString(9, merBilling.getBilAccount());
//					query.setInteger(10, merBilling.getBilType());
//					query.setInteger(11, merBilling.getBilStatus());
//					query.setString(12, merBilling.getMerCode().getId());
//					count = query.executeUpdate();
//					if(count < 1){
//						log.error("更新商户结算信息失败");
//						return false;
//					}else{
//						log.info("更新商户结算信息成功");
//						updateCount ++;
//						continue;
//					}
//				}
//		     }
//			//判断总数是否是一致的
//			if(totalCount == updateCount){
//				log.info("插入或者更新"+ totalCount +"个商户结算信息成功");
//				transaction.commit();
//				return true;
//			}
//		}catch(Exception e){
//			if(transaction != null)
//				transaction.rollback();
//			log.error("更新商户结算信息失败" + e.getMessage());
//			return false;
//		}finally{
//			closeSession(session);
//		}
//		
//		return false;
//	}
	public static void main(String[] args) {
//		cn.com.chinaebi.dz.object.dao.iface.MerBasicDAO merBasicDao = MerBasicDAO.getInstance();
//		MerBasic merBasic = new MerBasic();
//		merBasic.setId("872110058130100");
//		merBasic.setMerName("天津莎莎咖啡有限公da");
//		merBasic.setChannel("电银信息自主收单渠道1");
//		merBasic.setCity(null);
//		merBasic.setEndDate(20150301);
//		merBasic.setStartDate(20140330);
//		merBasic.setExpand("商户银行1");
//		merBasic.setExpandno("607444");
//		merBasic.setMerAbbreviation("莎莎咖啡1");
//		merBasic.setMerCategory(1);
//		merBasic.setMerState(6);
//		merBasic.setMerType(2);
//		merBasic.setProvinces("1199");
//		
//	    MerBilling merBilling = new MerBilling();
//	    merBilling.setBilAccount("11121212");
//	    merBilling.setBilAccountname("天津莎莎咖啡有限公司12");
//	    merBilling.setBilBank("308110023317");
//	    merBilling.setBilBankaccount("SMW8tzeEVvFNuLtbUdy0NlgN5Nyr8Wzv");
//	    merBilling.setBilBankname("招商银行股份有限公司天津十一经路支行1");
//	    merBilling.setBilCycle(2);
//	    merBilling.setBilManual(3);
//	    merBilling.setBilObject(2);
//	    merBilling.setBilSmallamt("5000");
//	    merBilling.setBilStatus(2);
//	    merBilling.setBilType(2);
//	    merBilling.setBilWay(1);
//	    merBilling.setMerCode(merBasic);
//	    merBilling.setMerPoundage("AMT*0.125");
//	    long startTime = System.currentTimeMillis();
//		for (int i = 0; i < 1000; i++) {
//		    List<MerBilling> ls = new ArrayList<MerBilling>();
//		    ls.add(merBilling);
//		    merBasicDao.updateMerDesInfo(ls);
//		}
//		long endTime = System.currentTimeMillis();
//		System.out.println(endTime - startTime);
	}
	
	@Override
	public Object queryMerInfoByMerCode(String merCode) {
		Object object = null;
		Session session = null;
		try {
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select a.mer_name, a.mer_category, a.mer_abbreviation, a.mer_state, a.mer_type,b.bil_way, b.refund_fee from mer_basic a,mer_billing b where a.mer_code = b.mer_code and a.mer_code = ?");
			query.setParameter(0, merCode);
			object = query.uniqueResult();
			if (object != null) {
				log.info("根据商户号：" + merCode + "查询商户基本信息成功");
				return object;
			} else {
				log.warn("根据商户号：" + merCode + "查询商户基本信息为空");
			}
		} catch (Exception e) {
			log.error("根据商户号：" + merCode + "查询商户基本信息出现异常：" + e.getMessage());
		} finally {
			closeSession(session);
		}
		return object;
	}

	@Override
	public Object queryMerTypeByMerCode(String merCode) {
		Object object = null;
		Session session = null;
		try {
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select mer_type from mer_basic where mer_code = ? and mer_state = 5");
			query.setParameter(0, merCode);
			object = query.uniqueResult();
			if (object != null) {
				return object;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			closeSession(session);
		}
		return object;
	}
	
	@Override
	public MerBasic queryMerBasic(String merCode) {
		MerBasic merBasic = null;
		Session session = null;
		try {
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select mer_state,mer_category,mer_abbreviation from mer_basic where mer_code = ?");
			query.setParameter(0, merCode);
			Object object = query.uniqueResult();
			if (object != null) {
				Object[] obj = (Object[])object;
				merBasic = new MerBasic();
				merBasic.setMerState(Integer.valueOf(obj[0].toString()));
				merBasic.setMerCategory(Integer.valueOf(obj[1].toString()));
				merBasic.setMerAbbreviation(obj[2].toString());
				return merBasic;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			closeSession(session);
		}
		return merBasic;
	}

	@Override
	public MerBasic getMerBasicByInSql(String merCode) throws Exception{
		Session session = null;
		MerBasic merBasic = null; 
		Transaction transaction = null;
		List<?> listResult = null;
		try {
			session = this.getSession();
			session.flush();
			session.clear();
			transaction = session.beginTransaction();
			SQLQuery query = session.createSQLQuery("select * from mer_basic where mer_code = ? ").addEntity(MerBasic.class);
			query.setString(0, merCode);
			transaction.commit();
			listResult = query.list();
			for (Object object : listResult) {
				if(object instanceof MerBasic){
					merBasic = (MerBasic) object;
				}
			}
		}catch (Exception e) {
			log.error(e);
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		log.info(merBasic == null?("select * from mer_basic where mer_code = "+ merCode +" is no data"):"查到商户基本信息为"+merCode+"的数据");
		return merBasic;
	}
}