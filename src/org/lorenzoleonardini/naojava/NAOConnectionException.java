package org.lorenzoleonardini.naojava;

public class NAOConnectionException extends Exception
{
	private static final long serialVersionUID = 1L;

	public NAOConnectionException()
	{
		super();
	}
	
	public NAOConnectionException(String message)
	{
		super(message);
	}
	
	public NAOConnectionException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public NAOConnectionException(Throwable cause)
	{
		super(cause);
	}
}
