package com.akalic.Core;

import com.akalic.Fetcher.ImageFetcher;
import com.akalic.Fetcher.JSONFetcher;
import com.akalic.Parser.XmlParser;
import com.akalic.Set.Set;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by jgonzal2 on 9/9/2014.
 */
public class NetrunnerCardFetcher
{
    public static void main(String[] args)
    {
//        String setsPath = "C:\\Users\\jgonzal2\\Documents\\OCTGN\\GameDatabase\\0f38e453-26df-4c04-9d67-6d43de939c77\\Sets";
//        String imagesPath = "C:\\Users\\jgonzal2\\Documents\\OCTGN\\ImageDatabase\\0f38e453-26df-4c04-9d67-6d43de939c77\\Sets";
        String setsPath = "GameDatabase\\0f38e453-26df-4c04-9d67-6d43de939c77\\Sets";
        String imagesPath = "ImageDatabase\\0f38e453-26df-4c04-9d67-6d43de939c77\\Sets";
        try
        {
            JSONArray json = JSONFetcher.getJSON();
            File f = new File(setsPath);
            File[] files = f.listFiles();
            for(File file: files)
            {
                if(file.isDirectory())
                {
                    Set s = XmlParser.parseXML(file.getCanonicalPath() + "\\set.xml");

                    if((!s.getSetName().equals("Promos")) && (!s.getSetName().equals("Markers")))
                        ImageFetcher.retrieveImage(s, imagesPath, json);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
