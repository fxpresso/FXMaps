package ai.cogmission.fxmaps.model;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * A representation of ticket cost for use on public transit.
 *
 * See <a href="https://developers.google.com/maps/documentation/directions/#Routes">the Routes
 * Documentation</a> for more detail.
 * 
 * @author cogmission
 */
public class Fare {
    private com.google.maps.model.Fare fare;
    
    /**
     * Creates a new {@code Fare}
     * @param fare  the google maps api version
     */
    public Fare(com.google.maps.model.Fare fare) {
        this.fare = fare;
    }
    
    /**
     * Returns the {@link Currency} indicating the currency that the amount is expressed in.
     * @return  the currency
     */
    public Currency getCurrency() {
        return fare.currency;
    }
    
    /**
     * Returns the total fare amount, in the currency specified in {@link #currency}.
     * @return  the total fare amount
     */
    public BigDecimal getValue() {
        return fare.value;
    }
}
