package cn.com.chinaebi.dz.object.dao;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.dz.object.base.BaseDzFileTabDAO;


public class DzFileTabDAO extends BaseDzFileTabDAO implements cn.com.chinaebi.dz.object.dao.iface.DzFileTabDAO {

	public DzFileTabDAO () {}
	
	public DzFileTabDAO (Session session) {
		super(session);
	}
	
	private Log log =LogFactory.getLog(getClass());

	public synchronized boolean insertDzFileData(String date,String file_name,String file_type,String create_last_time,String file_path,int object_id,String object_name) throws Exception{
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		if(StringUtils.isNotBlank(date)){
			try{
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery q_count = session.createSQLQuery("select count(*) from dz_file_tab where deduct_sys_date=? and file_type=? and file_name=? and object_id=?");
				q_count.setString(0, date);
				q_count.setString(1, file_type);
				q_count.setString(2, file_name);
				q_count.setInteger(3, object_id);
				Integer num = Integer.valueOf(q_count.uniqueResult().toString());
				log.info("根据日期，名称，文件类型三个条件查询对账文件表数据条数为:"+num);
				if(num > 0){
					log.info("更新对账文件表操作:");
					SQLQuery q_update = session.createSQLQuery("update dz_file_tab set create_last_time=?,file_path=? where deduct_sys_date=? and file_type=? and file_name=? and object_id=?");
					q_update.setString(0, create_last_time);
					q_update.setString(1, file_path);
					q_update.setString(2, date);
					q_update.setString(3, file_type);
					q_update.setString(4, file_name);
					q_update.setInteger(5, object_id);
					int count = q_update.executeUpdate();
					if(count > 0){
						transaction.commit();
						flag = true;
					}
				}else{
					log.info("向对账文件表中插入数据:");
					SQLQuery query = session.createSQLQuery("INSERT INTO dz_file_tab(deduct_sys_date,file_type,file_name,create_last_time,file_path,object_id,object_name) VALUES(?,?,?,?,?,?,?)");
					query.setString(0, date);
					query.setString(1, file_type);
					query.setString(2, file_name);
					query.setString(3, create_last_time);
					query.setString(4, file_path);
					query.setInteger(5, object_id);
					query.setString(6, object_name);
					int count = query.executeUpdate();
					if(count > 0){
						transaction.commit();
						flag = true;
					}
				}
			}catch(Exception e){
				transaction.rollback();
				log.error(e);
				throw e;
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}
		return flag;
	}

}