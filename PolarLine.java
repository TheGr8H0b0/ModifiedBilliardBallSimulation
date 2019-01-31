package square_simulation;

public class PolarLine {

	public double[] startpt;
	public double[] endpt = new double[] {0,0};
	public double length = 0;
	public double angle;
	
	//Only allows the line length to be updated once
	private boolean updatability = true;

	/**
	 * Creates a polar line with a set angle, and start point
	 * @param degrees - The degrees off of the positive x-axis the line will be projected at
	 * @param origin - The origin of the line in [x,y] coordinates
	 */
	PolarLine(double degrees, double[] origin) {
		double rad = Math.toRadians(degrees);
		endpt[0] = 2 * Math.cos(rad) + origin[0];
		endpt[1] = 2 * Math.sin(rad) + origin[1];
		startpt = origin;
		angle = rad;
	}
	
	/**
	 * Creates a polar line with a set length, angle, and start point
	 * @param l - the length of the line
	 * @param degrees - The degrees off of the positive x-axis the line will be projected at
	 * @param origin - The origin of the line in [x,y] coordinates
	 */
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
	
	/**
	 * Updates the length of the line
	 * @param l - the new length of the line to be set to
	 */
	public void updateLength(double l) {
		if (updatability) {
			length = l;
			endpt[0] = l * Math.cos(angle) + startpt[0];
			endpt[1] = l * Math.sin(angle) + startpt[1];
			updatability = false;
		}
	}
}
