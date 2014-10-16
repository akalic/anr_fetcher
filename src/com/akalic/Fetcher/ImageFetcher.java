package com.akalic.Fetcher;

import com.akalic.Set.Set;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

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
                //System.out.println(strURL + getCardCode(json, formatStr(cardNames.get(i))) + ".png");
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

    private static String fixes(String cardName)
    {
        if(cardName.equals("alix-t4lb07"))
            return "alix-t4lbo7";
        else if(cardName.equals("andromeda-dispossessed-ristie"))
            return "andromeda";
        else if(cardName.equals("chaos-theory-wunderkind"))
            return "chaos-theory";
        else if(cardName.equals("gabriel-santiago-consummate-professional"))
            return "gabriel-santiago";
        else if(cardName.equals("haas-bioroid-engineering-the-future"))
            return "haas-bioroid";
        else if(cardName.equals("jinteki-personal-evolution"))
            return "jinteki";
        else if(cardName.equals("kate-mac-mccaffrey-digital-tinker"))
            return "kate-mac-mccaffrey";
        else if(cardName.equals("llds-energy-regulator"))
            return "energy-regulator";
        else if(cardName.equals("mental-health-clinic"))
            return "psychiatric-clinic";
        else if(cardName.equals("nbn-making-news"))
            return "nbn";
        else if(cardName.equals("noise-hacker-extraordinaire"))
            return "noise";
        else if(cardName.equals("planned-assault"))
            return "planned-attack";
        else if(cardName.equals("push-your-luck"))
            return "double-or-nothing";
        else if(cardName.equals("security-testing"))
            return "security-check";
        else if(cardName.equals("weyland-consortium-building-a-better-world"))
            return "weyland-consortium";
        else if(cardName.equals("whizzard-master-gamer"))
            return "whizzard";
        else
            return cardName;
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
