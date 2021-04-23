import java.awt.*;

//This class represents a double-linked list for objects of class 'CosmicComponent'.
public class ComplexCosmicSystem implements CosmicComponent {

    //TODO: Define variables.
    private MyCosmicComponentNode head;
    private String name;

    // Initialises this system as an empty system with a name.
    public ComplexCosmicSystem(String name) {
        this.name = name;
    }

    // Adds 'comp' to the list of cosmic components of the system if the list does not already contain a
    // component with the same name as 'comp', otherwise does not change the object state. The method
    // returns 'true' if the list was changed as a result of the call and 'false' otherwise.
    public boolean add(CosmicComponent comp) {
        if (this.head != null) return head.add(comp);
        this.head = new MyCosmicComponentNode(comp);
        return true;
    }

    //Removes a component from the list if the list contains a component with the same name as the input component.
    //Returns true if removal was done, and false otherwise (no component with the same name).
    public boolean remove(CosmicComponent comp) {
        if (this.head == null) return false;
        if (this.head.get(0).getName().equals(comp.getName())) {
            this.head = this.head.getNextNode();
            this.head.setPrevNode(null);
            return true;
        }
        return this.head.remove(comp);
    }

    // Returns the CosmicComponent with the specified name or 'null' if no such component exists in the list.
    public CosmicComponent get(String name) {
        return this.head == null ? null : head.get(name);
    }

    // Returns the CosmicComponent with the same name as the input component or 'null' if no such CosmicComponent exists in the list.
    public CosmicComponent get(CosmicComponent c) {
        return this.head == null ? null : head.get(c.getName());
    }

    // Returns the name of this system.
    public String getName() {
        return this.name;
    }

    // Returns the number of CosmicComponent entries of the list.
    public int size() {
        return this.head == null ? 0 : this.head.size();
    }

    // Returns a readable representation of the ComplexCosmicSystem.
    // The representation should list all the names of its bodies and sub-systems, where the hierarchy is indicated by {} brackets
    //For instance, considering if we have a system called "Solar System" with the entries "Sun", "Earth System" and "Jupiter System".
    //"Sun" is a body, "Earth System" is a system with the bodies "Earth" and "Moon", and "Jupiter System" is a system with the body "Jupiter".
    //Then the output should be "Solar System{Sun, Earth System{Earth, Moon}, Jupiter System{Jupiter}}"
    //An empty system is indicated by empty brackets, e.g. "Jupiter System{}"
    //
    //CONSTRAINT: use the concept of dynamic binding to fulfill this task, i.e. don't use type casts, getClass() or instanceOf().
    public String toString() {
        return this.head == null ? this.getName() + "{}" : this.getName() + "{" + this.head.toString() + "}";
    }

    //Returns the overall number of bodies (i.e. objects of type 'Body') contained in the ComplexCosmicSystem.
    //For instance, the System "Solar System{Sun, Earth System{Earth, Moon}, Jupiter System{Jupiter}}" contains 4 bodies (Sun, Earth, Moon and Jupiter).
    //
    //CONSTRAINT: use the concept of dynamic binding to fulfill this task, i.e. don't use type casts, getClass() or instanceOf().
    public int numberOfBodies() {
        return this.head == null ? 0 : this.head.numberOfBodies();
    }

    //Returns the overall mass (sum of all contained components).
    //In case of an empty system, a mass of 0.0 should be returned.
    //
    //CONSTRAINT: use the concept of dynamic binding to fulfill this task, i.e. don't use type casts, getClass() or instanceOf().
    public double getMass() {
        return this.head == null ? 0.0 : this.head.getMass();
    }

    //Returns the gravitational center of this component (weighted average of contained components).
    //In case of an empty system, a vector [0.0, 0.0, 0.0] should be returned.
    //
    //CONSTRAINT: use the concept of dynamic binding to fulfill this task, i.e. don't use type casts, getClass() or instanceOf().
    public Vector3 getMassCenter() {
        return this.head == null ? new Vector3(0,0,0) : this.head.getMassCenter(new Vector3(0,0,0), 0);
    }
}