package bm.model;

import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface Explosive {

  @NotNull
  Duration explosionTimeMillis();
}
