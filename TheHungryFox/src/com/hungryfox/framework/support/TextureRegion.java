package com.hungryfox.framework.support;
import com.hungryfox.framework.Pixmap;

public class TextureRegion 
{
	public final float u1, v1;
    public final float u2, v2;
    public final Pixmap texture;
    
    public TextureRegion(Pixmap texture, float x, float y, float width, float height) 
    {
        this.u1 = x; // / texture.getWidth();
        this.v1 = y;// / texture.getHeight();
        this.u2 = width; //this.u1 + width / texture.getWidth();
        this.v2 = height; // this.v1 + height / texture.getHeight();        
        this.texture = texture;
    }
}
