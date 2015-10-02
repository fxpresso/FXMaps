package ai.cogmission.fxmaps.exception;

import ai.cogmission.fxmaps.model.MapStore;

/**
 * Thrown when an attempt to select a non-existent {@link PersistentMap}
 * is made.
 *  
 * @author cogmission
 * @see MapStore#selectMap(String)
 */
public class MapDoesNotExistException extends Exception {
    private static final long serialVersionUID = 1L;

    public MapDoesNotExistException(String message) {
        super(message);
    }
}
