package cn.com.chinaebi.dz.base.exception;

/**
 * 原始交易数据获取不到
 */
public class OriginalDataNotFoundException  extends Exception{
	
	private static final long serialVersionUID = 1886679090208008513L;

	public OriginalDataNotFoundException()
    {
    }

    public OriginalDataNotFoundException(String s)
    {
        super(s);
    }

    public OriginalDataNotFoundException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public OriginalDataNotFoundException(Throwable throwable)
    {
        super(throwable);
    }
}