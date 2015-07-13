package bm.viewcontroller;

import bm.model.actors.EnemyActor;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import static bm.viewcontroller.ViewConstants.FIELD_WORLD_SIZE;

public class EnemyActorViewController extends Group {

  public EnemyActorViewController(@NotNull final EnemyActor enemyActor) {

      ImageView imageView = new ImageView(new Image("bm/images/Bomberman_Balloom.png"));
      imageView.setFitHeight(FIELD_WORLD_SIZE);
      imageView.setFitWidth(FIELD_WORLD_SIZE);

      getChildren().add(imageView);

      relocate(enemyActor.getX() * FIELD_WORLD_SIZE, enemyActor.getY() * FIELD_WORLD_SIZE);

    enemyActor.positionProperty().addListener((observable, oldValue, newValue) -> {
      relocate(newValue.getX(), newValue.getY());
    });

  }
}
