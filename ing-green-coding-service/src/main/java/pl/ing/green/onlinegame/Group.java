/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.ing.green.onlinegame;

import java.util.ArrayList;
import java.util.List;
import pl.ing.green.onlinegame.model.Clan;

/**
 *
 * @author Jakub Strychowski
 */
public class Group {
    
    private List<Clan> clans;
    private int numberOfPlayers;
    private int numberOfPoints;
    private int maxNumberOfPlayers;
    private int index;
    
    public Group(int maxNumberOfPlayers, int index) {
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.clans = new ArrayList<Clan>(maxNumberOfPlayers);
        this.numberOfPlayers = 0;
        this.numberOfPoints = 0;
    }
    
    public void addClan(Clan clan) {
        clans.add(clan);
        numberOfPlayers += clan.getNumberOfPlayers();
        numberOfPoints += clan.getPoints();
    }
    
    public boolean isFull() {
        return numberOfPlayers >= maxNumberOfPlayers;
    }
    
    public int getFreeSpace() {
        return maxNumberOfPlayers - numberOfPlayers;
    }

    public List<Clan> getClans() {
        return clans;
    }

    public int getIndex() {
        return index;
    }
    
}
