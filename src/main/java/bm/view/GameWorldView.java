package bm.view;

import bm.controller.GameWorldController;
import bm.model.GameWorld;
import bm.model.actors.PlayerActor;
import bm.viewcontroller.BrickView;
import bm.viewcontroller.PlayerActorViewController;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static bm.viewcontroller.ViewConstants.FIELD_WORLD_SIZE;

/**
 * Class is responsible for drawing all objects in game, update them and put off from the game
 * world pane.
 */
@Component
public class GameWorldView {

    @Autowired
    GameWorldController gameWorldController;

    @Autowired
    GameWorld gameWorld;

    @Autowired
    PlayerActor playerActor;

    /**
     * Set of actor sprites.
     */
    private Set<Node> actorViews;

    /**
     * Initialize GameWorldView
     */
    public void initialize() {
        actorViews = new HashSet<>();
        drawBackground();
        drawSolidBlocks();
        drawCrates();
        drawActors();
    }

    /**
     * Draw background.
     */
    private void drawBackground() {
        Rectangle levelRectangle = new Rectangle(gameWorld.getLevel().getWidth() * FIELD_WORLD_SIZE,
                gameWorld.getLevel().getHeight() * FIELD_WORLD_SIZE);
        levelRectangle.setFill(new Color(0.8, 0.8, 0.8, 1.0));
        gameWorldController.getGameWorldPane().getChildren().add(levelRectangle);
    }

    /**
     * Draw solid blocks.
     */
    private void drawSolidBlocks() {
        gameWorld.getLevel().immutableObstacleMap.forEach((apexPosition, immutableObstacle) -> {
            Rectangle rectangle = new Rectangle(immutableObstacle.getX() * FIELD_WORLD_SIZE,
                    immutableObstacle.getY() * FIELD_WORLD_SIZE,
                    FIELD_WORLD_SIZE,
                    FIELD_WORLD_SIZE);
            rectangle.setFill(Color.CORAL);
            rectangle.setStroke(new Color(0.2, 0.2, 0.2, 1.0));
            rectangle.setStrokeWidth(FIELD_WORLD_SIZE / 20);
            gameWorldController.getGameWorldPane().getChildren().add(rectangle);
        });
    }

    /**
     * Draw crates.
     */
    private void drawCrates() {
        gameWorld.getLevel().mutableObstacleMap.forEach((apexPosition, mutableObstacle) ->
                gameWorldController.getGameWorldPane().getChildren().add(new BrickView(mutableObstacle)));
    }

    /**
     * Draw actors
     */
    private void drawActors() {
        actorViews.add(new PlayerActorViewController(playerActor));

        actorViews.forEach(node -> {
            gameWorldController.getGameWorldPane().getChildren().add(node);
            node.toFront();
        });
    }

    /**
     * @param node actor sprite
     */
    public void addActorView(@NotNull final Node node) {
        actorViews.add(node);
        gameWorldController.getGameWorldPane().getChildren().add(node);
    }
}
