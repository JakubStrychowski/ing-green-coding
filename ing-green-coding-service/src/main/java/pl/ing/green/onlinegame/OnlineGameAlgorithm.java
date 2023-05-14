package pl.ing.green.onlinegame;

/**
 * Defines available algorithms which organize players to a game.
 *
 * @author Jakub Strychowski
 */
public enum OnlineGameAlgorithm {
    
    /**
     * Puts clans to groups from best to worst.
     */
    BEST_CLANS_FIRST,
    
    /**
     * Generates groups from best to worst adding matching clans.
     * Uses fast search of clans using buckets related to clans sizes.
     */
    FAST_SEARCH_MACTCHING_CLAN,
    
    /**
     * Selects best algorithm according to input data.
     */
    SELECT_BEST,
    
    /**
     * Multi knackpack algorithm - Maximizes points in groups.
     * In this algorithm strong clans may go later if they can be replaced
     * be few weaker and smaller clans, but stronger when bind together.
     */
    MAXIMIZE_POINTS_IN_GROUP
    
}
