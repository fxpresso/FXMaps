package ai.cogmission.fxmaps.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URL;

import org.junit.Test;


public class GPXReaderTest {
    
    private String getTrackXML() {
        String retVal = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "<gpx xmlns=\"http://www.topografix.com/GPX/1/1\" creator=\"GPS Kit iOS App by Garafa\" version=\"1.1\" " +
            "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
            "xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd\">" +
            "<metadata>" +
                "<name>Mon Jul 28 2014</name>" +
            "</metadata>" +
            "<trk>" +
                "<name>7/28/14, 11:42:21 AM</name>" +
                "<trkseg>" +
                    "<trkpt lat=\"37.487170\" lon=\"-122.229105\">" +
                        "<ele>5</ele>" +
                        "<time>2014-07-28T18:42:22.4870Z</time>" +
                    "</trkpt>" +
                    "<trkpt lat=\"37.487224\" lon=\"-122.229121\">" +
                        "<ele>5</ele>" +
                        "<time>2014-07-28T18:42:27.3600Z</time>" +
                    "</trkpt>" +
                "</trkseg>" +
            "</trk>" +
        "</gpx>";
        
        return retVal;
    }
    
    private String getWaypointXML() {
        String retVal = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
        "<gpx xmlns=\"http://www.topografix.com/GPX/1/1\" version=\"1.1\" creator=\"RouteConverter\">" +
            "<metadata>" +
                "<name>Test file by Cogmission</name>" +
            "</metadata>" +
            "<wpt lon=\"9.860624216140083\" lat=\"54.9328621088893\">" +
                "<ele>0.0</ele>" +
                "<name>Position 1</name>" +
            "</wpt>" +
            "<wpt lon=\"9.86092208681491\" lat=\"54.93293237320851\">" +
                "<ele>0.0</ele>" +
                "<name>Position 2</name>" +
            "</wpt>" +
            "<wpt lon=\"9.86187816543752\" lat=\"54.93327743521187\">" +
                "<ele>0.0</ele>" +
                "<name>Position 3</name>" +
            "</wpt>" +
            "<wpt lon=\"9.862439849679859\" lat=\"54.93342326167919\">" +
                "<ele>0.0</ele>" +
                "<name>Position 4</name>" +
            "</wpt>" +
        "</gpx>";
        
        return retVal;
    }
    
    private String getRouteXML() {
        String retVal = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
        "<gpx xmlns=\"http://www.topografix.com/GPX/1/1\" version=\"1.1\" creator=\"RouteConverter\">" +
            "<metadata>" +
                "<name>Test file by Cogmission</name>" +
            "</metadata>" +
            "<rte>" +
                "<name>Cogmission's Route</name>" +
                "<rtept lon=\"9.860624216140083\" lat=\"54.9328621088893\">" +
                    "<ele>0.0</ele>" +
                    "<name>Position 1</name>" +
                "</rtept>" +
                "<rtept lon=\"9.86092208681491\" lat=\"54.93293237320851\">" +
                    "<ele>0.0</ele>" +
                    "<name>Position 2</name>" +
                "</rtept>" +
                "<rtept lon=\"9.86187816543752\" lat=\"54.93327743521187\">" +
                    "<ele>0.0</ele>" +
                    "<name>Position 3</name>" +
                "</rtept>" +
                "<rtept lon=\"9.862439849679859\" lat=\"54.93342326167919\">" +
                    "<ele>0.0</ele>" +
                    "<name>Position 4</name>" +
                "</rtept>" +
            "</rte>" +
        "</gpx>";
        
        return retVal;
    }
    
