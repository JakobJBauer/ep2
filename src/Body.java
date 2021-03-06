import java.awt.*;
import java.util.Objects;

// This class represents celestial bodies like stars, planets, asteroids, etc..
public class Body implements CosmicComponent, Cluster {

    public static final double G = 6.6743e-11;

    //changed modifiers.
    private String name;
    private double mass;
    private double radius;
    private Vector3 position; // position of the center.
    private Vector3 currentMovement;
    private Vector3 force;
    private Color color; // for drawing the body.

    //defined constructor.
    public Body(String name, double mass, double radius, Vector3 position, Vector3 currentMovement, Color color) {
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        this.position = position;
        this.currentMovement = currentMovement;
        this.color = color;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public Cluster add(Body c) {
        Cluster leftCluster = this;
        Cluster rightCluster = c;
        if (leftCluster.getMass() < rightCluster.getMass()) {
            Cluster buffer = leftCluster;
            leftCluster = rightCluster;
            rightCluster = buffer;
        }
        return new BalancedSystem(leftCluster, rightCluster);
    }

    @Override
    public int numberOfBodies() {
        return 1;
    }

    @Override
    public double getMass() {
        return this.mass;
    }

    @Override
    public Vector3 getMassCenter() {
        return this.position;
    }

    public void setForce(Vector3 force) {
        this.force = force;
    }

    // Returns the distance between this body and the specified 'body'.
    public double distanceTo(Body body) {
        return this.position.distanceTo(body.position);
    }

    //Returns a vector representing the gravitational force exerted by 'body' on this body.
    //The gravitational Force F is calculated by F = G*(m1*m2)/(r*r), with m1 and m2 being the masses of the objects
    //interacting, r being the distance between the centers of the masses and G being the gravitational constant.
    //To calculate the force exerted on b1, simply multiply the normalized vector pointing from b1 to b2 with the
    //calculated force
    public Vector3 gravitationalForce(Body body) {
        Vector3 direction = body.position.minus(this.position);
        double distance = direction.length();
        direction.normalize();
        double force = G*this.mass*body.mass/(distance * distance);
        return direction.times(force);
    }

    // Moves this body to a new position, according to the specified force vector 'force' exerted
    // on it, and updates the current movement accordingly.
    // (Movement depends on the mass of this body, its current movement and the exerted force)
    // Hint: see simulation loop in Simulation.java to find out how this is done
    public void move(Vector3 force) {
        Vector3 newPosition = this.position.plus(force.times(1/this.mass)).plus(currentMovement);
        this.currentMovement = newPosition.minus(this.position);
        this.position = newPosition;
    }
    public void move() {
        Vector3 newPosition = this.position.plus(this.force.times(1/this.mass)).plus(currentMovement);
        this.currentMovement = newPosition.minus(this.position);
        this.position = newPosition;
    }

    // Returns a string with the information about this body including
    // name, mass, radius, position and current movement. Example:
    // "Earth, 5.972E24 kg, radius: 6371000.0 m, position: [1.48E11,0.0,0.0] m, movement: [0.0,29290.0,0.0] m/s."
    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        double epsilon = 0.000001;
        Body body = (Body) o;
        return this.name.equals(body.name) && this.mass <= body.mass + epsilon && this.mass >= body.mass - epsilon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, (int)this.mass);
    }

    // Draws the body to the current StdDraw canvas as a dot using 'color' of this body.
    // The radius of the dot is in relation to the radius of the celestial body
    // (use a conversion based on the logarithm as in 'Simulation.java').
    // Hint: use the method drawAsDot implemented in Vector3 for this
    public void draw() {
        this.position.drawAsDot(this.radius, this.color);
    }

    public void drawLineTo(Body body) {
        StdDraw.line(this.position.getX(), this.position.getY(), body.position.getX(), body.position.getY());
    }

    public Body[] getBodies() {
        return new Body[]{this};
    }

    @Override
    public BodyIterator iterator() {
        return new MyBodyIterator2(this);
    }

    public class MyBodyIterator2 implements BodyIterator {
        Body b;
        boolean wasCalled = false;

        public MyBodyIterator2(Body b) {
            this.b = b;
        }

        @Override
        public boolean hasNext() {
            return !wasCalled;
        }

        @Override
        public Body next() {
            if (!wasCalled) {
                wasCalled = true;
                return b;
            }
            throw new IllegalStateException("No next body");
        }

        @Override
        public void remove() {

        }
    }
}


/*
#### Aufgabe 2

        Implementieren Sie in der Klasse `Body` eine Methode  `public void drawLineTo(Body body)`, die eine Linie von diesem Body zum Body `body` zeichnet. Rufen Sie in der Klasse `Simulation` die Methode so auf, dass eine Linie von Merkur zur Sonne und von der Erde zur Sonne gezeichnet wird.

        Hinweis:
        - zum Zeichnen einer Linie kann die Methode `StdDraw.line(double x1, double y1, double x2, double y2)` verwendet werden.
 */