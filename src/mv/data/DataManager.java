package mv.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.FlowPane;
import saf.components.AppDataComponent;
import mv.MapViewerApp;
import mv.gui.Workspace;
import saf.AppTemplate;

/**
 *
 * @author McKillaGorilla
 */
public class DataManager implements AppDataComponent {
    MapViewerApp app;
    ObservableList<RegionItem> items;
    Workspace workspace;
    
    public DataManager(MapViewerApp initApp) {
        app = initApp;
        items = FXCollections.observableArrayList();
    }
    
    public void addItem(RegionItem item) {
        items.add(item);
    }
    public ObservableList<RegionItem> getItems() {
	return items;
    }
    @Override
    public void reset() {
        FlowPane temp = (FlowPane)app.getGUI().getAppPane().getTop();
        temp.getChildren().get(0).setDisable(true);
        items.clear();
    }
}
