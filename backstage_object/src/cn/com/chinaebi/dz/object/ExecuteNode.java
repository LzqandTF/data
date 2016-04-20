package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseExecuteNode;



public class ExecuteNode extends BaseExecuteNode {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ExecuteNode () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ExecuteNode (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ExecuteNode (
		java.lang.String id,
		java.lang.Integer deductSysId,
		java.util.Date deductStmlDate) {

		super (
			id,
			deductSysId,
			deductStmlDate);
	}

/*[CONSTRUCTOR MARKER END]*/


}