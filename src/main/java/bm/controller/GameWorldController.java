package bm.controller;

import bm.model.ApexPosition;
import bm.model.Bomb;
import bm.model.GameWorld;
import bm.model.actors.PlayerActor;
import bm.view.GameWorldView;
import bm.viewcontroller.BombViewController;
import bm.viewcontroller.EnemyActorViewController;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static bm.Constants.RANGE_OF_EXPLOSION;

/**
 * Class which manages between model (GameWorld) and view. And the most important is that this
 * class has gameWorldPane.
 */
@Component
public class GameWorldController {

    @FXML
    private Pane gameWorldPane;

    @Autowired
    GameWorld gameWorld;

    @Autowired
    GameWorldView gameWorldView;

    private static final Logger LOG = LoggerFactory.getLogger(PlayerActor.class);

    @FXML
    public void initialize() {
        initializeGame();
    }

    /**
     * Initialize game model (GameWorld) and game view (GameWorldView)
     */
    public void initializeGame() {
        gameWorld.initialize();
        gameWorldView.initialize();

        gameWorld.getEnemies().forEach(enemyActor ->
                gameWorldView.addActorView(new EnemyActorViewController(enemyActor)));
    }


    /**
     * Update model and view.
     */
    public void update() {
        gameWorld.update();
//        gameWorldView.update();
    }

    /**
     * Handle detect collision.
     * <p/>
     * Firstly when enemyActor has collision with bound of strip or with
     * obstacles i must change him movement direction.
     * <p/>
     * Secondly when playerActor has collision with bound of strip or with obstacle i don't
     * change him position.
     * <p/>
     * Thirdly when enemyActor has collision with enemyActor i must kill playerActor and stopped
     * game.
     * <p/>
     * Fourthly when enemyActor has collision with explosion i must kill him.
     * <p/>
     * Fifthly when playerActor has collision with explosion i must kill him and stopped game.
     */
    public void handleCollision() {

        gameWorld.getEnemies().forEach(enemyActor -> {
            Set<ApexPosition> apexPositionSet = gameWorld.getLevel().getAllObstaclesPositions();

            if (apexPositionSet.contains(enemyActor.getNextPosition())) {
                LOG.debug("Collision detected");

                enemyActor.movementVector.setOppositeDirection();
            }
        });

    }


    public Pane getGameWorldPane() {
        return gameWorldPane;
    }


    public void addBombToGame(@NotNull final ApexPosition apexPosition) {
        Bomb bomb = new Bomb(apexPosition);
        gameWorld.addBomb(bomb);
        gameWorldView.addActorView(new BombViewController(bomb));

        //TODO: handleExplosion doesn't work properly
        bomb.isDestroyedProperty().addListener(observable -> handleExplosion(bomb.getPosition()));
    }

    /**
     * kill all object in range of explosion.
     *
     * @param apexPosition center position of explosion
     */
    public void handleExplosion(@NotNull final ApexPosition apexPosition) {
        Set<ApexPosition> apexPositionSet = new HashSet<>();

        for (int i = -RANGE_OF_EXPLOSION; i <= RANGE_OF_EXPLOSION; i++) {
            apexPositionSet.add(new ApexPosition(apexPosition.getX() + RANGE_OF_EXPLOSION,
                    apexPosition.getY()));
            apexPositionSet.add(new ApexPosition(apexPosition.getX(), apexPosition.getY() +
                    RANGE_OF_EXPLOSION));

        }

        apexPositionSet.forEach(explosionPosition ->
                gameWorld.getLevel().mutableObstacleMap.forEach((apexPosition2, mutableObstacle) -> {
                    if (mutableObstacle.getPosition().equals(explosionPosition))
                        gameWorld.getLevel().mutableObstacleMap.get(mutableObstacle.getPosition()).destroy();
                }));
    }
}
