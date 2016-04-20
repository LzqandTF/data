package cn.com.chinaebi.dz.object.base;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import cn.com.chinaebi.dz.object.dao.iface.RytXyyhDAO;
import org.hibernate.criterion.Order;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseRytXyyhDAO extends cn.com.chinaebi.dz.object.dao._RootDAO {

	public BaseRytXyyhDAO () {}
	
	public BaseRytXyyhDAO (Session session) {
		super(session);
	}

	// query name references


	public static RytXyyhDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static RytXyyhDAO getInstance () {
		if (null == instance) instance = new cn.com.chinaebi.dz.object.dao.RytXyyhDAO();
		return instance;
	}

	public Class getReferenceClass () {
		return cn.com.chinaebi.dz.object.RytXyyh.class;
	}

    public Order getDefaultOrder () {
		return null;
    }

	/**
	 * Cast the object as a cn.com.chinaebi.dz.object.RytXyyh
	 */
	public cn.com.chinaebi.dz.object.RytXyyh cast (Object object) {
		return (cn.com.chinaebi.dz.object.RytXyyh) object;
	}

	public cn.com.chinaebi.dz.object.RytXyyh get(java.lang.Long key)
	{
		return (cn.com.chinaebi.dz.object.RytXyyh) get(getReferenceClass(), key);
	}

	public cn.com.chinaebi.dz.object.RytXyyh get(java.lang.Long key, Session s)
	{
		return (cn.com.chinaebi.dz.object.RytXyyh) get(getReferenceClass(), key, s);
	}

	public cn.com.chinaebi.dz.object.RytXyyh load(java.lang.Long key)
	{
		return (cn.com.chinaebi.dz.object.RytXyyh) load(getReferenceClass(), key);
	}

	public cn.com.chinaebi.dz.object.RytXyyh load(java.lang.Long key, Session s)
	{
		return (cn.com.chinaebi.dz.object.RytXyyh) load(getReferenceClass(), key, s);
	}

	public cn.com.chinaebi.dz.object.RytXyyh loadInitialize(java.lang.Long key, Session s) 
	{ 
		cn.com.chinaebi.dz.object.RytXyyh obj = load(key, s); 
		if (!Hibernate.isInitialized(obj)) {
			Hibernate.initialize(obj);
		} 
		return obj; 
	}

/* Generic methods */

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List<cn.com.chinaebi.dz.object.RytXyyh> findAll () {
		return super.findAll();
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List<cn.com.chinaebi.dz.object.RytXyyh> findAll (Order defaultOrder) {
		return super.findAll(defaultOrder);
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 * Use the session given.
	 * @param s the Session
	 */
	public java.util.List<cn.com.chinaebi.dz.object.RytXyyh> findAll (Session s, Order defaultOrder) {
		return super.findAll(s, defaultOrder);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param rytXyyh a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.Long save(cn.com.chinaebi.dz.object.RytXyyh rytXyyh)
	{
		return (java.lang.Long) super.save(rytXyyh);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * Use the Session given.
	 * @param rytXyyh a transient instance of a persistent class
	 * @param s the Session
	 * @return the class identifier
	 */
	public java.lang.Long save(cn.com.chinaebi.dz.object.RytXyyh rytXyyh, Session s)
	{
		return (java.lang.Long) save((Object) rytXyyh, s);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param rytXyyh a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.RytXyyh rytXyyh)
	{
		saveOrUpdate((Object) rytXyyh);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default the
	 * instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the identifier
	 * property mapping. 
	 * Use the Session given.
	 * @param rytXyyh a transient instance containing new or updated state.
	 * @param s the Session.
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.RytXyyh rytXyyh, Session s)
	{
		saveOrUpdate((Object) rytXyyh, s);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param rytXyyh a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.RytXyyh rytXyyh) 
	{
		update((Object) rytXyyh);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * Use the Session given.
	 * @param rytXyyh a transient instance containing updated state
	 * @param the Session
	 */
	public void update(cn.com.chinaebi.dz.object.RytXyyh rytXyyh, Session s)
	{
		update((Object) rytXyyh, s);
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
	 * @param rytXyyh the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.RytXyyh rytXyyh)
	{
		delete((Object) rytXyyh);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param rytXyyh the instance to be removed
	 * @param s the Session
	 */
	public void delete(cn.com.chinaebi.dz.object.RytXyyh rytXyyh, Session s)
	{
		delete((Object) rytXyyh, s);
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
	public void refresh (cn.com.chinaebi.dz.object.RytXyyh rytXyyh, Session s)
	{
		refresh((Object) rytXyyh, s);
	}


}