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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
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
    int counterZoom = 0;
    int counterRight = 0;
    int counterUp = 0;
    int debug = 0;
    int xloc = 0;
    int yloc = 0;

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
        workspace.getTransforms().add(scale);
        workspace.setTranslateX(-10 + app.getGUI().getWindow().getWidth() / 2);
        workspace.setTranslateY(6 + app.getGUI().getWindow().getHeight() / 2);
    }

    private void setupHandlers() {
        app.getGUI().getPrimaryScene().setOnMousePressed(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                zoomIn(e);
            }
            if (e.getButton().equals(MouseButton.SECONDARY)) {
                zoomOut();
            }
        });

        app.getGUI().getPrimaryScene().setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.RIGHT)) {
                moveRight();
            }
            if (e.getCode().equals(KeyCode.LEFT)) {
                moveLeft();
            }
            if (e.getCode().equals(KeyCode.UP)) {
                moveUp();
            }
            if (e.getCode().equals(KeyCode.DOWN)) {
                moveDown();
            }
        }
        );

    }

    public void zoomIn(MouseEvent e) {
        int xloc = (int) (e.getX() - app.getGUI().getWindow().getWidth() / 2);
        int yloc = (int) (e.getY() - app.getGUI().getWindow().getHeight() / 2);
        counterRight = counterRight + xloc;
        counterUp = counterUp - yloc;
        workspace.setTranslateX(-10 - counterRight + app.getGUI().getWindow().getWidth() / 2);
        workspace.setTranslateY(6 + counterUp + app.getGUI().getWindow().getHeight() / 2);
        workspace.getTransforms().clear();
        Scale scale = new Scale();
        counterZoom++;
        scale.setX(5.3 + (counterZoom * .5));
        scale.setY(-5.3 - (counterZoom * .5));
        workspace.getTransforms().add(scale);
    }

    public void zoomOut() {
        workspace.getTransforms().clear();
        Scale scale = new Scale();
        counterZoom--;
        scale.setX(5.3 + (counterZoom * .5));
        scale.setY(-5.3 - (counterZoom * .5));

        workspace.getTransforms().add(scale);
    }

    public void moveRight() {
        counterRight = counterRight + 5;
        workspace.setTranslateX(-10 - counterRight + app.getGUI().getWindow().getWidth() / 2);

    }

    public void moveLeft() {
        counterRight = counterRight - 5;
        workspace.setTranslateX(-10 - counterRight + app.getGUI().getWindow().getWidth() / 2);

    }

    public void moveUp() {
        counterUp = counterUp + 5;
        workspace.setTranslateY(6 + counterUp + app.getGUI().getWindow().getHeight() / 2);
    }

    public void moveDown() {
        counterUp = counterUp - 5;
        workspace.setTranslateY(6 + counterUp + app.getGUI().getWindow().getHeight() / 2);
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
            app.getGUI().getPrimaryScene().setFill(Paint.valueOf("#add8e6"));
        }
        makeLines();
        app.getGUI().getAppPane().setStyle("-fx-background-color: blue;");

    }

    public void makeLines() {
        Line equator = new Line();
        equator.setStartX(0);
        equator.setStartY(-2000);
        equator.setEndX(0);
        equator.setEndY(2000);
        workspace.getChildren().add(equator);
    }

    @Override
    public void reloadWorkspace() {
        DataManager dataManager = (DataManager) app.getDataComponent();

        workspace.getTransforms().clear();
        workspace.getChildren().clear();
        int counterZoom = 0;
        int counterRight = 0;
        int counterUp = 0;
        int debug = 0;
        int xloc = 0;
        int yloc = 0;
        System.out.println("reload called");
        // workspace = new Pane();
        layoutMap();
        fixLayout();

    }

    @Override
    public void initStyle() {
        DataManager dataManager = (DataManager) app.getDataComponent();
    }

    public void removeExtra() {
        if (!workspaceActivated) {
            FlowPane temp = (FlowPane) app.getGUI().getAppPane().getTop();
            temp.getChildren().remove(0);
            temp.getChildren().remove(1);
        }
    }

    @Override
    public void activateWorkspace(BorderPane appPane) {
        if (!workspaceActivated) {

            // PUT THE WORKSPACE IN THE GUI
            appPane.setCenter(workspace);
            workspaceActivated = true;
            workspace.toBack();
        }
    }

}
