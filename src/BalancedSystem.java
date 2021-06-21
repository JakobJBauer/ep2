import java.io.IOException;

public class BalancedSystem implements Cluster {

    private Cluster left, right;

    //the constructor should throw a BalancedSystemIllegalArgumentException (to be implemented), if one of the arguments is null
    BalancedSystem(Cluster left, Cluster right) throws BalancedSystemIllegalArgumentException {
        if (left == null || right == null) {
            throw new BalancedSystemIllegalArgumentException("None of the arguments may be null!");
        }

        this.left = left;
        this.right = right;
    }

    @Override
    //adds a Body b to the BalancedSystem. If the mass of the left cluster is less than the mass of the right cluster,
    //the body is added to the left cluster, otherwise to the right cluster.
    //Returns this after the operation
    public Cluster add(Body b) {
        if (left.getMass() < right.getMass()) left.add(b);
        else right.add(b);
        assert Math.abs(left.getMass() - right.getMass()) < b.getMass();
        return this;
    }

    @Override
    //returns overall number of bodies in the cluster (ans its sub-clusters)
    public int numberOfBodies() {
        return left.numberOfBodies() + right.numberOfBodies();
    }

    @Override
    //returns the summed mass of all the bodies in the cluster (ans its sub-clusters)
    public double getMass() {
        return left.getMass() + right.getMass();
    }

    @Override
    //returns an iterator over all bodies
    public BodyIterator iterator() {
        return new MyBodyIterator(left, right);
    }

    @Override
    //returns a String indicating the masses of the left and right subsystem
    //E.g., "Left mass: 10.0, right mass: 12.0
    public String toString() {
        return "Left mass: " + left.getMass() + ", right mass: " + right.getMass();
    }


}
