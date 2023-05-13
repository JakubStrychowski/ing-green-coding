/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pl.ing.green.onlinegame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pl.ing.green.onlinegame.model.Clan;
import pl.ing.green.onlinegame.model.Players;

/**
 *
 * @author Jakub Strychowski
 */
public class OnlinegameServiceTest {
    
    public OnlinegameServiceTest() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    public List<Clan> createClanList(int[][] clanSizeAndPoints) {
        ArrayList<Clan> clans = new ArrayList<>();
        for (int[] clanParameters : clanSizeAndPoints) {
            Clan clan = new Clan(clanParameters[0], clanParameters[1]);
            clans.add(clan);
        }
        return clans;
    }
    
    private List<List<Clan>> createGroupsList(int[][][] groupsArray) {
        List<List<Clan>> groupsList = new ArrayList<>();
        for (int[][] group : groupsArray) {
            List<Clan> clans = new ArrayList<>(group.length);
            groupsList.add(clans);
            for (int[] clanData : group) {
                Clan clan = new Clan(clanData[0], clanData[1]);
                clans.add(clan);
            }
        }
        return groupsList;
    }
    
    
    private void checkResult(List<List<Clan>> expected, List<List<Clan>> result) {
        assertEquals(expected.size(), result.size());
        for (int groupNr = 0; groupNr < expected.size(); groupNr++) {
            List<Clan> expectedGroup = expected.get(groupNr);
            List<Clan> resultGroup = result.get(groupNr);
            assertEquals(expectedGroup.size(), resultGroup.size());
            for (int clanNr = 0; clanNr < expectedGroup.size(); clanNr++) {
                assertEquals(expectedGroup.get(clanNr), resultGroup.get(clanNr));
            }
        }
        
        validateResult(result);
    }

    
    /*
    Checks if any clan should be in higher group
    */
    public void validateResult(List<List<Clan>> result) {
        for (int groupIndex = 0; groupIndex < result.size(); groupIndex++) {
            List<Clan> group = result.get(groupIndex);
            for (int cpos = 0; cpos < group.size(); cpos++) {
                Clan clan = group.get(cpos);

                // validate clans within group
                for (int betterClanPos = 0; betterClanPos < cpos; betterClanPos++) {
                    Clan betterClan = group.get(betterClanPos);
                    if (
                            (clan.getPoints() > betterClan.getPoints())
                            ||
                                ((clan.getPoints() == betterClan.getPoints()) 
                                && 
                                (clan.getNumberOfPlayers() < betterClan.getNumberOfPlayers()))) {
                        fail("Better clan " + clan.toString() + " should be before " + betterClan.toString());
                    }
                }
                
                validateClan(result, clan, groupIndex - 1);
            }
        }
    }
    
    protected void validateClan(List<List<Clan>> result, Clan clan, int maxGroupIndex) {
        for (int groupIndex = 0; groupIndex <= maxGroupIndex; groupIndex++) {
            List<Clan> group = result.get(groupIndex);
            int possiblePlaces = 0;
            for (Clan clan2 : group) {
                if (clan.compareTo(clan2) > 0) {
                    possiblePlaces += clan2.getNumberOfPlayers();
                }
            }
            if (clan.getNumberOfPlayers() <= possiblePlaces) {
                String groupStr = Strings.join(group.iterator(), '|');
                fail("Clan " + clan.toString() + " can be placed in group " + groupStr);            
            }
        }
    }
    
    private void doTest(int maxGroupSize, List<Clan> clans, List<List<Clan>> expectedGroups) {
        Players players = new Players();
        players.setGroupCount(maxGroupSize);
        players.setClans(clans);
        
        
        List<List<Clan>> result;
        OnlinegameService service;
        
        service = new OnlinegameService(OnlineGameAlgorithm.BEST_CLANS_FIRST);
        result = service.organizePlayers(players);
        checkResult(expectedGroups, result);
        
        service = new OnlinegameService(OnlineGameAlgorithm.FAST_SEARCH_MACTCHING_CLAN);
        result = service.organizePlayers(players);
        checkResult(expectedGroups, result);

        service = new OnlinegameService(OnlineGameAlgorithm.SELECT_BEST);
        result = service.organizePlayers(players);
        checkResult(expectedGroups, result);
        
    }
    
    private void doTest(int maxGroupSize, int[][] clansArray, int[][][] expectedGroupsArray) {
        List<Clan> clans = createClanList(clansArray);
        List<List<Clan>> expeckedGroups = createGroupsList(expectedGroupsArray);
        doTest(maxGroupSize, clans, expeckedGroups);
    }
    
    @Test
    public void testExample1() {
        
        int maxGroupSize = 6;
        
        int[][] clansArray  = {
            {4,50}, {2,70}, {6,60}, {1,15}, {5,40}, {3,45}, {1,12}, {4,40}
        };
        
        int[][][] expectedGroups = {
            {{2,70}, {4,50}},
            {{6,60}},
            {{3,45}, {1,15}, {1,12}},
            {{4,40}},
            {{5,40}}
        };
        
        doTest(maxGroupSize, clansArray, expectedGroups);
        
    }

