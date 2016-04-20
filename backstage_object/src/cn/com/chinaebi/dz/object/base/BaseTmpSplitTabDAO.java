package cn.com.chinaebi.dz.object.base;

import org.hibernate.Session;
import cn.com.chinaebi.dz.object.dao.iface.TmpSplitTabDAO;
import org.hibernate.criterion.Order;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseTmpSplitTabDAO extends cn.com.chinaebi.dz.object.dao._RootDAO {

	public BaseTmpSplitTabDAO () {}
	
	public BaseTmpSplitTabDAO (Session session) {
		super(session);
	}

	// query name references


	public static TmpSplitTabDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static TmpSplitTabDAO getInstance () {
		if (null == instance) instance = new cn.com.chinaebi.dz.object.dao.TmpSplitTabDAO();
		return instance;
	}

	public Class getReferenceClass () {
		return cn.com.chinaebi.dz.object.TmpSplitTab.class;
	}

    public Order getDefaultOrder () {
		return null;
    }

	/**
	 * Cast the object as a cn.com.chinaebi.dz.object.TmpSplitTab
	 */
	public cn.com.chinaebi.dz.object.TmpSplitTab cast (Object object) {
		return (cn.com.chinaebi.dz.object.TmpSplitTab) object;
	}


/* Generic methods */

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List<cn.com.chinaebi.dz.object.TmpSplitTab> findAll () {
		return super.findAll();
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List<cn.com.chinaebi.dz.object.TmpSplitTab> findAll (Order defaultOrder) {
		return super.findAll(defaultOrder);
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 * Use the session given.
	 * @param s the Session
	 */
	public java.util.List<cn.com.chinaebi.dz.object.TmpSplitTab> findAll (Session s, Order defaultOrder) {
		return super.findAll(s, defaultOrder);
	}


	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tmpSplitTab a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.TmpSplitTab tmpSplitTab)
	{
		saveOrUpdate((Object) tmpSplitTab);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default the
	 * instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the identifier
	 * property mapping. 
	 * Use the Session given.
	 * @param tmpSplitTab a transient instance containing new or updated state.
	 * @param s the Session.
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.TmpSplitTab tmpSplitTab, Session s)
	{
		saveOrUpdate((Object) tmpSplitTab, s);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tmpSplitTab a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.TmpSplitTab tmpSplitTab) 
	{
		update((Object) tmpSplitTab);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * Use the Session given.
	 * @param tmpSplitTab a transient instance containing updated state
	 * @param the Session
	 */
	public void update(cn.com.chinaebi.dz.object.TmpSplitTab tmpSplitTab, Session s)
	{
		update((Object) tmpSplitTab, s);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tmpSplitTab the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.TmpSplitTab tmpSplitTab)
	{
		delete((Object) tmpSplitTab);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param tmpSplitTab the instance to be removed
	 * @param s the Session
	 */
	public void delete(cn.com.chinaebi.dz.object.TmpSplitTab tmpSplitTab, Session s)
	{
		delete((Object) tmpSplitTab, s);
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
	public void refresh (cn.com.chinaebi.dz.object.TmpSplitTab tmpSplitTab, Session s)
	{
		refresh((Object) tmpSplitTab, s);
	}


}