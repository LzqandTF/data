package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseRuleHandler;



public class RuleHandler extends BaseRuleHandler {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public RuleHandler () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public RuleHandler (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public RuleHandler (
		java.lang.Integer id,
		java.lang.String dzColumnName,
		java.lang.String attributeColumn,
		java.lang.String oldValue,
		java.lang.String newValue,
		java.lang.Integer handlerType,
		java.lang.Integer templateId) {

		super (
			id,
			dzColumnName,
			attributeColumn,
			oldValue,
			newValue,
			handlerType,
			templateId);
	}

/*[CONSTRUCTOR MARKER END]*/


}