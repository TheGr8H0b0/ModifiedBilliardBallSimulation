package square_simulation;

public class PolarLine {

	public double[] startpt;
	public double[] endpt = new double[] {0,0};
	public double length = 0;
	public double angle;
	
	private boolean updatability = true;

	PolarLine(double degrees, double[] origin) {
		double rad = Math.toRadians(degrees);
		endpt[0] = 2 * Math.cos(rad) + origin[0];
		endpt[1] = 2 * Math.sin(rad) + origin[1];
		startpt = origin;
		angle = rad;
	}
	
	PolarLine(double l, double degrees, double[] origin) {
		double rad = Math.toRadians(degrees);
		endpt[0] = l * Math.cos(rad);
		endpt[1] = l * Math.sin(rad);
		endpt[0] = ((int) endpt[0] * Math.pow(10, 17))/Math.pow(10, 17) + origin[0];
		endpt[1] = ((int) endpt[1] * Math.pow(10, 17))/Math.pow(10, 17) + origin[1];
		startpt = origin;
		angle = rad;
		length = l;
		updatability = false;
	}
	
	public void updateLength(double l) {
		if (updatability) {
			length = l;
			endpt[0] = l * Math.cos(angle) + startpt[0];
			endpt[1] = l * Math.sin(angle) + startpt[1];
			updatability = false;
		}
	}
}
