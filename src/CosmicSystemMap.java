import java.util.HashMap;

// define class
public class CosmicSystemMap implements CosmicSystemIndex {

    private Body[] ks = new Body[50];
    private ComplexCosmicSystem[] vs = new ComplexCosmicSystem[50];
    int count = 0;

    // Creates a hash map from the specified 'system'.
    // The resulting map has multiple (key, value) pairs, one for each
    // body of 'system'. The value is the reference
    // to the system (only the direct parent) to which the body belongs.

    public CosmicSystemMap(ComplexCosmicSystem system) {
        for (Body body: system) {
            this.put(body, system.getParent(body));
        }
    }

    @Override
    public ComplexCosmicSystem getParent(Body b) {
        return vs[this.find(b)];
    }

    @Override
    public boolean contains(Body b) {
        return ks[this.find((b))] != null;
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
        ComplexCosmicSystem old = vs[i];
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
