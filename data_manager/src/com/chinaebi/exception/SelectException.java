package com.chinaebi.exception;

public class SelectException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SelectException()
    {
    }

    public SelectException(String s)
    {
        super(s);
    }

    public SelectException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public SelectException(Throwable throwable)
    {
        super(throwable);
    }
}
