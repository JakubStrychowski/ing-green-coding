/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.ing.green.onlinegame;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import pl.ing.green.onlinegame.model.Clan;
import pl.ing.green.onlinegame.model.Players;

/**
 * Maximizes points in groups.
 * This is experimental algorithm realizing different task.
 * 
 * In this algorithm strong clans may go later if they can be replaced
 * be few weaker and smaller clans, but stronger when bind together.
 * 
 * Algorithm uses dynamic programming approach with backward caching.
 * 
 * @author Jakub Strychowski
 */
public class PlayersOrganizerMaximizePointsInGroup extends PlayersOrganizer {

    
    private Clan[] clansToAssign;
    private int numberOfClansToAssign;
    private int[][] cache;
    private List<List<Clan>> finalResult; 
    private int minClanId;
    
    public PlayersOrganizerMaximizePointsInGroup(Players players) {
        super(players);
    }

    private void initialize() {
        
        clansToAssign = clans.toArray(Clan[]::new);
        numberOfClansToAssign = clans.size();

        cache = new int[numberOfClansToAssign + 1][maxNumberOfPlayersInGroup + 1];
        for (int groupSize = 0; groupSize <= maxNumberOfPlayersInGroup; groupSize++) {
            cache[0][groupSize] = 0;
        }
        
        for (int clanId = clansToAssign.length; clanId >= 0; clanId--) {
            cache[clanId][0] = 0;
        }
        
        finalResult = new ArrayList<>();
    }
    
    private void fillCache(int startFrom) {
        for (int clanId = startFrom; clanId <= numberOfClansToAssign; clanId++) {
            Clan clan = clansToAssign[clanId - 1];
            int numberOfPlayers = clan.getNumberOfPlayers();
            int points = clan.getPoints();
            
            for (int groupSize = 1; groupSize <= maxNumberOfPlayersInGroup; groupSize++) {
                if (numberOfPlayers > groupSize) {
                    cache[clanId][groupSize] = cache[clanId - 1][groupSize];
                } else {
                    cache[clanId][groupSize] = Math.max(
                            cache[clanId - 1][groupSize],
                            cache[clanId - 1][groupSize - numberOfPlayers] + points
                            );
                }
                
            }
        }
    }
    
    public List<Clan> getClanGroup() {
        ArrayList<Clan> group = new ArrayList<>();
        
        int clanId = numberOfClansToAssign;
        int groupSize = maxNumberOfPlayersInGroup;

        Deque<Integer> clanIds = new ArrayDeque<>();
        minClanId = clanId;
        while (clanId > 0) {
            if (cache[clanId-1][groupSize] != cache[clanId][groupSize]) {
                Clan clan = clansToAssign[clanId - 1];
                group.add(clan);
                clanIds.push(clanId - 1);
                groupSize -= clan.getNumberOfPlayers();
                minClanId = clanId;
            }
            clanId--;
        }
        
        removeUsedClans(clanIds);
        
        
        
        group.sort((c1, c2) -> Integer.compare(c2.getPoints(), c1.getPoints()));
        return group;
        
    }

    private void removeUsedClans(Deque<Integer> clanIds) {
        int numberOfClansToRemove = clanIds.size();
        int index = 0;
        int clandId = clanIds.pop();
        for (int i = 0; i < numberOfClansToAssign; i++) {
            if (i == clandId) {
                clandId = clanIds.isEmpty() ? -1 : clanIds.pop();
            } else {
                clansToAssign[index++] = clansToAssign[i];
            }
        }
        numberOfClansToAssign -= numberOfClansToRemove;
    }

    @Override
    public List<List<Clan>> organizePlayers() {
        initialize();
        minClanId = 1;

        while (numberOfClansToAssign > 0) {
            fillCache(minClanId);
            List<Clan> group = getClanGroup(); 
            finalResult.add(group);
        }
        
        return finalResult;
    }


    
    
}
