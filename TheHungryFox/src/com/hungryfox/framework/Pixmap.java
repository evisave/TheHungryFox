package com.hungryfox.framework;

import com.hungryfox.framework.Graphics.PixmapFormat;

public interface Pixmap 
{
    public int getWidth();

    public int getHeight();

    public PixmapFormat getFormat();

    public void dispose();
}
