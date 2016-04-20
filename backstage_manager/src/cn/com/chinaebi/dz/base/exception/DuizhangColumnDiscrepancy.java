package cn.com.chinaebi.dz.base.exception;

/**
 * 对账字段不匹配
 *
 */
public class DuizhangColumnDiscrepancy extends Exception{
	
	private static final long serialVersionUID = 1886679090208008513L;

	public DuizhangColumnDiscrepancy()
    {
    }

    public DuizhangColumnDiscrepancy(String s)
    {
        super(s);
    }

    public DuizhangColumnDiscrepancy(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public DuizhangColumnDiscrepancy(Throwable throwable)
    {
        super(throwable);
    }
}