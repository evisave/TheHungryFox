package com.hungryfox.framework.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import com.hungryfox.framework.Parser;
import android.content.res.AssetManager;


class Part
{
	public String period;
	public String background;
	public MyRect[] framesAnimation;
	public MyRect[] touchRect;
}

public class XMLParser implements Parser
{
	private static XMLParser instance;
	
	XmlPullParserFactory pullParserFactory;
	static ArrayList<Part> parts;
	
	private XMLParser(AssetManager asset)
	{
		try 
		{
			pullParserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullParserFactory.newPullParser();
			InputStream in_s = asset.open("history.xml");
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	        parser.setInput(in_s, null);
	        parseXML(parser);
		} 
		catch (XmlPullParserException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static XMLParser getXMLParser(AssetManager asset)
	{
		if(instance == null)
		{
			instance = new XMLParser(asset);
		}
		return instance;
	}
	
	private void parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
	{
        int eventType = parser.getEventType();
        Part currentPart = null;
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            String name = null;
            switch (eventType)
            {
                case XmlPullParser.START_DOCUMENT:
                	parts = new ArrayList<Part>();
                    break;
                case XmlPullParser.START_TAG:
                {
                    name = parser.getName();
                    if (name.equalsIgnoreCase("part"))
                    {
                    	currentPart = new Part();
                    } 
                    else if (currentPart != null)
                    {
                    	if (name.equalsIgnoreCase("period"))
                        {
                        	currentPart.period = parser.nextText();
                        }
                    	else if (name.equalsIgnoreCase("background"))
                    	{
                    		currentPart.background = parser.nextText();
                    	}
                    	else if (name.equalsIgnoreCase("animation"))
                    	{
                    		String[] frames, rectFields;
                    		frames = parser.nextText().split(";");
                    		currentPart.framesAnimation = new MyRect[frames.length];
                    		for (int i = 0; i < frames.length; ++i)
                    		{
                    			rectFields = frames[i].split(",");
                        		currentPart.framesAnimation[i] = new MyRect();
                            	currentPart.framesAnimation[i].width = Integer.parseInt(rectFields[2]);
                            	currentPart.framesAnimation[i].height = Integer.parseInt(rectFields[3]);
                            	currentPart.framesAnimation[i].x = 
                            			Integer.parseInt(rectFields[0]) - currentPart.framesAnimation[i].width/2;
                            	currentPart.framesAnimation[i].y = 
                            			Integer.parseInt(rectFields[1]) - currentPart.framesAnimation[i].height/2;
                    		}
                    	}
                        else if (name.equalsIgnoreCase("rect"))
                        {
                        	String[] rectFields;
                        	// Pick each touchable rectangle of the current part.
                        	String[] touchableRect = parser.nextText().split(";");
                        	// Pick coord rectangle for each rectangle of the part.
                        	currentPart.touchRect = new MyRect[touchableRect.length];
                        	for (int i = 0; i < touchableRect.length; ++i)
                        	{
                        		rectFields = touchableRect[i].split(",");
                        		currentPart.touchRect[i] = new MyRect();
                            	currentPart.touchRect[i].width = Integer.parseInt(rectFields[2]);
                            	currentPart.touchRect[i].height = Integer.parseInt(rectFields[3]);
                            	currentPart.touchRect[i].x = 
                            			Integer.parseInt(rectFields[0]) - currentPart.touchRect[i].width/2;
                            	currentPart.touchRect[i].y = 
                            			Integer.parseInt(rectFields[1]) - currentPart.touchRect[i].height/2;
                        	}
                        	//String[] rectFields = parser.nextText().split(",");
                        	 
                        } 
                    }
                }
                break;
                case XmlPullParser.END_TAG:
                {
                    name = parser.getName();
                    if (name.equalsIgnoreCase("part") && currentPart != null)
                    {
                    	parts.add(currentPart);
                    }
                }
                break;
            }
            eventType = parser.nextToken();
        }
	}
	
	public static String getAllText()
	{
		String text = "";
		Iterator<Part> it = parts.iterator();
		while(it.hasNext())
		{
			Part currPart  = it.next();
			text = text + currPart.period + "\n";
		}
		return text;
	}
	
	public String getTextPart(int index)
	{
		Part part = parts.get(index);
		return part.period;
	}
	
	public MyRect getRectPart(int index)
	{
		Part part = parts.get(index);
		return part.touchRect[0];
	}

	public int taleLength()
	{
		return parts.size();
	}

	public String getBackground(int index) 
	{
		Part part = parts.get(index);
		return part.background;
	}
	
}
