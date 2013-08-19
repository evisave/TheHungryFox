package com.hungryfox.framework.impl;

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
	public String ID;
	public String period;
}

public class XMLParser implements Parser
{
	XmlPullParserFactory pullParserFactory;
	static ArrayList<Part> parts;
	
	public XMLParser(AssetManager asset)
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
                        if (name.equalsIgnoreCase("id"))
                        {
                        	currentPart.ID = parser.nextText();
                        } 
                        else if (name.equalsIgnoreCase("period"))
                        {
                        	currentPart.period = parser.nextText();
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
            eventType = parser.next();
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

	public int taleLength()
	{
		return parts.size();
	}
	
}
