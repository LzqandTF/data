package cn.com.chinaebi.dz.object.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.base.BaseBankInstDAO;


public class BankInstDAO extends BaseBankInstDAO implements cn.com.chinaebi.dz.object.dao.iface.BankInstDAO {
	
	private static Log log = LogFactory.getLog(BankInstDAO.class);
	
	public BankInstDAO () {}
	
	public BankInstDAO (Session session) {
		super(session);
	}

	@Override
	public BankInst getBankInstByIdInSQL(int bankId) throws Exception {
		Session session = null;
		BankInst bankInst = null; 
		Transaction transaction = null;
		List<?> listResult = null;
		try {
			session = this.getSession();
			session.flush();
			session.clear();
			transaction = session.beginTransaction();
			SQLQuery query = session.createSQLQuery("select * from bank_inst where bank_id = ? ").addEntity(BankInst.class);
			query.setInteger(0, bankId);
			transaction.commit();
			listResult = query.list();
			for (Object object : listResult) {
				if(object instanceof BankInst){
					bankInst = (BankInst) object;
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
		log.info(bankInst == null?("select * from bank_inst where bank_id = "+ bankId +" is no data"):"查到银行机构ID为"+bankId+"的数据");
		return bankInst;
	}

}