    private String getSubset() {
        String retVal = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
        "<gpx xmlns=\"http://www.topografix.com/GPX/1/1\" version=\"1.1\" creator=\"RouteConverter\">" +
            "<metadata>" +
                "<name>Test file by Cogmission</name>" +
            "</metadata>" +
            "<wpt lon=\"9.860624216140083\" lat=\"54.9328621088893\">" +
                "<ele>0.0</ele>" +
                "<name>Position 1</name>" +
            "</wpt>" +
            "<wpt lon=\"9.86092208681491\" lat=\"54.93293237320851\">" +
                "<ele>0.0</ele>" +
                "<name>Position 2</name>" +
            "</wpt>" +
            "<wpt lon=\"9.86187816543752\" lat=\"54.93327743521187\">" +
                "<ele>0.0</ele>" +
                "<name>Position 3</name>" +
            "</wpt>" +
            "<wpt lon=\"9.862439849679859\" lat=\"54.93342326167919\">" +
                "<ele>0.0</ele>" +
                "<name>Position 4</name>" +
            "</wpt>" +
            "<trk>" +
                "<name>7/28/14, 11:42:21 AM</name>" +
                "<trkseg>" +
                    "<trkpt lat=\"37.487170\" lon=\"-122.229105\">" +
                        "<ele>5</ele>" +
                        "<time>2014-07-28T18:42:22.4870Z</time>" +
                    "</trkpt>" +
                    "<trkpt lat=\"37.487224\" lon=\"-122.229121\">" +
                        "<ele>5</ele>" +
                        "<time>2014-07-28T18:42:27.3600Z</time>" +
                    "</trkpt>" +
                "</trkseg>" +
            "</trk>" +
        "</gpx>";
        
        return retVal;
    }
    
    private String getAll3() {
        String retVal = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
        "<gpx xmlns=\"http://www.topografix.com/GPX/1/1\" version=\"1.1\" creator=\"RouteConverter\">" +
            "<metadata>" +
                "<name>Test file by Cogmission</name>" +
            "</metadata>" +
            "<wpt lon=\"9.860624216140083\" lat=\"54.9328621088893\">" +
                "<ele>0.0</ele>" +
                "<name>Position 1</name>" +
            "</wpt>" +
            "<wpt lon=\"9.86092208681491\" lat=\"54.93293237320851\">" +
                "<ele>0.0</ele>" +
                "<name>Position 2</name>" +
            "</wpt>" +
            "<wpt lon=\"9.86187816543752\" lat=\"54.93327743521187\">" +
                "<ele>0.0</ele>" +
                "<name>Position 3</name>" +
            "</wpt>" +
            "<wpt lon=\"9.862439849679859\" lat=\"54.93342326167919\">" +
                "<ele>0.0</ele>" +
                "<name>Position 4</name>" +
            "</wpt>" +
            "<trk>" +
                "<name>7/28/14, 11:42:21 AM</name>" +
                "<trkseg>" +
                    "<trkpt lat=\"37.487170\" lon=\"-122.229105\">" +
                        "<ele>5</ele>" +
                        "<time>2014-07-28T18:42:22.4870Z</time>" +
                    "</trkpt>" +
                    "<trkpt lat=\"37.487224\" lon=\"-122.229121\">" +
                        "<ele>5</ele>" +
                        "<time>2014-07-28T18:42:27.3600Z</time>" +
                    "</trkpt>" +
                "</trkseg>" +
            "</trk>" +
            "<rte>" +
                "<name>Cogmission's Route</name>" +
                "<rtept lon=\"9.860624216140083\" lat=\"54.9328621088893\">" +
                    "<ele>0.0</ele>" +
                    "<name>Position 1</name>" +
                "</rtept>" +
                "<rtept lon=\"9.86092208681491\" lat=\"54.93293237320851\">" +
                    "<ele>0.0</ele>" +
                    "<name>Position 2</name>" +
                "</rtept>" +
                "<rtept lon=\"9.86187816543752\" lat=\"54.93327743521187\">" +
                    "<ele>0.0</ele>" +
                    "<name>Position 3</name>" +
                "</rtept>" +
                "<rtept lon=\"9.862439849679859\" lat=\"54.93342326167919\">" +
                    "<ele>0.0</ele>" +
                    "<name>Position 4</name>" +
                "</rtept>" +
            "</rte>" +
        "</gpx>";
        
        return retVal;
    }

    @Test
    public void testReadFromSampleFile() {
        GPXReader gpx = new GPXReader();
        
        URL url = getClass().getResource("sample.gpx");
        GPXPersistentMap map = gpx.read(url);//InputStream in = new ByteArrayInputStream(string.getBytes("UTF-8"));
        
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
        System.out.println("name = " + map.getTracks().get(1).trackSegment.trackPoints.get(0).lon);
    }

}
