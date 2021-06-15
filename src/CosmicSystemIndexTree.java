// A binary search tree implementation of 'CosmicSystemIndex'. This binary tree
// uses a specified comparator for sorting its keys.

import java.util.NoSuchElementException;

public class CosmicSystemIndexTree implements CosmicSystemIndex, BodyIterable {

    private IndexTreeNode root;
    private BodyComparator comparator;

    // Initialises this index with a 'comparator' for sorting
    // the keys of this index.
    public CosmicSystemIndexTree(BodyComparator comparator) {
        assert comparator != null;
        this.comparator = comparator;
        this.root = IndexTreeNullNode.NIL; // NIL in the beginning.

    }

    // Adds a system of bodies to the index.
    // Adding a system adds multiple (key, value) pairs to the
    // index, one for each body of the system, with the same
    // value, i.e., reference to the celestial system.
    // An attempt to add a system with a body that already exists
    // in the index leaves the index unchanged and the returned
    // value would be 'false'.
    // The method returns 'true' if the index was changed as a
    // result of the call and 'false' otherwise.
    public boolean add(ComplexCosmicSystem system) {

        if (system == null || system.numberOfBodies() == 0) {
            return false;
        }

        for (Body b: system) {
            assert b != null;
            if (this.contains(b)) {
                return false;
            }
        }

        for (Body b: system) {
            assert b != null;
            root = root.add(new IndexTreeNonNullNode(b, system.getParent(b), comparator));
        }

        return true;

    }

    // Returns the system with which a body is
    // associated. If body is not contained as a key, 'null'
    // is returned.
    public ComplexCosmicSystem getParent(Body body) {
        assert body != null;

        return root.get(body);
    }

    // Returns 'true' if the specified 'body' is listed
    // in the index.
    public boolean contains(Body body) {
        assert body != null;
        return getParent(body) != null;
    }

    // Returns a readable representation with (key, value) pairs sorted by the key.
    public String toString() {

        return "{" + root.toString() + "}";
    }

    // Returns the comparator used in this index.
    public BodyComparator getComparator() {
        return comparator;
    }

    @Override
    // Returns an iterator iterating over all celestial bodies of this index.
    public BodyIterator iterator() {

        return root.iterator(new TreeNodeIterator(null, null));
    }

    @Override
    // Returns a 'BodyCollection' view of all bodies of this index.
    public BodyCollection getBodies() {

        return new TreeBodyCollection(this);
    }

    public int size() {

        return root.size();
    }
}

interface IndexTreeNode {

    // Adds the specified 'node' to the tree of which 'this' is the root
    // node. If the tree already has a node with the same key as that
    // of 'node' the tree remains unchanged.
    // (V) node is a viable IndexTreeNode
    // (N) if node is in tree -> no changes
    // (N) if node isn't in tree -> node was added
    IndexTreeNode add(IndexTreeNode node);

    // Returns the cosmic system with which a body is associated, if 'body' is a key
    // which is contained in this tree (the tree of which 'this' is the root node).
    // If body is not contained as a key, 'null' is returned.
    // (V) body is a viable Body that is not null (How would get() preform then?)
    // (N) if body is no key -> no changes and return null
    // (N) if body is key -> returns the CosmicSystem with what a body is associated
    ComplexCosmicSystem get(Body body);

    // Returns a readable representation of the tree of which 'this' is the root node.
    // (N) returns a readable string of this object
    String toString();

    // Returns an iterator over all keys of the tree of which 'this' is the root node.
    // 'parent' is the iterator of the parent (path from the root).
    // (V) parent is a viable TreeNodeIterator that is not null
    // (N) returns an iterator over all keys of this object
    TreeNodeIterator iterator(TreeNodeIterator parent);

    // Returns the key of this node.
    // (N) returns the key of this object
    Body getKey();

    // Returns the number of entries in the tree of which 'this' is the root
    // node.
    // (N) returns the number of entries in this tree object
    int size();

}

// Implements a terminal node with no content (used instead of 'null').
class IndexTreeNullNode implements IndexTreeNode {
    // Singleton: only one instance is needed, because the
    // state of 'this' can not be changed.
    public static final IndexTreeNullNode NIL = new IndexTreeNullNode();

    // private to avoid object creation from outside
    private IndexTreeNullNode() {}

