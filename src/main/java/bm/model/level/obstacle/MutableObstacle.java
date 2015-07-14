package bm.model.level.obstacle;

import bm.model.ApexPosition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * For all mutable object in game, like a crate.
 */
public class MutableObstacle implements Obstacle, Destroyable {

  @NotNull
  private final ApexPosition position;

  @NotNull
  private final BooleanProperty isDestroyed;

  private static final Logger LOG = LoggerFactory.getLogger(MutableObstacle.class);


  public MutableObstacle(@NotNull final ApexPosition position) {
    this.position = position;
    isDestroyed = new SimpleBooleanProperty(false);
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

  @NotNull
  public BooleanProperty isDestroyedProperty() {
    return isDestroyed;
  }

  @NotNull
//  @Override
  public ApexPosition getPosition() {
    return position;
  }
}
