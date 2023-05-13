/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.ing.green.onlinegame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import pl.ing.green.onlinegame.model.Clan;
import pl.ing.green.onlinegame.model.Players;

/**
 *
 * @author Jakub Strychowski
 */
public class PlayersOrganizerBestClansFirst extends PlayersOrganizer {
    
    
    
    public PlayersOrganizerBestClansFirst(Players players) {
        super(players);
    }

            
    @Override
    public List<List<Clan>> organizePlayers() {
        

        List<Group> allGroups = new ArrayList(players.getClans().size());
        List<Group> freeGroups = new LinkedList();
        
        clans.sort((Clan c1, Clan c2) -> c2.compareTo(c1));

        int index = 0;
        int maxFreeSpace = maxNumberOfPlayersInGroup;
        
        for (Clan clan : clans) {
            int playersCount = clan.getNumberOfPlayers();
            
            if (playersCount > maxNumberOfPlayersInGroup) {
                throw new IllegalArgumentException(
                    "Number of players cannot be greater then maximum size of a group!");
            }

            boolean found = false;
            if (maxFreeSpace >= clan.getNumberOfPlayers()) {
                int freeSpace = 0;
                Iterator<Group> it = freeGroups.iterator();
                while (!found && it.hasNext()) {
                    Group group = it.next();
                    if (group.getFreeSpace() >= playersCount) {
                        found = true;
                        group.addClan(clan);
                        if (group.isFull()) {
                            it.remove();
                        }
                    }
                    freeSpace = Math.max(freeSpace, group.getFreeSpace());
                }
                if (!found) {
                    maxFreeSpace = freeSpace;
                }
            }
            if (!found) {
                Group newGroup = new Group(players.getGroupCount(), index++);
                newGroup.addClan(clan);
                allGroups.add(newGroup);
                if (!newGroup.isFull()) {
                    freeGroups.add(newGroup);
                    maxFreeSpace = Math.max(maxFreeSpace, newGroup.getFreeSpace());
                } 
            }
            
            //System.out.println("max free space: " +  maxFreeSpace);
            //System.out.println("free grups: " +  freeGroups.size());
        }
        
        List<List<Clan>> result = new ArrayList<>(allGroups.size());
        allGroups.forEach(group -> result.add(group.getClans()));
        
        return result;
        
    }
    
    
}
