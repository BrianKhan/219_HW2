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
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
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
 * @author Brian Khaneyan
 */
public class Workspace extends AppWorkspaceComponent {

    MapViewerApp app;
    FlowPane temp;
    //counters for continuity of zoom and movement
    int counterZoom = 0;
    int counterRight = 0;
    int counterUp = 0;
    int debug = 0;
    int xloc = 0;
    int yloc = 0;
    boolean reloaded;
    //initialization of lines
    Line equator = new Line();
    Line meridian = new Line();
    //vertical lines
    Line first = new Line();
    Line second = new Line();
    Line third = new Line();
    Line fourth = new Line();
    Line fifth = new Line();
    Line sixth = new Line();
    Line seventh = new Line();
    Line eighth = new Line();
    Line ninth = new Line();
    Line tenth = new Line();
    Line eleventh = new Line();
    Line twelvth = new Line();
    //horizontal lines
    Line firsty = new Line();
    Line secondy = new Line();
    Line thirdy = new Line();
    Line fourthy = new Line();
    Line fifthy = new Line();
    Line sixthy = new Line();
    boolean on = true;

    public Workspace(MapViewerApp initApp) {
        app = initApp;
        workspace = new Pane();
        // make sure save and new are removed
        removeExtra();
        //start handlers
        setupHandlers();

    }

    public void fixLayout() {
        //we apply the default view and move the camera to the appropriate place
        Scale scale = new Scale();
        scale.setX(5.3);
        scale.setY(-5.3);
        workspace.getTransforms().add(scale);
        workspace.setTranslateX(-10 + app.getGUI().getWindow().getWidth() / 2);
        workspace.setTranslateY(6 + app.getGUI().getWindow().getHeight() / 2);
    }

    private void setupHandlers() {
        //event handling
        app.getGUI().getPrimaryScene().setOnMouseClicked(e -> {
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
            if (e.getCode().equals(KeyCode.G)) {
                toggleLines();
            }
        }
        );

    }

    public void zoomIn(MouseEvent e) {
        //we grab the xlocation and ylocation in reference to our "center"
        xloc = (int) (e.getX() - app.getGUI().getWindow().getWidth() / 2);
        yloc = (int) (e.getY() - app.getGUI().getWindow().getHeight() / 2);
        //we move the screen to the clicked location
        counterRight = counterRight + xloc;
        counterUp = counterUp - yloc;
        workspace.setTranslateX(-10 - counterRight + app.getGUI().getWindow().getWidth() / 2);
        workspace.setTranslateY(6 + counterUp + app.getGUI().getWindow().getHeight() / 2);
        //we remove all zoom effects, and then apply a new zoom based on counterzoom
        workspace.getTransforms().clear();
        Scale scale = new Scale();
        //we increment counterZoom every time we zoom
        counterZoom++;
        scale.setX(5.3 + (counterZoom * .5));
        scale.setY(-5.3 - (counterZoom * .5));
        workspace.getTransforms().add(scale);
    }

    public void zoomOut() {
        //siimilar to zoomIn(), but we don't move the camera, and we decrement counterZoom
        workspace.getTransforms().clear();
        Scale scale = new Scale();
        counterZoom--;
        scale.setX(5.3 + (counterZoom * .5));
        scale.setY(-5.3 - (counterZoom * .5));
        workspace.getTransforms().add(scale);
    }

    public void moveRight() {
        //simply move the camera 5 pixels to the right
        counterRight = counterRight + 5;
        workspace.setTranslateX(-10 - counterRight + app.getGUI().getWindow().getWidth() / 2);

    }

    public void moveLeft() {
        //simply move the camera 5 pixels to the left
        counterRight = counterRight - 5;
        workspace.setTranslateX(-10 - counterRight + app.getGUI().getWindow().getWidth() / 2);

    }

    public void moveUp() {
        //simply move the camera 5 pixels to the up
        counterUp = counterUp + 5;
        workspace.setTranslateY(6 + counterUp + app.getGUI().getWindow().getHeight() / 2);
    }

    public void moveDown() {
        //simply move the camera 5 pixels to the down
        counterUp = counterUp - 5;
        workspace.setTranslateY(6 + counterUp + app.getGUI().getWindow().getHeight() / 2);
    }

