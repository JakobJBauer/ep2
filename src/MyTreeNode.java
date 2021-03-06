public class MyTreeNode {

    private String key;
    private CosmicSystem value;
    private MyTreeNode leftChild;
    private MyTreeNode rightChild;

    public MyTreeNode (String key, CosmicSystem value) {
        this.key = key;
        this.value = value;
    }

    public void add(String key, CosmicSystem value) {
        int cmpVal = key.compareTo(this.key);
        if (cmpVal < 0) {
            if (leftChild == null) leftChild = new MyTreeNode(key, value);
            else leftChild.add(key, value);
        }
        else { // only +1 and -1 possible
            if (rightChild == null) rightChild = new MyTreeNode(key, value);
            else rightChild.add(key, value);
        }
    }

    public CosmicSystem get(String name) {
        int cmpVal = name.compareTo(this.key);
        if (cmpVal == 0) return this.value; // cant be null
        else if (leftChild != null && cmpVal < 0) return leftChild.get(name);
        else if (rightChild!= null && cmpVal > 0) return rightChild.get(name);
        else return null;
    }

    public int numberOfBodies() {
        int leftBodies = leftChild == null ? 0 : leftChild.numberOfBodies();
        int rightBodies = rightChild == null ? 0 : rightChild.numberOfBodies();
        return 1 + leftBodies + rightBodies;
    }

    public String toString() {
        String output = "";
        output += leftChild == null ? "" : leftChild.toString();
        output += "(" + this.key + ", " + this.value.getName() + ")\n";
        output += rightChild == null ? "" : rightChild.toString();
        return output;
    }

    public void draw(double x, double y, double xOffset, double yOffset) {
        StdDraw.text(x, y, this.key + ", " + this.value.getName());

        if (this.leftChild != null) {
            this.leftChild.draw(x - xOffset, y - yOffset, xOffset/2, yOffset);
            StdDraw.line(x - 10, y - 10, x - xOffset, y - yOffset + 10);
        }
        if (this.rightChild != null) {
            StdDraw.line(x + 10, y - 10, x + xOffset, y - yOffset + 10);
            this.rightChild.draw(x + xOffset, y - yOffset, xOffset/2, yOffset);
        }
    }
}
