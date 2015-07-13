package bm.viewcontroller;

import bm.model.level.obstacle.MutableObstacle;
import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import static bm.viewcontroller.ViewConstants.*;

public class BrickView extends ImageView {

  public BrickView(@NotNull final MutableObstacle obstacle) {

    setImage(new Image("bm/images/brick01.png"));

    setFitHeight(FIELD_WORLD_SIZE);
    setFitWidth(FIELD_WORLD_SIZE);

    relocate(obstacle.getX() * FIELD_WORLD_SIZE, obstacle.getY() * FIELD_WORLD_SIZE);

    obstacle.isDestroyedProperty().addListener((observable, oldValue, newValue) -> {

      setImage(new Image("bm/images/explosion01.png"));

      FadeTransition ft = new FadeTransition(Duration.millis(500), this);
      ft.setFromValue(1.0);
      ft.setToValue(0.0);
      ft.setCycleCount(1);
      ft.play();
    });
  }
}
