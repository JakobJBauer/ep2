public class MyNode {
    private Body data;
    private MyNode nextNode;
    private MyNode prevNode;


    public MyNode(Body data) {
        this.data = data;
    }

    public void setNextNode(MyNode node) {this.nextNode = node;}
    public void setPrevNode(MyNode node) {this.prevNode = node;}

    public boolean addNode(Body body) {
        if (body.getName().equals(this.data.getName())) return false;
        if (this.nextNode != null) return this.nextNode.addNode(body);
        this.nextNode = new MyNode(body);
        this.nextNode.prevNode = this;
        return true;
    }

    public boolean addNode(int i, Body body) {
        if (i==1) {
            MyNode buffer = this.nextNode;
            this.nextNode = new MyNode(body);
            this.nextNode.nextNode = buffer;
            this.nextNode.prevNode = this;
            if (this.nextNode.nextNode != null)
                this.nextNode.nextNode.prevNode = this.nextNode;
            return true;
        }
        if (this.nextNode == null) return false;
        return this.nextNode.addNode(i-1, body);
    }

    public boolean nameInList(Body body) {
        if (body.getName().equals(this.data.getName())) return true;
        if (this.nextNode != null) return this.nextNode.nameInList(body);
        return false;
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
