package com.thehungryfox;

public class TextPart 
{
	public String text;
	public float xCoord;
	public float yCoord;
	public String[] lines;
	public int alpha;

	public TextPart(String t)
	{
		text = t;
		xCoord = 0.0f;
		yCoord = 0.0f;
		alpha = 0;
		lines = null;
	}
}
