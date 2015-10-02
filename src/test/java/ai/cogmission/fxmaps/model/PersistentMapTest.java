package ai.cogmission.fxmaps.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import ai.cogmission.fxmaps.ui.Map;


public class PersistentMapTest {

    @Test
    public void testPersistenceEquality() {
        Route r1 = null;
        Route r2 = null;
        Route r3 = null;
        try {
            r1 = Map.createRoute("r");
            
            LatLon ll = new LatLon(20, -20);
            MarkerOptions opts = new MarkerOptions()
                .position(ll)
                .title("Waypoint 1")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            Waypoint w = new Waypoint(ll, new Marker(opts));
            r1.addWaypoint(w);
            
            ll = new LatLon(20, -15);
            opts = new MarkerOptions()
                .position(ll)
                .title("Waypoint 2")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            w = new Waypoint(ll, new Marker(opts));
            r1.addWaypoint(w);
            
            ll = new LatLon(20, -10);
            opts = new MarkerOptions()
                .position(ll)
                .title("Waypoint 3")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            w = new Waypoint(ll, new Marker(opts));
            r1.addWaypoint(w);
            
            ////// r2
            r2 = Map.createRoute("r2");
            
            ll = new LatLon(20, -20);
            opts = new MarkerOptions()
                .position(ll)
                .title("Waypoint 1")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            w = new Waypoint(ll, new Marker(opts));
            r2.addWaypoint(w);
            
            ll = new LatLon(20, -3);
            opts = new MarkerOptions()
                .position(ll)
                .title("Waypoint 2")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            w = new Waypoint(ll, new Marker(opts));
            r2.addWaypoint(w);
            
            ll = new LatLon(20, -10);
            opts = new MarkerOptions()
                .position(ll)
                .title("Waypoint 3")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            w = new Waypoint(ll, new Marker(opts));
            r2.addWaypoint(w);
            
            ////// r3
            r3 = Map.createRoute("r3");
            
            ll = new LatLon(20, -20);
            opts = new MarkerOptions()
                .position(ll)
                .title("Waypoint 1")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            w = new Waypoint(ll, new Marker(opts));
            r3.addWaypoint(w);
            
            ll = new LatLon(20, -15);
            opts = new MarkerOptions()
                .position(ll)
                .title("Waypoint 2")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            w = new Waypoint(ll, new Marker(opts));
            r3.addWaypoint(w);
            
            ll = new LatLon(20, -10);
            opts = new MarkerOptions()
                .position(ll)
                .title("Waypoint 3")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            w = new Waypoint(ll, new Marker(opts));
            r3.addWaypoint(w);
            
            assertTrue(r1.pathEquals(r3));
            assertFalse(r1.pathEquals(r2));
            
            MapStore store = new MapStore(System.getProperty("user.home").concat("/testMapStore.json"));
            store.addMap("test");
            store.addRoute(r1);
            store.addRoute(r2);
            store.addRoute(r3);
            store.store();
            
            MapStore loadedStore = MapStore.load(System.getProperty("user.home").concat("/testMapStore.json"));
            loadedStore.selectMap("test");
            
            List<Route> routes = loadedStore.getRoutes();
            assertEquals(routes.get(0), r1);
            assertEquals(routes.get(1), r2);
            assertEquals(routes.get(2), r3);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
