package com.chinaebi.exception;

public class DeleteException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1886679090208008513L;

	public DeleteException()
    {
    }

    public DeleteException(String s)
    {
        super(s);
    }

    public DeleteException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public DeleteException(Throwable throwable)
    {
        super(throwable);
    }
}
