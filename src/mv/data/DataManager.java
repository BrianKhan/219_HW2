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
 * @author Brian Khaneyan
 */
public class DataManager implements AppDataComponent {

    // this data manager is a simple implementation that contains an observable list with an add and get function, as well as a reload function
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
        // we clear the list as well as disable some ui elements
        FlowPane temp = (FlowPane) app.getGUI().getAppPane().getTop();
        temp.getChildren().get(0).setDisable(true);
        items.clear();
    }
}
