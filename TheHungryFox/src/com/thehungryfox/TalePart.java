package com.thehungryfox;

import java.util.ArrayList;

import com.hungryfox.framework.Graphics;
import com.hungryfox.framework.Pixmap;
import com.hungryfox.framework.support.Animation;
import com.hungryfox.framework.support.MyRect;
import com.hungryfox.framework.support.TextureRegion;

public class TalePart 
{
	private static TalePart instance;
	
	ArrayList<Pixmap> backgrounds;
	Animation[] animations;
	TextureRegion[] regions;
	ArrayList<MyRect> touchableRect;

	private TalePart()
	{
		backgrounds = new ArrayList<Pixmap>();
	}
	
	// TalePart is a Singleton class.
	public static TalePart getTalePartInstance()
	{
		if(instance == null)
		{
			instance = new TalePart();
		}
		return instance;
	}
	
	public void addBackground(Pixmap bg)
	{
		backgrounds.add(bg);
	}
	
	public void drawBackground(Graphics g, int index) 
	{
		g.drawPixmap(backgrounds.get(index), 0, 0);
	}
}
