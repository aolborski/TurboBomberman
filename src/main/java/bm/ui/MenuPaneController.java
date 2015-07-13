package bm.ui;

import bm.controller.GameLoop;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static bm.ui.BmState.IN_GAME;

@Component
public class MenuPaneController implements NodeController {

    @Autowired
    private BmContext bmContext;

    @Autowired
    GameLoop gameLoop;

    @FXML
    private BorderPane menuPane;

    @NotNull
    @Override
    public Node getNode() {
        return menuPane;
    }

    @FXML
    public void onNewGame() {
        bmContext.uiStateProperty().setValue(IN_GAME);
        gameLoop.beginGameLoop();
    }

    @FXML
    public void onExit() {
        System.exit(0);
    }
}
