package ai.cogmission.fxmaps.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;

import ai.cogmission.fxmaps.model.PersistentMap;


public class GPXPersistentMapTest {

    @Test
    public void testAsFXMap() {
        String trackXML = GPXTestData.getTrackXML();
        try {
            InputStream in = new ByteArrayInputStream(trackXML.getBytes("UTF-8"));
            GPXReader gpx = new GPXReader();
            GPXPersistentMap map = gpx.read(in);
            
            PersistentMap pMap = GPXPersistentMap.asFXMap(map, GPXType.TRACK);
            System.out.println(pMap.getMapOptions());
            assertNotNull(pMap.getRoutes());
            assertEquals(1, pMap.getRoutes().size());
            assertNotNull(pMap.getRoutes().get(0).getWaypoints());
            assertEquals(2, pMap.getRoutes().get(0).getWaypoints().size());
            assertEquals(1, pMap.getRoutes().get(0).getLines().size());
            assertTrue(pMap.getRoutes().get(0).getWaypoints().get(0).getMarker().getMarkerOptions().getIcon().indexOf("MarkerA") != -1);
            assertTrue(pMap.getRoutes().get(0).getWaypoints().get(1).getMarker().getMarkerOptions().getIcon().indexOf("MarkerB") != -1);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
