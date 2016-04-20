package cn.com.chinaebi.dz.object.base;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import cn.com.chinaebi.dz.object.dao.iface.DuizhangPufaBankLstDAO;
import org.hibernate.criterion.Order;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseDuizhangPufaBankLstDAO extends cn.com.chinaebi.dz.object.dao._RootDAO {

	public BaseDuizhangPufaBankLstDAO () {}
	
	public BaseDuizhangPufaBankLstDAO (Session session) {
		super(session);
	}

	// query name references


	public static DuizhangPufaBankLstDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static DuizhangPufaBankLstDAO getInstance () {
		if (null == instance) instance = new cn.com.chinaebi.dz.object.dao.DuizhangPufaBankLstDAO();
		return instance;
	}

	public Class getReferenceClass () {
		return cn.com.chinaebi.dz.object.DuizhangPufaBankLst.class;
	}

    public Order getDefaultOrder () {
		return null;
    }

	/**
	 * Cast the object as a cn.com.chinaebi.dz.object.DuizhangPufaBankLst
	 */
	public cn.com.chinaebi.dz.object.DuizhangPufaBankLst cast (Object object) {
		return (cn.com.chinaebi.dz.object.DuizhangPufaBankLst) object;
	}

	public cn.com.chinaebi.dz.object.DuizhangPufaBankLst get(java.lang.String key)
	{
		return (cn.com.chinaebi.dz.object.DuizhangPufaBankLst) get(getReferenceClass(), key);
	}

	public cn.com.chinaebi.dz.object.DuizhangPufaBankLst get(java.lang.String key, Session s)
	{
		return (cn.com.chinaebi.dz.object.DuizhangPufaBankLst) get(getReferenceClass(), key, s);
	}

	public cn.com.chinaebi.dz.object.DuizhangPufaBankLst load(java.lang.String key)
	{
		return (cn.com.chinaebi.dz.object.DuizhangPufaBankLst) load(getReferenceClass(), key);
	}

	public cn.com.chinaebi.dz.object.DuizhangPufaBankLst load(java.lang.String key, Session s)
	{
		return (cn.com.chinaebi.dz.object.DuizhangPufaBankLst) load(getReferenceClass(), key, s);
	}

	public cn.com.chinaebi.dz.object.DuizhangPufaBankLst loadInitialize(java.lang.String key, Session s) 
	{ 
		cn.com.chinaebi.dz.object.DuizhangPufaBankLst obj = load(key, s); 
		if (!Hibernate.isInitialized(obj)) {
			Hibernate.initialize(obj);
		} 
		return obj; 
	}

/* Generic methods */

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List<cn.com.chinaebi.dz.object.DuizhangPufaBankLst> findAll () {
		return super.findAll();
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List<cn.com.chinaebi.dz.object.DuizhangPufaBankLst> findAll (Order defaultOrder) {
		return super.findAll(defaultOrder);
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 * Use the session given.
	 * @param s the Session
	 */
	public java.util.List<cn.com.chinaebi.dz.object.DuizhangPufaBankLst> findAll (Session s, Order defaultOrder) {
		return super.findAll(s, defaultOrder);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param duizhangPufaBankLst a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.DuizhangPufaBankLst duizhangPufaBankLst)
	{
		return (java.lang.String) super.save(duizhangPufaBankLst);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * Use the Session given.
	 * @param duizhangPufaBankLst a transient instance of a persistent class
	 * @param s the Session
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.DuizhangPufaBankLst duizhangPufaBankLst, Session s)
	{
		return (java.lang.String) save((Object) duizhangPufaBankLst, s);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param duizhangPufaBankLst a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.DuizhangPufaBankLst duizhangPufaBankLst)
	{
		saveOrUpdate((Object) duizhangPufaBankLst);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default the
	 * instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the identifier
	 * property mapping. 
	 * Use the Session given.
	 * @param duizhangPufaBankLst a transient instance containing new or updated state.
	 * @param s the Session.
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.DuizhangPufaBankLst duizhangPufaBankLst, Session s)
	{
		saveOrUpdate((Object) duizhangPufaBankLst, s);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param duizhangPufaBankLst a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.DuizhangPufaBankLst duizhangPufaBankLst) 
	{
		update((Object) duizhangPufaBankLst);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * Use the Session given.
	 * @param duizhangPufaBankLst a transient instance containing updated state
	 * @param the Session
	 */
	public void update(cn.com.chinaebi.dz.object.DuizhangPufaBankLst duizhangPufaBankLst, Session s)
	{
		update((Object) duizhangPufaBankLst, s);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id)
	{
		delete((Object) load(id));
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param id the instance ID to be removed
	 * @param s the Session
	 */
	public void delete(java.lang.String id, Session s)
	{
		delete((Object) load(id, s), s);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param duizhangPufaBankLst the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.DuizhangPufaBankLst duizhangPufaBankLst)
	{
		delete((Object) duizhangPufaBankLst);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param duizhangPufaBankLst the instance to be removed
	 * @param s the Session
	 */
	public void delete(cn.com.chinaebi.dz.object.DuizhangPufaBankLst duizhangPufaBankLst, Session s)
	{
		delete((Object) duizhangPufaBankLst, s);
	}
	
	/**
	 * Re-read the state of the given instance from the underlying database. It is inadvisable to use this to implement
	 * long-running sessions that span many business tasks. This method is, however, useful in certain special circumstances.
	 * For example 
	 * <ul> 
	 * <li>where a database trigger alters the object state upon insert or update</li>
	 * <li>after executing direct SQL (eg. a mass update) in the same session</li>
	 * <li>after inserting a Blob or Clob</li>
	 * </ul>
	 */
	public void refresh (cn.com.chinaebi.dz.object.DuizhangPufaBankLst duizhangPufaBankLst, Session s)
	{
		refresh((Object) duizhangPufaBankLst, s);
	}


}