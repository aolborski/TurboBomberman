package bm.ui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * Class responsible for switch between UI window and change context.
 */
@Component
public class BmContext {

    /**
     * UI state is charge of UI window and context.
     */
    @NotNull
    private final ObjectProperty<BmState> uiState;

    /**
     * Constructor
     */
    public BmContext() {
        uiState = new SimpleObjectProperty<>(BmState.MENU);
    }

    /**
     * Return actually UI state.
     *
     * @return uiState
     */
    public ObjectProperty<BmState> uiStateProperty() {
        return uiState;
    }
}
