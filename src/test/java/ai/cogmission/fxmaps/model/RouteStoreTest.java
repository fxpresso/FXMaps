package ai.cogmission.fxmaps.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Worker;

import org.junit.Test;

import ai.cogmission.fxmaps.ui.Map;

import com.lynden.gmapsfx.javascript.IWebEngine;
import com.lynden.gmapsfx.javascript.JavascriptRuntime;


public class RouteStoreTest {

    @Test
    public void testStoreRoutes() {
        try {
            JavascriptRuntime.setDefaultWebEngine(new IWebEngine() {

                @Override
                public Object executeScript(String command) {
                    return null;
                }

                @Override public Worker<Void> getLoadWorker() {return null;}
                @Override public void load(String url) {}
                
            });
            
            Route r = Map.createRoute("name1");
            LatLon ll = new LatLon(20, -20);
            MarkerOptions opts = new MarkerOptions()
                .position(ll)
                .title("Waypoint 1")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            Waypoint w = new Waypoint(ll, new Marker(opts));
            r.add(w);
            
            ll = new LatLon(20, -15);
            opts = new MarkerOptions()
                .position(ll)
                .title("Waypoint 2")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            w = new Waypoint(ll, new Marker(opts));
            r.add(w);
            
            ll = new LatLon(20, -10);
            opts = new MarkerOptions()
                .position(ll)
                .title("Waypoint 3")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            w = new Waypoint(ll, new Marker(opts));
            r.add(w);
            
            ////////////// 2nd Route //////////////
            MarkerType.reset(); //Reset Marker Names
            
            Route r2 = Map.createRoute("name2");
            LatLon ll2 = new LatLon(40, -40);
            MarkerOptions opts2 = new MarkerOptions()
                .position(ll2)
                .title("Waypoint 1")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            Waypoint w2 = new Waypoint(ll2, new Marker(opts2));
            r2.add(w2);
            
            ll2 = new LatLon(40, -35);
            opts2 = new MarkerOptions()
                .position(ll2)
                .title("Waypoint 2")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            w2 = new Waypoint(ll2, new Marker(opts2));
            r2.add(w2);
            
            ll2 = new LatLon(40, -30);
            opts2 = new MarkerOptions()
                .position(ll2)
                .title("Waypoint 3")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            w2 = new Waypoint(ll2, new Marker(opts2));
            r2.add(w2);
            
            RouteStore store = new RouteStore();
            store.addRoute(r);
            store.addRoute(r2);
            
            String json = store.store();
            
            assertNotNull(json);
            assertEquals(7073, json.length());
            
            //////////// Load /////////////
            
            RouteStore loadedStore = RouteStore.load();
            
            assertEquals(2, loadedStore.getRoutes().size());
            Route r1 = loadedStore.getRoutes().get(0);
            assertEquals("name1", r1.getName());
            assertNotNull(r1.getOrigin());
            assertEquals(Waypoint.class, r1.getOrigin().getClass());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     */
    @Test
    public void testStoreRoutesWithLines() {
        try {
            JavascriptRuntime.setDefaultWebEngine(new IWebEngine() {

                @Override
                public Object executeScript(String command) {
                    return null;
                }

                @Override public Worker<Void> getLoadWorker() {return null;}
                @Override public void load(String url) {}
                
            });
            
            Route r = Map.createRoute("name1");
            LatLon ll1 = new LatLon(20, -20);
            MarkerOptions opts = new MarkerOptions()
                .position(ll1)
                .title("Waypoint 1")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            Waypoint w = new Waypoint(ll1, new Marker(opts));
            r.add(w);
            
            LatLon ll2 = new LatLon(20, -15);
            opts = new MarkerOptions()
                .position(ll2)
                .title("Waypoint 2")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            w = new Waypoint(ll2, new Marker(opts));
            r.add(w);
            
            List<LatLon> latLons = new ArrayList<LatLon>();
            latLons.add(ll1);
            latLons.add(ll2);
            PolylineOptions lineOpts = new PolylineOptions() 
                .path(latLons)
                .strokeWeight(2)
                .strokeColor("red");
            r.addLine(new Polyline(lineOpts));
            
            LatLon ll3 = new LatLon(20, -10);
            opts = new MarkerOptions()
                .position(ll3)
                .title("Waypoint 3")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            w = new Waypoint(ll3, new Marker(opts));
            r.add(w);
            
            latLons = new ArrayList<LatLon>();
            latLons.add(ll2);
            latLons.add(ll3);
            lineOpts = new PolylineOptions() 
                .path(latLons)
                .strokeWeight(2)
                .strokeColor("red");
            r.addLine(new Polyline(lineOpts));
            
            ////////////// 2nd Route //////////////
            MarkerType.reset(); //Reset Marker Names
            
            Route r2 = Map.createRoute("name2");
            LatLon ll4 = new LatLon(40, -40);
            MarkerOptions opts2 = new MarkerOptions()
                .position(ll4)
                .title("Waypoint 1")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            Waypoint w2 = new Waypoint(ll4, new Marker(opts2));
            r2.add(w2);
            
            LatLon ll5 = new LatLon(40, -35);
            opts2 = new MarkerOptions()
                .position(ll5)
                .title("Waypoint 2")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            w2 = new Waypoint(ll5, new Marker(opts2));
            r2.add(w2);
            
            latLons = new ArrayList<LatLon>();
            latLons.add(ll4);
            latLons.add(ll5);
            lineOpts = new PolylineOptions() 
                .path(latLons)
                .strokeWeight(2)
                .strokeColor("red");
            r2.addLine(new Polyline(lineOpts));
            
            LatLon ll6 = new LatLon(40, -30);
            opts2 = new MarkerOptions()
                .position(ll6)
                .title("Waypoint 3")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            w2 = new Waypoint(ll6, new Marker(opts2));
            r2.add(w2);
            
            latLons = new ArrayList<LatLon>();
            latLons.add(ll5);
            latLons.add(ll6);
            lineOpts = new PolylineOptions() 
                .path(latLons)
                .strokeWeight(2)
                .strokeColor("red");
            r2.addLine(new Polyline(lineOpts));
            
            RouteStore store = new RouteStore();
            store.addRoute(r);
            store.addRoute(r2);
            
            String json = store.store();
            
            assertNotNull(json);
            assertEquals(9199, json.length());
            
            //////////// Load /////////////
            
            RouteStore loadedStore = RouteStore.load();
            
            assertEquals(2, loadedStore.getRoutes().size());
            Route r1 = loadedStore.getRoutes().get(0);
            assertEquals("name1", r1.getName());
            assertNotNull(r1.getOrigin());
            assertEquals(Waypoint.class, r1.getOrigin().getClass());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
