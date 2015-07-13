package bm.ui;

import bm.controller.GameLoop;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static bm.ui.BmState.MENU;

@Component
public class InGamePaneController implements NodeController {

    @Autowired
    private BmContext bmContext;

    @Autowired
    GameLoop gameLoop;

    @FXML
    private Pane inGamePane;

    /**
     * Spring Framework requires this method.
     */
    @FXML
    public void initialize() {

    }

    @FXML
    public void onBackToMenu() {
        bmContext.uiStateProperty().setValue(MENU);
        gameLoop.stopGameLoop();
    }

    @NotNull
    @Override
    public Node getNode() {
        return inGamePane;
    }
}