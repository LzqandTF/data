package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseRuleTemplate;



public class RuleTemplate extends BaseRuleTemplate {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public RuleTemplate () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public RuleTemplate (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public RuleTemplate (
		java.lang.Integer id,
		java.lang.String templateName,
		java.lang.String templateFunction) {

		super (
			id,
			templateName,
			templateFunction);
	}

/*[CONSTRUCTOR MARKER END]*/


}