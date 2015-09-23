package ai.cogmission.fxmaps.model;

/**
 * Encapsulates location information obtained from the 
 * {@link Locator} utility.
 * 
 * @author cogmission
 * @see Locator
 */
public class Location {
    private String city;
    private String zipcode;
    private String country;
    private String timeZone;
    
    private Double latitude;
    private Double longitude;
    
    private Integer confidence;
    private Integer accuracyRadius;
    private Integer populationDensity;
    private Integer averageIncome;
    
    
    /**
     * Constructs a new {@code Location}
     * 
     * @param city
     * @param zipcode
     * @param country
     * @param timeZone
     * @param latitude
     * @param longitude
     * @param confidence
     * @param accuracyRadius
     * @param populationDensity
     * @param averageIncome
     */
    public Location(String city, String zipcode, String country, String timeZone, 
        Double latitude, Double longitude, Integer confidence, Integer accuracyRadius, 
            Integer populationDensity, Integer averageIncome) {
        
        this.city = city;
        this.zipcode = zipcode;
        this.country = country;
        this.timeZone = timeZone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.confidence = confidence;
        this.accuracyRadius = accuracyRadius;
        this.populationDensity = populationDensity;
        this.averageIncome = averageIncome;
    }

    public String getCity() {
        return city;
    }
    
    public String getZipcode() {
        return zipcode;
    }
    
    public String getCountry() {
        return country;
    }
    
    public String getTimeZone() {
        return timeZone;
    }
    
    public Double getLatitude() {
        return latitude;
    }
    
    public Double getLongitude() {
        return longitude;
    }
    
    public Integer getConfidence() {
        return confidence;
    }
    
    public Integer getAccuracyRadius() {
        return accuracyRadius;
    }
    
    public Integer getPopulationDensity() {
        return populationDensity;
    }
    
    public Integer getAverageIncome() {
        return averageIncome;
    }
    
}
