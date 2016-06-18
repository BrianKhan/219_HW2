/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mv.gui;

import java.util.ArrayList;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;
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
        fixLayout();
    }

    public void fixLayout() {

        Scale scale = new Scale();

        scale.setX(1);
        scale.setY(-1);
        scale.pivotYProperty().bind(Bindings.createDoubleBinding(()
                -> workspace.getBoundsInLocal().getMinY() + workspace.getBoundsInLocal().getHeight() / 2,
                workspace.boundsInLocalProperty()));
        workspace.getTransforms().add(scale);
    }

    public void layoutMap() {
        DataManager dataManager = (DataManager) app.getDataComponent();
        Group g = new Group();

        for (int i = 0; i < dataManager.getItems().size(); i++) {
            ArrayList<Double[]> listy = dataManager.getItems().get(i).get();
            Polygon myGon = new Polygon();

            for (int j = 0; j < listy.size(); j++) {
                Double[] myAr = listy.get(j);
                myGon.getPoints().addAll(myAr);
                System.out.println("added: " + myAr[0] + " AND " + myAr[1]);

            }
            myGon.setFill(Paint.valueOf("#006400"));
            workspace.getChildren().add(myGon);
            app.getGUI().getAppPane().setCenter(workspace);
        }
        // workspace.setBackground(new Background(new BackgroundFill(Paint.valueOf("#add8e6"), CornerRadii.EMPTY, Insets.EMPTY)));

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
