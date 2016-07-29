package org.lorenzoleonardini.nao;

public abstract class Expression
{
	private String name;
	private int faceColor;
	private int brainColor;
	private int earColor;
	private int chestColor;
	
	public Expression(String name, int faceColor, int brainColor, int earColor, int chestColor)
	{
		this.name = name;
		this.faceColor = faceColor;
		this.brainColor = brainColor;
		this.earColor = earColor;
		this.chestColor = chestColor;
	}

	public String getName()
	{
		return name;
	}
	
	public int getFaceColor()
	{
		return faceColor;
	}

	public int getBrainColor()
	{
		return brainColor;
	}

	public int getEarColor()
	{
		return earColor;
	}

	public int getChestColor()
	{
		return chestColor;
	}
}