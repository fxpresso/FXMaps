package ai.cogmission.fxmaps.model;

import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;

/**
 * Indicates the different Google Maps types.
 * 
 * @author Rob Terpilowski
 * @author cogmission
 */
public enum MapType {
    TERRAIN(MapTypeIdEnum.TERRAIN), 
    ROADMAP(MapTypeIdEnum.ROADMAP),
    SATELLITE(MapTypeIdEnum.SATELLITE),
    HYBRID(MapTypeIdEnum.HYBRID);
    
    private MapTypeIdEnum mtie;
    
    private MapType(MapTypeIdEnum e) {
        this.mtie = e;
    }
    
    @Override
    public String toString() {
        return mtie.toString();
    }
    
    /**
     * Returns the JavascriptEnum type.
     * 
     * @return
     */
    public MapTypeIdEnum convert() {
        return mtie;
    }
}
