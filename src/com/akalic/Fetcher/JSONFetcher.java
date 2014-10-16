package com.akalic.Fetcher;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by jgonzal2 on 10/15/2014.
 */
public class JSONFetcher
{
    public static JSONArray getJSON()
    {
        BufferedReader reader = null;
        URL url = null;
        String output = "";

        try
        {
            url = new URL("http://www.netrunnerdb.com/api/cards/");
            reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            output = reader.readLine();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(reader != null)
                    reader.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        return new JSONArray(output);
    }
}
