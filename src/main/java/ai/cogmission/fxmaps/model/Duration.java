package ai.cogmission.fxmaps.model;

/**
 * The duration component for Directions API results.
 * 
 * @author cogmission
 */
public class Duration {
    private com.google.maps.model.Duration duration;
    
    /**
     * Constructs a new Duration
     * @param duration
     */
    public Duration(com.google.maps.model.Duration duration) {
        this.duration = duration;
    }
    
    /**
     * This is the numeric duration, in seconds. This is intended to be used only in
     * algorithmic situations, e.g. sorting results by some user specified metric.
     */
    public long getSeconds() {
        return duration.inSeconds;
    }
    
    /**
     * This is the human friendly duration. Use this for display purposes.
     */
    public String getHumanReadable() {
        return duration.humanReadable;
    }
    
    @Override
    public String toString() {
        return duration.humanReadable;
    }
}
