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


package com.cardFetcher.Parser;

import com.cardFetcher.Set.Set;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlParser
{
    public static Set parseXML(String filePath)
    {
        try
        {
            ArrayList<String> cardNames = new ArrayList<String>();
            ArrayList<String> octgnNames = new ArrayList<String>();
            String setID ="", setName = "";
            String line;
            char flag = '0';
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF8"));
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
                    //System.out.println(line);
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
                    //System.out.println(line);
                    String re1=".*?";	// Non-greedy match on filler
                    String re2="(\".*?\")";	// Double Quote String 1

                    Pattern p = Pattern.compile(re1+re2,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                    Matcher m = p.matcher(line);
                    if (m.find())
                        setName = sterilizeSetName(m.group(1).replace("\"", "").trim());
                }
                else if(line.contains("id="))
                {
                    //System.out.println(line);
                    String re1=".*?";	// Non-greedy match on filler
                    String re2="(\".*?\")";	// Double Quote String 1

                    Pattern p = Pattern.compile(re1+re2,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                    Matcher m = p.matcher(line);
                    if(m.find())
                        setID = m.group(1).replace("\"", "").trim();
                }
                else if(line.contains("Subtitle"))
                {
                    //System.out.println(line);
                    String re1=".*?";	// Non-greedy match on filler
                    String re2="\".*?\"";	// Uninteresting: string
                    String re3=".*?";	// Non-greedy match on filler
                    String re4="(\".*?\")";	// Double Quote String 1

                    Pattern p = Pattern.compile(re1+re2+re3+re4,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                    Matcher m = p.matcher(line);
                    if (m.find())
                    {
                        String subtitle = m.group(1).replace("\"", "");
                        if(subtitle.endsWith(" (J)"))
                        {
                            subtitle = subtitle.substring(0, subtitle.length() - 4);
                        }
                        cardNames.set(cardNames.size() - 1, cardNames.get(cardNames.size() - 1).concat(" " + subtitle).trim());
                    }
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
