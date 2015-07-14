package bm.model;

import org.jetbrains.annotations.NotNull;

public interface HasApexPosition {

  @NotNull
  ApexPosition getPosition();

  @NotNull
  default Integer getX() {
    return getPosition().getX();
  }

  @NotNull
  default Integer getY() {
    return getPosition().getY();
  }
}
