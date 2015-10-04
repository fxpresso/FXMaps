package ai.cogmission.fxmaps.xml;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class GPXTrackSegment {
    @SerializedName("trkpt")
    protected List<GPXTrackPoint> trackPoints;
}
