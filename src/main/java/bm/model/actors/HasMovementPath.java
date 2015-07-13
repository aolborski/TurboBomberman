package bm.model.actors;

import bm.model.ApexPosition;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public interface HasMovementPath {

  @NotNull
  LinkedList<ApexPosition> getMovementPath();
}
