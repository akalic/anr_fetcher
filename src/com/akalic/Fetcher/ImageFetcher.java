package com.akalic.Fetcher;

import com.akalic.Set.Set;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by jgonzal2 on 9/9/2014.
 */
public class ImageFetcher
{
    private final static String strURL = "http://www.netrunnerdb.com/web/bundles/netrunnerdbcards/images/cards/en/";

    public static void retrieveImage(Set set, String location, JSONArray json)
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
            System.out.println("[" + set.getSetName() + "]");
            for(int i = 0; i < cardNames.size(); i++)
            {
                System.out.println("\t" + formatStr(cardNames.get(i)) + " (" + (i + 1) + "/" + cardNames.size() + ")");
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

    private static String formatStr(String cardName)
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

    private static void purgeFolder(File pFile)
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

    private static String getCardCode(JSONArray json, String cardName)
    {
        JSONObject curr = null;
        for(int x = 0; x < json.length(); x++)
        {
            curr = (JSONObject) json.get(x);
            //System.out.println(curr.getString("title"));
            if(formatStr(curr.getString("title")).toLowerCase().equals(cardName.toLowerCase()))
            {
                if(cardName.equals("Corporate Troubleshooter"))
                    return "01065";
                return curr.getString("code");
            }

        }
        return null;
    }
}
