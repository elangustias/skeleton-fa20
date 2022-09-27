public class NBody {
	public static double readRadius(String str) {
		In in = new In(str);
		double radius = in.readDouble();
		radius = in.readDouble();
		return radius;
	}
	public static Body[] readBodies(String str) {
		In in = new In(str);
		/* Throwaways for first 2 nums */
		in.readDouble();
		in.readDouble();
		Body[] bodies = new Body[5];
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
		readBodies(filename);
		readRadius(filename);
	}
}