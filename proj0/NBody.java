public class NBody {
	public static double readRadius(String str) {
		In in = new In(str);
		double radius = in.readDouble();
		radius = in.readDouble();
		return radius;
	}
}