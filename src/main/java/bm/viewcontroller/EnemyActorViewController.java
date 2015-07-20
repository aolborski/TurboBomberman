package bm.viewcontroller;

import bm.model.actors.EnemyActor;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import static bm.viewcontroller.ViewConstants.FIELD_WORLD_SIZE;

public class EnemyActorViewController extends Group {

    public EnemyActorViewController(@NotNull final EnemyActor enemyActor) {
        ImageView imageView = new ImageView(new Image("bm/images/Bomberman_Enemy_1.png"));
        imageView.setFitHeight(0.9 * FIELD_WORLD_SIZE);
        imageView.setFitWidth(0.9 * FIELD_WORLD_SIZE);

        // useful in detecting intersects
        imageView.setId("enemyActor");

        getChildren().add(imageView);
        relocate(enemyActor.getX() * FIELD_WORLD_SIZE, enemyActor.getY() * FIELD_WORLD_SIZE);

        enemyActor.positionProperty().addListener((observable, oldValue, newValue) -> {
            relocate(newValue.getX(), newValue.getY());
        });

        enemyActor.isDestroyedProperty().addListener((observable, oldValue, newValue) -> {
            imageView.setImage(new Image("bm/images/Bomberman_Enemy_4.png"));

            FadeTransition ft = new FadeTransition(Duration.millis(1000), this);
            ft.setFromValue(1.0);
            ft.setToValue(0.5);
            ft.setCycleCount(1);
            ft.play();
            ft.onFinishedProperty().setValue(event -> getChildren().clear());
        });
    }
}
