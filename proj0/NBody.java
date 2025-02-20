public class NBody {
	/** Wasn't part of assignment but trying to make a new method numPlanets bc there are
	 *  a coule of areas where you need that data if you're going to generalize the program.
	 *  Hopefully it won't bug out.
	 */
	public static int readNumPlanets(String str) {
		In in = new In(str);
		int numPlanets = in.readInt();
		return numPlanets;
	}
	public static double readRadius(String str) {
		In in = new In(str);
		in.readDouble(); //throws away numPlanets
		double radius = in.readDouble();
		return radius;
	}
	public static Body[] readBodies(String str) {
		In in = new In(str);
		int numPlanets = in.readInt(); // I don't need readNumPlanets method here
		in.readDouble(); // radius is a throwaway here
		Body[] bodies = new Body[numPlanets];
		for (int i = 0; i < bodies.length; i += 1) {
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String img = in.readString();
			bodies[i] = new Body(xxPos, yyPos, xxVel, yyVel, mass, img);
		}
		return bodies;
	}
	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = readRadius(filename);
		Body[] bodies = readBodies(filename);
		int numPlanets = readNumPlanets(filename); // IT WORKED!
		StdDraw.setScale(-radius, radius);
		StdDraw.picture(0, 0, "images/starfield.jpg");
		for (Body b : bodies) {
			b.draw();
		}
		double time = 0;
		StdDraw.enableDoubleBuffering();
		while (time <= T) {
			double[] xForces = new double[numPlanets];
			double[] yForces = new double[numPlanets];
			for (int i = 0; i < bodies.length; i += 1) {
				xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
				yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
			}
			for (int i = 0; i < bodies.length; i += 1) {
				bodies[i].update(dt, xForces[i], yForces[i]);
			}
			StdDraw.picture(0, 0, "images/starfield.jpg");
			for (Body b : bodies) {
				b.draw();
			}
			StdDraw.show();
			StdDraw.pause(10); // 10 milliseconds
			time += dt;
		}
		StdOut.printf("%d\n", bodies.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
    		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  			bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                  			bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
}

	}
}