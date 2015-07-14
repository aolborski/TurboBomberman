package bm.viewcontroller;

import bm.model.actors.MovementDirection;
import bm.model.actors.PlayerActor;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import org.jetbrains.annotations.NotNull;

import static bm.model.actors.MovementDirection.NONE;
import static bm.viewcontroller.ViewConstants.FIELD_WORLD_SIZE;
import static javafx.scene.input.KeyCode.*;

public class PlayerActorViewController extends Group {

    @NotNull
    private ImageView imageView;

    public PlayerActorViewController(@NotNull final PlayerActor playerActor) {

        imageView = new ImageView(new Image("bm/images/Bomberman_White.png"));
        imageView.setFitHeight(FIELD_WORLD_SIZE);
        imageView.setFitWidth(FIELD_WORLD_SIZE);

        getChildren().add(imageView);
        this.setFocusTraversable(true);
        this.relocate(playerActor.getX() * FIELD_WORLD_SIZE, playerActor.getY() * FIELD_WORLD_SIZE);

        this.setOnKeyPressed(event -> {

            KeyCode code = event.getCode();

            MovementDirection movementDirection = MovementDirection.NONE;

            //TODO: Fix this.
            /**
             * code == UP is equivalent with MovementVector.DOWN because i probably fucked something up
             * with directions between JavaFX gamePane and my gameWorld.
             */
            if (code == UP)
                movementDirection = MovementDirection.DOWN;
            else if (code == RIGHT)
                movementDirection = MovementDirection.RIGHT;
            else if (code == DOWN)
                movementDirection = MovementDirection.UP;
            else if (code == LEFT)
                movementDirection = MovementDirection.LEFT;
            else if (code == SPACE)
                playerActor.plant();

            playerActor.setMovementDirection(movementDirection);

            event.consume();
        });

        this.setOnKeyReleased(event -> playerActor.setMovementDirection(NONE));

        playerActor.positionProperty().addListener((observable, oldValue, newValue) -> {
            relocate(this.getLayoutX() + playerActor.getMovementVector().getX(),
                    this.getLayoutY() + playerActor.getMovementVector().getY());
        });
    }
}
