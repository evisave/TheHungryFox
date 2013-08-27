package com.hungryfox.framework.support;

import com.hungryfox.framework.Graphics;

import android.util.Log;

public class FPSCounter {
    long startTime = System.nanoTime();
    int fps, frames = 0;
    
    public void logFrame() 
    {
        frames++;
        if(System.nanoTime() - startTime >= 1000000000) 
        {
            Log.d("FPSCounter", "fps: " + frames);
            frames = 0;
            startTime = System.nanoTime();
        }
    }
    
    public void drawFrame(Graphics ag)
    {
    	ag.drawText("FPS: " + fps, 20.0f, 20.0f);
    	frames++;
    	if(System.nanoTime() - startTime >= 1000000000) 
        {
    		fps = frames;
    		frames = 0;
            startTime = System.nanoTime();
        }
    }
} 
