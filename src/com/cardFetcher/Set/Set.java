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


package com.cardFetcher.Set;

import java.util.ArrayList;

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
