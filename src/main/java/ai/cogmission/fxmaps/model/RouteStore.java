package ai.cogmission.fxmaps.model;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * Implements application {@link Route} persistence.
 *  
 * @author cogmission
 */
public class RouteStore {
    /** Stores list of routes */
    private List<Route> routes = new ArrayList<>();
    
    /**
     * Adds the specified {@link Route} to this store.
     * @param r     the route to add
     */
    public void addRoute(Route r) {
        if(!routes.contains(r)) {
            routes.add(r);
        }
    }
    
    /**
     * Removes the specified {@link Route} from this store.
     * 
     * @param r     the route to remove
     */
    public void removeRoute(Route r) {
        routes.remove(r);
    }
    
    /**
     * Returns a list of {@link Route}s
     * @return  returns this store's list of {@link Route}s
     */
    public List<Route> getRoutes() {
        return routes;
    }
    
    /**
     * Called prior to serialization to load the serializable data structure.
     */
    public void preSerialize() {
        for(Route r : routes) {
            r.preSerialize();
        }
    }
    
    /**
     * Called following deserialization to load the observable list. The observable
     * list cannot be serialized using the current method, so we use a simple list
     * for serialization and then copy the data over, after deserialization.
     */
    public void postDeserialize() {
        for(Route r : routes) {
            r.postDeserialize();
        }
    }
    
    /**
     * Implements the {@link Route} storage persistence.
     * 
     * @return  the serialized json string.
     */
    public String store() {
        preSerialize();
        
        //System.getProperty("user.home")
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            // convert user object to json string, and save to a file
            mapper.writeValue(new File(System.getProperty("user.home").concat("/route_store.json")), this);

            String json = null;
            // display to console
            System.out.println(json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this));
            
            return json; 
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Called to load the serialized {@link Routes} into this store.
     * 
     * @return  a {@code RouteStore} containing previously serialized {@link Route}s
     * or an empty store if there were no Routes previously persisted.
     */
    public static RouteStore load() {
        try {
            Gson gson = new Gson();
            String filePath = System.getProperty("user.home").concat("/route_store.json");
            RouteStore rs = gson.fromJson(
                Files.newBufferedReader(FileSystems.getDefault().getPath(filePath)), RouteStore.class);
            
            rs.postDeserialize();
            return rs;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return new RouteStore();
    }
}
