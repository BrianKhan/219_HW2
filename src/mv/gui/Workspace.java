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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
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
    int counterZoom = 1;
    int counterRight = 0;
    int counterUp = 0;
    

    public Workspace(MapViewerApp initApp) {
        app = initApp;
        workspace = new Pane();
        // make sure save and new are removed
        removeExtra();
        //layoutMap();
        setupHandlers();

    }

    public void fixLayout() {

        Scale scale = new Scale();

        scale.setX(5.3);
        scale.setY(-5.3);
        //scale.setPivotX(app.getGUI().getWindow().getWidth() / 2);
        // scale.setPivotY(app.getGUI().getWindow().getHeight() / 2);

       // scale.pivotYProperty().bind(Bindings.createDoubleBinding(()
        //        -> workspace.getBoundsInLocal().getMinY() + workspace.getBoundsInLocal().getHeight() / 2,
        //        workspace.boundsInLocalProperty()));
        workspace.getTransforms().add(scale);
        workspace.setTranslateX(-10 +app.getGUI().getWindow().getWidth() / 2);
        workspace.setTranslateY(-55 +app.getGUI().getWindow().getHeight() / 2);
    }
    private void setupHandlers() {
    app.getGUI().getPrimaryScene().setOnMousePressed(e -> {
                zoom();
        });
    app.getGUI().getPrimaryScene().setOnKeyPressed(e -> {
        if(e.getCode().equals(KeyCode.RIGHT)) {
            moveRight();
        }
        if(e.getCode().equals(KeyCode.LEFT)) {
            moveLeft();
        }
        if(e.getCode().equals(KeyCode.UP)) {
            moveUp();
        }
        if(e.getCode().equals(KeyCode.DOWN)) {
            moveDown();
        }
    }
    );

    }
    public void zoom() {
        workspace.getTransforms().clear();
        Scale scale = new Scale();
        scale.setX(5.3+(counterZoom*.5));
        scale.setY(-5.3-(counterZoom*.5));
        counterZoom++;
        workspace.getTransforms().add(scale);
    }
    public void moveRight() {
        counterRight++;
        workspace.setTranslateX(-10 - counterRight  +app.getGUI().getWindow().getWidth() / 2);
        
    }
    public void moveLeft() {
       counterRight--;
        workspace.setTranslateX(-10 - counterRight  +app.getGUI().getWindow().getWidth() / 2);
        
    }
    public void moveUp() {
        counterUp++;
        workspace.setTranslateY(-55 +counterUp +app.getGUI().getWindow().getHeight() / 2);
    }
    public void moveDown() {
        counterUp--;
        workspace.setTranslateY(-55 +counterUp +app.getGUI().getWindow().getHeight() / 2);
    }

    public void layoutMap() {
        DataManager dataManager = (DataManager) app.getDataComponent();
        for (int i = 0; i < dataManager.getItems().size(); i++) {
            ArrayList<Double[]> listy = dataManager.getItems().get(i).get();
            Polygon myGon = new Polygon();
            for (int j = 0; j < listy.size(); j++) {
                Double[] myAr = listy.get(j);
              //   myAr[0] = myAr[0] + workspace.getBoundsInLocal().getWidth() / 2;
              //   myAr[1] = myAr[1] + workspace.getBoundsInLocal().getHeight() / 2;
                myGon.getPoints().addAll(myAr);
            }
            myGon.setFill(Paint.valueOf("#006400"));
            myGon.setStroke(Paint.valueOf("#000000"));
            myGon.setStrokeWidth(.01);
            workspace.getChildren().add(myGon);
            app.getGUI().getAppPane().setCenter(workspace);
            app.getGUI().getPrimaryScene().setFill(Paint.valueOf("#add8e6"));
        }
        

        // workspace.setBackground(new Background(new BackgroundFill(Paint.valueOf("#add8e6"), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    @Override
    public void reloadWorkspace() {
        FileManager temp = (FileManager) app.getFileComponent();
        if (temp.getLoaded()) {
            layoutMap();
            fixLayout();
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
