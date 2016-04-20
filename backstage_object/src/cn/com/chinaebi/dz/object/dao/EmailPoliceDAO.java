package cn.com.chinaebi.dz.object.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import cn.com.chinaebi.dz.object.EmailPolice;
import cn.com.chinaebi.dz.object.base.BaseEmailPoliceDAO;


public class EmailPoliceDAO extends BaseEmailPoliceDAO implements cn.com.chinaebi.dz.object.dao.iface.EmailPoliceDAO {
	private Log log = LogFactory.getLog(getClass());
	
	public EmailPoliceDAO () {}
	
	public EmailPoliceDAO (Session session) {
		super(session);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Object> queryByPoliceId(int policeId) {
		Session session = null;
		String content = "";
		String event = "";
		StringBuffer email = new StringBuffer("");
		EmailPolice emailPolice = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			session = this.getSession();
			Query query = session.createQuery(" from EmailPolice where police_id = ? ");
			query.setInteger(0, policeId);
			List list_ = query.list();
			if(list_ != null && list_.size() > 0){
				for(int i=0;i<list_.size();i++){
					emailPolice = (EmailPolice)list_.get(i);
					email.append(emailPolice.getEmail() + ",");
					if(i == 0){
						content = emailPolice.getEmailContent();
						event = emailPolice.getEmailTheme();
					}
				}
				map.put("emailTheme", event);
				map.put("emailContent", content);
				map.put("email", email == null?"":email.toString().substring(0, email.toString().length()-1));
				if (map != null && map.size() > 0) {
					return map;
				}
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
		return map;
	}
}