package pl.ing.green.onlinegame;

import java.util.List;
import pl.ing.green.onlinegame.model.Clan;
import pl.ing.green.onlinegame.model.Players;

/**
 * Abstract class representing different algorithms organizing players before
 * game.
 *
 * @author Jakub Strychowski
 */
public abstract class PlayersOrganizer {

    /**
     * Input data - information about clans.
     */
    protected Players players;

    /**
     * Clans which should be organized to the game.
     */
    protected List<Clan> clans;

    /**
     * Maximum number of players which can enter the game in a group.
     */
    protected int maxNumberOfPlayersInGroup;

    /**
     * COnstructs the objects using input data of a service.
     *
     * @param players Information about clans.
     */
    protected PlayersOrganizer(Players players) {
        this.players = players;
        clans = players.getClans();
        maxNumberOfPlayersInGroup = players.getGroupCount();
    }

    /**
     * Performs organization process for clann in a game.
     *
     * @return Order of groups and clans for a game.
     */
    public abstract List<List<Clan>> organizePlayers();

}
