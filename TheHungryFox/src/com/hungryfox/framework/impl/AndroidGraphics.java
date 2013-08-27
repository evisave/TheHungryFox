package com.hungryfox.framework.impl;

import java.io.IOException;
import java.io.InputStream;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.hungryfox.framework.Graphics;
import com.hungryfox.framework.Pixmap;
import com.hungryfox.framework.support.TextureRegion;

public class AndroidGraphics implements Graphics
{
    AssetManager assets;
    Bitmap frameBuffer;
    Canvas canvas;
    Paint paint;
    Typeface font;
    Rect srcRect = new Rect();
    Rect dstRect = new Rect();

    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) 
    {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
    }

    public Pixmap newPixmap(String fileName, PixmapFormat format) 
    {
    	Config config = null;
        if (format == PixmapFormat.RGB565)
            config = Config.RGB_565;
        else if (format == PixmapFormat.ARGB4444)
            config = Config.ARGB_4444;
        else
            config = Config.ARGB_8888;

        Options options = new Options();
        options.inPreferredConfig = config;

        InputStream in = null;
        Bitmap bitmap = null;
        try 
        {
            in = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(in);
            if (bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '" + fileName + "'");
        } 
        catch (IOException e) 
        {
            throw new RuntimeException("Couldn't load bitmap from asset '" + fileName + "'");
        } 
        finally 
        {
            if (in != null) 
            {
                try 
                {
                    in.close();
                } 
                catch (IOException e) 
                {
                }
            }
        }

        if (bitmap.getConfig() == Config.RGB_565)
            format = PixmapFormat.RGB565;
        else if (bitmap.getConfig() == Config.ARGB_4444)
            format = PixmapFormat.ARGB4444;
        else
            format = PixmapFormat.ARGB8888;

        return new AndroidPixmap(bitmap, format);
    }

    /*public Font newFont(String fontName)
    {
		return new AndroidFont(assets, fontName);
    }*/
    
    public Typeface newFont(String fontName, int size, int color)
    {
    	font = Typeface.createFromAsset(assets, fontName);
    	paint.setColor(color);
		paint.setTypeface(font);
		paint.setTextSize(size);
		return font;
    }
    
    public void clear(int color) 
    {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,(color & 0xff));
    }
    
    public void drawPixel(int x, int y, int color) 
    {
        paint.setColor(color);
        canvas.drawPoint(x, y, paint);
    }

    public void drawLine(int x, int y, int x2, int y2, int color) 
    {
        paint.setColor(color);
        canvas.drawLine(x, y, x2, y2, paint);
    }

    public void drawRect(int x, int y, int width, int height, int color) 
    {
        paint.setColor(color);
        paint.setStyle(Style.FILL);
        canvas.drawRect(x, y, x + width - 1, y + width - 1, paint);
    }

    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight) 
    {
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth - 1;
        srcRect.bottom = srcY + srcHeight - 1;

        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth - 1;
        dstRect.bottom = y + srcHeight - 1;

        canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, srcRect, dstRect, null);
    }

    public void drawPixmap(Pixmap pixmap, int x, int y) 
    {
        canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, x, y, null);
    }

    public void drawRegion(Pixmap pixmap, TextureRegion region, int x, int y) 
    {
    	srcRect.left = (int)region.u1;
        srcRect.top = (int)region.v1;
        srcRect.right = (int)region.u1 + (int)region.u2 - 1;
        srcRect.bottom = (int)region.v1 + (int)region.v2 - 1;
        
        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + (int)region.u2 - 1;
        dstRect.bottom = y + (int)region.v2 - 1;
        
    	canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, srcRect, dstRect, null);
    }
    
    public void drawText(String text, float x, float y)
    {
    	canvas.drawText(text, x, y, paint);
    }
    
    public int getWidth() 
    {
        return frameBuffer.getWidth();
    }

    public int getHeight() 
    {
        return frameBuffer.getHeight();
    }
    
    public int getTextWidth(String textLine)
    {
    	Rect bound = new Rect();
    	paint.getTextBounds(textLine, 0, textLine.length(), bound);
    	return bound.width();
    }
    
    public int getTextHeight(String textLine)
    {
    	Rect bound = new Rect();
    	paint.getTextBounds(textLine, 0, textLine.length(), bound);
    	return bound.height();
    }
    
    public void setAlpha(int alpha)
    {
    	paint.setAlpha(alpha);
    }
}

