package cn.com.chinaebi.dz.object.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import cn.com.chinaebi.dz.object.TimingTaskConf;
import cn.com.chinaebi.dz.object.base.BaseTimingTaskConfDAO;


public class TimingTaskConfDAO extends BaseTimingTaskConfDAO implements cn.com.chinaebi.dz.object.dao.iface.TimingTaskConfDAO {

	private static Log log = LogFactory.getLog(TimingTaskConfDAO.class);
	public TimingTaskConfDAO () {}
	
	public TimingTaskConfDAO (Session session) {
		super(session);
	}

	public TimingTaskConf getDzHandlerTime(String whereColumn,String value){
		TimingTaskConf timingTaskConf = null;
		Session session = null;
		try {
			session = this.getSession();
			Query query = session.createQuery("from TimingTaskConf where "+whereColumn+" = ?");
			query.setString(0, value);
			Object obj = query.uniqueResult();
			if(obj != null){
				timingTaskConf = (TimingTaskConf)obj;
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		
		return timingTaskConf;
	}
	
	
	public Object getReturnColumn(String whereColumn,String value,String returnColumn){
		Object rtobj = null;
		Session session = null;
		try {
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select "+returnColumn+" from timing_task_conf where "+whereColumn+" = ?");
			query.setString(0, value);
			Object obj = query.uniqueResult();
			if(obj != null){
				rtobj = obj;
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		
		return rtobj;
	}
	
	public TimingTaskConf getInstIdAntInstType(String dzHandleName){
		TimingTaskConf taskConf = null;
		Session session = null;
		try{
			session = this.getSession();
			Query query = session.createQuery("from TimingTaskConf where DzHandlerTimeName = ?");
			query.setString(0, dzHandleName);
			Object obj = query.uniqueResult();
			if(obj != null){
				taskConf = (TimingTaskConf)obj;
			}
		}catch(Exception e){
			log.error(e);
		}
		return taskConf;
	}
	
	/**
	 * 通过渠道id获得配置定时任务
	 * @param channel_id
	 * @return
	 */
	public List<TimingTaskConf> getTimingTaskConfListByChannelId(int channel_id,int inst_type){
		Session session = null;
		List<TimingTaskConf> list = null;
		try {
			session = this.getSession();
			Query query = session.createQuery("from TimingTaskConf where channel_id = ? and inst_type = ? ");
			query.setInteger(0, channel_id);
			query.setInteger(1, inst_type);
			List list_ = query.list();
			if(list_ != null){
				list = list_;
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return list;
	}
}