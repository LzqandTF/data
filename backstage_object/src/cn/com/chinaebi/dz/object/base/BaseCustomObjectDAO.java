package cn.com.chinaebi.dz.object.base;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import cn.com.chinaebi.dz.object.dao.iface.CustomObjectDAO;
import org.hibernate.criterion.Order;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseCustomObjectDAO extends cn.com.chinaebi.dz.object.dao._RootDAO {

	public BaseCustomObjectDAO () {}
	
	public BaseCustomObjectDAO (Session session) {
		super(session);
	}

	// query name references


	public static CustomObjectDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static CustomObjectDAO getInstance () {
		if (null == instance) instance = new cn.com.chinaebi.dz.object.dao.CustomObjectDAO();
		return instance;
	}

	public Class getReferenceClass () {
		return cn.com.chinaebi.dz.object.CustomObject.class;
	}

    public Order getDefaultOrder () {
		return null;
    }

	/**
	 * Cast the object as a cn.com.chinaebi.dz.object.CustomObject
	 */
	public cn.com.chinaebi.dz.object.CustomObject cast (Object object) {
		return (cn.com.chinaebi.dz.object.CustomObject) object;
	}

	public cn.com.chinaebi.dz.object.CustomObject get(java.lang.Integer key)
	{
		return (cn.com.chinaebi.dz.object.CustomObject) get(getReferenceClass(), key);
	}

	public cn.com.chinaebi.dz.object.CustomObject get(java.lang.Integer key, Session s)
	{
		return (cn.com.chinaebi.dz.object.CustomObject) get(getReferenceClass(), key, s);
	}

	public cn.com.chinaebi.dz.object.CustomObject load(java.lang.Integer key)
	{
		return (cn.com.chinaebi.dz.object.CustomObject) load(getReferenceClass(), key);
	}

	public cn.com.chinaebi.dz.object.CustomObject load(java.lang.Integer key, Session s)
	{
		return (cn.com.chinaebi.dz.object.CustomObject) load(getReferenceClass(), key, s);
	}

	public cn.com.chinaebi.dz.object.CustomObject loadInitialize(java.lang.Integer key, Session s) 
	{ 
		cn.com.chinaebi.dz.object.CustomObject obj = load(key, s); 
		if (!Hibernate.isInitialized(obj)) {
			Hibernate.initialize(obj);
		} 
		return obj; 
	}

/* Generic methods */

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List<cn.com.chinaebi.dz.object.CustomObject> findAll () {
		return super.findAll();
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List<cn.com.chinaebi.dz.object.CustomObject> findAll (Order defaultOrder) {
		return super.findAll(defaultOrder);
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 * Use the session given.
	 * @param s the Session
	 */
	public java.util.List<cn.com.chinaebi.dz.object.CustomObject> findAll (Session s, Order defaultOrder) {
		return super.findAll(s, defaultOrder);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param customObject a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.Integer save(cn.com.chinaebi.dz.object.CustomObject customObject)
	{
		return (java.lang.Integer) super.save(customObject);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * Use the Session given.
	 * @param customObject a transient instance of a persistent class
	 * @param s the Session
	 * @return the class identifier
	 */
	public java.lang.Integer save(cn.com.chinaebi.dz.object.CustomObject customObject, Session s)
	{
		return (java.lang.Integer) save((Object) customObject, s);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param customObject a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.CustomObject customObject)
	{
		saveOrUpdate((Object) customObject);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default the
	 * instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the identifier
	 * property mapping. 
	 * Use the Session given.
	 * @param customObject a transient instance containing new or updated state.
	 * @param s the Session.
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.CustomObject customObject, Session s)
	{
		saveOrUpdate((Object) customObject, s);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param customObject a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.CustomObject customObject) 
	{
		update((Object) customObject);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * Use the Session given.
	 * @param customObject a transient instance containing updated state
	 * @param the Session
	 */
	public void update(cn.com.chinaebi.dz.object.CustomObject customObject, Session s)
	{
		update((Object) customObject, s);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.Integer id)
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
	public void delete(java.lang.Integer id, Session s)
	{
		delete((Object) load(id, s), s);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param customObject the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.CustomObject customObject)
	{
		delete((Object) customObject);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param customObject the instance to be removed
	 * @param s the Session
	 */
	public void delete(cn.com.chinaebi.dz.object.CustomObject customObject, Session s)
	{
		delete((Object) customObject, s);
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
	public void refresh (cn.com.chinaebi.dz.object.CustomObject customObject, Session s)
	{
		refresh((Object) customObject, s);
	}


}