package com.hungryfox.framework.impl;

//import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
//import android.graphics.Point;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
//import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import com.hungryfox.framework.Audio;
import com.hungryfox.framework.FileIO;
import com.hungryfox.framework.Game;
import com.hungryfox.framework.Graphics;
import com.hungryfox.framework.Input;
import com.hungryfox.framework.Parser;
import com.hungryfox.framework.Screen;

public abstract class AndroidGame extends Activity implements Game 
{
    AndroidFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    Parser parser;
    WakeLock wakeLock;

    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        int frameBufferWidth = isLandscape ? 480 : 320;
        int frameBufferHeight = isLandscape ? 320 : 480;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Config.RGB_565);

//		@Deprecated.
		float scaleX = (float) frameBufferWidth
                / getWindowManager().getDefaultDisplay().getWidth();
		float scaleY = (float) frameBufferHeight
                / getWindowManager().getDefaultDisplay().getHeight();

        //  --- Requires Target API 13
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        float scaleX = (float) frameBufferWidth / size.x;
//        float scaleY = (float) frameBufferHeight / size.y;
        //  -----------------------------
        
        renderView = new AndroidFastRenderView(this, frameBuffer);
        graphics = new AndroidGraphics(getAssets(), frameBuffer);
        fileIO = new AndroidFileIO(this);
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderView, scaleX, scaleY);
        parser = new XMLParser(getAssets());
        screen = getStartScreen();
        setContentView(renderView);
        
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
    }

    @Override
    public void onResume() 
    {
        super.onResume();
        wakeLock.acquire();
        screen.resume();
        renderView.resume();
    }

    @Override
    public void onPause() 
    {
        super.onPause();
        wakeLock.release();
        renderView.pause();
        screen.pause();

        if (isFinishing())
            screen.dispose();
    }

    public Input getInput() 
    {
        return input;
    }

    public FileIO getFileIO() 
    {
        return fileIO;
    }

    public Graphics getGraphics() 
    {
        return graphics;
    }

    public Audio getAudio() 
    {
        return audio;
    }

    public Parser getParser() 
    {
        return parser;
    }
    
    public void setScreen(Screen screen) 
    {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    public Screen getCurrentScreen() 
    {
        return screen;
    }
}

