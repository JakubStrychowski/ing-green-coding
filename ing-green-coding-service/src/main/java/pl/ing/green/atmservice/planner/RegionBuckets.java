package pl.ing.green.atmservice.planner;

import java.util.ArrayList;
import pl.ing.green.atmservice.model.RequestTypeEnum;
import pl.ing.green.atmservice.model.Task;

/**
 * Represents buckets of tasks for a region. Each buckets relates to different
 * request types;
 *
 * @author Jakub Strychowski
 */
public class RegionBuckets {

    /**
     * Number which identifies region. Regions should be visited in order of
     * identifiers.
     */
    protected int regionNumber;

    /**
     * Bucket of standard tasks.
     */
    protected ArrayList<Integer> standardTasks;

    /**
     * Bucket of priority tasks.
     */
    protected ArrayList<Integer> priorytyTasks;

    /**
     * Bucket for ATMs with low amount of money.
     */
    protected ArrayList<Integer> lowMoneyTasks;

    /**
     * Bucket for ATMs with lost communication.
     */
    protected ArrayList<Integer> failureTasks;

    /**
     * Creates bucket holder for a region having the given number.
     *
     * @param number Id of a region.
     */
    public RegionBuckets(int number) {
        this.regionNumber = number;
        this.standardTasks = new ArrayList();
        this.priorytyTasks = new ArrayList();
        this.lowMoneyTasks = new ArrayList();
        this.failureTasks = new ArrayList();
    }

    /**
     * Puts task to a bucket corresponding to request type.
     *
     * @param task Task from a base plan.
     */
    public void addTask(Task task) {
        Integer atmId = task.getAtmId();
        RequestTypeEnum type = task.getRequestType();
        switch (type) {
            case STANDARD ->
                standardTasks.add(atmId);
            case FAILURE_RESTART ->
                failureTasks.add(atmId);
            case PRIORITY ->
                priorytyTasks.add(atmId);
            case SIGNAL_LOW ->
                lowMoneyTasks.add(atmId);
        }
    }

}
