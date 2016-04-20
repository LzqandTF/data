package cn.com.chinaebi.dz.object.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;


import cn.com.chinaebi.dz.object.CardTransfer;
import cn.com.chinaebi.dz.object.base.BaseCardTransferDAO;


public class CardTransferDAO extends BaseCardTransferDAO implements cn.com.chinaebi.dz.object.dao.iface.CardTransferDAO {

	private Log log = LogFactory.getLog(getClass());
	public CardTransferDAO () {}
	
	public CardTransferDAO (Session session) {
		super(session);
	}

	@Override
	public CardTransfer getCardTransferByCardNo(String cardNo) {
		
//		log.info(cardNo);
		if(StringUtils.isBlank(cardNo) || cardNo.length() < 12 || cardNo.length() > 19){
			return null;
		}
		CardTransfer cardTransfer = null;
		Session session = null;
		session = this.getSession();
		String querySql = "from CardTransfer where CardHead in(?,?,?,?,?,?,?,?) and CardNoLength = ? order by CardLength desc limit 1";
		try
		{
			Query query = session.createQuery(querySql);
			query.setString(0, cardNo.substring(0, 5));
			query.setString(1, cardNo.substring(0, 6));
			query.setString(2, cardNo.substring(0, 7));
			query.setString(3, cardNo.substring(0, 8));
			query.setString(4, cardNo.substring(0, 9));
			query.setString(5, cardNo.substring(0, 10));
			query.setString(6, cardNo.substring(0, 11));
			query.setString(7, cardNo.substring(0, 12));
			query.setInteger(8, cardNo.length());
			Object obj = query.uniqueResult();
			if(obj != null){
				cardTransfer = (CardTransfer)obj;
			}
		}catch(Exception e){
			log.error(e);
		}finally{
			closeSession(session);
		}
		return cardTransfer;
	}


}