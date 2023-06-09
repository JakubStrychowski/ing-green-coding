package pl.ing.green.atmservice.planner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import pl.ing.green.atmservice.model.ATM;
import pl.ing.green.atmservice.model.RequestTypeEnum;
import pl.ing.green.atmservice.model.Task;

/**
 * Tests ATM Service.
 *
 * @author Jakub Strychowski
 */
public class AtmServiceTaskPlannerTest {

    public AtmServiceTaskPlannerTest() {
    }

    /**
     * Tests examples from the specification.
     */
    @Test
    public void testExample1() {

        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(4, 1, RequestTypeEnum.STANDARD));
        tasks.add(new Task(1, 1, RequestTypeEnum.STANDARD));
        tasks.add(new Task(2, 1, RequestTypeEnum.STANDARD));
        tasks.add(new Task(3, 2, RequestTypeEnum.PRIORITY));
        tasks.add(new Task(3, 1, RequestTypeEnum.STANDARD));
        tasks.add(new Task(2, 1, RequestTypeEnum.SIGNAL_LOW));
        tasks.add(new Task(5, 2, RequestTypeEnum.STANDARD));
        tasks.add(new Task(5, 1, RequestTypeEnum.FAILURE_RESTART));

        AtmServiceTaskPlanner planner = new AtmServiceTaskPlanner();
        List<ATM> result = planner.planTasks(tasks);
        validateResult(tasks, result);

