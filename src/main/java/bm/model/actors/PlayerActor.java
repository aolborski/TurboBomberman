package bm.model.actors;

import bm.controller.GameWorldController;
import bm.model.ApexPosition;
import bm.model.HasApexPosition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static bm.model.DiscreteUtils.fromDouble;
import static bm.model.actors.MovementDirection.NONE;
import static bm.viewcontroller.ViewConstants.FIELD_WORLD_SIZE;

@Component
public class PlayerActor implements Movable, HasApexPosition, BombPlanter {

    //TODO: remove this dependency injection
    @Autowired
    GameWorldController gameWorldController;

    @NotNull
    private ObjectProperty<Point2D> position;

    @NotNull
    private MovementVector movementVector;

    private static final Logger LOG = LoggerFactory.getLogger(PlayerActor.class);

    public PlayerActor() {
        position = new SimpleObjectProperty<>(new Point2D(1 * FIELD_WORLD_SIZE, 1 * FIELD_WORLD_SIZE));
        movementVector = new MovementVector(NONE);

        LOG.debug("\n\n\n\n\n\n\nposition = " + position.toString() + "\n\n\n\n\n\n");
    }

    /**
     * Constructor
     *
     * @param point2D           started point of player actor
     * @param movementDirection started direction of player actor movement
     */
    public PlayerActor(final Point2D point2D, final MovementDirection movementDirection) {
        this.position = new SimpleObjectProperty<>(new Point2D(point2D.getX() * FIELD_WORLD_SIZE,
                point2D.getY() * FIELD_WORLD_SIZE));
        this.movementVector = new MovementVector(movementDirection);
    }

    @Override
    public void move() {
        position.setValue(new Point2D(position.get().getX() + movementVector.getValueVector()
                .getX(),
                position.get().getY() + movementVector.getValueVector().getY()));
    }

    @NotNull
    public ObjectProperty<Point2D> positionProperty() {
        return position;
    }

    @NotNull
    @Override
    public MovementVector getMovementVector() {
        return movementVector;
    }

    @NotNull
    @Override
    public ApexPosition getNextPosition() {
        Point2D point2D = (new Point2D(position.get().getX() + movementVector.getX(), position.get()
                .getY()
                + movementVector.getY()));
        return fromDouble(point2D);
    }

    public void setMovementDirection(final MovementDirection movementDirection) {
        this.movementVector = new MovementVector(movementDirection);
    }

    @NotNull
    @Override
    public ApexPosition getPosition() {
        return fromDouble(position.get());
    }

    @Override
    public void plant() {
        LOG.debug("bomb planted at " + getPosition());
        gameWorldController.addBombToGame(getPosition());
    }
}
