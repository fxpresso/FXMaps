package ai.cogmission.fxmaps.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class RouteStore {
    private List<Route> routes = new ArrayList<>();
    
    public void addRoute(Route r) {
        routes.add(r);
    }
    
    public void removeRoute(Route r) {
        routes.remove(r);
    }
    
    public void store() {
        //System.getProperty("user.home")
        ObjectMapper mapper = new ObjectMapper();
        
        try {

            // convert user object to json string, and save to a file
            mapper.writeValue(new File(System.getProperty("user.home").concat("/route_store.json")), routes);

            // display to console
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(routes));

        } catch (JsonGenerationException e) {

            e.printStackTrace();

        } catch (JsonMappingException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
}
