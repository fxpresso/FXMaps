package ai.cogmission.fxmaps.model;

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
            MarkerOptions opts = new MarkerOptions(true)
                .position(ll)
                .title("Waypoint 1")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            Waypoint w = new Waypoint(ll, new Marker(opts));
            r.add(w);
            
            ll = new LatLon(20, -15);
            opts = new MarkerOptions(true)
                .position(ll)
                .title("Waypoint 2")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            w = new Waypoint(ll, new Marker(opts));
            r.add(w);
            
            ll = new LatLon(20, -10);
            opts = new MarkerOptions(true)
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
            MarkerOptions opts2 = new MarkerOptions(true)
                .position(ll2)
                .title("Waypoint 1")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            Waypoint w2 = new Waypoint(ll2, new Marker(opts2));
            r2.add(w2);
            
            ll2 = new LatLon(40, -35);
            opts2 = new MarkerOptions(true)
                .position(ll2)
                .title("Waypoint 2")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            w2 = new Waypoint(ll2, new Marker(opts2));
            r2.add(w2);
            
            ll2 = new LatLon(40, -30);
            opts2 = new MarkerOptions(true)
                .position(ll2)
                .title("Waypoint 3")
                .icon(MarkerType.GREEN.nextPath())
                .visible(true);
            w2 = new Waypoint(ll2, new Marker(opts2));
            r2.add(w2);
            
            RouteStore store = new RouteStore();
            store.addRoute(r);
            store.addRoute(r2);
            store.store();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
