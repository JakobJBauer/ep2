import java.util.Arrays;

public class MyBodyCollection implements BodyCollection {

    private ComplexCosmicSystem system;

    public MyBodyCollection(ComplexCosmicSystem system) {
        this.system = system;
    }

    @Override
    public boolean add(Body b) {
        return false;
    }

    @Override
    public boolean contains(Body b) {
        return this.system.contains(b);
    }

    @Override
    public int size() {
        return this.system.numberOfBodies();
    }

    @Override
    public Body[] toArray() {
        Body[] buffer = new Body[size()];
        BodyIterator iter = this.iterator();
        for (int i = 0; i < buffer.length && iter.hasNext(); i++){
            buffer[i] = iter.next();
        }
        return buffer;
    }

    @Override
    public BodyIterator iterator() {
        return this.system.iterator();
    }

    public boolean isEqualTo(CosmicSystemIndex index) {
        BodyCollection sysBodyC = index.getBodies();

        if (sysBodyC.size() != this.size()) return false;

        boolean equal = true;

        for (Body b : this.toArray()){
            equal = equal && sysBodyC.contains(b);
        }

        return equal;
    }
}
