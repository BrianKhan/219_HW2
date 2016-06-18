/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mv.gui;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import saf.components.AppWorkspaceComponent;
import mv.MapViewerApp;

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

        temp.getChildren().remove(0);
        temp.getChildren().remove(1);
    }

    @Override
    public void reloadWorkspace() {
        app.getGUI().updateToolbarControls(true);
        FlowPane temp = (FlowPane) app.getGUI().getAppPane().getTop();

    }

    @Override
    public void initStyle() {

    }

}
