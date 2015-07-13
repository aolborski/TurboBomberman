package bm.ui;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static bm.ui.BmState.MENU;

@Component
public class RootPaneController {

  @Autowired
  private MenuPaneController menuPaneController;

  @Autowired
  private InGamePaneController inGamePaneController;

  @Autowired
  private BmContext bmContext;

  @PostConstruct
  public void initialize() {

    bmContext.uiStateProperty().addListener((observable, currentState, newState) -> {
      NodeController currentStateController = stateController(currentState);
      NodeController newStateController = stateController(newState);

      currentStateController.getNode().setVisible(false);
      newStateController.getNode().setVisible(true);
    });
  }

  @NotNull
  private NodeController stateController(BmState state) {

    return state == MENU ? menuPaneController : inGamePaneController;
  }
}
