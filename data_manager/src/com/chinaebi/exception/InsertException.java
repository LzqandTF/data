package com.chinaebi.exception;

public class InsertException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8897131244146415500L;

	public InsertException()
    {
    }

    public InsertException(String s)
    {
        super(s);
    }

    public InsertException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public InsertException(Throwable throwable)
    {
        super(throwable);
    }
}
