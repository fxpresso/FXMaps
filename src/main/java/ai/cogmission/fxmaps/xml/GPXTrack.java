package ai.cogmission.fxmaps.xml;

import com.google.gson.annotations.SerializedName;


public class GPXTrack {
    @SerializedName("trkseg")
    protected GPXTrackSegment trackSegment;
    
    @SerializedName("name")
    protected String name;
}
