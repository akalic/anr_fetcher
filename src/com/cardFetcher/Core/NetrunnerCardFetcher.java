/*
* Copyright Jose A Gonzalez 2014
*
* This file is part of NetCard.
*
* NetCard is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*
* Run safely my friends.
 */


package com.cardFetcher.Core;

import com.cardFetcher.Fetcher.ImageFetcher;
import com.cardFetcher.Fetcher.JSONFetcher;
import com.cardFetcher.Parser.XmlParser;
import com.cardFetcher.Set.Set;
import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;

public class NetrunnerCardFetcher
{
    public static void main(String[] args)
    {
//        String setsPath = "GameDatabase\\0f38e453-26df-4c04-9d67-6d43de939c77\\Sets";
//        String imagesPath = "ImageDatabase\\0f38e453-26df-4c04-9d67-6d43de939c77\\Sets";
        String setsPath = "C:\\Users\\jose.gonzalez\\Documents\\OCTGN\\GameDatabase\\0f38e453-26df-4c04-9d67-6d43de939c77\\Sets";
        String imagesPath = "C:\\Users\\jose.gonzalez\\Documents\\OCTGN\\ImageDatabase\\0f38e453-26df-4c04-9d67-6d43de939c77\\Sets";
//        String setsPath = "C:\\Users\\jgonzal2\\Documents\\OCTGN\\GameDatabase\\0f38e453-26df-4c04-9d67-6d43de939c77\\Sets";
//        String imagesPath = "C:\\Users\\jgonzal2\\Documents\\OCTGN\\ImageDatabase\\0f38e453-26df-4c04-9d67-6d43de939c77\\Sets";

        try
        {
            JSONArray json = JSONFetcher.getJSON();
            //System.out.println(json);
            File f = new File(setsPath);
            File[] files = f.listFiles();
            int count = 0;
            for(File file: files)
            {
                if(file.isDirectory())
                {
                    Set s = XmlParser.parseXML(file.getCanonicalPath() + "\\set.xml");

                    if((!s.getSetName().equals("Promos")) && (!s.getSetName().equals("Markers")))
                    {
                        //System.out.println(s);
                        ImageFetcher fetch = new ImageFetcher(s, imagesPath, json);
                        System.out.print("thread " + count++ + ": ");
                        fetch.start();
                    }
                }
            }
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
