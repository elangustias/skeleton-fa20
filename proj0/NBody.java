public class NBody {
	public static double readRadius(String str) {
		In in = new In(str);
		double radius;
		radius = in.readDouble();
		radius = in.readDouble();
		return radius;
	}
	public static Body[] readBodies(String str) {
		In in = new In(str);
		/* Throwaways for first 2 nums */
		in.readDouble();
		in.readDouble();
		Body[] bodies = new Body[5]; // can this be generalized further???
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
		StdDraw.setScale(-radius, radius);
		StdDraw.picture(0, 0, "images/starfield.jpg");
		for (Body b : bodies) {
			b.draw();
		}
		double time = 0;
		StdDraw.enableDoubleBuffering(); // might need to go somewhere else
		while (time <= T) {
			double[] xForces = new double[5]; // can this be generalized further???
			double[] yForces = new double[5]; // can this be generalized further???
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
			StdDraw.pause(10); //milliseconds, might bug
			time += dt;
		}
	}
}