package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;
import java.util.Date;

import cn.com.chinaebi.dz.object.FtpUploadRecord;

public interface FtpUploadRecordDAO {
	public cn.com.chinaebi.dz.object.FtpUploadRecord get(java.lang.Integer key);

	public cn.com.chinaebi.dz.object.FtpUploadRecord load(java.lang.Integer key);

	public java.util.List<cn.com.chinaebi.dz.object.FtpUploadRecord> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param ftpUploadRecord a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.Integer save(cn.com.chinaebi.dz.object.FtpUploadRecord ftpUploadRecord);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param ftpUploadRecord a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.FtpUploadRecord ftpUploadRecord);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param ftpUploadRecord a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.FtpUploadRecord ftpUploadRecord);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.Integer id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param ftpUploadRecord the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.FtpUploadRecord ftpUploadRecord);
	
	public FtpUploadRecord queryFtpUploadRecordByObjectIdAndDate(int object_id,String date);
	
	public boolean insertOrUpdateFtpUploadRecord(int object_id,String date,String object_name,String upload_content,Date generate_time,int upload_record) throws Exception;


}