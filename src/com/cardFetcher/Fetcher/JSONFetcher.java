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


package com.cardFetcher.Fetcher;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

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
