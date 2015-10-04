package ai.cogmission.fxmaps.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import org.junit.Test;


public class GPXReaderTest {
    @Test
    public void testReadFromSampleFile() {
        GPXReader gpx = new GPXReader();
        
        URL url = getClass().getResource("sample.gpx");
        GPXPersistentMap map = gpx.read(url);
        
        assertEquals("Mon Jul 28 2014", map.getName());
        assertEquals(5, map.getTracks().size());
        
        //Track 1
        assertEquals("7/28/14, 11:42:21 AM", map.getTracks().get(0).name);
        assertNotNull(map.getTracks().get(0).trackSegment);
        assertEquals(54, map.getTracks().get(0).trackSegment.trackPoints.size());
        assertEquals(5, map.getTracks().get(0).trackSegment.trackPoints.get(0).ele, 0.0);
        assertEquals(37.48717, map.getTracks().get(0).trackSegment.trackPoints.get(0).lat, 0.0);
        assertEquals(-122.229105, map.getTracks().get(0).trackSegment.trackPoints.get(0).lon, 0.0);
        
        //Track 2
        assertEquals("7/28/14, 12:04:32 PM", map.getTracks().get(1).name);
        assertNotNull(map.getTracks().get(1).trackSegment);
        assertEquals(79, map.getTracks().get(1).trackSegment.trackPoints.size());
        assertEquals(6, map.getTracks().get(1).trackSegment.trackPoints.get(0).ele, 0.0);
        assertEquals(37.486869, map.getTracks().get(1).trackSegment.trackPoints.get(0).lat, 0.0);
        assertEquals(-122.228952, map.getTracks().get(1).trackSegment.trackPoints.get(0).lon, 0.0);
    }
    
    @Test
    public void testReadTracksFromStream() {
        String trackXML = GPXTestData.getTrackXML();
        try {
            InputStream in = new ByteArrayInputStream(trackXML.getBytes("UTF-8"));
            GPXReader gpx = new GPXReader();
            GPXPersistentMap map = gpx.read(in);
            assertNotNull(map);
            
            assertEquals("Mon Jul 28 2014", map.getName());
            assertEquals(1, map.getTracks().size());
            assertEquals(2, map.getTracks().get(0).trackSegment.trackPoints.size());
            
            assertEquals(37.48717, map.getTracks().get(0).trackSegment.trackPoints.get(0).lat, 0.0);
            assertEquals(-122.229105, map.getTracks().get(0).trackSegment.trackPoints.get(0).lon, 0.0);
            assertEquals(5, map.getTracks().get(0).trackSegment.trackPoints.get(0).ele, 0.0);
            assertEquals("2014-07-28T18:42:22.4870Z", map.getTracks().get(0).trackSegment.trackPoints.get(0).time);
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testReadRoutesFromStream() {
        String routeXML = GPXTestData.getRouteXML();
        try {
            InputStream in = new ByteArrayInputStream(routeXML.getBytes("UTF-8"));
            GPXReader gpx = new GPXReader();
            GPXPersistentMap map = gpx.read(in);
            assertNotNull(map);
            
            assertEquals("Test file by Cogmission", map.getName());
            assertEquals(1, map.getRoutes().size());
            assertEquals(4, map.getRoutes().get(0).routePoints.size());
            
            assertEquals(54.9328621088893, map.getRoutes().get(0).routePoints.get(0).lat, 0.0);
            assertEquals(9.860624216140083, map.getRoutes().get(0).routePoints.get(0).lon, 0.0);
            assertEquals(0, map.getRoutes().get(0).routePoints.get(0).ele, 0.0);
            assertEquals("Position 1", map.getRoutes().get(0).routePoints.get(0).name);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testWaypointsRoutesFromStream() {
        String waypointXML = GPXTestData.getWaypointXML();
        try {
            InputStream in = new ByteArrayInputStream(waypointXML.getBytes("UTF-8"));
            GPXReader gpx = new GPXReader();
            GPXPersistentMap map = gpx.read(in);
            assertNotNull(map);
            
            assertEquals("Test file by Cogmission", map.getName());
            assertEquals(4, map.getWaypoints().size());
            
            assertEquals(54.9328621088893, map.getWaypoints().get(0).lat, 0.0);
            assertEquals(9.860624216140083, map.getWaypoints().get(0).lon, 0.0);
            assertEquals(0, map.getWaypoints().get(0).ele, 0.0);
            assertEquals("Position 1", map.getWaypoints().get(0).name);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testAllThreeTypesFromStream() {
        String allXML = GPXTestData.getAll3();
        try {
            InputStream in = new ByteArrayInputStream(allXML.getBytes("UTF-8"));
            GPXReader gpx = new GPXReader();
            GPXPersistentMap map = gpx.read(in);
            assertNotNull(map);
            
            // Tracks
            assertEquals(1, map.getTracks().size());
            assertEquals(2, map.getTracks().get(0).trackSegment.trackPoints.size());
            
            assertEquals(37.48717, map.getTracks().get(0).trackSegment.trackPoints.get(0).lat, 0.0);
            assertEquals(-122.229105, map.getTracks().get(0).trackSegment.trackPoints.get(0).lon, 0.0);
            assertEquals(5, map.getTracks().get(0).trackSegment.trackPoints.get(0).ele, 0.0);
            assertEquals("2014-07-28T18:42:22.4870Z", map.getTracks().get(0).trackSegment.trackPoints.get(0).time);
            
            // Routes
            assertEquals(1, map.getRoutes().size());
            assertEquals(4, map.getRoutes().get(0).routePoints.size());
            
            assertEquals(54.9328621088893, map.getWaypoints().get(0).lat, 0.0);
            assertEquals(9.860624216140083, map.getWaypoints().get(0).lon, 0.0);
            assertEquals(0, map.getWaypoints().get(0).ele, 0.0);
            assertEquals("Position 1", map.getWaypoints().get(0).name);
            
            // Waypoints
            assertEquals("Test file by Cogmission", map.getName());
            assertEquals(4, map.getWaypoints().size());
            
            assertEquals(54.9328621088893, map.getWaypoints().get(0).lat, 0.0);
            assertEquals(9.860624216140083, map.getWaypoints().get(0).lon, 0.0);
            assertEquals(0, map.getWaypoints().get(0).ele, 0.0);
            assertEquals("Position 1", map.getWaypoints().get(0).name);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
