package mv.data;

import javafx.scene.layout.FlowPane;
import saf.components.AppDataComponent;
import mv.MapViewerApp;

/**
 *
 * @author McKillaGorilla
 */
public class DataManager implements AppDataComponent {
    MapViewerApp app;
    
    public DataManager(MapViewerApp initApp) {
        app = initApp;
    }
    
    public void addItem(RegionItem item) {
        
    }
    
    @Override
    public void reset() {
        FlowPane temp = (FlowPane)app.getGUI().getAppPane().getTop();
        temp.getChildren().get(0).setDisable(true);
    }
}
