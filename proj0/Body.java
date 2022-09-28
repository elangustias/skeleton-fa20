public class Body {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	public Body(double xP, double yP, double xV,
				double yV, double m, String img) {
		this.xxPos = xP;
		this.yyPos = yP;
		this.xxVel = xV;
		this.yyVel = yV;
		this.mass = m;
		this.imgFileName = img;
	}
	public Body(Body b) {
		this.xxPos = b.xxPos;
		this.yyPos = b.yyPos;
		this.xxVel = b.xxVel;
		this.yyVel = b.yyVel;
		this.mass = b.mass;
		this.imgFileName = b.imgFileName;
	}
	public double calcDistance(Body b) {
		double xxDist = b.xxPos - this.xxPos;
		double yyDist = b.yyPos - this.yyPos;
		return Math.sqrt((xxDist * xxDist) + (yyDist * yyDist));
	}
	public double calcForceExertedBy(Body b) {
		double g = 6.67e-11;
		double dist = this.calcDistance(b);
		return g * this.mass * b.mass / (dist * dist);
	}
	public double calcForceExertedByX(Body b) {
		double force = this.calcForceExertedBy(b);
		double xxDist = b.xxPos - this.xxPos;
		double dist = this.calcDistance(b);
		return force * xxDist / dist;
	}
	public double calcForceExertedByY(Body b) {
		double force = this.calcForceExertedBy(b);
		double yyDist = b.yyPos - this.yyPos;
		double dist = this.calcDistance(b);
		return force * yyDist / dist;
	}
	public double calcNetForceExertedByX(Body[] a) {
		double netForce = 0.0;
		for (Body x : a) {
			if (this.equals(x)) {
				continue;
			}
			netForce += this.calcForceExertedByX(x);
		}
		return netForce;
	}
	public double calcNetForceExertedByY(Body[] a) {
		double netForce = 0.0;
		for (Body y : a) {
			if (this.equals(y)) {
				continue;
			}
			netForce += this.calcForceExertedByY(y);
		}
		return netForce;
	}
	public void update(double dt, double fX, double fY) {
		/** accelX = Fnetx/mass
		 * 	accelY = Fnety/mass
		 * 	example - Saturn's accelX = -2e22 (FnetX) / 6e26 (mass) = 0.3333e-4
		 */
		double xxAccel = fX/this.mass;
		double yyAccel = fY/this.mass;
		this.xxVel = this.xxVel + dt * xxAccel;
		this.yyVel = this.yyVel + dt * yyAccel;
		this.xxPos = this.xxPos + dt * this.xxVel;
		this.yyPos = this.yyPos + dt * this.yyVel;
	}
	public void draw() {
		StdDraw.picture(this.xxPos, this.yyPos, "images/"+this.imgFileName);
	}
}
