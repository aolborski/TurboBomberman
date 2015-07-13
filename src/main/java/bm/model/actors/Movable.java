package bm.model.actors;

import bm.model.ApexPosition;
import javafx.geometry.Point2D;
import org.jetbrains.annotations.NotNull;

public interface Movable {

  void move();

  @NotNull
  MovementVector getMovementVector();

  @NotNull
  ApexPosition getNextPosition();
}
