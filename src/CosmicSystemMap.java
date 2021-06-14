import java.util.HashMap;

// define class
public class CosmicSystemMap implements CosmicSystemIndex {

    private ComplexCosmicSystem system;
    private Body[] ks = new Body[65];
    private ComplexCosmicSystem[] vs = new ComplexCosmicSystem[65];
    int count = 0;

    // Creates a hash map from the specified 'system'.
    // The resulting map has multiple (key, value) pairs, one for each
    // body of 'system'. The value is the reference
    // to the system (only the direct parent) to which the body belongs.

    public CosmicSystemMap(ComplexCosmicSystem system) {
        this.system = system;
        for (Body body: system) {
            this.put(body, system.getParent(body));
        }
    }

    public int countCollisions() {
        int counter = 0;
        int[] hashes = new int[65];
        int index = 0;
        for (ComplexCosmicSystem cur: this.vs) {
            if (cur == null) continue;
            int currHash = cur.hashCode();
            if (this.contains(hashes, currHash)) counter++;
            else {
                hashes[index] = currHash;
                index++;
            }
        }
        return counter;
    }

    private boolean contains(int[] values, int key) {
        for (int value : values) {
            if (value == key) return true;
        }
        return false;
    }

    @Override
    public ComplexCosmicSystem getParent(Body b) {
        return vs[this.find(b)];
    }

    @Override
    public boolean contains(Body b) {
        return ks[this.find((b))] != null;
    }

    @Override
    public BodyCollection getBodies() {
        return new MyBodyCollection(this.system);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (Body body: ks) {
            if (body != null) out.append(body.toString()).append("\n");
        }
        return out.toString();
    }

    private int find(Body k) {
        if (k == null) {
            return ks.length - 1;
        }
        int i = k.hashCode() & (ks.length - 2);
        while (ks[i] != null && !ks[i].equals(k)) {
            i = (i + 1) & (ks.length - 2);
        }
        return i;
    }

    private void put(Body key, ComplexCosmicSystem value) {
        if (key == null) return;
        int i = find(key);
        vs[i] = value;
        if (ks[i] == null) {
            ks[i] = key;
            if (count++ >= ks.length * 3/4)
                this.resize();
        }
    }

    private void resize() {
        Body[] oks = ks;
        ComplexCosmicSystem[] ovs = vs;
        ks = new Body[(oks.length << 1) - 1];
        vs = new ComplexCosmicSystem[(oks.length << 1) - 1];
        for (int j = 0; j < oks.length; j++) {
            if (oks[j] != null) {
                int i = find(oks[j]);
                ks[i] = oks[j];
                vs[i] = ovs[j];
            }
        }
    }
}
