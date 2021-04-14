//This class represents a linked list for objects of class 'Body'
public class CosmicSystem {

    private String name;
    private MyNode head;

    public String getName() {
        return this.name;
    }

    // Initialises this system as an empty system with a name.
    public CosmicSystem(String name) {
        this.name = name;
    }

    // Adds 'body' to the end of the list of bodies if the list does not already contain a
    // body with the same name as 'body', otherwise does not change the object state. The method
    // returns 'true' if the list was changed as a result of the call and 'false' otherwise.
    public boolean add(Body body) {
        if (this.head != null) return head.addNode(body);
        this.head = new MyNode(body);
        return true;
    }

    // Returns the 'body' with the index 'i'. The body that was first added to the list has the
    // index 0, the body that was most recently added to the list has the largest index (size()-1).
    // Precondition: 'i' is a valid index.
    public Body get(int i) {
        return this.head == null ? null : head.getBody(i);
    }

    // Returns the body with the specified name or 'null' if no such body exits in the list.
    public Body get(String name) {
        return this.head == null ? null : head.getBody(name);
    }

    // Returns the body with the same name as the input body or 'null' if no such body exits in the list.
    public Body get(Body body) {
        return this.head == null ? null : head.getBody(body.getName());
    }

    // returns the number of entries of the list.
    public int size() {
        return this.head == null ? 0 : this.head.size();
    }

    // Inserts the specified 'body' at the specified position
    // in this list if 'i' is a valid index and there is no body
    // in the list with the same name as that of 'body'.
    // Shifts the element currently at that position (if any) and
    // any subsequent elements to the right (adds 1 to their
    // indices). The first element of the list has the index 0.
    // Returns 'true' if the list was changed as a result of
    // the call, 'false' otherwise.
    public boolean add(int i, Body body) {
        if (i < 0) return false;
        if (this.head != null) {
            if (this.head.nameInList(body)) return false;
            if (i==0) { // Replace first node with non-empty List
                MyNode buffer = this.head;
                this.head = new MyNode(body);
                buffer.setPrevNode(this.head);
                this.head.setNextNode(buffer);
            }
            return head.addNode(i, body);
        }
        if (i == 0) {
            this.head = new MyNode(body);
            return true;
        }
        return false;
    }

    //removes the body at index i from the list, if i is a valid index
    //returns true if removal was done, and false otherwise (invalid index)
    public boolean remove(int i) {
        //TODO: implement method.
        return false;
    }

    //removes a body from the list, if the list contains a body with the same name as the input body
    //returns true if removal was done, and false otherwise (no body with the same name)
    public boolean remove(Body body) {
        //TODO: implement method.
        return false;
    }

    // Returns a new list that contains the same elements as this list in reverse order. The list 'this'
    // is not changed and only the references to the bodies are copied, not their content (shallow copy).
    public CosmicSystem reverse() {
        //TODO: implement method.
        return null;
    }

    // Returns a readable representation with the name of the system and all bodies in order of the list.
    // E.g.,
    // Jupiter System:
    // Jupiter, 1.898E27 kg, radius: 6.9911E7 m, position: [0.0,0.0,0.0] m, movement: [0.0,0.0,0.0] m/s.
    // Io, 8.9E22 kg, radius: 1822000.0 m, position: [0.0,0.0,0.0] m, movement: [0.0,0.0,0.0] m/s.
    //
    //Hint: also use toString() in Body.java for this.
    public String toString() {
        //TODO: implement method.
        return null;
    }
}