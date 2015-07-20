package bm.view;

import bm.controller.GameWorldController;
import bm.model.GameWorld;
import bm.model.actors.PlayerActor;
import bm.viewcontroller.BrickViewController;
import bm.viewcontroller.PlayerActorViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static bm.viewcontroller.ViewConstants.FIELD_WORLD_SIZE;

/**
 * Class is responsible for drawing all objects in game, update them and put off from the game
 * world pane. After that this class support detect collisions and with using BlockingQue sends
 * information about collisions to controller (GameWorldController).
 */
@Component
public class GameWorldView {

    @Autowired
    GameWorldController gameWorldController;

    @Autowired
    GameWorld gameWorld;

    @Autowired
    PlayerActor playerActor;

    private static final Logger LOGGER = LoggerFactory.getLogger(GameWorldView.class);

    /**
     * Queue for sending information's about intersects from view to controller.
     */

    /**
     * Collection of all mutable objects views in game.
     */
    private final ObservableList<Node> mutableObjectViews = FXCollections.observableArrayList();

//    private final ObservableMap<UUID, Node> chuj = FXCollections.observableHashMap();

    /**
     * Collection of all immutable objects views in game.
     */
    private final ObservableList<Node> immutableObjectsViews = FXCollections.observableArrayList();

    private final ObservableList<NodePair> intersections = FXCollections.observableArrayList();


    /**
     * Initialize GameWorldView
     */
    public void initialize() {
        drawBackground();
        drawSolidBlocks();
        drawCrates();
        drawActors();
    }

    /**
     * @param node actor sprite
     */
    public void addActorView(@NotNull final Node node) {
        mutableObjectViews.add(node);
        gameWorldController.getGameWorldPane().getChildren().add(node);
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

            //TODO: intersects
            rectangle.setId("solidBlock");
            immutableObjectsViews.add(rectangle);
        });
    }

    /**
     * Draw crates.
     */
    private void drawCrates() {
        gameWorld.getLevel().mutableObstacleMap.forEach((apexPosition, mutableObstacle) -> {
            BrickViewController brickViewController = new BrickViewController(mutableObstacle);
            gameWorldController.getGameWorldPane().getChildren().add(brickViewController);

            //TODO: intersects
//            brickViewController.setId("crates");
        });
    }

    /**
     * Draw actors
     */
    private void drawActors() {
        PlayerActorViewController playerActorViewController = new PlayerActorViewController
                (playerActor);
        mutableObjectViews.add(playerActorViewController);


        mutableObjectViews.forEach(node -> {
            gameWorldController.getGameWorldPane().getChildren().add(node);
            node.toFront();
        });

        immutableObjectsViews.forEach(node -> {
            gameWorldController.getGameWorldPane().getChildren().add(node);
            node.toFront();
        });
    }

    /**
     * Update the list of intersections.
     */
    private void testIntersections() {
        intersections.clear();

        /** For each mutable object view test it's intersection with all other views. */
        for (Node source : mutableObjectViews) {
            /** Firstly check intersect source with other mutble objects view. */
            for (Node destination : mutableObjectViews) {
                NodePair nodePair = new NodePair(source, destination);
                if (!intersections.contains(nodePair) && nodePair.intersects())
                    intersections.add(nodePair);
            }
            //TODO: Maybe I can remove this redundant code below. Check it.
            /** Secondly check intersect source with all immutable objects view. */
            for (Node destination : immutableObjectsViews) {
                NodePair nodePair = new NodePair(source, destination);
                if (!intersections.contains(nodePair) && nodePair.intersects())
                    intersections.add(nodePair);
            }
        }
    }

    /**
     * Check intersects and if exists any then send information to controller.
     */
    public void update() {
        LOGGER.debug("\nUPDATE");
        testIntersections();
        while (!intersections.isEmpty()) {
            //TODO: Check it.
            NodePair nodePair = intersections.remove(0);


            LOGGER.debug(nodePair.toString());

        }


    }

}
