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

import com.cardFetcher.Set.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;

public class ImageFetcher implements Runnable
{
    private final static String strURL = "http://www.netrunnerdb.com/bundles/netrunnerdbcards/images/cards/en/";
    private Thread t;
    private Set set;
    private String location;
    private JSONArray json;

    public ImageFetcher(Set set, String location, JSONArray json)
    {
        this.set = set;
        this.location = location;
        this.json = json;
    }

    public void start ()
    {
        System.out.println("Starting " +  set.getSetName() );
        if (t == null)
        {
            t = new Thread (this, set.getSetName());
            t.start ();
        }
    }

    public void run()
    {
        retrieveImage();
        System.out.println("Ending " + set.getSetName());
    }

    public void retrieveImage()
    {
        try
        {
            ArrayList<String> cardNames = set.getCards();
            ArrayList<String> cardIDs = set.getOctgnCards();
            String setName = set.getSetName();
            String setID = set.getOctgnSetName();
            InputStream is;
            OutputStream os;
            byte[] b;
            int length;

            purgeFolder(new File(location + "\\" + setID + "\\Cards\\"));
            if(new File(location + "\\" + setID + "\\Cards\\").mkdir())
                throw new Exception();
            //System.out.println("[" + set.getSetName() + "]");
            for(int i = 0; i < cardNames.size(); i++)
            {
                System.out.println("\t" + formatStr(cardNames.get(i)));
                is = (new URL(strURL + getCardCode(json, formatStr(cardNames.get(i))) + ".png").openStream());
                os = new FileOutputStream(location + "\\" + setID + "\\Cards\\" + cardIDs.get(i) + ".png");
                b = new byte[2048];
                while ((length = is.read(b)) != -1)
                {
                    os.write(b, 0, length);
                }
                is.close();
                os.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private String formatStr(String cardName)
    {
        cardName = cardName.replace(":", "");
        cardName = cardName.replace("é", "e");
        cardName = cardName.replace("à", "a");
        cardName = cardName.replace("*", "");
        cardName = cardName.replace("ä", "a");
        cardName = cardName.replace("ō", "o");
        cardName = cardName.replace("ü", "u");
        cardName = cardName.replace("&amp;", "&");
        cardName = cardName.replace("&#33;", "!");
        cardName = cardName.replace("&#039;", "\'");
        cardName = cardName.replace("&#39;", "\'");
        cardName = cardName.replace("&quot;", "\"");
        if(cardName.equals("Melange Mining Corp"))
            cardName += ".";
        if(cardName.equals("NeoTokyo City Grid"))
            cardName = "NeoTokyo Grid";
        if(cardName.equals("Unregistered S&W 35"))
            cardName = "Unregistered S&W \'35";
        return(cardName);
    }

    private void purgeFolder(File pFile)
    {
        if(pFile.exists())
        {
            if(pFile.isDirectory())
            {
                if(pFile.list().length != 0)
                {
                    String[] strFiles = pFile.list();

                    for(String strFilename: strFiles)
                    {
                        File fileToDelete = new File(pFile, strFilename);
                        purgeFolder(fileToDelete);
                    }
                }
            }
            else
            {
                pFile.delete();
            }
        }
    }

    private String getCardCode(JSONArray json, String cardName)
    {
        JSONObject curr = null;

        for(int x = 0; x < json.length(); x++)
        {
            curr = (JSONObject) json.get(x);
            String jsonName = curr.getString("title");

            //Normalizer is for special characters like in CT or Shi.Kyu (maybe more, but never ran into issues), still use formatStr() since there's always issues with names (Da gun, NeoTokyo, etc)
            if(formatStr(Normalizer.normalize(jsonName, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "")).toLowerCase().equals(Normalizer.normalize(cardName, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase()))
            {
                if(cardName.equals("Corporate Troubleshooter"))
                    return "01065";
                return curr.getString("code");
            }

        }
        return null;
    }
}
