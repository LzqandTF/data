package cn.com.chinaebi.dz.object.dao;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.dz.object.ExecuteNode;
import cn.com.chinaebi.dz.object.base.BaseExecuteNodeDAO;
import cn.com.chinaebi.dz.util.DYDataUtil;


public class ExecuteNodeDAO extends BaseExecuteNodeDAO implements cn.com.chinaebi.dz.object.dao.iface.ExecuteNodeDAO {

	private Log log = LogFactory.getLog(getClass());
	public ExecuteNodeDAO () {}
	
	public ExecuteNodeDAO (Session session) {
		super(session);
	}

	public boolean updateExecuteNodeStatus(String column,int instId,String deductStmlDate,String status,String inst_name,int inst_type){
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(column)){
			log.info("进入更新节点状态方法");
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				StringBuffer sb = new StringBuffer();
				SQLQuery query = session.createSQLQuery("select count(*) from execute_node where deduct_sys_id = ? and deduct_stml_date = ? and inst_type = ? ");
				query.setInteger(0, instId);
				query.setParameter(1, DYDataUtil.getformatConversionDate3(deductStmlDate));
				query.setInteger(2, inst_type);
				Integer num = Integer.valueOf(query.uniqueResult().toString());
				log.info("根据渠道ID"+instId+",渠道类型"+inst_type+"和清算日期"+deductStmlDate+"查到的工作流节点对象数目为"+num);
				if(num > 0){
					log.info("进行更新对应清算日期与渠道ID的工作流节点对象");
					try{
						StringBuffer sbr = new StringBuffer();
						sbr.append("select count(*) from execute_node where deduct_sys_id = ? and deduct_stml_date = ? and inst_type = ? and ");
						sbr.append(column+"="+status);
						SQLQuery query1 = session.createSQLQuery(sbr.toString());
						query1.setInteger(0, instId);
						query1.setParameter(1, DYDataUtil.getformatConversionDate3(deductStmlDate));
						query1.setInteger(2, inst_type);
						Integer num1 = Integer.valueOf(query1.uniqueResult().toString());
						if(num1 > 0){
							log.info("更新的状态与原状态相同，不需要修改");
							transaction.commit();
							flag = true;
						}else{
							log.info("更新的状态与原状态不同，进行修改");
							sb.append("update execute_node set ");
							sb.append(column+" = "+status);
							sb.append("  where deduct_sys_id = ? and deduct_stml_date = ? and inst_type = ? ");
							SQLQuery sqlQuery = session.createSQLQuery(sb.toString());
							sqlQuery.setInteger(0, instId);
							sqlQuery.setParameter(1, DYDataUtil.getformatConversionDate3(deductStmlDate));
							sqlQuery.setInteger(2, inst_type);
							int count =  sqlQuery.executeUpdate();
							if(count > 0 ){
								transaction.commit();
								flag = true;
							}
						}
					}catch(Exception e){
						log.error("更新对应清算日期与渠道ID的工作流节点对象抛出异常:"+e);
					}
				}else{
					log.info("根据渠道ID和清算日期未找到对应的工作流节点对象，添加一条数据");
					try{
						sb.append("INSERT INTO execute_node(id,deduct_sys_id,deduct_stml_date,inst_name,inst_type,");
						sb.append(column);
						sb.append(") VALUES(");
						sb.append("'"+UUID.randomUUID().toString().replaceAll("-", "")+"'");
						sb.append(","+instId);
						sb.append(",'"+deductStmlDate+"'");
						sb.append(",'"+inst_name+"'");
						sb.append(","+inst_type);
						sb.append(","+status);
						sb.append(")");
						SQLQuery sqlQuery = session.createSQLQuery(sb.toString());
						int count =  sqlQuery.executeUpdate();
						if(count > 0 ){
							transaction.commit();
							flag = true;
						}
					}catch(Exception e){
						log.error("添加工作流节点数据抛出异常:"+e);
					}
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
			log.warn(column +", 更新字段不存在");
		}
		return flag;
	}

	@Override
	public ExecuteNode findExecuteNodeData(String deductStmlDate, Integer instId,
			Integer instType) {
		Session session = null;
		ExecuteNode executeNode = null;
		try {
			session = this.getSession();
			Date startTime = DYDataUtil.getformatConversionDate3(deductStmlDate);
			Date endTime = DYDataUtil.getformatConversionDate4(deductStmlDate);
			Query query = session.createQuery("from ExecuteNode where DeductSysId = ? and DeductStmlDate BETWEEN ? and ? and InstType = ?");
			query.setParameter(0, instId);
			query.setParameter(1, startTime);
			query.setParameter(2, endTime);
			query.setParameter(3, instType);
			Object object = query.uniqueResult();
			if(object != null){
				executeNode = (ExecuteNode)object;
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return executeNode;
	}

}