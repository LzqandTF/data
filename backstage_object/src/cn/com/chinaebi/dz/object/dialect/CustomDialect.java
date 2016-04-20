package cn.com.chinaebi.dz.object.dialect;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQL5Dialect;

/**
 * 
 * @author zhu.hongliang
 *
 */
public class CustomDialect extends MySQL5Dialect {
	public CustomDialect() {
		super();
		registerHibernateType(-1, Hibernate.STRING.getName());
		registerHibernateType(-4, Hibernate.STRING.getName());
	}
}

