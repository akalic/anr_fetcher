package com.akalic.Parser;

import com.akalic.Set.Set;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jgonzal2 on 9/9/2014.
 */
public class XmlParser
{
    public static Set parseXML(String filePath)
    {
        try
        {
            ArrayList<String> cardNames = new ArrayList<String>();
            ArrayList<String> octgnNames = new ArrayList<String>();
            String setID ="", setName = "";
            //File xmlFile = new File(filePath);
            String line;
            char flag = '0';
            BufferedReader reader = new BufferedReader((new FileReader(filePath)));
            while((line = reader.readLine()) != null)
            {
                if(line.contains("name=\"Markers\""))
                    return new Set(null, null, "Markers", "");
                if(line.contains("name=\"Promos\""))
                    return new Set(null, null, "Promos", "");
                if(line.contains("<alternate"))
                {
                    flag = '1';
                    continue;
                }
                if(flag == '1')
                {
                    flag = '0';
                    continue;
                }
                if(line.contains("name=") && line.contains("<card "))
                {
                    String re1=".*?";	// Non-greedy match on filler
                    String re2="(\".*?\")";	// Double Quote String 1
                    String re3=".*?";	// Non-greedy match on filler
                    String re4="(\".*?\")";	// Double Quote String 2

                    Pattern p = Pattern.compile(re1+re2+re3+re4,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                    Matcher m = p.matcher(line);
                    if (m.find())
                        cardNames.add(m.group(2).replace("\"", "").trim());
                        octgnNames.add(m.group(1).replace("\"", "").trim());
                }
                else if(line.contains("name=") && !(line.contains("property")))
                {
                    String re1=".*?";	// Non-greedy match on filler
                    String re2="(\".*?\")";	// Double Quote String 1

                    Pattern p = Pattern.compile(re1+re2,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                    Matcher m = p.matcher(line);
                    if (m.find())
                        setName = sterilizeSetName(m.group(1).replace("\"", "").trim());
                }
                else if(line.contains("id="))
                {
                    String re1=".*?";	// Non-greedy match on filler
                    String re2="(\".*?\")";	// Double Quote String 1

                    Pattern p = Pattern.compile(re1+re2,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                    Matcher m = p.matcher(line);
                    if(m.find())
                        setID = m.group(1).replace("\"", "").trim();
                }
                else if(line.contains("Subtitle"))
                {
                    String re1=".*?";	// Non-greedy match on filler
                    String re2="\".*?\"";	// Uninteresting: string
                    String re3=".*?";	// Non-greedy match on filler
                    String re4="(\".*?\")";	// Double Quote String 1

                    Pattern p = Pattern.compile(re1+re2+re3+re4,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                    Matcher m = p.matcher(line);
                    if (m.find())
                        cardNames.set(cardNames.size() - 1, cardNames.get(cardNames.size() - 1).concat(" " + m.group(1).replace("\"", "")).trim());
                }
            }
            return new Set(cardNames, octgnNames, setName, setID);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private static String sterilizeSetName(String str)
    {
        if(str.contains("&amp;"))
            return str.replace("&amp;", "and");
        else
            return str;
    }
}