    @Test
    public void testOneGroup() {
        
        int maxGroupSize = 26;
        
        int[][] clansArray  = {
            {4,50}, {2,70}, {6,60}, {1,15}, {5,40}, {3,45}, {1,12}, {4,40}
        };
        
        int[][][] expectedGroups = {
            {{2,70}, {6,60}, {4,50}, {3,45}, {4,40}, {5,40}, {1,15}, {1,12}}, 
        };
        
        doTest(maxGroupSize, clansArray, expectedGroups);
        
    }

    @Test
    public void testTwoGroups() {
        
        int maxGroupSize = 25;
        
        int[][] clansArray  = {
            {4,50}, {2,70}, {6,60}, {1,15}, {5,40}, {3,45}, {1,12}, {4,40}
        };
        
        int[][][] expectedGroups = {
            {{2,70}, {6,60}, {4,50}, {3,45}, {4,40}, {5,40}, {1,15}}, 
            {{1,12}}
        };
        
        doTest(maxGroupSize, clansArray, expectedGroups);
    }
    
    @Test
    public void testGroupSize8() {
        
        int maxGroupSize = 8;
        
        int[][] clansArray  = {
            {4,50}, {2,70}, {6,60}, {1,15}, {5,40}, {3,45}, {1,12}, {4,40}
        };
        
        int[][][] expectedGroups = {
            {{2,70}, {6,60}},
            {{4,50}, {3,45}, {1,15} },
            {{4,40}, {1,12}},
            {{5,40}}
        };
        
        doTest(maxGroupSize, clansArray, expectedGroups);
    }
    
    @Test
    public void testOneColumn() {
        
        int maxGroupSize = 10;
        
        int[][] clansArray  = {
            {8,50}, {8,70}, {6,70}, {9,60}, {7,15}, {9,40}, {10,45}, {7,12}, {8, 12}, {9,40}
        };
        
        int[][][] expectedGroups = {
            {{6,70}}, 
            {{8,70}}, 
            {{9,60}}, 
            {{8,50}}, 
            {{10,45}}, 
            {{9,40}}, 
            {{9,40}},
            {{7,15}}, 
            {{7,12}}, 
            {{8,12}}
        };
        
        doTest(maxGroupSize, clansArray, expectedGroups);
    }

    @Test
    public void testOneElement() {
        
        int maxGroupSize = 10;
        
        int[][] clansArray  = {
            {8,50}
        };
        
        int[][][] expectedGroups = {
            {{8,50}}
        };
        
        doTest(maxGroupSize, clansArray, expectedGroups);
    }

    @Test
    public void testNoClans() {
        
        int maxGroupSize = 10;
        
        int[][] clansArray  = {
        };
        
        int[][][] expectedGroups = {
        };
        
        doTest(maxGroupSize, clansArray, expectedGroups);
    }

    @Test
    public void testWrongGroupSize() {
        
        int maxGroupSize = 5;
        
        int[][] clansArray  = {
            {4,50}, {2,70}, {6,60}, {1,15}, {5,40}, {3,45}, {1,12}, {4,40}
        };
        
        int[][][] expectedGroups = {
            {{2,70}, {6,60}, {4,50}, {3,45}, {4,40}, {5,40}, {1,15}}, 
            {{1,12}}
        };
        
        try {
            doTest(maxGroupSize, clansArray, expectedGroups);
            fail("Exception should be throwns");
        } catch (Throwable exception) {
            if (!(exception instanceof IllegalArgumentException)) {
                fail("Invalid exception has been thrown:" + exception.getMessage(), exception);
            }
        }
    }
    
    /**
     * Test of arrangePlayers method, of class OnlinegameService.
     */
    @Test
    public void testRandom() {
        
        int numberOfRandomTests = 10;
        for (int i = 1 ; i <= numberOfRandomTests; i++) {
            System.out.println("Test onlinegame service on randomized data. Test #" + i);
            makeRandomTest();
        }
        
    }

    private void makeRandomTest() {
        
        Random random = new Random();
        
        int maxPlayersInGroup = 10 + random.nextInt(1, 200);
        int numberOfClans = random.nextInt(1, 5000);

        System.out.println(
                String.format("Test for %d maximum players in group and %d clans.", 
                        maxPlayersInGroup, 
                        numberOfClans));
        
        Players players = new Players();
        players.setGroupCount(maxPlayersInGroup);
        
        ArrayList<Clan> clans = new ArrayList<>();
        for (int i = 0; i < numberOfClans; i++) {
            int numberOfPlayers = 1 + random.nextInt(1, maxPlayersInGroup / 4);
            int numberOfPoints = 1 + random.nextInt(1, 100 * maxPlayersInGroup);
            clans.add(new Clan(numberOfPlayers, numberOfPoints));
        }
        players.setClans(clans);

        OnlinegameService service;
        List<List<Clan>> result;
        
        service = new OnlinegameService(OnlineGameAlgorithm.BEST_CLANS_FIRST);
        result = service.organizePlayers(players);
        validateResult(result);
        
        service = new OnlinegameService(OnlineGameAlgorithm.FAST_SEARCH_MACTCHING_CLAN);
        result = service.organizePlayers(players);
        validateResult(result);

        service = new OnlinegameService(OnlineGameAlgorithm.SELECT_BEST);
        result = service.organizePlayers(players);
        validateResult(result);
        
    }

    
    
}
