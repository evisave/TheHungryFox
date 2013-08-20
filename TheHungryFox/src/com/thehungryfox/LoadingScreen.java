package com.thehungryfox;


import com.hungryfox.framework.Game;
import com.hungryfox.framework.Graphics;
import com.hungryfox.framework.Screen;
import com.hungryfox.framework.Graphics.PixmapFormat;
import com.hungryfox.framework.impl.AndroidGraphics;

public class LoadingScreen extends Screen {

	public LoadingScreen(Game game)
	{
		super(game);
	}

	@Override
	public void update(float deltaTime) 
	{
		Graphics g = game.getGraphics();
		Assets.fox = g.newPixmap("fox.png", PixmapFormat.ARGB4444);
		Assets.grapes = g.newPixmap("grapes.png", PixmapFormat.ARGB4444);
		
		Assets.historyFont = ((AndroidGraphics) g).newFont("Roboto-Black.ttf", 14, 0xff000000);
				
		//Assets.click = game.getAudio().newSound("click.ogg");
		
		game.setScreen(new MainScreen(game));
		
	}

	@Override
	public void present(float deltaTime) 
	{
	}

	@Override
	public void pause() 
	{
	}

	@Override
	public void resume() 
	{
	}

	@Override
	public void dispose()
	{
	}

}
