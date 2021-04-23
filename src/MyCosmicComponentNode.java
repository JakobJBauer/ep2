public class MyCosmicComponentNode {

    private CosmicComponent data;
    private MyCosmicComponentNode nextNode;
    private MyCosmicComponentNode prevNode;


    public MyCosmicComponentNode(CosmicComponent data) {
        this.data = data;
    }

    public MyCosmicComponentNode getNextNode() {return this.nextNode;}
    public void setNextNode(MyCosmicComponentNode node) {this.nextNode = node;}
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

    public CosmicComponent get(String name) {
        if (this.data.getName().equals(name)) return this.data;
        return this.nextNode == null ? null : this.nextNode.get(name);
    }

    // Helping Method
    public CosmicComponent get(int i) {
        if (i != 0 && this.nextNode == null)  return null;
        return i==0 ? this.data : this.nextNode.get(--i);
    }

    public int size() {
        return this.nextNode == null ? 1 : 1 + this.nextNode.size();
    }

    public String toString() {
        return this.nextNode == null ? this.data.toString() : this.data.toString() + ", " + this.nextNode.toString();
    }

    public int numberOfBodies() {
        return this.nextNode == null ? this.data.numberOfBodies() : this.data.numberOfBodies() + this.nextNode.numberOfBodies();
    }

    public double getMass() {
        return this.nextNode == null ? this.data.getMass() : this.data.getMass() + this.nextNode.getMass();
    }

    public Vector3 getMassCenter(Vector3 massCenter, double mass) {
        Vector3 output = (massCenter.times(mass).plus(this.data.getMassCenter().times(this.data.getMass())).times(1/(mass + this.data.getMass())));
        if (this.nextNode != null)
            output = this.nextNode.getMassCenter(output, mass + this.data.getMass());
        return output;
    }
}
