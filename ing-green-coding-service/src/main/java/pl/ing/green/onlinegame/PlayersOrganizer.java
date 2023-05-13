/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.ing.green.onlinegame;

import java.util.List;
import pl.ing.green.onlinegame.model.Clan;
import pl.ing.green.onlinegame.model.Players;

/**
 *
 * @author Jakub Strychowski
 */
public abstract class PlayersOrganizer {
    
    protected Players players;
    protected List<Clan> clans;
    protected int maxNumberOfPlayersInGroup;
    
    protected PlayersOrganizer(Players players) {
        this.players = players;
        clans = players.getClans();
        maxNumberOfPlayersInGroup = players.getGroupCount();
    }
    
    public abstract List<List<Clan>> organizePlayers();
    
    
}
