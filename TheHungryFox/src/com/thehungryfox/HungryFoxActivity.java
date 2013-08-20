package com.thehungryfox;

import com.hungryfox.framework.Screen;

import com.hungryfox.framework.impl.AndroidGame;

public class HungryFoxActivity extends AndroidGame 
{
	@Override
	public Screen getStartScreen() 
	{
		return new LoadingScreen(this);
	} 
}
