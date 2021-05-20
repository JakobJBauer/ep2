import java.awt.*;
import java.util.Objects;

//This class represents a double-linked list for objects of class 'CosmicComponent'.
public class ComplexCosmicSystem implements CosmicComponent, BodyIterable, CosmicSystemIndex {

    private MyCosmicComponentNode head;
    private String name;

    // Initialises this system with a name and at least two cosmic components.
    public ComplexCosmicSystem(String name, CosmicComponent c1, CosmicComponent c2, CosmicComponent... ci) {
        this.name = name;
        this.add(c1);
        this.add(c2);
        for (CosmicComponent comp: ci) this.add(comp);
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
        if (this.head.getData().getName().equals(comp.getName())) {
            this.head = this.head.getNextNode();
            this.head.setPrevNode(null);
            return true;
        }
        return this.head.remove(comp);
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
    @Override
    public String toString() {
        return this.head == null ? this.getName() + "{}" : this.getName() + "{" + this.head.toString() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexCosmicSystem system = (ComplexCosmicSystem) o;
        return this.head.size() == system.size() && this.head.equals(system.head);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head.hashCode());
    }

    //Returns the overall number of bodies (i.e. objects of type 'Body') contained in the ComplexCosmicSystem.
    //For instance, the System "Solar System{Sun, Earth System{Earth, Moon}, Jupiter System{Jupiter}}" contains 4 bodies (Sun, Earth, Moon and Jupiter).
    //
    //CONSTRAINT: use the concept of dynamic binding to fulfill this task, i.e. don't use type casts, getClass() or instanceOf().
    public int numberOfBodies() {
        return this.head == null ? 0 : this.head.numberOfBodies();
    }

    public Body[] getBodies() {
        return this.head.getBodies();
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

    @Override
    public BodyIterator iterator() {
        return new ComplexCosmicSystemIterator(this.head);
    }

    @Override
    public ComplexCosmicSystem getParent(Body b) {
        ComplexCosmicSystem buffer = this;
        MyCosmicComponentNode node = this.head;
        while (node != null) {
            if (node.getData().getClass() == this.getClass()) buffer = (ComplexCosmicSystem) node.getData();
            else if (node.getData().equals(b)) return buffer;
            node = node.getNextNode();
        }
        CosmicSystemMap map = new CosmicSystemMap(this);
        return buffer;
    }

    @Override
    public boolean contains(Body b) {
        return this.head.contains(b);
    }
}


class ComplexCosmicSystemIterator implements BodyIterator {
    Body[] bodies;
    int index = 0;
    public ComplexCosmicSystemIterator (MyCosmicComponentNode head) {
        this.bodies = head.getBodies();
    }

    @Override
    public boolean hasNext() {
        return this.index < this.bodies.length;
    }

    @Override
    public Body next() {
        return this.bodies[index++];
    }
}