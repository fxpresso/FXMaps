package ai.cogmission.fxmaps;

import ai.cogmission.fxmaps.ui.MapPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Reference implementation for the FXMaps library.
 * 
 * @author cogmission
 *
 */
public class RefImpl extends Application {
    private MapPane mapPane;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        mapPane = new MapPane();
        mapPane.setPrefWidth(1000);
        mapPane.setPrefHeight(780);
        mapPane.setDirectionsVisible(true);
        
        Scene scene = new Scene(mapPane, 1000, 780);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("java.net.useSystemProxies", "true");
        launch(args);
    }
}
