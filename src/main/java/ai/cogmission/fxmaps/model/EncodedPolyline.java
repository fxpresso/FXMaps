package ai.cogmission.fxmaps.model;

import java.util.List;
import java.util.stream.Collectors;

import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.LatLng;

/**
 * Encoded Polylines are used by the Google Maps API to represent paths.
 * 
 * @author cogmission
 */
public class EncodedPolyline extends com.google.maps.model.EncodedPolyline {
    private final String points;
    
    public EncodedPolyline(com.google.maps.model.EncodedPolyline line) {
        super(line.getEncodedPath());
        this.points = line.getEncodedPath();
    }
    
    public EncodedPolyline(String encodedPoints) {
        super(encodedPoints);
        this.points = getEncodedPath();
    }
    
    /**
     * @param points A path as a collection of {@code LatLng} points.
     */
    public EncodedPolyline(List<LatLon> points) {
        super(
            points.stream()
            .map(ll -> { return new LatLng(ll.latitude(), ll.longitude()); })
            .collect(Collectors.toList()));
        
        this.points = getEncodedPath();
    }

    public List<LatLng> decodePath() {
        return PolylineEncoding.decode(points);
    }
}
