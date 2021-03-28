//This class represents a linked list for objects of class 'Body'
public class CosmicSystem {

    private String name;
    private MyNode head;


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
}