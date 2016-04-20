package com.chinaebi.exception;

public class UpdateException extends Exception{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2211722065784582872L;

	public UpdateException()
    {
    }

    public UpdateException(String s)
    {
        super(s);
    }

    public UpdateException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public UpdateException(Throwable throwable)
    {
        super(throwable);
    }
}
