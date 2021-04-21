public class MyComplexSystemNode {

    private Body data;
    private MyComplexSystemNode nextNode;
    private MyComplexSystemNode prevNode;


    public MyComplexSystemNode(Body data) {
        this.data = data;
    }

    public MyComplexSystemNode getNextNode() {return this.nextNode;}
    public void setNextNode(MyComplexSystemNode node) {this.nextNode = node;}
    public void setPrevNode(MyComplexSystemNode node) {this.prevNode = node;}

    public boolean addNode(Body body) {
        if (body.getName().equals(this.data.getName())) return false;
        if (this.nextNode != null) return this.nextNode.addNode(body);
        this.nextNode = new MyComplexSystemNode(body);
        this.nextNode.prevNode = this;
        return true;
    }

    public boolean addNode(int i, Body body) {
        if (i==1) {
            MyComplexSystemNode buffer = this.nextNode;
            this.nextNode = new MyComplexSystemNode(body);
            this.nextNode.nextNode = buffer;
            this.nextNode.prevNode = this;
            if (this.nextNode.nextNode != null)
                this.nextNode.nextNode.prevNode = this.nextNode;
            return true;
        }
        return this.nextNode != null && this.nextNode.addNode(i-1, body);
    }

    public Body getBody(int i) {
        if (i != 0 && this.nextNode == null)  return null;
        return i==0 ? this.data : this.nextNode.getBody(--i);
    }

    public Body getBody(String name) {
        if (this.data.getName().equals(name)) return this.data;
        return this.nextNode == null ? null : this.nextNode.getBody(name);
    }

    public int size() {
        return this.nextNode == null ? 1 : 1 + this.nextNode.size();
    }

    public int depth(String name, int layer) {
        if (this.data.getName().equals(name)) return layer;
        return this.nextNode.depth(name, layer + 1);
    }

    public boolean remove(int i) {
        if (i == 0) {
            this.prevNode.nextNode = this.nextNode;
            if (this.nextNode != null) this.nextNode.prevNode = this.prevNode;
            return true;
        }
        return this.nextNode != null && this.nextNode.remove((i - 1));
    }

    public boolean remove(Body body) {
        if (this.data.getName().equals(body.getName())) return this.remove(0);
        return this.nextNode != null && this.nextNode.remove(body);
    }

    public void reverse(CosmicSystem cosmos) {
        cosmos.add(this.data);
        if (this.prevNode != null) this.prevNode.reverse(cosmos);
    }

    public String toString() {
        return this.nextNode == null ? this.data.toString() + "\n" : this.data.toString() + "\n" + this.nextNode.toString();
    }

}
