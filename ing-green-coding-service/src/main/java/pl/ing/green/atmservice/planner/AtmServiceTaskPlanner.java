/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.ing.green.atmservice.planner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import pl.ing.green.atmservice.model.ATM;
import pl.ing.green.atmservice.model.Task;

/**
 *
 * @author Jakub Strychowski
 */
public class AtmServiceTaskPlanner {
    
    private int numberOfTasks;
    private Map<Integer, RegionBuckets> regionsMap;
    private ArrayList<Integer> regionsList;

    
    public List<ATM> planTasks(List<Task> tasks) {
        
        fillBuckets(tasks);

        sortRegions();
        
        return fillResult();
    }
    

    private void fillBuckets(List<Task> tasks) {
        numberOfTasks = 0;
        regionsMap = new HashMap<>();
        for (Task task : tasks) {
            Integer region = task.getRegion();
            RegionBuckets buckets = regionsMap.get(region);
            if (buckets == null) {
                buckets = new RegionBuckets(region);
                regionsMap.put(region, buckets);
            }
            buckets.addTask(task);
            numberOfTasks++;
        }
    }

    private void sortRegions() {
        regionsList = new ArrayList<>(regionsMap.keySet());
        regionsList.sort( (r1, r2) -> r1.compareTo(r2));
    }

    private List<ATM> fillResult() {
        List<ATM> result = new ArrayList<>(numberOfTasks);
        Set<Integer> visitedAtms = new HashSet<>();
        for (Integer region : regionsList) {
            RegionBuckets buckets = regionsMap.get(region);
            
            visitedAtms.clear();

            /* Innym jest sygnał o awarii bankomatu z którym zespół techniczny 
               nie ma komunikacji i nie może przeprowadzić procedury zdalnego 
               ponownego uruchomienia. Takie zgłoszenie powinno zostać 
               zrealizowane w pierwszej kolejności w danym regionie.  */
            addTasksAsATM(result, buckets.failureTasks, region, visitedAtms);

            /* Priorytetowe planowane zasilenie bankomatu dotyczy urządzenia 
               gdzie trend zużycia stanu gotówki jest wysoki dlatego ta operacja 
               jest wykonywana przed zleceniami standardowymi. */
            addTasksAsATM(result, buckets.priorytyTasks, region, visitedAtms);
            
            /* Jednym z takich zgłoszeń jest sygnał o niskim stanie gotówki bankomatu, 
             który nie był na dzisiaj zaplanowany takie zgłoszenie powinno być 
             zrealizowane zaraz po zakończeniu prac nad zleceniami 
             planowanymi priorytetowymi w danym regionie. */
            addTasksAsATM(result, buckets.lowMoneyTasks, region, visitedAtms);
            
            addTasksAsATM(result, buckets.standardTasks, region, visitedAtms);
            
        }
        return result;
    }

    private void addTasksAsATM(List<ATM> result, List<Integer> bucket, int region, Set<Integer> visitedAtms) {
        if (bucket != null) {
            for (Integer atmId : bucket) {
                if (!visitedAtms.contains(atmId)) {
                    ATM atm = new ATM(region, atmId);
                    result.add(atm);
                    visitedAtms.add(atmId);
                }
            }
        }
    }


    
}
