package bm.model.actors;

import bm.error.BmDataException;
import javafx.geometry.Point2D;
import org.jetbrains.annotations.NotNull;

import static bm.model.actors.MovementDirection.*;

/**
 * Movement vector has direction and method which returns point2D depended from direction. The
 * point at which this vector is applied is equal (0,0).
 */
public class MovementVector {

    @NotNull
    private MovementDirection movementDirection;

    /**
     *
     * @param movementDirection
     */
    public MovementVector(@NotNull final MovementDirection movementDirection) {
        this.movementDirection = movementDirection;
    }



    /**
     *
     * @return Point2D
     */
    public Point2D getValueVector() {
        Point2D point2D = new Point2D(0,0);

        if (movementDirection == UP)
            point2D = new Point2D(0,1);
        else if (movementDirection == RIGHT)
            point2D = new Point2D(1,0);
        else if (movementDirection == DOWN)
            point2D = new Point2D(0,-1);
        else if (movementDirection == LEFT)
            point2D = new Point2D(-1,0);
        else if (movementDirection == NONE)
            point2D = new Point2D(0,0);

        return point2D;
    }

    public double getX() {
        return getValueVector().getX();
    }

    public double getY() {
        return getValueVector().getY();
    }

    public MovementDirection getMovementDirection() {
        return movementDirection;
    }

    public void setOppositeDirection() {
        if (movementDirection == UP)
            movementDirection = DOWN;
        else if (movementDirection == DOWN)
            movementDirection = UP;
        else if (movementDirection == RIGHT)
            movementDirection = LEFT;
        else
            movementDirection = RIGHT;

    }
}
