package ai.cogmission.fxmaps.xml;

import com.google.gson.annotations.SerializedName;


public class GPXRoutePoint {
    @SerializedName("@lat")
    public double lat;
    
    @SerializedName("@lon")
    public double lon;
    
    @SerializedName("ele")
    public double ele;
    
    @SerializedName("name")
    public String name;
}
