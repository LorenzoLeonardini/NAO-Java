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
	
	public static class DefaultExpression extends Expression
	{
		public DefaultExpression()
		{
			super("Default", 0xffffff, 0x0000ff, 0x0000ff, 0xffff00);
		}
	}
	
	public static class HappyExpression extends Expression
	{
		public HappyExpression()
		{
			super("Happy", 0x00ff00, 0x0000ff, 0x0000ff, 0x00ff00);
		}
	}
	
	public static class AngryExpression extends Expression
	{
		public AngryExpression()
		{
			super("Angry", 0xff0000, 0xff0000, 0xff0000, 0xff0000);
		}
	}

}
