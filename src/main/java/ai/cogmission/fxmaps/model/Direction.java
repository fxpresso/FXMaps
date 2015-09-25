package ai.cogmission.fxmaps.model;

import ai.cogmission.fxmaps.ui.DirectionsPane;

/**
 * Designates the pixel location of a composite image containing
 * directional icons used for the {@link DirectionsPane}.
 * 
 * @author cogmission
 *
 */
public enum Direction {
    LEFT(825), RIGHT(963), SLIGHT_LEFT(753), SLIGHT_RIGHT(103), HEAD(1064), 
    UTURN_LEFT(607), UTURN_RIGHT(71), MERGE(283), FORK_LEFT(1099, 33, 33), FORK_RIGHT(999),
    RAMP_LEFT(34), RAMP_RIGHT(859), SHARP_LEFT(0), SHARP_RIGHT(1168, 33, 33), ROUND_LEFT(463), ROUND_RIGHT(391);
    
    private int index;
    private int w;
    private int h;
    
    private Direction(int index) {
        this(index, 36, 36);
    }
    
    private Direction(int index, int w, int h) {
        this.index = index;
        this.w = w;
        this.h = h;
    }
    
    public int index() {
        return index;
    }
    
    public int w() { return w; }
    public int h() { return h; }
    
    public static Direction forText(String s) {
        s = s.toLowerCase();
        //U-Turn special case:
        if(s.indexOf("u-turn") != -1) {
            if(s.indexOf("right") != -1) return UTURN_RIGHT;
            return UTURN_LEFT;
        }
        
        for(Direction d : values()) {
            String[] sa = d.toString().toLowerCase().split("\\_");
            boolean isMatch = true;
            for(String str : sa) {
                if(s.indexOf(str) == -1) {
                    isMatch = false;
                    break;
                }
            }
            if(isMatch) {
                return d;
            }
        }
        return null;
    }
}
