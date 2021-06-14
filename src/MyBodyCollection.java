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
}
