package cn.com.chinaebi.dz.object.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.dz.object.CustomObject;
import cn.com.chinaebi.dz.object.base.BaseCustomObjectDAO;


public class CustomObjectDAO extends BaseCustomObjectDAO implements cn.com.chinaebi.dz.object.dao.iface.CustomObjectDAO {

	private Log log =LogFactory.getLog(getClass());
	
	public CustomObjectDAO () {}
	
	public CustomObjectDAO (Session session) {
		super(session);
	}
	
	public List<CustomObject> queryAll(){
		List<CustomObject> list = null;
		Session session = null;
		try {
			session = this.getSession();
			String sql = "from CustomObject";
			Query query = session.createQuery(sql);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}else{
				log.warn(" not data");
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return list;
	}
	public Object queryCustomObjectByObjectId(int object_id){
		Object obj = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			String sql = "select object_id,object_name,file_address,dz_file_name,error_file_name,whether_upload,ftp_ip,ftp_address,ftp_port,ftp_username,ftp_password,file_suffix,generate_number,file_need_online_data,data_type,whether_create_error_file,file_type,whether_create_file_by_range from custom_object where object_id = ?";
			SQLQuery query = session.createSQLQuery(sql);
			query.setInteger(0,object_id);
			transaction.commit();
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				obj = listResult.get(0);
			}else{
				log.warn("系统接口id"+object_id+" not data");
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return obj;
	}
	
	
	public CustomObject queryCustomObject(int object_id){
		CustomObject customObject = null;
		Session session = null;
		try {
			session = this.getSession();
			String sql = "from CustomObject where Id = ?";
			Query query = session.createQuery(sql);
			query.setInteger(0, object_id);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				customObject = (CustomObject) listResult.get(0);
			}else{
				log.warn(" not data");
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return customObject;
	}
	/**
	 * 查询需要生成内部清算文件的系统接口
	 * @return
	 */
	public List<CustomObject> queryCreateInnerFileCustomObject(){
		List<CustomObject> list = null;
		Session session = null;
		try {
			session = this.getSession();
			String sql = "from CustomObject where FileType = 3";
			Query query = session.createQuery(sql);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}else{
				log.warn(" not data");
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return list;
	}

	@Override
	public List<CustomObject> queryCustomObjectByFileType(int file_type) {
		List<CustomObject> list = null;
		Session session = null;
		try {
			session = this.getSession();
			String sql = "from CustomObject where FileType = ?";
			Query query = session.createQuery(sql);
			query.setInteger(0, file_type);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}else{
				log.warn("from CustomObject where FileType = "+file_type+" not data");
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return list;
	}

}