package com.thehungryfox;

import android.graphics.Typeface;

import com.hungryfox.framework.Graphics;
import com.hungryfox.framework.Pixmap;
import com.hungryfox.framework.Graphics.PixmapFormat;
import com.hungryfox.framework.impl.AndroidGame;
import com.hungryfox.framework.impl.AndroidGraphics;
import com.hungryfox.framework.impl.Animation;
import com.hungryfox.framework.impl.TextureRegion;

public class Assets 
{
	// Images assets, really they will be TextureRegion.
	// First Part.
	//public static Pixmap fox01;
	public static Pixmap spritesheet;
	public static Pixmap bg01;
	public static Animation foxWait;
	
	// Second Part.
	//public static Pixmap fox02;
	public static Pixmap bg02;
	public static TextureRegion grapes01;
	public static TextureRegion grapes02;
	public static TextureRegion grapes03;
	public static TextureRegion vine;
	/*public static Pixmap vine;
	public static Pixmap grapes01;
	public static Pixmap grapes02;
	public static Pixmap grapes03;*/
	
	// Third Part.
	//public static Pixmap fox03;
	public static Pixmap bg03;
	public static TextureRegion trellis;
	//public static Pixmap trellis;
	
	// Fourth Part. Add animation...
	//public static Pixmap fox04;
	//public static Pixmap bg04;
	public static Animation foxJump;
	
	// Fifth Part. 
	// Sixth Part.
	//public static Pixmap fox05;
	
	public static Typeface historyFont;
	
	//public static Sound click;
	
	public static void load(AndroidGame game) 
	{
		Graphics g = game.getGraphics();
		spritesheet = g.newPixmap("hungryfox.png", PixmapFormat.ARGB4444);
		historyFont = ((AndroidGraphics) g).newFont("Roboto-Black.ttf", 14, 0xffffffff);
		
		// Part 1
		bg01 = g.newPixmap("bg01.jpg", PixmapFormat.ARGB4444);
		foxWait = new Animation(0.2f, 
								new TextureRegion(spritesheet,0, 0, 32, 64),
								new TextureRegion(spritesheet,32, 0, 32, 64),
								new TextureRegion(spritesheet,64, 0, 32, 64));
		// Part 2
		bg02 = g.newPixmap("bg02.jpg", PixmapFormat.ARGB4444);
		grapes01 = new TextureRegion(spritesheet, 224, 0, 64, 64);
		grapes02 = new TextureRegion(spritesheet, 288, 0, 64, 64);
		grapes03 = new TextureRegion(spritesheet, 352, 0, 64, 64);
		vine = new TextureRegion(spritesheet, 0, 64, 256, 256);
	}
}
