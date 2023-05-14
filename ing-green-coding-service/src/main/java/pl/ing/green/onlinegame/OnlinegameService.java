package pl.ing.green.onlinegame;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.ing.green.onlinegame.api.OnlinegameApi;
import pl.ing.green.onlinegame.model.Clan;
import pl.ing.green.onlinegame.model.Players;

/**
 * Services which arrange order of players entry to the game.
 *
 * @author Jakub Strychowski
 */
@RestController
public class OnlinegameService implements OnlinegameApi {
    
    /**
     * Algorithm used to organize players.
     */
    private OnlineGameAlgorithm algorithm;

    /**
     * Creates a service which automatically estimates best algorithm for a task.
     */
    public OnlinegameService() {
        algorithm = OnlineGameAlgorithm.SELECT_BEST;
    }
    
    /**
     * Creates a service which uses the given algorithm for a task.
     *
     * @param algorithm
     */
    public OnlinegameService(OnlineGameAlgorithm algorithm) {
        this.algorithm = algorithm;
    }
    
    /**
     * Plans order of entry of clans to the game.
     *
     * @param players information about clans, and maximum size of a single group.
     * 
     * @return List of groups of clans. Clans enter the game in order of groups.
     */
    public List<List<Clan>> organizePlayers(Players players) {
        PlayersOrganizer organizer;
        switch (algorithm) {
            case BEST_CLANS_FIRST:
                // O(C, GSIZE, GCOUNT) = MAX(C x log(C), C x GCOUNT) 
                // This algorithm may be faster when we have
                // not too much clans, and we have quite big max group size.
                // If clans can be put to few groups algorithm is almost
                // linear. It can be slower if average clan size is big.
                organizer = new PlayersOrganizerBestClansFirst(players);
                break;
            case FAST_SEARCH_MACTCHING_CLAN:
                // This algorithm is best in most real cases (a lot of clans and many groups).
                // O(C, GSIZE, GCOUNT) = MAX(C x log(C), C x GSIZE) 
                organizer = new PlayersOrganizerSearchMatchingClan(players);
                break;
            case MAXIMIZE_POINTS_IN_GROUP:
                // This algorithm solves other task then specified.
                // It maximizes the strenge of a group (max points of a group).
                // The implementation solves multi knapsack problem with a constant
                // size of knapsacks (constant maximum number of players).
                // https://en.wikipedia.org/wiki/List_of_knapsack_problems
                organizer = new PlayersOrganizerMaximizePointsInGroup(players);
                break;
            default:
                // Best algorithm depends from size and structure of input data.
                // Following is a simple aproximation to select best algorithm.
                // 
                // GCOUNT ~ C * AVG_C_SIZE / GSIZE 
                // AVG_C_SIZE ~ GSIZE / 2  - for random C size between 0 and GSIZE
                // 
                // C x GCOUNT < C x GSIZE 
                // GCOUNT < GSIZE
                // C * AVG_C_SIZE / GSIZE < GSIZE
                // C * GSIZE / GSIZE * 2 < GSIZE
                // C / 2 < GSIZE
                // GSIZE > 2 * C
                if (players.getGroupCount() > 2 * players.getClans().size()) {
                    organizer = new PlayersOrganizerBestClansFirst(players);
                } else {
                    organizer = new PlayersOrganizerSearchMatchingClan(players);
                }
        }
                
        return organizePlayers(players, organizer);
    }
    
    /**
     * Plans order of entry of clans to the game using the given organizer.
     *
     * @param players
     * @param organizer
     * @return
     */
    protected List<List<Clan>> organizePlayers(Players players, PlayersOrganizer organizer) {
        List<List<Clan>> result = organizer.organizePlayers();
        return result;
    }
    
    /**
     * Prints result to the System.out.
     * This method is used for debuging purposes.
     *
     * @param result result to print.
     */
    public void logResult(List<List<Clan>> result) {
        for (List<Clan> group : result) {
            StringBuilder line = new StringBuilder();
            int pointsSum = 0;
            int playersSum = 0;
            for (Clan clan : group) {
                line.append(clan.toString());
                line.append(' ');
                pointsSum += clan.getPoints();
                playersSum += clan.getNumberOfPlayers();
            }
            line.append("  total players = ");
            line.append(playersSum);
            line.append("  total points = ");
            line.append(pointsSum);
            System.out.println(line.toString());
        }
        
        System.out.println("Number of all groups: " + result.size());
    }
    
    @Override
    public ResponseEntity<List<List<Clan>>> calculate(Players players) {
        
        List<List<Clan>> result = organizePlayers(players);
        
        ResponseEntity<List<List<Clan>>> response = new ResponseEntity<>(result, HttpStatus.OK);
        return response;        


    }
    
    
}
