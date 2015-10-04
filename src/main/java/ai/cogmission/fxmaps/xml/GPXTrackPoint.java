package ai.cogmission.fxmaps.xml;

import com.google.gson.annotations.SerializedName;

public class GPXTrackPoint {
    @SerializedName("@lat")
    protected double lat;
    @SerializedName("@lon")
    protected double lon;
    @SerializedName("ele")
    protected double ele;
    @SerializedName("time")
    protected String time;
    
    public double getLat() {
        return lat;
    }
    
    public double getLon() {
        return lon;
    }
}
