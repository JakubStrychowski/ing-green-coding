/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.ing.green.atmservice.planner;

import java.util.ArrayList;
import pl.ing.green.atmservice.model.RequestTypeEnum;
import pl.ing.green.atmservice.model.Task;

/**
 *
 * @author Jakub Strychowski
 */
public class RegionBuckets {
    
    protected int regionNumber;
    protected ArrayList<Integer> standardTasks;
    protected ArrayList<Integer> priorytyTasks;
    protected ArrayList<Integer> lowMoneyTasks;
    protected ArrayList<Integer> failureTasks;
    
    public RegionBuckets(int number) {
        this.regionNumber = number;
        this.standardTasks = new ArrayList();
        this.priorytyTasks = new ArrayList();
        this.lowMoneyTasks = new ArrayList();
        this.failureTasks = new ArrayList();
    }

    public void addTask(Task task) {
        Integer atmId = task.getAtmId();
        RequestTypeEnum type = task.getRequestType();
        switch (type) {
            case STANDARD -> standardTasks.add(atmId);
            case FAILURE_RESTART -> failureTasks.add(atmId);
            case PRIORITY -> priorytyTasks.add(atmId);
            case SIGNAL_LOW -> lowMoneyTasks.add(atmId);
        }
    }
            
    
}
