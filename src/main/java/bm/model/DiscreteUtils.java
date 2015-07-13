package bm.model;

import javafx.geometry.Point2D;
import org.jetbrains.annotations.NotNull;

import static bm.viewcontroller.ViewConstants.FIELD_WORLD_SIZE;

public class DiscreteUtils {

  private DiscreteUtils() {

  }

  @NotNull
  public static ApexPosition pos(@NotNull Integer x, @NotNull Integer y) {

    return new ApexPosition(x, y);
  }

  public static ApexPosition fromDouble(Point2D point2D) {

    return pos((int) ((point2D.getX() / FIELD_WORLD_SIZE) + 0.5), (int) ((point2D.getY() /
            FIELD_WORLD_SIZE) + 0.5));
  }

}
