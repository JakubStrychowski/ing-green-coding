package pl.ing.green.onlinegame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import pl.ing.green.onlinegame.model.Clan;
import pl.ing.green.onlinegame.model.Players;

/**
 * Puts clans to groups from best to worst. This algorithm is best if we have
 * big groups and small clans.
 *
 * @author Jakub Strychowski
 */
public class PlayersOrganizerBestClansFirst extends PlayersOrganizer {

    /**
     * Creates a new organizer for the given task.
     *
     * @param players Information about clans.
     */
    public PlayersOrganizerBestClansFirst(Players players) {
        super(players);
    }

    /**
     * Organize players. This method sorts clans from best to worst and puts
     * clans to groups in this order. It also stores groups having free space in
     * separate collection - it don't scan all groups. If clan can be put to any
     * of this group it is added decreasing space in a group. If group is filled
     * it is removed from collection of groups with free space. If there is no
     * enough space in any group, a new group is added to the pool. To speed up
     * processing, this algorithm calculates on the fly maximum size of free
     * space in groups, and may decide not to scan groups at all, and a new
     * group if needed.
     *
     * @return Proper order of groups and clans.
     */
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
                int nextMaxFreeSpace = 0;
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
                    nextMaxFreeSpace = Math.max(nextMaxFreeSpace, group.getFreeSpace());
                }
                if (!found) {
                    maxFreeSpace = nextMaxFreeSpace;
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

        }

        List<List<Clan>> result = new ArrayList<>(allGroups.size());
        allGroups.forEach(group -> result.add(group.getClans()));

        return result;

    }

}
