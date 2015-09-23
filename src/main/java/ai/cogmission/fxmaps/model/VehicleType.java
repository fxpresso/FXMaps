package ai.cogmission.fxmaps.model;


public enum VehicleType {
    /**
     * Rail.
     */
    RAIL(com.google.maps.model.VehicleType.RAIL),

    /**
     * Light rail transit.
     */
    METRO_RAIL(com.google.maps.model.VehicleType.METRO_RAIL),

    /**
     * Underground light rail.
     */
    SUBWAY(com.google.maps.model.VehicleType.SUBWAY),

    /**
     * Above ground light rail.
     */
    TRAM(com.google.maps.model.VehicleType.TRAM),

    /**
     * Monorail.
     */
    MONORAIL(com.google.maps.model.VehicleType.MONORAIL),

    /**
     * Heavy rail.
     */
    HEAVY_RAIL(com.google.maps.model.VehicleType.HEAVY_RAIL),

    /**
     * Commuter rail.
     */
    COMMUTER_TRAIN(com.google.maps.model.VehicleType.COMMUTER_TRAIN),

    /**
     * High speed train.
     */
    HIGH_SPEED_TRAIN(com.google.maps.model.VehicleType.HIGH_SPEED_TRAIN),

    /**
     * Bus.
     */
    BUS(com.google.maps.model.VehicleType.BUS),

    /**
     * Intercity bus.
     */
    INTERCITY_BUS(com.google.maps.model.VehicleType.INTERCITY_BUS),

    /**
     * Trolleybus.
     */
    TROLLEYBUS(com.google.maps.model.VehicleType.TROLLEYBUS),

    /**
     * Share taxi is a kind of bus with the ability to drop off and pick up passengers anywhere on its
     * route.
     */
    SHARE_TAXI(com.google.maps.model.VehicleType.SHARE_TAXI),

    /**
     * Ferry.
     */
    FERRY(com.google.maps.model.VehicleType.FERRY),

    /**
     * A vehicle that operates on a cable, usually on the ground. Aerial cable cars may be of the type
     * {@code GONDOLA_LIFT}.
     */
    CABLE_CAR(com.google.maps.model.VehicleType.CABLE_CAR),

    /**
     * An aerial cable car.
     */
    GONDOLA_LIFT(com.google.maps.model.VehicleType.GONDOLA_LIFT),

    /**
     * A vehicle that is pulled up a steep incline by a cable. A Funicular typically consists of two
     * cars, with each car acting as a counterweight for the other.
     */
    FUNICULAR(com.google.maps.model.VehicleType.FUNICULAR),
    
    /**
     * All other vehicles will return this type.
     */
    OTHER(com.google.maps.model.VehicleType.OTHER);
    
    private com.google.maps.model.VehicleType type;
    
    private VehicleType(com.google.maps.model.VehicleType type) {
        this.type = type;
    }
    
    public com.google.maps.model.VehicleType convert() {
        return type;
    }
}
