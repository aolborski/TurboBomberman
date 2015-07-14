package bm.model.level.obstacle;

import javafx.beans.property.BooleanProperty;

public interface Destroyable {

    void destroy();

    BooleanProperty isDestroyedProperty();
}
