package bm.model.level;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public interface LevelFactory {

    @NotNull
    Level newLevel();
}