        assertEquals(7, result.size());
        int index = 0;
        assertEquals(result.get(index++), new ATM(1, 1));
        assertEquals(result.get(index++), new ATM(2, 1));
        assertEquals(result.get(index++), new ATM(3, 2));
        assertEquals(result.get(index++), new ATM(3, 1));
        assertEquals(result.get(index++), new ATM(4, 1));
        assertEquals(result.get(index++), new ATM(5, 1));
        assertEquals(result.get(index++), new ATM(5, 2));

    }

    /**
     * Test second example from specification.
     */
    @Test
    public void testExample2() {

        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1, 2, RequestTypeEnum.STANDARD));
        tasks.add(new Task(1, 1, RequestTypeEnum.STANDARD));
        tasks.add(new Task(2, 3, RequestTypeEnum.PRIORITY));
        tasks.add(new Task(3, 4, RequestTypeEnum.STANDARD));
        tasks.add(new Task(4, 5, RequestTypeEnum.STANDARD));
        tasks.add(new Task(5, 2, RequestTypeEnum.PRIORITY));
        tasks.add(new Task(5, 1, RequestTypeEnum.STANDARD));
        tasks.add(new Task(3, 2, RequestTypeEnum.SIGNAL_LOW));
        tasks.add(new Task(2, 1, RequestTypeEnum.SIGNAL_LOW));
        tasks.add(new Task(3, 1, RequestTypeEnum.FAILURE_RESTART));

        AtmServiceTaskPlanner planner = new AtmServiceTaskPlanner();
        List<ATM> result = planner.planTasks(tasks);
        validateResult(tasks, result);

        assertEquals(10, result.size());
        int index = 0;
        assertEquals(result.get(index++), new ATM(1, 2));
        assertEquals(result.get(index++), new ATM(1, 1));
        assertEquals(result.get(index++), new ATM(2, 3));
        assertEquals(result.get(index++), new ATM(2, 1));
        assertEquals(result.get(index++), new ATM(3, 1));
        assertEquals(result.get(index++), new ATM(3, 2));
        assertEquals(result.get(index++), new ATM(3, 4));
        assertEquals(result.get(index++), new ATM(4, 5));
        assertEquals(result.get(index++), new ATM(5, 2));
        assertEquals(result.get(index++), new ATM(5, 1));

    }

    /**
     * Tests with one task as input data.
     */
    @Test
    public void testOneTask() {

        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(4, 2, RequestTypeEnum.STANDARD));

        AtmServiceTaskPlanner planner = new AtmServiceTaskPlanner();
        List<ATM> result = planner.planTasks(tasks);
        validateResult(tasks, result);

        assertEquals(1, result.size());
        int index = 0;
        assertEquals(result.get(index++), new ATM(4, 2));

    }

    /**
     * Tests with empty input data.
     */
    @Test
    public void testNoTasks() {

        List<Task> tasks = new ArrayList<>();

        AtmServiceTaskPlanner planner = new AtmServiceTaskPlanner();
        List<ATM> result = planner.planTasks(tasks);
        validateResult(tasks, result);

        assertEquals(0, result.size());

    }

    /**
     * Tests 10 times with random input data.
     */
    @Test
    public void testRandom() {
        for (int i = 0; i < 10; i++) {
            makeRandomTest();
        }
    }

    private void makeRandomTest() {

        Random random = new Random();

        int numberOfRegions = random.nextInt(1, 2000);
        int maxNumberOfATMsInRegion = random.nextInt(5, 500);
        int numberOfTasks = 3 * numberOfRegions * maxNumberOfATMsInRegion;

        System.out.println(
                String.format(
                        """
                        
                Testin service planner with %d number of regions;
                max number of ATMs in each region = %d;
                number of tasks = %d""",
            numberOfRegions, maxNumberOfATMsInRegion, numberOfTasks)
        );

        List<Task> tasks = new ArrayList<>(numberOfTasks);
        for (int i = 0; i < numberOfTasks; i++) {
            int region = random.nextInt(1, numberOfRegions + 1);
            int atmId = random.nextInt(1, maxNumberOfATMsInRegion + 1);
            int requestTypeId = random.nextInt(0, 4);
            RequestTypeEnum requestType = RequestTypeEnum.values()[requestTypeId];
            tasks.add(new Task(region, atmId, requestType));
        }

        long startTime = System.currentTimeMillis();
        AtmServiceTaskPlanner planner = new AtmServiceTaskPlanner();
        List<ATM> result = planner.planTasks(tasks);
        long duration = System.currentTimeMillis() - startTime;
        System.out.println(
            String.format("Planning processed accomplished in %d ms", duration));
        
        validateResult(tasks, result);

    }

    /**
     * Checks if result data are consistent.
     */
    private void validateResultConsistency(List<ATM> result) {

        int lastRegionNumber = -1;
        Set<ATM> atms = new HashSet<>(result.size());

        for (ATM atm : result) {

            // regions according to theire numbers
            int region = atm.getRegion();
            assertTrue(region >= lastRegionNumber);
            lastRegionNumber = region;

            // each atm only ones
            assertFalse(atms.contains(atm));

            atms.add(atm);

        }
    }

    /**
     * Checks if result is valid according to given input data.
     * 
     * @param tasks Tasks given as input.
     * @param result Planned sequence of visits to ATMs.
     */
    private void validateResult(List<Task> tasks, List<ATM> result) {
        validateResultConsistency(result);

        // group tasks by regions
        Map<Integer, List<Task>> tasksMap = tasks.stream().collect(Collectors.groupingBy(Task::getRegion));

        // group atms by regions
        Map<Integer, List<ATM>> atmsMap = result.stream().collect(Collectors.groupingBy(ATM::getRegion));

        // regions should match
        atmsMap.keySet().equals(tasksMap.keySet());

        // check each region
        tasksMap.keySet().forEach((region) -> validateRegion(tasksMap.get(region), atmsMap.get(region)));
    }

    /**
     * Validates if AMTs in a region are visited in proper order.
     * 
     * @param tasks Input tasks;
     * @param result Result in a single region.
     */
    private void validateRegion(List<Task> tasks, List<ATM> result) {
        Map<Integer, List<Task>> tasksMap = tasks.stream().collect(Collectors.groupingBy(Task::getAtmId));

        RequestTypeEnum lastRequestType = RequestTypeEnum.FAILURE_RESTART;
        for (ATM atm : result) {
            List<Task> atmTasks = tasksMap.get(atm.getAtmId());
            RequestTypeEnum requestType = getEffectiveRequestType(atmTasks);
            if (lastRequestType != requestType) {
                switch (requestType) {
                    case FAILURE_RESTART:
                        assertTrue(lastRequestType != RequestTypeEnum.STANDARD
                                && lastRequestType != RequestTypeEnum.PRIORITY
                                && lastRequestType != RequestTypeEnum.SIGNAL_LOW);
                        break;
                    case PRIORITY:
                        assertTrue(lastRequestType != RequestTypeEnum.STANDARD
                                && lastRequestType != RequestTypeEnum.SIGNAL_LOW);
                        break;
                    case SIGNAL_LOW:
                        assertTrue(lastRequestType != RequestTypeEnum.STANDARD);
                        break;
                    case STANDARD:
                        break;
                }
                lastRequestType = requestType;
            }
        }
    }


    /**
     * Determines which request type should be assigned to a ATM.
     * 
     * @param atmTasks Tasks related to single ATM.
     * 
     * @return request type according to priorities given in the specification.
     */
    private RequestTypeEnum getEffectiveRequestType(List<Task> atmTasks) {
        RequestTypeEnum result = RequestTypeEnum.STANDARD;
        for (Task task : atmTasks) {
            switch (task.getRequestType()) {
                case PRIORITY:
                    result = RequestTypeEnum.PRIORITY;
                    break;
                case SIGNAL_LOW:
                    if (result != RequestTypeEnum.PRIORITY) {
                        result = RequestTypeEnum.SIGNAL_LOW;
                    }
                    break;
                case FAILURE_RESTART:
                    result = RequestTypeEnum.FAILURE_RESTART;
                    return result;
                case STANDARD:
                    break;
            }
        }
        return result;
    }

//    private void printResult(List<ATM> result) {
//        System.out.println(Strings.join(result.iterator(), '\n'));
//    }
    
}
