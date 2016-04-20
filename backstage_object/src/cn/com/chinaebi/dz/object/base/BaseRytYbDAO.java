package cn.com.chinaebi.dz.object.base;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import cn.com.chinaebi.dz.object.dao.iface.RytYbDAO;
import org.hibernate.criterion.Order;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseRytYbDAO extends cn.com.chinaebi.dz.object.dao._RootDAO {

	public BaseRytYbDAO () {}
	
	public BaseRytYbDAO (Session session) {
		super(session);
	}

	// query name references


	public static RytYbDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static RytYbDAO getInstance () {
		if (null == instance) instance = new cn.com.chinaebi.dz.object.dao.RytYbDAO();
		return instance;
	}

	public Class getReferenceClass () {
		return cn.com.chinaebi.dz.object.RytYb.class;
	}

    public Order getDefaultOrder () {
		return null;
    }

	/**
	 * Cast the object as a cn.com.chinaebi.dz.object.RytYb
	 */
	public cn.com.chinaebi.dz.object.RytYb cast (Object object) {
		return (cn.com.chinaebi.dz.object.RytYb) object;
	}

	public cn.com.chinaebi.dz.object.RytYb get(java.lang.Long key)
	{
		return (cn.com.chinaebi.dz.object.RytYb) get(getReferenceClass(), key);
	}

	public cn.com.chinaebi.dz.object.RytYb get(java.lang.Long key, Session s)
	{
		return (cn.com.chinaebi.dz.object.RytYb) get(getReferenceClass(), key, s);
	}

	public cn.com.chinaebi.dz.object.RytYb load(java.lang.Long key)
	{
		return (cn.com.chinaebi.dz.object.RytYb) load(getReferenceClass(), key);
	}

	public cn.com.chinaebi.dz.object.RytYb load(java.lang.Long key, Session s)
	{
		return (cn.com.chinaebi.dz.object.RytYb) load(getReferenceClass(), key, s);
	}

	public cn.com.chinaebi.dz.object.RytYb loadInitialize(java.lang.Long key, Session s) 
	{ 
		cn.com.chinaebi.dz.object.RytYb obj = load(key, s); 
		if (!Hibernate.isInitialized(obj)) {
			Hibernate.initialize(obj);
		} 
		return obj; 
	}

/* Generic methods */

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List<cn.com.chinaebi.dz.object.RytYb> findAll () {
		return super.findAll();
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List<cn.com.chinaebi.dz.object.RytYb> findAll (Order defaultOrder) {
		return super.findAll(defaultOrder);
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 * Use the session given.
	 * @param s the Session
	 */
	public java.util.List<cn.com.chinaebi.dz.object.RytYb> findAll (Session s, Order defaultOrder) {
		return super.findAll(s, defaultOrder);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param rytYb a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.Long save(cn.com.chinaebi.dz.object.RytYb rytYb)
	{
		return (java.lang.Long) super.save(rytYb);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * Use the Session given.
	 * @param rytYb a transient instance of a persistent class
	 * @param s the Session
	 * @return the class identifier
	 */
	public java.lang.Long save(cn.com.chinaebi.dz.object.RytYb rytYb, Session s)
	{
		return (java.lang.Long) save((Object) rytYb, s);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param rytYb a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.RytYb rytYb)
	{
		saveOrUpdate((Object) rytYb);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default the
	 * instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the identifier
	 * property mapping. 
	 * Use the Session given.
	 * @param rytYb a transient instance containing new or updated state.
	 * @param s the Session.
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.RytYb rytYb, Session s)
	{
		saveOrUpdate((Object) rytYb, s);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param rytYb a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.RytYb rytYb) 
	{
		update((Object) rytYb);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * Use the Session given.
	 * @param rytYb a transient instance containing updated state
	 * @param the Session
	 */
	public void update(cn.com.chinaebi.dz.object.RytYb rytYb, Session s)
	{
		update((Object) rytYb, s);
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
	 * @param rytYb the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.RytYb rytYb)
	{
		delete((Object) rytYb);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param rytYb the instance to be removed
	 * @param s the Session
	 */
	public void delete(cn.com.chinaebi.dz.object.RytYb rytYb, Session s)
	{
		delete((Object) rytYb, s);
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
	public void refresh (cn.com.chinaebi.dz.object.RytYb rytYb, Session s)
	{
		refresh((Object) rytYb, s);
	}


}