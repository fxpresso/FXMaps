package ai.cogmission.fxmaps.model;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.HashMap;

import ai.cogmission.fxmaps.exception.MapDoesNotExistException;
import ai.cogmission.fxmaps.ui.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.stream.MalformedJsonException;

/**
 * Implements application {@link Map} persistence.
 *  
 * @author cogmission
 */
public class MapStore {
    public static final String DEFAULT_STORE_PATH = System.getProperty("user.home").concat("/map_store.json");
    
    private java.util.Map<String, PersistentMap> maps = new java.util.HashMap<>();
    
    private String storePath = DEFAULT_STORE_PATH;
    
    private String selectedMap;
    
    
    /**
     * Construct a new {@code MapStore}
     */
    public MapStore() {}
    
    /**
     * Create a MapStore with a specified path.
     * @param storePath
     */
    public MapStore(String storePath) {
        this.storePath = storePath;
    }
    
    /**
     * Adds a new {@link PersistentMap} if one does not exist.
     * This method is non-destructive (will not overwrite an existing map,
     * with a new empty map).
     * 
     * @param newMapName    the name for the new map.
     */
    public void addMap(String newMapName) {
        if(newMapName == null) {
            return;
        }
        
        if(maps.get(newMapName) == null) {
            maps.put(newMapName, new PersistentMap(selectedMap = newMapName));
        }
    }
    
    /**
     * Selects the currently active map
     * @param mapName   the name of the map to select
     * @throws MapDoesNotExistException
     */
    public void selectMap(String mapName) throws MapDoesNotExistException {
        if(!maps.containsKey(mapName)) {
            throw new MapDoesNotExistException("No map exists with name: " + mapName);
        }
        
        selectedMap = mapName;
    }
    
    /**
     * Returns the currently selected {@link PersistentMap} name.
     * @return
     */
    public String getSelectedMapName() {
        return selectedMap;
    }
    
    /**
     * Removes the map with the specified name. 
     * @param mapName   the name of the map to remove
     */
    public void deleteMap(String mapName) {
        maps.remove(mapName);
    }
    
    /**
     * Returns the {@link PersistentMap} with the specified
     * name or null if it doesn't exist.
     * 
     * @param name  the name of the desired map to return
     * @return  the {@code PersistentMap} with the specified name
     */
    public PersistentMap getMap(String name) {
        return maps.get(name);
    }
    
    /**
     * Returns the {@link HashMap} of {@link PersistentMap}s.
     * @return  the {@link HashMap} of {@link PersistentMap}s.
     */
    public java.util.Map<String, PersistentMap> getMaps() {
        return maps;
    }
    
    /**
     * Called prior to serialization to load the serializable data structure.
     */
    public void preSerialize() {
        if(selectedMap == null || maps.get(selectedMap) == null) {
            return;
        }
        
        for(Route r : maps.get(selectedMap).getRoutes()) {
            r.preSerialize();
        }
    }
    
    /**
     * Called following deserialization to load the observable list. The observable
     * list cannot be serialized using the current method, so we use a simple list
     * for serialization and then copy the data over, after deserialization.
     */
    public void postDeserialize() throws MalformedJsonException {
        for(Route r : maps.get(selectedMap).getRoutes()) {
            r.postDeserialize();
        }
    }
    
    /**
     * Implements the {@link Route} storage persistence.
     * 
     * @return  the serialized json string.
     */
    public String store() {
        System.out.println("MapStore.store()");
        preSerialize();
        
        //System.getProperty("user.home")
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            // convert user object to json string, and save to a file
            mapper.writeValue(new File(storePath), this);

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
     * Called to load the serialized {@link Routes} into this store. Uses the store
     * path defined in the creation of this instance.
     * 
     * @return  a {@code RouteStore} containing previously serialized {@link Route}s
     * or an empty store if there were no Routes previously persisted.
     */
    public MapStore load() {
        return MapStore.load(storePath);
    }
    
    /**
     * Called to load the serialized {@link Routes} into this store.
     * 
     * @return  a {@code RouteStore} containing previously serialized {@link Route}s
     * or an empty store if there were no Routes previously persisted.
     * 
     * @param path  the path to the persistent store json file
     */
    public static MapStore load(String path) {
        path = path == null ? DEFAULT_STORE_PATH : path;
        
        try {
            Gson gson = new Gson();
            MapStore mapStore = gson.fromJson(
                Files.newBufferedReader(FileSystems.getDefault().getPath(path)), MapStore.class);
            
            for(String mapName : mapStore.getMaps().keySet()) {
                mapStore.selectMap(mapName);
                mapStore.postDeserialize();
            }
           
            mapStore.storePath = path;
            
            return mapStore;
        }catch(MalformedJsonException m) {
            System.out.println(m.getMessage());
            File f = new File(path);
            f.delete();
        }catch(Exception e) {
            System.out.println("No map store found, creating new map store.");
        }
        return new MapStore();
    }
}
