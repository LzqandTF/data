package cn.com.chinaebi.dz.object.base;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import cn.com.chinaebi.dz.object.dao.iface.RytYunnanNxsDAO;
import org.hibernate.criterion.Order;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseRytYunnanNxsDAO extends cn.com.chinaebi.dz.object.dao._RootDAO {

	public BaseRytYunnanNxsDAO () {}
	
	public BaseRytYunnanNxsDAO (Session session) {
		super(session);
	}

	// query name references


	public static RytYunnanNxsDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static RytYunnanNxsDAO getInstance () {
		if (null == instance) instance = new cn.com.chinaebi.dz.object.dao.RytYunnanNxsDAO();
		return instance;
	}

	public Class getReferenceClass () {
		return cn.com.chinaebi.dz.object.RytYunnanNxs.class;
	}

    public Order getDefaultOrder () {
		return null;
    }

	/**
	 * Cast the object as a cn.com.chinaebi.dz.object.RytYunnanNxs
	 */
	public cn.com.chinaebi.dz.object.RytYunnanNxs cast (Object object) {
		return (cn.com.chinaebi.dz.object.RytYunnanNxs) object;
	}

	public cn.com.chinaebi.dz.object.RytYunnanNxs get(java.lang.Long key)
	{
		return (cn.com.chinaebi.dz.object.RytYunnanNxs) get(getReferenceClass(), key);
	}

	public cn.com.chinaebi.dz.object.RytYunnanNxs get(java.lang.Long key, Session s)
	{
		return (cn.com.chinaebi.dz.object.RytYunnanNxs) get(getReferenceClass(), key, s);
	}

	public cn.com.chinaebi.dz.object.RytYunnanNxs load(java.lang.Long key)
	{
		return (cn.com.chinaebi.dz.object.RytYunnanNxs) load(getReferenceClass(), key);
	}

	public cn.com.chinaebi.dz.object.RytYunnanNxs load(java.lang.Long key, Session s)
	{
		return (cn.com.chinaebi.dz.object.RytYunnanNxs) load(getReferenceClass(), key, s);
	}

	public cn.com.chinaebi.dz.object.RytYunnanNxs loadInitialize(java.lang.Long key, Session s) 
	{ 
		cn.com.chinaebi.dz.object.RytYunnanNxs obj = load(key, s); 
		if (!Hibernate.isInitialized(obj)) {
			Hibernate.initialize(obj);
		} 
		return obj; 
	}

/* Generic methods */

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List<cn.com.chinaebi.dz.object.RytYunnanNxs> findAll () {
		return super.findAll();
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List<cn.com.chinaebi.dz.object.RytYunnanNxs> findAll (Order defaultOrder) {
		return super.findAll(defaultOrder);
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 * Use the session given.
	 * @param s the Session
	 */
	public java.util.List<cn.com.chinaebi.dz.object.RytYunnanNxs> findAll (Session s, Order defaultOrder) {
		return super.findAll(s, defaultOrder);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param rytYunnanNxs a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.Long save(cn.com.chinaebi.dz.object.RytYunnanNxs rytYunnanNxs)
	{
		return (java.lang.Long) super.save(rytYunnanNxs);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * Use the Session given.
	 * @param rytYunnanNxs a transient instance of a persistent class
	 * @param s the Session
	 * @return the class identifier
	 */
	public java.lang.Long save(cn.com.chinaebi.dz.object.RytYunnanNxs rytYunnanNxs, Session s)
	{
		return (java.lang.Long) save((Object) rytYunnanNxs, s);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param rytYunnanNxs a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.RytYunnanNxs rytYunnanNxs)
	{
		saveOrUpdate((Object) rytYunnanNxs);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default the
	 * instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the identifier
	 * property mapping. 
	 * Use the Session given.
	 * @param rytYunnanNxs a transient instance containing new or updated state.
	 * @param s the Session.
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.RytYunnanNxs rytYunnanNxs, Session s)
	{
		saveOrUpdate((Object) rytYunnanNxs, s);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param rytYunnanNxs a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.RytYunnanNxs rytYunnanNxs) 
	{
		update((Object) rytYunnanNxs);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * Use the Session given.
	 * @param rytYunnanNxs a transient instance containing updated state
	 * @param the Session
	 */
	public void update(cn.com.chinaebi.dz.object.RytYunnanNxs rytYunnanNxs, Session s)
	{
		update((Object) rytYunnanNxs, s);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.Long id)
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
	public void delete(java.lang.Long id, Session s)
	{
		delete((Object) load(id, s), s);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param rytYunnanNxs the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.RytYunnanNxs rytYunnanNxs)
	{
		delete((Object) rytYunnanNxs);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param rytYunnanNxs the instance to be removed
	 * @param s the Session
	 */
	public void delete(cn.com.chinaebi.dz.object.RytYunnanNxs rytYunnanNxs, Session s)
	{
		delete((Object) rytYunnanNxs, s);
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
	public void refresh (cn.com.chinaebi.dz.object.RytYunnanNxs rytYunnanNxs, Session s)
	{
		refresh((Object) rytYunnanNxs, s);
	}


}