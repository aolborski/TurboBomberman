package bm.viewcontroller;

import bm.model.Bomb;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import static bm.viewcontroller.ViewConstants.FIELD_WORLD_SIZE;

public class BombViewController extends Group {

    public BombViewController(@NotNull final Bomb bomb) {
        ImageView imageView = new ImageView(new Image("bm/images/bomb.png"));
        imageView.setFitHeight(FIELD_WORLD_SIZE);
        imageView.setFitWidth(FIELD_WORLD_SIZE);

        getChildren().add(imageView);

        relocate(bomb.getX() * FIELD_WORLD_SIZE, bomb.getY() * FIELD_WORLD_SIZE);

        bomb.isDestroyedProperty().addListener((observable, oldValue, newValue) -> {
            imageView.setImage(new Image("bm/images/explosion01.png"));

            FadeTransition ft = new FadeTransition(Duration.millis(500), this);
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            ft.setCycleCount(1);
            ft.play();
        });
    }
}
