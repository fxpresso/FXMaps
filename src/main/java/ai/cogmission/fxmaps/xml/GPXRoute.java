package ai.cogmission.fxmaps.xml;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class GPXRoute {
    @SerializedName("rtept")
    public List<GPXRoutePoint> routePoints;
    
    @SerializedName("name")
    public String name;
}
