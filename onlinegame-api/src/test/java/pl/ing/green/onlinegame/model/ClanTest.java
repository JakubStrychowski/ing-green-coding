package pl.ing.green.onlinegame.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests methods which were added or modified after code generation.
 *
 * @author Jakub Strychowski
 */
public class ClanTest {
    
    public ClanTest() {
    }


    @Test
    public void testEquals() {
        Clan clan1 = new Clan(10, 100);
        Clan clan2 = new Clan(10, 100);
        Clan clan3 = new Clan(2, 100);
        Clan clan4 = new Clan(3, 1);
        
        assertEquals(clan1, clan2);
        assertEquals(clan2, clan1);

        assertNotEquals(clan1, clan3);
        assertNotEquals(clan3, clan1);
        assertNotEquals(clan1, clan4);
        assertNotEquals(clan4, clan1);
        
        assertNotEquals(clan2, clan3);
        assertNotEquals(clan3, clan2);
        assertNotEquals(clan2, clan4);
        assertNotEquals(clan4, clan2);
        
        assertNotEquals(clan3, clan4);
        assertNotEquals(clan4, clan3);
        
    }

    @Test
    public void testCompareTo() {
        Clan clan1 = new Clan(1000, 100000);
        Clan clan2 = new Clan(1000, 100000);
        Clan clan3 = new Clan(200, 100000);
        Clan clan4 = new Clan(300, 100);
        
        assertEquals(0, clan1.compareTo(clan1));
        
        assertEquals(0, clan1.compareTo(clan2));
        assertEquals(0, clan2.compareTo(clan1));
        
        assertEquals(-1, clan1.compareTo(clan3));
        assertEquals(1, clan3.compareTo(clan1));
        
        assertEquals(1, clan1.compareTo(clan4));
        assertEquals(-1, clan4.compareTo(clan1));
        
    }
    
}
