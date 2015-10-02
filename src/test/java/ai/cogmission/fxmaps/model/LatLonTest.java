package ai.cogmission.fxmaps.model;

import static org.junit.Assert.*;

import org.junit.Test;


public class LatLonTest {

    @Test
    public void testLatLonEquality() {
        LatLon ll1 = new LatLon(20, -20);
        
        LatLon ll2 = new LatLon(20, -15);
        
        LatLon ll3 = new LatLon(20, -20);
        
        assertNotEquals(ll1, ll2);
        assertEquals(ll1, ll3);
    }

}
