public class Simulation {

    // gravitational constant
    public static final double G = 6.6743e-11;

    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;

    // all quantities are based on units of kilogram respectively second and meter.

    // The main simulation method using instances of other classes.
    public static void main(String[] args) {

        //changed implementation of this method according to 'Aufgabe1.md'.

        Body sun = new Body(
                "Sol",
                1.989e30,
                696340e3,
                new Vector3(0,0,0),
                new Vector3(0,0,0),
                StdDraw.YELLOW
        );

        Body earth = new Body(
                "Earth",
                5.972e24,
                6371e3,
                new Vector3(148e9,0,0),
                new Vector3(0,29.29e3,0),
                StdDraw.BLUE
        );

        Body mercury = new Body(
                "Mercury",
                3.301e23,
                2.4397e3,
                new Vector3(-46.0e9, 0, 0),
                new Vector3(0, -47.87e3,0),
                StdDraw.RED
        );

        Body mars = new Body(
                "Mars",
                6.39e23,
                3389e3,
                new Vector3(207.7e9, 0,0),
                new Vector3(0, 24e3, 0),
                StdDraw.ORANGE
        );

        Body asteroid = new Body(
                "Asteroid",
                2.39e25,
                389e3,
                new Vector3(120e10, 46e9,0),
                new Vector3(-50e3, 2.1e3, 0),
                StdDraw.WHITE
        );


        Body[] bodies = new Body[] {earth, sun, mercury, mars, asteroid};
        Vector3[] forceOnBody = new Vector3[bodies.length];

        StdDraw.setCanvasSize(500, 500);
        StdDraw.setXscale(-2*AU,2*AU);
        StdDraw.setYscale(-2*AU,2*AU);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);

        double seconds = 0;

        // simulation loop
        while(true) {

            seconds++; // each iteration computes the movement of the celestial bodies within one second.

            // for each body (with index i): compute the total force exerted on it.
            for (int i = 0; i < bodies.length; i++) {
                forceOnBody[i] = new Vector3(0,0,0); // begin with zero
                for (int j = 0; j < bodies.length; j++) {
                    if (i == j) continue;
                    Vector3 forceToAdd = bodies[i].gravitationalForce(bodies[j]);
                    forceOnBody[i] = forceOnBody[i].plus(forceToAdd);
                }
            }
            // now forceOnBody[i] holds the force vector exerted on body with index i.

            // for each body (with index i): move it according to the total force exerted on it.
            for (int i = 0; i < bodies.length; i++) {
                bodies[i].move(forceOnBody[i]);
            }

            // show all movements in StdDraw canvas only every 3 hours (to speed up the simulation)
            if (seconds%(3*3600) == 0) {
                // clear old positions (exclude the following line if you want to draw orbits).
                StdDraw.clear(StdDraw.BLACK);

                // draw new positions
                for (Body body : bodies) {
                    body.draw();
                }

                // show new positions
                StdDraw.show();
            }

        }

    }

    //removed static methods below.
}

//answered additional questions of 'Aufgabe1'.

/*
* Datenkapselung ist das Zusammenfassen von Variablen und Funktionen in einem einzelnen Objekt
* Ein Beispiel hierfür ist die berechnung der Gravitation auf ein Objekt, welches nach den Änderungen im Objekt
* selbst geschieht. Somit sind die Attribute und die passenden Funktionen nun an einem Ort gesammelt, dem Objekt
*
* Data Hiding ist die beschränkung des Zugriffes auf Variablen (und Funktionen) in einem Objekt von außen
* Ein Beispiel hierfür sind die Variablen/Attribute der Klasse Body, welche nun privat sind, und nur über den
* Konstruktor initialisiert, und durch public Funktionen geändert werden können.
*/


