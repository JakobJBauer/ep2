import javax.print.attribute.standard.JobKOctets;
import java.util.Objects;

public class MyCosmicComponentNode {

    private CosmicComponent data;
    private MyCosmicComponentNode nextNode;
    private MyCosmicComponentNode prevNode;


    public MyCosmicComponentNode(CosmicComponent data) {this.data = data;}

    public MyCosmicComponentNode getNextNode() {return this.nextNode;}
    public CosmicComponent getData() {return this.data;}
    public void setPrevNode(MyCosmicComponentNode node) {this.prevNode = node;}

    public boolean add(CosmicComponent component) {
        if (component.getName().equals(this.data.getName())) return false;
        if (this.nextNode != null) return this.nextNode.add(component);
        this.nextNode = new MyCosmicComponentNode(component);
        this.nextNode.prevNode = this;
        return true;
    }

    public boolean remove(CosmicComponent component) {
        if (this.data.getName().equals(component.getName())) {
            this.prevNode.nextNode = this.nextNode;
            if (this.nextNode != null) this.nextNode.prevNode = this.prevNode;
            return true;
        }
        return this.nextNode != null && this.nextNode.remove(component);
    }

    public int size() {
        return this.nextNode == null ? 1 : 1 + this.nextNode.size();
    }

    @Override
    public String toString() {return this.nextNode == null ? this.data.toString() : this.data.toString() + ", " + this.nextNode.toString();}

    public int numberOfBodies() {return this.nextNode == null ? this.data.numberOfBodies() : this.data.numberOfBodies() + this.nextNode.numberOfBodies();}

    public double getMass() {return this.nextNode == null ? this.data.getMass() : this.data.getMass() + this.nextNode.getMass();}

    public Vector3 getMassCenter(Vector3 massCenter, double mass) {
        Vector3 output = (massCenter.times(mass).plus(this.data.getMassCenter().times(this.data.getMass())).times(1/(mass + this.data.getMass())));
        if (this.nextNode != null)
            output = this.nextNode.getMassCenter(output, mass + this.data.getMass());
        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MyCosmicComponentNode head = (MyCosmicComponentNode) o;
        return this.nextNode == null ? head.contains(this.data) : head.contains(this.data) && this.nextNode.equals(o);
    }

    @Override
    public int hashCode() {         // what is the order of hashing?
        return this.nextNode == null ? Objects.hash(data) : Objects.hash(data) + this.nextNode.hashCode();
    }

    public ComplexCosmicSystem getParent(ComplexCosmicSystem currentSystem, Body b) {
        if (this.data.equals((b))) return currentSystem;
        if (this.data.getClass() == currentSystem.getClass()) {
            ComplexCosmicSystem newSystem = (ComplexCosmicSystem) this.data;
            if (newSystem.getParent(b) != null) return newSystem.getParent(b);
        }
        if (this.nextNode == null) return null;
        return this.nextNode.getParent(currentSystem, b);
    }

    public boolean contains(CosmicComponent x) {
        return this.nextNode == null ? this.data.equals(x) : this.data.equals(x) || this.nextNode.contains(x);
    }

    public Body[] getBodies() {
        if (this.nextNode != null)
            return this.concatenate(this.data.getBodies(), this.nextNode.getBodies());
        return this.data.getBodies();
    }

    private Body[] concatenate(Body[] a, Body[] b) {
        Body[] out = new Body[a.length + b.length];
        System.arraycopy(a, 0, out, 0, a.length);
        System.arraycopy(b, 0, out, a.length, b.length);
        return out;
    }
}
