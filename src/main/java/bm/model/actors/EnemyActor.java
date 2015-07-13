package bm.model.actors;

import bm.model.ApexPosition;
import bm.model.HasApexPosition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import org.jetbrains.annotations.NotNull;

import static bm.Constants.ENEMY_SPEED;
import static bm.model.DiscreteUtils.fromDouble;
import static bm.viewcontroller.ViewConstants.FIELD_WORLD_SIZE;

/**
 * All enemy in games are enemy actor.
 */
public class EnemyActor implements HasApexPosition, Movable {

    @NotNull
    private ObjectProperty<Point2D> position;

    /**
     * Actual direction wherein actor is moving.
     */
    @NotNull
    public MovementVector movementVector;

    /**
     * Constructor
     *
     * @param point2D           started point of enemy
     * @param movementDirection started direction of enemy movement
     */
    public EnemyActor(final Point2D point2D, final MovementDirection movementDirection) {
        this.position = new SimpleObjectProperty<>(new Point2D(point2D.getX() * FIELD_WORLD_SIZE,
                point2D.getY() * FIELD_WORLD_SIZE));

        this.movementVector = new MovementVector(movementDirection);
    }

    @Override
    @NotNull
    public ApexPosition getPosition() {
        return fromDouble(position.get());
    }

    @NotNull
    public ObjectProperty<Point2D> positionProperty() {
        return position;
    }

    @Override
    public void move() {
        position.setValue(new Point2D(position.get().getX() + ENEMY_SPEED * movementVector
                .getValueVector()
                .getX(), position.get().getY() + ENEMY_SPEED * movementVector.getValueVector().getY()));
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
}
