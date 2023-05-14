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
 * Tests Onlinegame Service.
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
    
    /**
     * Creates test data from given arrays.
     * 
     * @param clanSizeAndPoints definition of clans in form of arrays used in tests.
     * 
     * @return input in form which may be used by the service.
     */
    public List<Clan> createClanList(int[][] clanSizeAndPoints) {
        ArrayList<Clan> clans = new ArrayList<>();
        for (int[] clanParameters : clanSizeAndPoints) {
            Clan clan = new Clan(clanParameters[0], clanParameters[1]);
            clans.add(clan);
        }
        return clans;
    }
    
    /**
     * Creates test result from given array.
     * 
     * @param groupsArray definition of result groups in form of arrays used in tests.
     * 
     * @return output in form returned by the service.
     */
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
    
    
    /**
     * Checks if the given result is equals according to expectation.
     * This method also validates correctness of the result.
     * 
     * @param expected Expected result.
     * @param result Order of groups and clans returned by service.
     */
    public void checkResult(List<List<Clan>> expected, List<List<Clan>> result) {
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

    
    /**
     * Validates if the given result is correct.
     * This method checks if any clan may be placed in any group which 
     * has higher priority then group in which this clan has been placed.
     * 
     * @param result Result of the Online Game Service.
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
    
    /**
     * Validates single clan if it should be placed in higher group.
     *
     * @param result Result of the Online Game Service.
     * @param clan Clan to validate.
     * @param maxGroupIndex Check group having at most this index.
     */
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
    
    /**
     * Performs test on the given input data, and validates it with expected output.
     * This method sets order of clans using different algorithms.
     *
     * @param maxGroupSize Maximum number of players in a group.
     * @param clans List of players' clans to organize in order.
     * @param expectedGroups Expected order of clans in groups.
     */
    protected void doTest(int maxGroupSize, List<Clan> clans, List<List<Clan>> expectedGroups) {
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
    
    /**
     * Tests example from the specification.
     */
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

    /**
     *  Test in which result should contain only one group.
     */
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

    /**
     * Test in which result should contain two groups.
     */
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
    
    /**
     * Test using example from the specification but with group size = 8.
     */
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
    
    /**
     * Test which should return one column of clans (each group contains exactly one clan).
     */
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

    /**
     * Test which process only single clan.
     */
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

    /**
     * Test which empty input data.
     */
    @Test
    public void testNoClans() {
        
        int maxGroupSize = 10;
        
        int[][] clansArray  = {
        };
        
        int[][][] expectedGroups = {
        };
        
        doTest(maxGroupSize, clansArray, expectedGroups);
    }

    /**
     * Test duplicate clans.
     */
    @Test
    public void testDuplicateClandTo2Groups() {
        
        int maxGroupSize = 926;
        
        int[][] clansArray  = {
            {194, 44},{177,41},{228,40},{187,10},{187,10}
        };
        
        int[][][] expectedGroups = {
            {{194, 44},{177,41},{228,40},{187,10}}, 
            {{187,10}}
        };
        
        doTest(maxGroupSize, clansArray, expectedGroups);
    }
    
    /**
     * Test what happen if input data is incorrect - size of a clan bigger the
     * maximum size of a group. Algorithm should avoid infinite loops in that case.
     */
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
     * Performs many iteration of a test on randomized input data.
     */
    @Test
    public void testRandom() {
        
        int numberOfRandomTests = 10;
        for (int i = 1 ; i <= numberOfRandomTests; i++) {
            System.out.println("Test onlinegame service on randomized data. Test #" + i);
            makeRandomTest();
        }
        
    }

    /**
     * Performs single iteration of a test on a randomized input data.
     */
    protected void makeRandomTest() {
        
        Random random = new Random();
        
        int maxPlayersInGroup = 10 + random.nextInt(1, 1000);
        int numberOfClans = random.nextInt(1, 20000);

        System.out.println(
                String.format("\nTest for %d maximum players in group and %d clans.", 
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
        
        long startTime = System.currentTimeMillis();
        service = new OnlinegameService(OnlineGameAlgorithm.BEST_CLANS_FIRST);
        result = service.organizePlayers(players);
        long duration = System.currentTimeMillis() - startTime;
        System.out.println(String.format("Test time using algorithm %s: %d ms",
                OnlineGameAlgorithm.BEST_CLANS_FIRST.toString(), duration));
        validateResult(result);
        
        startTime = System.currentTimeMillis();
        service = new OnlinegameService(OnlineGameAlgorithm.FAST_SEARCH_MACTCHING_CLAN);
        result = service.organizePlayers(players);
        duration = System.currentTimeMillis() - startTime;
        System.out.println(String.format("Test time using algorithm %s: %d ms",
                OnlineGameAlgorithm.FAST_SEARCH_MACTCHING_CLAN.toString(), duration));
        validateResult(result);

        startTime = System.currentTimeMillis();
        service = new OnlinegameService(OnlineGameAlgorithm.SELECT_BEST);
        result = service.organizePlayers(players);
        duration = System.currentTimeMillis() - startTime;
        System.out.println(String.format("Test time using algorithm %s: %d ms",
                OnlineGameAlgorithm.SELECT_BEST.toString(), duration));
        validateResult(result);
        
    }

    
    
}
