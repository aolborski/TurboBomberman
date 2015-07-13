package bm.model.level.obstacle;

import bm.model.ApexPosition;
import org.jetbrains.annotations.NotNull;

/**
 * For all immutable object in game, like a solid block on center of strip.
 */
public class ImmutableObstacle implements Obstacle {

  @NotNull
  private final ApexPosition position;

  public ImmutableObstacle(@NotNull final ApexPosition position) {
    this.position = position;
  }

  @NotNull
  @Override
  public ApexPosition getPosition() {
    return position;
  }
}
