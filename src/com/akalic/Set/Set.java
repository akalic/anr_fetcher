package com.akalic.Set;

import java.util.ArrayList;

/**
 * Created by jgonzal2 on 9/9/2014.
 */
public class Set
{
    private ArrayList<String> cards;
    private ArrayList<String> octgnCards;
    private String setName;
    private String octgnSetName;

    public Set(ArrayList<String> cards, ArrayList<String> octgnCards, String setName, String octgnSetName)
    {
        this.cards = cards;
        this.octgnCards = octgnCards;
        this.setName = setName;
        this.octgnSetName = octgnSetName;
    }

    @Override
    public String toString()
    {
        String ret = "[ {Set name: " + setName + "},\n{Set id: " + octgnSetName + "},\n{Cards: [";
        for(int i = 0; i < cards.size(); i++)
            ret += ("{" + cards.get(i) + ", " + octgnCards.get(i) + "}\n");
        return ret +"} ]";
    }

    public ArrayList<String> getCards()
    {
        return cards;
    }

    public void setCards(ArrayList<String> cards)
    {
        this.cards = cards;
    }

    public ArrayList<String> getOctgnCards()
    {
        return octgnCards;
    }

    public void setOctgnCards(ArrayList<String> octgnCards)
    {
        this.octgnCards = octgnCards;
    }

    public String getSetName()
    {
        return setName;
    }

    public void setSetName(String setName)
    {
        this.setName = setName;
    }

    public String getOctgnSetName()
    {
        return octgnSetName;
    }

    public void setOctgnSetName(String octgnSetName)
    {
        this.octgnSetName = octgnSetName;
    }
}
