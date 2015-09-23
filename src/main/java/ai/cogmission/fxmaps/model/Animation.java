package ai.cogmission.fxmaps.model;

/**
 * Enum to designate an animation type.
 * 
 * @author cogmission
 *
 */
public enum Animation {
    NULL("null"),
    BOUNCE("google.maps.Animation.BOUNCE"),
    DROP("google.maps.Animation.DROP");
    
    private com.lynden.gmapsfx.javascript.object.Animation animation;
    private String type;
    
    private Animation(String type) {
        this.type = type;
        
        switch(this.type) {
            case "null" : this.animation = com.lynden.gmapsfx.javascript.object.Animation.NULL;break;
            case "google.maps.Animation.BOUNCE" : this.animation =
                com.lynden.gmapsfx.javascript.object.Animation.BOUNCE; break;
            case "google.maps.Animation.DROP" : this.animation = 
                com.lynden.gmapsfx.javascript.object.Animation.DROP; break;
        }
    }
    
    /**
     * Internally used to convert this model type to a GMapsFX model type.
     * @return
     */
    public com.lynden.gmapsfx.javascript.object.Animation convert() {
        return animation;
    }
    
}
