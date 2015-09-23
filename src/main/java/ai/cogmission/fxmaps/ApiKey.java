package ai.cogmission.fxmaps;

import com.google.maps.GeoApiContext;

/**
 * Api key abstraction used to access the wrapped Google Service(s) API.
 * 
 * @author cogmission
 * @see Directions
 * @see Geocoder
 */
public class ApiKey {
    /** internal context used to access the wrapped Google Service API */
    private static GeoApiContext context;
    
    /**
     * Private constructor for creating an {@code ApiKey}
     * @param key
     */
    private ApiKey(String key) {
        context = new GeoApiContext().setApiKey(key);
    }
    
    /**
     * Creates a new {@link ApiKey}
     * @param apiKey
     */
    public static ApiKey create(String apiKey) {
        return new ApiKey(apiKey);
    }
    
    /**
     * Returns the {@link GeoApiContext}
     * @return
     */
    GeoApiContext context() {
        return context;
    }
}
