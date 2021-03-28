//This class represents a binary search tree for objects of class 'CosmicSystem'
public class CosmicSystemTree {

    private MyTreeNode head;

    // Adds a system of bodies to the tree. Since the keys of the tree are the names of bodies,
    // adding a system adds multiple (key, value) pairs to the tree, one for each body of the
    // system, with the same value, i.e., reference to the cosmic system.
    // An attempt to add a system with a body that already exists in the tree
    // leaves the tree unchanged and the returned value would be 'false'.
    // For example, it is not allowed to have a system with the bodies "Earth" and "Moon" and another
    // system with the bodies "Jupiter" and "Moon" indexed by the tree, since "Moon" is not unique.
    // The method returns 'true' if the tree was changed as a result of the call and
    // 'false' otherwise.
    public boolean add(CosmicSystem system) {
        // Check if one of the names exists in the tree
        for (int i = 0; i < system.size(); i++) {
            if (this.get(system.get(i).getName()) != null) return false;
        }

        // Add every body in system to the searchTree
        for (int i = 0; i < system.size(); i++) {
            if (head != null) head.add(system.get(i).getName(), system);
            else head = new MyTreeNode(system.get(i).getName(), system);
        }
        return true;
    }

    // Returns the cosmic system in which a body with the specified name exists.
    // For example, if the specified name is "Europa", the system of Jupiter (Jupiter, Io,
    // Europa, Ganymed, Kallisto) will be returned.
    // If no such system is found, 'null' is returned.
    public CosmicSystem get(String name) {
        return this.head == null ? null : this.head.get(name);
    }

    // Returns the overall number of bodies indexed by the tree.
    public int numberOfBodies() {
        return this.head == null ? 0 : this.head.numberOfBodies();
    }

    // Returns a readable representation with (key, value) pairs, sorted alphabetically by the key.
    //E.g.,
    //    (Deimos,Mars System)
    //    (Earth,Earth System)
    //
    //Hint: for this you will also need a method in CosmicSystem.java to access the name of a CosmicSystem object.
    public String toString() {
        //TODO: implement method
        return null;
    }

    //BONUS TASK: sets a new canvas and draws the tree using StdDraw
    public void drawTree() {
        //TODO: implement method (optional bonus task)
    }
}