    public void layoutMap() {
        //this is the initial load method for our polygons
        DataManager dataManager = (DataManager) app.getDataComponent();
        //we iterate over our arraylist of arrays and make a polygon for each entry in the arraylist
        for (int i = 0; i < dataManager.getItems().size(); i++) {
            ArrayList<Double[]> listy = dataManager.getItems().get(i).get();
            Polygon myGon = new Polygon();
            for (int j = 0; j < listy.size(); j++) {
                Double[] myAr = listy.get(j);
                myGon.getPoints().addAll(myAr);
            }
            //green fill, black outline
            myGon.setFill(Paint.valueOf("#21FF42"));
            myGon.setStroke(Paint.valueOf("#000000"));
            myGon.setStrokeWidth(.01);
            workspace.getChildren().add(myGon);
            //blue ocean
            app.getGUI().getPrimaryScene().setFill(Paint.valueOf("#add8e6"));
        }
        //we call our function to make the grid lines
        makeLines();
        //blue ocean
        app.getGUI().getAppPane().setStyle("-fx-background-color: #99d6ff;");

    }

    public void makeLines() {
        // There should be a better way of bulk making lines, but for this method we make the lines individually
        // we use -2000, and 2000 as our start and end values because that extends the map by a far amount
        //this is our equator
        equator.setFill(Paint.valueOf("#ffffff"));
        equator.setStroke(Paint.valueOf("#ffffff"));
        equator.setStartX(-2000);
        equator.setStartY(0);
        equator.setEndX(2000);
        equator.setEndY(0);
        // this is the prime meridian
        meridian.setFill(Paint.valueOf("#ffffff"));
        meridian.setStroke(Paint.valueOf("#ffffff"));
        meridian.setStartX(0);
        meridian.setStartY(-2000);
        meridian.setEndX(0);
        meridian.setEndY(2000);
        // these are the grid lines
        first.setFill(Paint.valueOf("#ffffff"));
        first.setStroke(Paint.valueOf("#ffffff"));
        first.setStartX(-179);
        first.setStartY(2000);
        first.setEndX(-179);
        first.setEndY(-2000);
        second.setFill(Paint.valueOf("#ffffff"));
        second.setStroke(Paint.valueOf("#ffffff"));
        second.setStartX(179.5);
        second.setStartY(2000);
        second.setEndX(179.5);
        second.setEndY(-2000);
        third.setFill(Paint.valueOf("#ffffff"));
        third.setStroke(Paint.valueOf("#ffffff"));
        third.setStartX(-150);
        third.setStartY(2000);
        third.setEndX(-150);
        third.setEndY(-2000);
        fourth.setFill(Paint.valueOf("#ffffff"));
        fourth.setStroke(Paint.valueOf("#ffffff"));
        fourth.setStartX(-120);
        fourth.setStartY(2000);
        fourth.setEndX(-120);
        fourth.setEndY(-2000);
        fifth.setFill(Paint.valueOf("#ffffff"));
        fifth.setStroke(Paint.valueOf("#ffffff"));
        fifth.setStartX(-90);
        fifth.setStartY(2000);
        fifth.setEndX(-90);
        fifth.setEndY(-2000);
        sixth.setFill(Paint.valueOf("#ffffff"));
        sixth.setStroke(Paint.valueOf("#ffffff"));
        sixth.setStartX(-60);
        sixth.setStartY(2000);
        sixth.setEndX(-60);
        sixth.setEndY(-2000);
        seventh.setFill(Paint.valueOf("#ffffff"));
        seventh.setStroke(Paint.valueOf("#ffffff"));
        seventh.setStartX(-30);
        seventh.setStartY(2000);
        seventh.setEndX(-30);
        seventh.setEndY(-2000);
        eighth.setFill(Paint.valueOf("#ffffff"));
        eighth.setStroke(Paint.valueOf("#ffffff"));
        eighth.setStartX(30);
        eighth.setStartY(2000);
        eighth.setEndX(30);
        eighth.setEndY(-2000);
        ninth.setFill(Paint.valueOf("#ffffff"));
        ninth.setStroke(Paint.valueOf("#ffffff"));
        ninth.setStartX(60);
        ninth.setStartY(2000);
        ninth.setEndX(60);
        ninth.setEndY(-2000);
        tenth.setFill(Paint.valueOf("#ffffff"));
        tenth.setStroke(Paint.valueOf("#ffffff"));
        tenth.setStartX(90);
        tenth.setStartY(2000);
        tenth.setEndX(90);
        tenth.setEndY(-2000);
        eleventh.setFill(Paint.valueOf("#ffffff"));
        eleventh.setStroke(Paint.valueOf("#ffffff"));
        eleventh.setStartX(120);
        eleventh.setStartY(2000);
        eleventh.setEndX(120);
        eleventh.setEndY(-2000);
        twelvth.setFill(Paint.valueOf("#ffffff"));
        twelvth.setStartX(150);
        twelvth.setStartY(2000);
        twelvth.setEndX(150);
        twelvth.setEndY(-2000);
        twelvth.setStroke(Paint.valueOf("#ffffff"));

        firsty.setFill(Paint.valueOf("#ffffff"));
        firsty.setStroke(Paint.valueOf("#ffffff"));
        firsty.setStartX(-2000);
        firsty.setStartY(30);
        firsty.setEndX(2000);
        firsty.setEndY(30);

        secondy.setFill(Paint.valueOf("#ffffff"));
        secondy.setStroke(Paint.valueOf("#ffffff"));
        secondy.setStartX(-2000);
        secondy.setStartY(60);
        secondy.setEndX(2000);
        secondy.setEndY(60);

        thirdy.setFill(Paint.valueOf("#ffffff"));
        thirdy.setStroke(Paint.valueOf("#ffffff"));
        thirdy.setStartX(-2000);
        thirdy.setStartY(90);
        thirdy.setEndX(2000);
        thirdy.setEndY(90);

        fourthy.setFill(Paint.valueOf("#ffffff"));
        fourthy.setStroke(Paint.valueOf("#ffffff"));
        fourthy.setStartX(-2000);
        fourthy.setStartY(-30);
        fourthy.setEndX(2000);
        fourthy.setEndY(-30);

        fifthy.setFill(Paint.valueOf("#ffffff"));
        fifthy.setStroke(Paint.valueOf("#ffffff"));
        fifthy.setStartX(-2000);
        fifthy.setStartY(-60);
        fifthy.setEndX(2000);
        fifthy.setEndY(-60);

        sixthy.setFill(Paint.valueOf("#ffffff"));
        sixthy.setStroke(Paint.valueOf("#ffffff"));
        sixthy.setStartX(-2000);
        sixthy.setStartY(-90);
        sixthy.setEndX(2000);
        sixthy.setEndY(-90);
        // we set all the grid lines as dashed lines
        twelvth.getStrokeDashArray().addAll(2d, 2d);
        eleventh.getStrokeDashArray().addAll(2d, 2d);
        tenth.getStrokeDashArray().addAll(2d, 2d);
        ninth.getStrokeDashArray().addAll(2d, 2d);
        eighth.getStrokeDashArray().addAll(2d, 2d);
        seventh.getStrokeDashArray().addAll(2d, 2d);
        sixth.getStrokeDashArray().addAll(2d, 2d);
        fifth.getStrokeDashArray().addAll(2d, 2d);
        fourth.getStrokeDashArray().addAll(2d, 2d);
        third.getStrokeDashArray().addAll(2d, 2d);
        second.getStrokeDashArray().addAll(2d, 2d);
        first.getStrokeDashArray().addAll(2d, 2d);
        sixthy.getStrokeDashArray().addAll(2d, 2d);
        fifthy.getStrokeDashArray().addAll(2d, 2d);
        fourthy.getStrokeDashArray().addAll(2d, 2d);
        thirdy.getStrokeDashArray().addAll(2d, 2d);
        secondy.getStrokeDashArray().addAll(2d, 2d);
        firsty.getStrokeDashArray().addAll(2d, 2d);
        // we make the grid lines smaller than the equator and meridian lines
        first.setStrokeWidth(.5);
        second.setStrokeWidth(.5);
        third.setStrokeWidth(.5);
        fourth.setStrokeWidth(.5);
        fifth.setStrokeWidth(.5);
        sixth.setStrokeWidth(.5);
        seventh.setStrokeWidth(.5);
        eighth.setStrokeWidth(.5);
        ninth.setStrokeWidth(.5);
        tenth.setStrokeWidth(.5);
        eleventh.setStrokeWidth(.5);
        twelvth.setStrokeWidth(.5);
        firsty.setStrokeWidth(.5);
        secondy.setStrokeWidth(.5);
        thirdy.setStrokeWidth(.5);
        fourthy.setStrokeWidth(.5);
        fifthy.setStrokeWidth(.5);
        sixthy.setStrokeWidth(.5);
        // we add all the gridlines
        workspace.getChildren().add(equator);
        workspace.getChildren().add(meridian);
        workspace.getChildren().add(first);
        workspace.getChildren().add(second);
        workspace.getChildren().add(third);
        workspace.getChildren().add(fourth);
        workspace.getChildren().add(fifth);
        workspace.getChildren().add(sixth);
        workspace.getChildren().add(seventh);
        workspace.getChildren().add(eighth);
        workspace.getChildren().add(ninth);
        workspace.getChildren().add(tenth);
        workspace.getChildren().add(eleventh);
        workspace.getChildren().add(twelvth);
        workspace.getChildren().add(firsty);
        workspace.getChildren().add(secondy);
        workspace.getChildren().add(thirdy);
        workspace.getChildren().add(fourthy);
        workspace.getChildren().add(fifthy);
        workspace.getChildren().add(sixthy);

    }

