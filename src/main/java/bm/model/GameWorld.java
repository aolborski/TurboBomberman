package bm.model;

import bm.model.actors.EnemyActor;
import bm.model.actors.Movable;
import bm.model.actors.PlayerActor;
import bm.model.level.Level;
import bm.model.level.LevelFactory;
import javafx.geometry.Point2D;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static bm.model.actors.MovementDirection.RIGHT;


@Component
public class GameWorld {

    @Autowired
    private PlayerActor playerActor;

    @Autowired
    private LevelFactory levelFactory;

    private Level level;

    private Set<EnemyActor> enemies;

    public LinkedList<Bomb> bombs;

    public void initialize() {
        enemies = new HashSet<>();
        bombs = new LinkedList<>();

        level = levelFactory.newLevel();

        EnemyActor enemyActor = new EnemyActor(new Point2D(1, 1), RIGHT);
        enemies.add(enemyActor);

    }

    public Set<EnemyActor> getEnemies() {
        return enemies;
    }

    @NotNull
    public Level getLevel() {
        return level;
    }

    /**
     * Update all objects in game (actors, crates, ...).
     */
    public void update() {

        level.mutableObstacleMap.forEach(((apexPosition, mutableObstacle) ->
                mutableObstacle.isDestroyedProperty().addListener((observable, oldValue,
                                                                   newValue) -> {
                    level.mutableObstacleMap.remove(mutableObstacle.getPosition());
                })
        ));

        playerActor.move();
        enemies.forEach(Movable::move);
    }

//
//    public void addBomb(@NotNull final Bomb bomb) {
//        bombs.add(bomb);
//    }
//
//    public void removeBomb(@NotNull final Bomb bomb) {
//        bombs.remove(bomb);
//    }

    public void removeEnemy(@NotNull final EnemyActor enemyActor) {
        enemies.remove(enemyActor);
    }
}
