package com.thehungryfox;

import java.util.List;

import com.hungryfox.framework.Game;
import com.hungryfox.framework.Graphics;
import com.hungryfox.framework.Pixmap;
import com.hungryfox.framework.Screen;
import com.hungryfox.framework.Input.TouchEvent;
import com.hungryfox.framework.impl.Animation;
import com.hungryfox.framework.impl.MyRect;
import com.hungryfox.framework.impl.TextureRegion;
import com.hungryfox.framework.impl.XMLParser;

public class MainScreen extends Screen 
{
	static final float margin = 20.0f;
	static final int maxAlpha = 250;
	float yOff;
	//String text, previousTextPart, nextTextPart;
	TextPart currentTextPart, previousTextPart, nextTextPart;
	int imgX, imgY;
	Graphics g;
	Pixmap currentPixmap;
	Pixmap spritesheet;
	XMLParser parser;
	int taleLength, indexPart = 0;
	float stateTime = 0.0f;
	//String[] lines;
	
	public MainScreen(Game game) 
	{
		super(game);
		g = game.getGraphics();
		parser = (XMLParser)game.getParser();
		currentPixmap = Assets.bg01;
		spritesheet = Assets.spritesheet;
		imgX = parser.getRectPart(indexPart).x;
		imgY = parser.getRectPart(indexPart).y;
		taleLength = parser.taleLength();
		
		currentTextPart = new TextPart(parser.getTextPart(indexPart));
		currentTextPart.lines = currentTextPart.text.split("\n");
		yOff = g.getTextHeight(currentTextPart.lines[0]);
		currentTextPart.yCoord = g.getHeight() - yOff - margin;
		previousTextPart = new TextPart("");
		nextTextPart = new TextPart("");
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
            stateTime = 0.0f;
            if (event.type == TouchEvent.TOUCH_UP) 
            {
            	if (inBounds(event, parser.getRectPart(indexPart)))
            	{
            		if (indexPart == 0)
                	{
                		currentPixmap = Assets.bg02;
                	}
            		if (indexPart < taleLength - 1)
            		{
            			previousTextPart.text = currentTextPart.text;
            			previousTextPart.alpha = maxAlpha;
            			previousTextPart.xCoord = currentTextPart.xCoord;
            			previousTextPart.yCoord = currentTextPart.yCoord;
            			previousTextPart.lines = currentTextPart.lines;
            		
            			currentTextPart.lines = null;
            			currentTextPart.alpha = 0;
            			currentTextPart.text = parser.getTextPart(++indexPart);
            			currentTextPart.lines = currentTextPart.text.split("\n");
            			if (indexPart + 1 < taleLength - 1)
            				nextTextPart.text = parser.getTextPart(indexPart + 1);
            			else
            				nextTextPart.text = "";
            		
            			imgX = parser.getRectPart(indexPart).x;
            			imgY = parser.getRectPart(indexPart).y;
            		}
            	}
            	return;
            }
        }
        if (previousTextPart.alpha != 0)
        {
        	previousTextPart.alpha -= 10;
        	g.setAlpha(previousTextPart.alpha);
        	previousTextPart.yCoord -= 50.0f * deltaTime;
        }
        else
        {
        	if (currentTextPart.alpha < maxAlpha)
        	{
        		currentTextPart.alpha += 2;
        		g.setAlpha(currentTextPart.alpha);
        	}
        	else 
        	{
        		currentTextPart.alpha = maxAlpha;
        		g.setAlpha(currentTextPart.alpha);
        	}
        }
	}

	public void present(float deltaTime) 
	{
		stateTime += deltaTime;
		g.clear(0xffffffff);
		
		// Draw images.
		g.drawPixmap(currentPixmap, 0, 0);
		
		TextureRegion keyFrame = Assets.foxWait.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
		g.drawPixmap(spritesheet, imgX, imgY, (int)keyFrame.u1, (int)keyFrame.v1, (int)keyFrame.u2, (int)keyFrame.v2);
		
		// Draw text.
		if (previousTextPart.alpha != 0)
		{
			int off = 0;
			for (int i = 0; i < previousTextPart.lines.length; ++i) 
			{ 
				previousTextPart.xCoord = g.getWidth()/2 - g.getTextWidth(previousTextPart.lines[i])/2;
				g.drawText(previousTextPart.lines[i], previousTextPart.xCoord, previousTextPart.yCoord + off);
				off += yOff;
			}
		}
		else
		{
			int off = 0;
			for (int i = 0; i < currentTextPart.lines.length; ++i) 
			{
				currentTextPart.xCoord = g.getWidth()/2 - g.getTextWidth(currentTextPart.lines[i])/2;
				g.drawText(currentTextPart.lines[i], currentTextPart.xCoord, currentTextPart.yCoord + off);
				off += yOff;
			}
		}
		
	}

	 private boolean inBounds(TouchEvent event, MyRect touchRect)
	 {
		 if(event.x > touchRect.x && event.x < touchRect.x + touchRect.width - 1 
				 && event.y > touchRect.y && event.y < touchRect.y + touchRect.height - 1) 
			 return true;
		 else
			 return false;
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
