public class MyNode {
    public Body data;
    public MyNode nextNode;

    public MyNode(Body data) {
        this.data = data;
    }

    public boolean addNode(Body body) {
        if (body.getName().equals(this.data.getName())) return false;
        if (this.nextNode != null) return this.nextNode.addNode(body);
        this.nextNode = new MyNode(body);
        return true;
    }

    public Body getBody(int i) {
        return i==0 ? this.data : this.nextNode.getBody(--i);
    }

    public Body getBody(String name) {
        if (this.data.getName().equals(name)) return this.data;
        return this.nextNode == null ? null : this.nextNode.getBody(name);
    }

    public int size() {
        return this.nextNode == null ? 1 : 1 + this.nextNode.size();
    }
}
