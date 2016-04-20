package cn.com.chinaebi.dz.object.dao;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.dz.object.FtpUploadRecord;
import cn.com.chinaebi.dz.object.base.BaseFtpUploadRecordDAO;
import cn.com.chinaebi.dz.util.DYDataUtil;


public class FtpUploadRecordDAO extends BaseFtpUploadRecordDAO implements cn.com.chinaebi.dz.object.dao.iface.FtpUploadRecordDAO {

	private static Log log = LogFactory.getLog(FtpUploadRecordDAO.class);
	
	public FtpUploadRecordDAO () {}
	
	public FtpUploadRecordDAO (Session session) {
		super(session);
	}

	public FtpUploadRecord queryFtpUploadRecordByObjectIdAndDate(int object_id,String date){
		FtpUploadRecord ftpUploadRecord = null;
		Session session = null;
		try {
			session = this.getSession();
			Query query = session.createQuery("from FtpUploadRecord where ObjectId = ? and DeductStlmDate = ?");
			query.setInteger(0, object_id);
			query.setString(1, date);
			Object obj = query.uniqueResult();
			if(obj != null){
				ftpUploadRecord = (FtpUploadRecord)obj;
			}else{
				log.warn("from FtpUploadRecord where ObjectId = "+object_id+" and DeductStlmDate = "+date+" is no data");
			}
		}catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return ftpUploadRecord;
	}
	
	public boolean insertOrUpdateFtpUploadRecord(int object_id,String date,String object_name,
			String upload_content,Date generate_time,int upload_record) throws Exception{
		Session session = null;
		Transaction transaction = null;
		FtpUploadRecord ftpUploadRecord = null;
		boolean flag = false;
		try{
			log.info("通过object_id="+object_id+"date="+date+"为条件查询FTP上传记录：");
			ftpUploadRecord = queryFtpUploadRecordByObjectIdAndDate(object_id, date);
			log.info("通过object_id="+object_id+"date="+date+"为条件查询FTP上传记录，结果"+ftpUploadRecord == null?"为空":"不为空");
			if(ftpUploadRecord == null){
				try{
					session = this.getSession();
					transaction = session.beginTransaction();
					StringBuffer sb = new StringBuffer();
					transaction = session.beginTransaction();
					sb.append("INSERT INTO ftp_upload_record(deduct_stlm_date,object_id,object_name,upload_content,generate_time,upload_status");
					sb.append(") VALUES('");
					sb.append(date);
					sb.append("',"+object_id);
					sb.append(",'"+object_name);
					sb.append("','"+upload_content);
					sb.append("','"+DYDataUtil.getSimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(generate_time));
					sb.append("',"+upload_record);
					sb.append(")");
					SQLQuery sqlQuery = session.createSQLQuery(sb.toString());
					int count =  sqlQuery.executeUpdate();
					if(count > 0 ){
						transaction.commit();
						flag = true;
					}
				}catch(Exception e){
					log.info("添加FTP上传记录数据抛出异常");
					log.error(e);
				}
			}else{
				try{
					StringBuffer sb = new StringBuffer();
					session = this.getSession();
					transaction = session.beginTransaction();
					sb.append("update ftp_upload_record set ");
					sb.append(" upload_status = "+upload_record);
					log.info("修改前的上传内容为:"+ftpUploadRecord.getUploadContent()+"-------"+"当前的上传内容为"+upload_content);
					if(ftpUploadRecord.getUploadContent().contains(upload_content)){
						
					}else{
						String content= ftpUploadRecord.getUploadContent()+";"+upload_content;
						sb.append(" , upload_content = '"+content);
						sb.append("'");
					}
					sb.append(" where object_id = "+object_id);
					sb.append(" and deduct_stlm_date = '"+date);
					sb.append("'");
					
					SQLQuery sqlQuery = session.createSQLQuery(sb.toString());
					int count =  sqlQuery.executeUpdate();
					if(count > 0 ){
						transaction.commit();
						flag = true;
					}
				}catch(Exception e){
					log.info("修改FTP上传记录数据抛出异常");
					log.error(e);
				}
			}
		}catch(Exception e){
			log.error(e);
			throw e;
		}finally {
			if (session != null) {
				session.close();
			}
		}
		return flag;
	}

}