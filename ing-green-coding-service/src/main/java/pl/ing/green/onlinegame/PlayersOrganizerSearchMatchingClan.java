/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.ing.green.onlinegame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import pl.ing.green.onlinegame.model.Clan;
import pl.ing.green.onlinegame.model.Players;

/**
 *
 * @author Jakub Strychowski
 */
public class PlayersOrganizerSearchMatchingClan extends PlayersOrganizer {
    
    
    private ArrayList<ArrayList<Clan>> buckets;
    private int[] nextNotEmptyBucket;
    private int numberOfClansLeft;
    private int groupIndex;

    public PlayersOrganizerSearchMatchingClan(Players players) {
        super(players);
    }

    private void fillBuckets() {
        int[] statistics = new int[maxNumberOfPlayersInGroup + 1];
        for (Clan clan : clans) {
            int playersCount = clan.getNumberOfPlayers();
            
            if (playersCount > maxNumberOfPlayersInGroup) {
                throw new IllegalArgumentException(
                    "Number of players cannot be greater then maximum size of a group!");
            }
            statistics[clan.getNumberOfPlayers()]++;
        }
        
        buckets = new ArrayList<>(maxNumberOfPlayersInGroup + 1);
        for (int i = 0; i <= maxNumberOfPlayersInGroup; i++) {
            buckets.add(statistics[i] > 0 ? new ArrayList(statistics[i]) : null);
        }

        for (Clan clan : clans) {
            buckets.get(clan.getNumberOfPlayers()).add(clan);
        }

        nextNotEmptyBucket = new int[buckets.size()];
        int lastNotEmpty = -1;
        for (int index = 0; index < buckets.size(); index++) {
            ArrayList<Clan> bucket = buckets.get(index);
            nextNotEmptyBucket[index] = lastNotEmpty;
            if (bucket != null) {
                bucket.sort((c1, c2) -> c1.compareTo(c2));
                lastNotEmpty = index;
            }
        }
        
        
    }

    private Group fillGroup() {
        Group group = new Group(maxNumberOfPlayersInGroup, groupIndex++);
        
        boolean end = false;
        int freePlaces = maxNumberOfPlayersInGroup;
        Clan bestClan;
        
        // repeat until there is a free place in a group and any clan can be added
        do {

            // search for a clan with maximum number of points.
            int maxPoints = Integer.MIN_VALUE;
            int bestBucketPos = 0;
            bestClan = null;
            // start searching from a bucket containing clans with can be put 
            // to free place in a group
            for (int pos = freePlaces; pos >= 0; pos--) {
            //for (int pos = freePlaces; pos >= 0; pos = nextNotEmptyBucket[pos]) {
                // check last element in a bucket of sorted clans
                ArrayList<Clan> bucket = buckets.get(pos);
                if (bucket != null) {
                    Clan clan = bucket.get(bucket.size() - 1);
                    int points = clan.getPoints();
                    if (points >= maxPoints) {
                        maxPoints = points;
                        bestClan = clan;
                        bestBucketPos = pos;
                    }
                }
            }
            if (bestClan != null) {
                // Clan has been found. Add it to group 
                group.addClan(bestClan);
                
                // decrease global number of clans do arrange
                numberOfClansLeft--;
                
                // descrese free places in a group
                freePlaces -= bestClan.getNumberOfPlayers();
                
                // Remove clan from its bucket
                ArrayList<Clan> bestBucket = buckets.get(bestBucketPos);
                if (bestBucket.size() == 1) {
                    buckets.set(bestBucketPos, null);
//                    int lastNotEmpty = nextNotEmptyBucket[bestBucketPos];
//                    for (int j = bestBucketPos + 1; j < maxNumberOfPlayersInGroup && buckets.get(j) != null; j++) {
//                        if (nextNotEmptyBucket[j] == bestBucketPos) {
//                            nextNotEmptyBucket[j] = lastNotEmpty;
//                        }
//                    }
                } else {
                    // fast remove without coping array
                    bestBucket.remove(bestBucket.size() - 1);
                }
            }
        } while (bestClan != null && freePlaces > 0);
                
        return group;
    }
    
    
    @Override
    public List<List<Clan>> organizePlayers() {
        
        fillBuckets();
        List<List<Clan>> result = new ArrayList(players.getClans().size());
        
        groupIndex = 0;
        numberOfClansLeft = clans.size(); 
        while (numberOfClansLeft > 0) {
            Group group = fillGroup();
            result.add(group.getClans());
        }
        return result;
    }
    
    
    
}