    public void toggleLines() {
        //we toggle a boolean and disable or enable the gridlines, default is grid lines on
        if (on) {
            workspace.getChildren().get(workspace.getChildren().indexOf(sixthy)).setVisible(false);
            workspace.getChildren().get(workspace.getChildren().indexOf(fifthy)).setVisible(false);
            workspace.getChildren().get(workspace.getChildren().indexOf(fourthy)).setVisible(false);
            workspace.getChildren().get(workspace.getChildren().indexOf(thirdy)).setVisible(false);
            workspace.getChildren().get(workspace.getChildren().indexOf(secondy)).setVisible(false);
            workspace.getChildren().get(workspace.getChildren().indexOf(firsty)).setVisible(false);
            workspace.getChildren().get(workspace.getChildren().indexOf(twelvth)).setVisible(false);
            workspace.getChildren().get(workspace.getChildren().indexOf(eleventh)).setVisible(false);
            workspace.getChildren().get(workspace.getChildren().indexOf(tenth)).setVisible(false);
            workspace.getChildren().get(workspace.getChildren().indexOf(ninth)).setVisible(false);
            workspace.getChildren().get(workspace.getChildren().indexOf(eighth)).setVisible(false);
            workspace.getChildren().get(workspace.getChildren().indexOf(seventh)).setVisible(false);
            workspace.getChildren().get(workspace.getChildren().indexOf(sixth)).setVisible(false);
            workspace.getChildren().get(workspace.getChildren().indexOf(fifth)).setVisible(false);
            workspace.getChildren().get(workspace.getChildren().indexOf(fourth)).setVisible(false);
            workspace.getChildren().get(workspace.getChildren().indexOf(third)).setVisible(false);
            workspace.getChildren().get(workspace.getChildren().indexOf(second)).setVisible(false);
            workspace.getChildren().get(workspace.getChildren().indexOf(first)).setVisible(false);
            workspace.getChildren().get(workspace.getChildren().indexOf(equator)).setVisible(false);
            workspace.getChildren().get(workspace.getChildren().indexOf(meridian)).setVisible(false);

            on = false;
        } else if (!on) {
            workspace.getChildren().get(workspace.getChildren().indexOf(sixthy)).setVisible(true);
            workspace.getChildren().get(workspace.getChildren().indexOf(fifthy)).setVisible(true);
            workspace.getChildren().get(workspace.getChildren().indexOf(fourthy)).setVisible(true);
            workspace.getChildren().get(workspace.getChildren().indexOf(thirdy)).setVisible(true);
            workspace.getChildren().get(workspace.getChildren().indexOf(secondy)).setVisible(true);
            workspace.getChildren().get(workspace.getChildren().indexOf(firsty)).setVisible(true);
            workspace.getChildren().get(workspace.getChildren().indexOf(twelvth)).setVisible(true);
            workspace.getChildren().get(workspace.getChildren().indexOf(eleventh)).setVisible(true);
            workspace.getChildren().get(workspace.getChildren().indexOf(tenth)).setVisible(true);
            workspace.getChildren().get(workspace.getChildren().indexOf(ninth)).setVisible(true);
            workspace.getChildren().get(workspace.getChildren().indexOf(eighth)).setVisible(true);
            workspace.getChildren().get(workspace.getChildren().indexOf(seventh)).setVisible(true);
            workspace.getChildren().get(workspace.getChildren().indexOf(sixth)).setVisible(true);
            workspace.getChildren().get(workspace.getChildren().indexOf(fifth)).setVisible(true);
            workspace.getChildren().get(workspace.getChildren().indexOf(fourth)).setVisible(true);
            workspace.getChildren().get(workspace.getChildren().indexOf(third)).setVisible(true);
            workspace.getChildren().get(workspace.getChildren().indexOf(second)).setVisible(true);
            workspace.getChildren().get(workspace.getChildren().indexOf(first)).setVisible(true);
            workspace.getChildren().get(workspace.getChildren().indexOf(equator)).setVisible(true);
            workspace.getChildren().get(workspace.getChildren().indexOf(meridian)).setVisible(true);
            on = true;
        }
    }

    @Override
    public void reloadWorkspace() {
        //we grab our dataManager and completely reset our workspace, note that we do not toggle the grid lines
        DataManager dataManager = (DataManager) app.getDataComponent();
        workspace.getTransforms().clear();
        workspace.getChildren().clear();
        counterZoom = 0;
        counterRight = 0;
        counterUp = 0;
        debug = 0;
        xloc = 0;
        yloc = 0;
        reloaded = true;
        // since this method runs after we have data loaded, we can put our initial render methods here
        layoutMap();
        fixLayout();
        reloaded = false;
    }

    @Override
    public void initStyle() {
        // there is no styling that needs to be done for this program
        DataManager dataManager = (DataManager) app.getDataComponent();
    }

    public void removeExtra() {
        //this method removes save and new
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
            // we make sure that there is no overflow
            workspace.toBack();
        }
    }

}
