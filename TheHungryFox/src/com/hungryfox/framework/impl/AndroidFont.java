package com.hungryfox.framework.impl;

import android.graphics.Typeface;
import com.hungryfox.framework.Font;

public class AndroidFont implements Font 
{
	Typeface font;
	
	public AndroidFont(Typeface font) 
	{
		this.font = font;
	}

	@Override
	public int getWidth() 
	{
		return 0;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSize(int size) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
