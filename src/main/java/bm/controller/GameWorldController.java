package bm.controller;

import bm.model.ApexPosition;
import bm.model.Bomb;
import bm.model.GameWorld;
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
 * Class which manages between model (GameWorld) and view.
 * <p/>
 * The most important thing is gameWorldPane. For a while i think that this field should be in
 * class GameWorldView but then in GameWorldPane.fxml written fx:controller="bm.view.GameWorldView".
 */
@Component
public class GameWorldController {

    @FXML
    private Pane gameWorldPane;

    @Autowired
    GameWorld gameWorld;

    @Autowired
    GameWorldView gameWorldView;

    private static final Logger LOG = LoggerFactory.getLogger(GameWorldController.class);

    @FXML
    public void initialize() {
        initializeGame();
    }

    /**
     * Initialize game model (GameWorld) and game view (GameWorldView), add enemies to game.
     */
    private void initializeGame() {
        gameWorld.initialize();
        gameWorldView.initialize();

        gameWorld.getEnemies().forEach(enemyActor ->
                gameWorldView.addActorView(new EnemyActorViewController(enemyActor)));
    }

    /**
     * Update model and view. After that handle collision.
     */
    public void update() {
        gameWorld.update();
        handleCollision();
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
    private void handleCollision() {
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

    /**
     * Create bomb on apex position and add listener so that handle explosion in properly moment.
     *
     * @param apexPosition center position of explosion
     */
    public void addBombToGame(@NotNull final ApexPosition apexPosition) {
        Bomb bomb = new Bomb(apexPosition);
        gameWorld.bombs.add(bomb);
        gameWorldView.addActorView(new BombViewController(bomb));
        bomb.isDestroyedProperty().addListener(observable -> handleExplosion(bomb));
    }

    /**
     * Destroy all object in range of explosion.
     *
     * @param bomb this is exploding
     */
    private void handleExplosion(@NotNull final Bomb bomb) {
        LOG.debug("handle explosion on apexPosition = " + bomb.getPosition().toString());
        Set<ApexPosition> apexPositionSet = setRangeOfExplosion(bomb.getPosition());

        apexPositionSet.forEach(apexPosition1 -> {
            if (gameWorld.getLevel().mutableObstacleMap.containsKey(apexPosition1))
                gameWorld.getLevel().mutableObstacleMap.get(apexPosition1).destroy();
            else {
                gameWorld.getEnemies().forEach(enemyActor -> {
                    if (enemyActor.getPosition().equals(apexPosition1)) {
                        enemyActor.destroy();
                        gameWorld.removeEnemy(enemyActor);
                    }
                });
            }
        });

        gameWorld.bombs.remove(bomb);
    }

    /**
     * @param apexPosition center position of explosion
     * @return Set of apex position which contains in range of explosion
     */
    private Set<ApexPosition> setRangeOfExplosion(@NotNull final ApexPosition apexPosition) {
        Set<ApexPosition> apexPositionSet = new HashSet<>();

        apexPositionSet.addAll(addRangeExplosionInOneDirection(apexPosition, 1, 0));
        apexPositionSet.addAll(addRangeExplosionInOneDirection(apexPosition, 0, 1));
        apexPositionSet.addAll(addRangeExplosionInOneDirection(apexPosition, -1, 0));
        apexPositionSet.addAll(addRangeExplosionInOneDirection(apexPosition, 0, -1));

        return apexPositionSet;
    }

    /**
     * @param apexPosition center position of explosion
     * @param x one of coordinates of explosion direction
     * @param y one of coordinates of explosion direction
     * @return Set of apex position which contains in explosion direction
     */
    private Set<ApexPosition> addRangeExplosionInOneDirection(@NotNull final ApexPosition
                                                                      apexPosition, final int x, final int y) {
        Set<ApexPosition> apexPositionSet = new HashSet<>();

        for (int k = 0; k <= RANGE_OF_EXPLOSION; k++) {
            ApexPosition apexPosition1 = new ApexPosition(apexPosition.getX() + k * x,
                    apexPosition.getY() + k * y);
            if (gameWorld.getLevel().immutableObstacleMap.containsKey(apexPosition1))
                break;
            else
                apexPositionSet.add(apexPosition1);
        }

        return apexPositionSet;
    }
}