    // (V) node is a viable IndexTreeNode
    // (N) no changes, returns the input
    /*
        At first glance it seems like this method interferes with the add() method of IndexTreeNode
        On further investigation, one can see that this object has no elements in it. Thus, it has to add node to this object, which doesn't happen.
        The inaccuracy lies in     // (N) if node isn't in tree -> node was added       from IndexTreeNode
    */
    public IndexTreeNode add(IndexTreeNode node) {
        return node;
    }

    // (V) body is a viable Body that is not null
    // (N) no changes, returns null
    /*
        This method fits the description in IndexTreeNode, as body is never containes as a key in an empty tree
    */
    public ComplexCosmicSystem get(Body body) {
        return null;
    }

    // (N) returns an representation of this object
    /*
        This is correct, as "" is the most fitting description for an emty node
    */
    public String toString() {
        return "";
    }

    // (V) parent is a viable TreeNodeIterator that is not null
    // (N) returns the input
    /*
        According to the description in IndexTreeNode, this should return an empty iterator, as this tree has no objects
    */
    public TreeNodeIterator iterator(TreeNodeIterator parent) {
        return parent;
    }

    // (N) returns the key of this object
    /*
        This is correct, as this object has no key
    */
    public Body getKey() {
        return null;
    }

    // (N) returns the size of this object
    /*
        This is correct, as an empty tree has an size of 0
    */
    public int size() { return 0; }

}

// A node with a content.
class IndexTreeNonNullNode implements IndexTreeNode {
    private IndexTreeNode left;
    private IndexTreeNode right;
    private Body key;
    private ComplexCosmicSystem cs;
    private BodyComparator comparator;

    public IndexTreeNonNullNode(Body key, ComplexCosmicSystem cs,
                                BodyComparator comparator) {
        this.key = key;
        this.cs = cs;
        this.left = IndexTreeNullNode.NIL;
        this.right = IndexTreeNullNode.NIL;
        this.comparator = comparator;

    }

    public IndexTreeNode add(IndexTreeNode node) {
        int comp = this.comparator.compare(this.key, node.getKey());
        if (comp > 0) {
            left = left.add(node);
        } else {
            if (comp < 0) {
                right = right.add(node);
            }
        }

        return this;
    }

    public ComplexCosmicSystem get(Body body) {
        if (key.equals(body)) {
            return cs;
        }

        if (this.comparator.compare(this.key, body) > 0) {
            return left.get(body);
        } else {
            return right.get(body);
        }

    }

    public String toString() {
        String result;
        String right = this.right.toString();
        result = this.left.toString();
        result += result.isEmpty() ? "" : ",\n";
        result += this.key + " belongs to " + this.cs;
        result += right.isEmpty() ? "" : ",\n";
        result += right;
        return result;

    }

    public Body getKey() {
        return key;
    }

    public TreeNodeIterator iterator(TreeNodeIterator next) {
        return left.iterator(new TreeNodeIterator(this, next));

    }

    public TreeNodeIterator nextStep(TreeNodeIterator next) {
        return right.iterator(next);
    }

    public int size() { return 1 + left.size() + right.size(); }

}

class TreeNodeIterator implements BodyIterator {
    private IndexTreeNonNullNode node;
    private TreeNodeIterator next;

    public TreeNodeIterator(IndexTreeNonNullNode node, TreeNodeIterator next) {
        this.node = node;
        this.next = next;
    }

    @Override
    public boolean hasNext() {
        return node != null;
    }

    @Override
    public Body next() {
        if (hasNext()) {
            Body key = node.getKey();
            next = node.nextStep(next);
            node = next.node;
            next = next.next;
            return key;
        }
        throw new NoSuchElementException("This index has no (more) entries!");
    }

}

class TreeBodyCollection implements BodyCollection {

    private CosmicSystemIndexTree tree;

    public TreeBodyCollection(CosmicSystemIndexTree tree) {
        this.tree = tree;
    }

    public boolean add(Body b) {
        return false;
    }

    public boolean contains(Body b) {
        return tree.contains(b);
    }

    public int size() {
        return tree.size();

    }

    public Body[] toArray() {
        Body[] result = new Body[tree.size()];

        BodyIterator it = tree.iterator();

        for (int i = 0; i < result.length; i++) {

            result[i] = it.next();
        }

        return result;
    }

    @Override
    public BodyIterator iterator() {
        return tree.iterator();

    }
}


