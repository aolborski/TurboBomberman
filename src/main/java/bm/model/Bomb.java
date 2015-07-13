package bm.model;

import bm.model.level.obstacle.Destroyable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bomb implements Explosive, HasApexPosition, Destroyable {

    @NotNull
    private final ApexPosition position;

    @NotNull
    private final BooleanProperty isDestroyed;

    @NotNull
    private final Timeline timeline;

    private static final Logger LOG = LoggerFactory.getLogger(Bomb.class);

    /**
     * Constructor
     *
     * @param position bomb in game world
     */
    public Bomb(@NotNull final ApexPosition position) {
        this.position = position;
        isDestroyed = new SimpleBooleanProperty(false);
        timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.getKeyFrames().add(new KeyFrame(this.explosionTimeMillis(), event -> {
            destroy();
        }));

        timeline.play();
    }

    @NotNull
    public BooleanProperty isDestroyedProperty() {
        return isDestroyed;
    }

    @NotNull
    @Override
    public Duration explosionTimeMillis() {
        return Duration.millis(3000);
    }

    @NotNull
    @Override
    public ApexPosition getPosition() {
        return position;
    }

    @Override
    public void destroy() {
        LOG.debug("destroyed");
        isDestroyed.setValue(true);
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed.get();
    }
}
