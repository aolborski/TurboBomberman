package bm.viewcontroller;

import bm.model.level.obstacle.MutableObstacle;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import static bm.viewcontroller.ViewConstants.FIELD_WORLD_SIZE;


public class BrickViewController extends Group {

    public BrickViewController(@NotNull final MutableObstacle obstacle) {
        ImageView imageView = new ImageView(new Image("bm/images/brick01.png"));
        imageView.setFitHeight(0.9 * FIELD_WORLD_SIZE);
        imageView.setFitWidth(0.9 * FIELD_WORLD_SIZE);

        //useful in detecting intersects
        imageView.setId("brick");

        getChildren().add(imageView);
        relocate(obstacle.getX() * FIELD_WORLD_SIZE, obstacle.getY() * FIELD_WORLD_SIZE);

        obstacle.isDestroyedProperty().addListener((observable, oldValue, newValue) -> {
            imageView.setImage(new Image("bm/images/explosion01.png"));

            FadeTransition ft = new FadeTransition(Duration.millis(500), this);
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            ft.setCycleCount(1);
            ft.play();
            ft.onFinishedProperty().setValue(event -> getChildren().clear());
        });
    }
}
