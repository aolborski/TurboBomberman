package bm.view;

import javafx.scene.Node;

/**
 * Records a pair of (possibly) intersecting objects. Intersecting is detect using method
 * <code>getLayoutBounds</code>.
 */
public class NodePair {

    private Node a;

    private Node b;

    public NodePair(final Node source, final Node destination) {
        this.a = source;
        this.b = destination;
    }

    public boolean intersects() {
        return a != b && a.intersects(b.getLayoutBounds());
    }

    @Override
    public String toString() {
        return a.getId() + " : " + b.getId();
    }

    @Override
    public boolean equals(Object other) {
        NodePair nodePair = (NodePair) other;
        return nodePair != null && ((a == nodePair.a && b == nodePair.b) || (a == nodePair.b && b
                == nodePair.a));
    }

    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        return result;
    }
}
