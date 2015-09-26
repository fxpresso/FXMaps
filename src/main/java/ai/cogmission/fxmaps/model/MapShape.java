package ai.cogmission.fxmaps.model;


/**
 * Base class of all shapes added to the {@link Map}
 * 
 * @author cogmission
 */
public abstract class MapShape {
    
    /**
     * Returns the shape converted to the GMapsFX model
     * 
     * @return  the shape converted to the GMapsFX model
     */
    public abstract com.lynden.gmapsfx.javascript.object.MapShape convert();
    
}
