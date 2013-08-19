package com.thehungryfox;

import java.util.List;

import android.util.Log;

import com.hungryfox.framework.Game;
import com.hungryfox.framework.Graphics;
import com.hungryfox.framework.Pixmap;
import com.hungryfox.framework.Screen;
import com.hungryfox.framework.Input.TouchEvent;
import com.hungryfox.framework.impl.XMLParser;

public class MainScreen extends Screen 
{
	static final int margin = 20;
	
	String text;
	int x, y, imgX;
	Graphics g;
	Pixmap currentPixmap;
	XMLParser parser;
	int indexPart = 0;
	
	public MainScreen(Game game) 
	{
		super(game);
		g = game.getGraphics();
		parser = (XMLParser)game.getParser();
		currentPixmap = Assets.fox;
		imgX = 100;
	}

	@Override
	public void update(float deltaTime) 
	{
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int len = touchEvents.size();
        for (int i = 0; i < len; i++) 
        {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) 
            {
            	if (indexPart == 0)
            	{
            		currentPixmap = Assets.grapes;
                	imgX = 120;
            	}
            	if (indexPart < parser.taleLength() - 1)
            	{
            		indexPart++;
            		Log.d("IndexPart:", " " + indexPart);
            	}
            	return;
            }
        }
        
        text = "";
		text = parser.getTextPart(indexPart);
		x = g.getWidth()/2 - g.getTextWidth(text)/2;
		y = g.getHeight() - g.getTextHeight(text) - margin;
	}

	@Override
	public void present(float deltaTime) 
	{
		g.clear(0xffffffff);
		g.drawPixmap(currentPixmap, imgX, 100);
		g.drawText(text, x, y);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
