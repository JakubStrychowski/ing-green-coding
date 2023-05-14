package pl.ing.green.atmservice;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.ing.green.atmservice.api.AtmsApi;
import pl.ing.green.atmservice.model.ATM;
import pl.ing.green.atmservice.model.Task;
import pl.ing.green.atmservice.planner.AtmServiceTaskPlanner;

/**
 * REST service which realize ATM task.
 *
 * @author Jakub Strychowski
 */
@RestController
public class AtmService implements AtmsApi {

    /**
     * Order visits of ATMs in order according to priorities of given tasks.
     *
     * @param tasks Information about tasks to realize with ATMs.
     *
     * @return Best plan of visits of ATMs in regions.
     */
    @Override
    public ResponseEntity<List<ATM>> calculate(List<Task> tasks) {
        AtmServiceTaskPlanner planner = new AtmServiceTaskPlanner();
        List<ATM> result = planner.planTasks(tasks);
        ResponseEntity<List<ATM>> response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;
    }

}
