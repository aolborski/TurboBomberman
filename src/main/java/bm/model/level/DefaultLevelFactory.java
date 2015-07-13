package bm.model.level;

import bm.model.ApexPosition;
import bm.model.level.obstacle.ImmutableObstacle;
import bm.model.level.obstacle.MutableObstacle;
import bm.model.level.obstacle.Obstacle;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.*;

import static bm.model.DiscreteUtils.pos;
import static com.google.common.collect.Collections2.filter;

@Component
@Primary
public class DefaultLevelFactory implements LevelFactory {

    @Value("15")
    private Integer levelSize;

    @Value("10")
    private Integer mutableObstacleCount;

    private Set<Obstacle> obstacles;

    @NotNull
    @Override
    public Level newLevel() {
        obstacles = new HashSet<>();
        initImmutableFields();
        initMutableFields();
        return new Level(obstacles, levelSize, levelSize);
    }

    /**
     * Add immutable fields on bounds and infield on positions which two coordinates are even
     * number.
     */
    private void initImmutableFields() {
        Set<ApexPosition> possibleObstaclePositions = new HashSet<>();

        for (int y = 0; y < levelSize; y++)
            for (int x = 0; x < levelSize; x++)
                possibleObstaclePositions.add(pos(x, y));

        filter(possibleObstaclePositions, input -> {
            Integer x = input.getX();
            Integer y = input.getY();


            return (x == 0) || (y == 0 ) || (x == levelSize -1) || (y == levelSize -1) || (x % 2 == 0 && y % 2 == 0);
//            return (x % 2 == 0 && y % 2 == 0) && (x > 0 && x < levelSize - 1) && (y > 0 && y <
//                    levelSize - 1);
        }).forEach(apexPosition -> obstacles.add(new ImmutableObstacle(apexPosition)));
    }

    //TODO: I think that this can be simplify
    private void initMutableFields() {
        List<ApexPosition> possibleMutableObstaclePositions = new ArrayList<>();

        for (int y = 0; y < levelSize; y++)
            for (int x = 0; x < levelSize; x++)
                possibleMutableObstaclePositions.add(pos(x, y));

        obstacles.forEach(obstacle -> possibleMutableObstaclePositions.remove(obstacle.getPosition()));

        possibleMutableObstaclePositions.remove(pos(2, 1));
        possibleMutableObstaclePositions.remove(pos(1, 2));

        Random random = new Random();

        for (int i = 0; i < mutableObstacleCount; i++) {
            int index = random.nextInt(possibleMutableObstaclePositions.size());
            obstacles.add(new MutableObstacle(possibleMutableObstaclePositions.get(index)));
            possibleMutableObstaclePositions.remove(index);
        }
    }
}
