package cn.com.chinaebi.dz.base.exception;

/**
 * 对账文件数据获取不到
 */
public class DuizhangNotFoundException extends Exception{
	
	private static final long serialVersionUID = 1886679090208008513L;

	public DuizhangNotFoundException()
    {
    }

    public DuizhangNotFoundException(String s)
    {
        super(s);
    }

    public DuizhangNotFoundException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public DuizhangNotFoundException(Throwable throwable)
    {
        super(throwable);
    }
}