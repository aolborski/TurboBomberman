package bm.model.level;

import bm.error.BmDataException;
import bm.model.ApexPosition;
import bm.model.level.obstacle.ImmutableObstacle;
import bm.model.level.obstacle.MutableObstacle;
import bm.model.level.obstacle.Obstacle;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Level has static objects of game. Obstacles which i don't have influence (like a solid blocks)
 * and removable crates.
 */
public class Level {

    /**
     * Obstacles which can be removed like a crate.
     */
    @NotNull
    public final Map<ApexPosition, MutableObstacle> mutableObstacleMap;

    /**
     * Obstacles which can't be removed or change like a solid blocks on center of the strip.
     */
    @NotNull
    public final Map<ApexPosition, ImmutableObstacle> immutableObstacleMap;

    /**
     * Parameters of level, height and width
     */
    private final int height;

    private final int width;

    /**
     * Constructor
     *
     * @param obstacles set of obstacles
     * @param height    height of level
     * @param width     width of level
     */
    public Level(@NotNull final Set<Obstacle> obstacles, final int height, final int width) {
        this.mutableObstacleMap = new HashMap<>();
        this.immutableObstacleMap = new HashMap<>();

        this.height = height;
        this.width = width;

        obstacles.forEach(obstacle -> {
            if (obstacle.getClass().isAssignableFrom(MutableObstacle.class))
                mutableObstacleMap.put(obstacle.getPosition(), (MutableObstacle) obstacle);
            else if (obstacle.getClass().isAssignableFrom(ImmutableObstacle.class))
                immutableObstacleMap.put(obstacle.getPosition(), (ImmutableObstacle) obstacle);
            else
                throw new BmDataException("Wrong type of obstacle");
        });
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Set<ApexPosition> getAllObstaclesPositions() {
        Set<ApexPosition> apexPositionSet = new HashSet<>();

        apexPositionSet.addAll(immutableObstacleMap.keySet());
        apexPositionSet.addAll(mutableObstacleMap.keySet());
        return apexPositionSet;
    }

}
