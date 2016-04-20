package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseObjectRelevanceColumn;



public class ObjectRelevanceColumn extends BaseObjectRelevanceColumn {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ObjectRelevanceColumn () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ObjectRelevanceColumn (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ObjectRelevanceColumn (
		java.lang.Integer id,
		cn.com.chinaebi.dz.object.DzFileColumnConf dzColumn,
		cn.com.chinaebi.dz.object.CustomObject object,
		java.lang.Integer ruleId,
		java.lang.Integer fileType,
		java.lang.String showAttributeName) {

		super (
			id,
			dzColumn,
			object,
			ruleId,
			fileType,
			showAttributeName);
	}

/*[CONSTRUCTOR MARKER END]*/


}