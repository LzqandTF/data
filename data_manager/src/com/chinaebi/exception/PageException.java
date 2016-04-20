package com.chinaebi.exception;

public class PageException extends Exception{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5430067090275931139L;

	public PageException()
    {
    }

    public PageException(String s)
    {
        super(s);
    }

    public PageException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public PageException(Throwable throwable)
    {
        super(throwable);
    }
}
