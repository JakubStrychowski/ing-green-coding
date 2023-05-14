package pl.ing.green.onlinegame;

import java.util.ArrayList;
import java.util.List;
import pl.ing.green.onlinegame.model.Clan;
import pl.ing.green.onlinegame.model.Players;

/**
 * Generates groups from best to worst searching for matching clans.
 * Uses fast search of clans using buckets and ordered lists.
 * This algorithm is fastest for most cases.
 *
 * @author Jakub Strychowski
 */
public class PlayersOrganizerSearchMatchingClan extends PlayersOrganizer {
    
    /**
     * List of buckets of clans.
     * Each bucket contains clans having the same size.
     * Clans in a bucket are ordered from week to strong (stronger clans goes first to group).
     * Buckets are ordered according to number of players in clans
     */
    private ArrayList<ArrayList<Clan>> buckets;
    
    /**
     * Buckets can be emptied and we can quickly skip to next not empty bucket
     * using this index. This approach required additional benchmarking to check
     * if this speeds up processing.
     * In this version parts related to index are commented.
     */
    private int[] nextNotEmptyBucket;
    
    /**
     * Number of clans to put to group.
     */
    private int numberOfClansLeft;
    
    /**
     * Number of processed group.
     */
    private int groupIndex;

    /**
     * Creates a new organizer for the given task.
     *
     * @param players Information about clans.
     */
    public PlayersOrganizerSearchMatchingClan(Players players) {
        super(players);
    }

    /**
     * Organizes players.
     * This algorithm puts clans to buckets. Clans with the same number of players
     * go to the same buckets. Buckets are sorted in order of clans' points.
     * Then we create groups and fill these groups with clans.
     * Having buckets we can very quickly find clan with desired size 
     * (according to free space in a group and best clan which fits to a group).
     * We don't need to check all clans in a bucket - we only check best one from ordered list.
     * This algorithm uses some smart skips - skips empty buckets, start searching from bucket
     * of size less then free space in a group.
     *
     * @return Proper order of groups and clans.
     */
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
//                      This ha
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
    
    
    
    
    
}
