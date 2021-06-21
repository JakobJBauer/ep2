public class MyBodyIterator implements BodyIterator {
    BodyIterator left, right;

    public MyBodyIterator(Cluster left, Cluster right) {
        this.left = left.iterator();
        this.right = right.iterator();
    }

    @Override
    public boolean hasNext() {
        return left.hasNext() || right.hasNext();
    }

    @Override
    public Body next() {
        if (left.hasNext()) {
            return left.next();
        }
        if (right.hasNext()) {
            return right.next();
        }
        throw new IllegalStateException("No next element");
    }

    @Override
    public void remove() {

    }
}