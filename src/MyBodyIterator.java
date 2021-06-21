public class MyBodyIterator implements BodyIterator {
    Cluster left, right;

    public MyBodyIterator(Cluster left, Cluster right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean hasNext() {
        return left.iterator().hasNext() || right.iterator().hasNext();
    }

    @Override
    public Body next() {
        if (left.iterator().hasNext()) {
            return left.iterator().next();
        }
        if (right.iterator().hasNext()) {
            return right.iterator().next();
        }
        throw new IllegalStateException("No next element");
    }

    @Override
    public void remove() {

    }
}