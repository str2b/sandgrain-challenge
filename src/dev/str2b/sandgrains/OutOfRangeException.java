package dev.str2b.sandgrains;

import java.awt.Point;

public class OutOfRangeException extends Exception {
	
	private String message;
	
	public String getMessage()
	{
		return message;
	}
	
	public OutOfRangeException(Point p)
	{
		this.message = "Point is out of range: " + p;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8614144490858272070L;
	
}
