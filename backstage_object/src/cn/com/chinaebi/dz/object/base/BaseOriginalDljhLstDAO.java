package cn.com.chinaebi.dz.object.base;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import cn.com.chinaebi.dz.object.dao.iface.OriginalDljhLstDAO;
import org.hibernate.criterion.Order;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseOriginalDljhLstDAO extends cn.com.chinaebi.dz.object.dao._RootDAO {

	public BaseOriginalDljhLstDAO () {}
	
	public BaseOriginalDljhLstDAO (Session session) {
		super(session);
	}

	// query name references


	public static OriginalDljhLstDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static OriginalDljhLstDAO getInstance () {
		if (null == instance) instance = new cn.com.chinaebi.dz.object.dao.OriginalDljhLstDAO();
		return instance;
	}

	public Class getReferenceClass () {
		return cn.com.chinaebi.dz.object.OriginalDljhLst.class;
	}

    public Order getDefaultOrder () {
		return null;
    }

	/**
	 * Cast the object as a cn.com.chinaebi.dz.object.OriginalDljhLst
	 */
	public cn.com.chinaebi.dz.object.OriginalDljhLst cast (Object object) {
		return (cn.com.chinaebi.dz.object.OriginalDljhLst) object;
	}

	public cn.com.chinaebi.dz.object.OriginalDljhLst get(java.lang.String key)
	{
		return (cn.com.chinaebi.dz.object.OriginalDljhLst) get(getReferenceClass(), key);
	}

	public cn.com.chinaebi.dz.object.OriginalDljhLst get(java.lang.String key, Session s)
	{
		return (cn.com.chinaebi.dz.object.OriginalDljhLst) get(getReferenceClass(), key, s);
	}

	public cn.com.chinaebi.dz.object.OriginalDljhLst load(java.lang.String key)
	{
		return (cn.com.chinaebi.dz.object.OriginalDljhLst) load(getReferenceClass(), key);
	}

	public cn.com.chinaebi.dz.object.OriginalDljhLst load(java.lang.String key, Session s)
	{
		return (cn.com.chinaebi.dz.object.OriginalDljhLst) load(getReferenceClass(), key, s);
	}

	public cn.com.chinaebi.dz.object.OriginalDljhLst loadInitialize(java.lang.String key, Session s) 
	{ 
		cn.com.chinaebi.dz.object.OriginalDljhLst obj = load(key, s); 
		if (!Hibernate.isInitialized(obj)) {
			Hibernate.initialize(obj);
		} 
		return obj; 
	}

/* Generic methods */

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List<cn.com.chinaebi.dz.object.OriginalDljhLst> findAll () {
		return super.findAll();
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List<cn.com.chinaebi.dz.object.OriginalDljhLst> findAll (Order defaultOrder) {
		return super.findAll(defaultOrder);
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 * Use the session given.
	 * @param s the Session
	 */
	public java.util.List<cn.com.chinaebi.dz.object.OriginalDljhLst> findAll (Session s, Order defaultOrder) {
		return super.findAll(s, defaultOrder);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param originalDljhLst a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.OriginalDljhLst originalDljhLst)
	{
		return (java.lang.String) super.save(originalDljhLst);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * Use the Session given.
	 * @param originalDljhLst a transient instance of a persistent class
	 * @param s the Session
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.OriginalDljhLst originalDljhLst, Session s)
	{
		return (java.lang.String) save((Object) originalDljhLst, s);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param originalDljhLst a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.OriginalDljhLst originalDljhLst)
	{
		saveOrUpdate((Object) originalDljhLst);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default the
	 * instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the identifier
	 * property mapping. 
	 * Use the Session given.
	 * @param originalDljhLst a transient instance containing new or updated state.
	 * @param s the Session.
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.OriginalDljhLst originalDljhLst, Session s)
	{
		saveOrUpdate((Object) originalDljhLst, s);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param originalDljhLst a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.OriginalDljhLst originalDljhLst) 
	{
		update((Object) originalDljhLst);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * Use the Session given.
	 * @param originalDljhLst a transient instance containing updated state
	 * @param the Session
	 */
	public void update(cn.com.chinaebi.dz.object.OriginalDljhLst originalDljhLst, Session s)
	{
		update((Object) originalDljhLst, s);
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
	 * @param originalDljhLst the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.OriginalDljhLst originalDljhLst)
	{
		delete((Object) originalDljhLst);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param originalDljhLst the instance to be removed
	 * @param s the Session
	 */
	public void delete(cn.com.chinaebi.dz.object.OriginalDljhLst originalDljhLst, Session s)
	{
		delete((Object) originalDljhLst, s);
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
	public void refresh (cn.com.chinaebi.dz.object.OriginalDljhLst originalDljhLst, Session s)
	{
		refresh((Object) originalDljhLst, s);
	}


}