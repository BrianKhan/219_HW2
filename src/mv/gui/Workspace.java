/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mv.gui;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import saf.components.AppWorkspaceComponent;
import mv.MapViewerApp;
import mv.data.DataManager;
import mv.file.FileManager;

/**
 *
 * @author McKillaGorilla
 */
public class Workspace extends AppWorkspaceComponent {

    MapViewerApp app;
    FlowPane temp;

    public Workspace(MapViewerApp initApp) {
        app = initApp;
        workspace = new Pane();
        // make sure save and new are removed
        removeExtra();
        //layoutMap();
    }

    public void layoutMap() {
        DataManager dataManager = (DataManager) app.getDataComponent();
        Group g = new Group();
        for (int i = 0; i < dataManager.getItems().size(); i++) {
            ArrayList<Double[]> listy = dataManager.getItems().get(i).get();
            for (int j = 0; j < listy.size(); j++) {
                Polygon myGon = new Polygon();
                myGon.getPoints().addAll(listy.get(j));
                g.getChildren().add(myGon);
            }

        }

        workspace.getScene().setRoot(g);

    }

    @Override
    public void reloadWorkspace() {
        FileManager temp = (FileManager) app.getFileComponent();
        if (temp.getLoaded()) {
            layoutMap();
        }

    }

    @Override
    public void initStyle() {
        DataManager dataManager = (DataManager) app.getDataComponent();
    }

    public void removeExtra() {
        FlowPane temp = (FlowPane) app.getGUI().getAppPane().getTop();
        temp.getChildren().remove(0);
        temp.getChildren().remove(1);
    }

}
