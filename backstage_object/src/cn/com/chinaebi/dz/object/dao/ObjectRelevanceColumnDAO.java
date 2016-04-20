package cn.com.chinaebi.dz.object.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import cn.com.chinaebi.dz.object.DuizhangCupsLst;
import cn.com.chinaebi.dz.object.base.BaseObjectRelevanceColumnDAO;
import cn.com.chinaebi.dz.util.CustomDzFileInfoClass;


public class ObjectRelevanceColumnDAO extends BaseObjectRelevanceColumnDAO implements cn.com.chinaebi.dz.object.dao.iface.ObjectRelevanceColumnDAO {

	private Log log =LogFactory.getLog(getClass());
	
	public ObjectRelevanceColumnDAO () {}
	
	public ObjectRelevanceColumnDAO (Session session) {
		super(session);
	}

	public List<Object> queryCustomDzFileInfo(int object_id,int file_type){
		List<Object> list = null;
		Session session = null;
		try {
			session = this.getSession();
			String sql ="select o.file_type,o.show_attribute_name,d.attribute_column,d.attribute_name,"+
						"r.handler_type,r.new_value,r.old_value,t.template_function,r.attribute_column,d.column_length,r.rule_id,d.attribute_column_online,d.attribute_column_online_refund from object_relevance_column o "+
						" left join dz_file_column_conf d on o.dz_column_id = d.dz_column_id"+
						" left join rule_handler r on o.rule_id = r.rule_id "+
						" left join rule_template t on r.template_id = t.template_id"+
						" where o.object_id = ? and o.file_type = ? ORDER BY o.id asc";
//			String sql ="select new CustomDzFileInfoClass(o.FileType,o.ShowAttributeName,d.AttributeColumn,d.AttributeName,"+
//					"r.HandlerType,r.NewValue,r.OldValue,t.Id) from ObjectRelevanceColumn o "+
//					" left join DzFileColumnConf d on o.DzColumn = d.Id"+
//					" left join RuleHandler r on o.RuleId = r.Id"+
//					" left join RuleTemplate t on r.TemplateId = t.Id"+
//					" where o.Object = ? and o.FileType = ?";
			SQLQuery query = session.createSQLQuery(sql);
			query.setInteger(0, object_id);
			query.setInteger(1, file_type);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
			}else{
				log.warn("select * from object_relevance_column left join dz_file_column_conf left join rule_handler left join rule_template not data");
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
	public List<Object> queryReplaceValue(int rule_id){
		List<Object> list = null;
		Session session = null;
		try {
			session = this.getSession();
			String sql ="select r.rule_id,r.old_value,r.new_value from replace_value_tab r "+
					" where r.rule_id = ?  ORDER BY r.rule_id asc";
			SQLQuery query = session.createSQLQuery(sql);
			query.setInteger(0, rule_id);
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
	public List<CustomDzFileInfoClass> queryCustomDzFileInfo1(int object_id,int file_type){
		List<CustomDzFileInfoClass> list = null;
		Session session = null;
		try {
			session = this.getSession();
			String sql ="select o.FileType as file_type,o.ShowAttributeName as show_attribute_name,d.AttributeColumn as attribute_column,d.AttributeName as attribute_name,"+
					"r.HandlerType as handler_type,r.NewValue as new_value,r.OldValue as old_value,t.Id as template_id from ObjectRelevanceColumn o "+
					" ,DzFileColumnConf d "+
					" ,RuleHandler r "+
					" ,RuleTemplate t "+
					" where o.DzColumn = d.Id and o.RuleId = r.Id and r.TemplateId = t.Id and o.Object = ? and o.FileType = ?";
			Query query = session.createQuery(sql);
			query.setInteger(0, object_id);
			query.setInteger(1, file_type);
			List listResult = query.setResultTransformer(Transformers.aliasToBean(CustomDzFileInfoClass.class)).list();
//			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
				session.flush();
				session.clear();
			}else{
				log.warn(" not data");
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return list;
	}

}