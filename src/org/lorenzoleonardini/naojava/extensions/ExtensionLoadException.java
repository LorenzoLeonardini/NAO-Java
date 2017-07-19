package org.lorenzoleonardini.naojava.extensions;

public class ExtensionLoadException extends Exception
{
	private static final long serialVersionUID = 1L;

	public ExtensionLoadException()
	{
		super();
	}
	
	public ExtensionLoadException(String message)
	{
		super(message);
	}
	
	public ExtensionLoadException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public ExtensionLoadException(Throwable cause)
	{
		super(cause);
	}
}
