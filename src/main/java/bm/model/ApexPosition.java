package bm.model;

import org.jetbrains.annotations.NotNull;

public class ApexPosition {

  @NotNull
  private final Integer x;

  @NotNull
  private final Integer y;

  public ApexPosition(@NotNull Integer x, @NotNull Integer y) {

    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ApexPosition that = (ApexPosition) o;

    return x.equals(that.x) && y.equals(that.y);

  }

  @Override
  public int hashCode() {

    int result = x.hashCode();
    result = 31 * result + y.hashCode();
    return result;
  }

  @NotNull
  public Integer getX() {

    return x;
  }

  @NotNull
  public Integer getY() {

    return y;
  }

  @Override
  public String toString() {

    return String.format("ApexPosition{x=%d, y=%d}", x, y);
  }
}
