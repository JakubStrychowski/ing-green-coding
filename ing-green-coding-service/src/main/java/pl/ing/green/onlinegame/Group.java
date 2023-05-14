package pl.ing.green.onlinegame;

import java.util.ArrayList;
import java.util.List;
import pl.ing.green.onlinegame.model.Clan;

/**
 * Represents a group of clans which enter to a game together.
 *
 * @author Jakub Strychowski
 */
public class Group {
    
    /**
     * Clans in a group - better clans should be first.
     */
    private List<Clan> clans;
    
    /**
     * Number of players in a group.
     */
    private int numberOfPlayers;
    
    /**
     * Total number of points in a group.
     */
    private int numberOfPoints;
    
    /**
     * Maximum number of players in a group.
     */
    private int maxNumberOfPlayers;
    
    /**
     * Group number.
     */
    private int index;
    
    /**
     * Initialize a group of clans.
     *
     * @param maxNumberOfPlayers maximum number of players in a group.
     * @param index group number.
     */
    public Group(int maxNumberOfPlayers, int index) {
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.clans = new ArrayList<Clan>(maxNumberOfPlayers);
        this.numberOfPlayers = 0;
        this.numberOfPoints = 0;
    }
    
    /**
     * Adds clan to a group.
     * 
     * @param clan a new clan in this group.
     */
    public void addClan(Clan clan) {
        clans.add(clan);
        numberOfPlayers += clan.getNumberOfPlayers();
        numberOfPoints += clan.getPoints();
    }
    
    /**
     * Checks if this group is full - there is no space for next clans.
     * 
     * @return <code>true</code> if there is no more space in this group.
     */
    public boolean isFull() {
        return numberOfPlayers >= maxNumberOfPlayers;
    }
    
    /**
     * Return free space in a group.
     * 
     * @return Number of players which can be added to group.
     */
    public int getFreeSpace() {
        return maxNumberOfPlayers - numberOfPlayers;
    }

    /**
     * Return clans added to this group.
     *
     * @return list of clans in this group.
     */
    public List<Clan> getClans() {
        return clans;
    }

    /**
     * Return index of a group.
     *
     * @return return index of this group in ordered groups.
     */
    public int getIndex() {
        return index;
    }
    
}
