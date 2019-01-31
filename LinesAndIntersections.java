package square_simulation;


import java.util.ArrayList;


public class LinesAndIntersections {

	private double degreeofPerfection = 0.0000000001;
	private boolean printLineInfo = true;

	public void seeLineInfo(boolean print) {
		printLineInfo = print;
	}

	/*
	 * Returns the point of intersection of two lines 
	 * null is returned if they don't intersect
	 */
	public double[] findIntersection(PolarLine line1, PolarLine line2) {
		//Calculate the determinant of the line
		double det = -(getXDiff(line1)*getYDiff(line2)-getYDiff(line1)*getXDiff(line2));
		//Calculate the x determinant
		double detx = getC(line1)*getYDiff(line2) - getYDiff(line1)*getC(line2);
		//Calculate the y determinant
		double dety = getXDiff(line1)*getC(line2) - getC(line1)*getXDiff(line2);

		//If the determinant is not 0 then the lines intersect
		if (det != 0) {
			double xIntersect = detx/det;
			double yIntersect = dety/det;
			return new double[] {xIntersect, yIntersect};
		} else {
			return new double[] {-100,-100};
		}
	}

	public double getXDiff(PolarLine line) {
		return line.startpt[1] - line.endpt[1];
	}

	public double getYDiff(PolarLine line) {
		return line.endpt[0] - line.startpt[0];
	}

	//I forget the name of what this is doing, but it works...
	public double getC(PolarLine line) {
		return line.startpt[0]*line.endpt[1] - line.endpt[0]*line.startpt[1];
	}

	/**
	 * Finds the closest line intersection to the current line by comparing it to all the other lines that it
	 * intersects with
	 * @param intersections An ArrayList of double[] that contains the x,y coordinates of all the intersections for this line
	 * @param currentLine The current line 
	 * @return
	 */
	public double[] findClosestIntersection(ArrayList<double[]> intersections, PolarLine currentLine) {
		double shortestLength = 5;
		int cordEntry = -1;
		//Checks all intersections that are inside the unit square
		for (int i = 0; i < intersections.size(); i++) {
			if (Math.toDegrees(currentLine.angle) >= 180 && Math.toDegrees(currentLine.angle) <= 360 
					&& currentLine.startpt[1] - intersections.get(i)[1] >= 0) {
				double length = Math.sqrt(Math.pow((currentLine.startpt[0]-intersections.get(i)[0]),2)+Math.pow((currentLine.startpt[1]-intersections.get(i)[1]),2));
				if (length < shortestLength && length >= degreeofPerfection) {
					shortestLength = length;
					cordEntry = i;
				}
			} else if (Math.toDegrees(currentLine.angle) < 180 && Math.toDegrees(currentLine.angle) >= 0 
					&& currentLine.startpt[1] - intersections.get(i)[1] <= 0) {
				double length = Math.sqrt(Math.pow((currentLine.startpt[0]-intersections.get(i)[0]),2)+Math.pow((currentLine.startpt[1]-intersections.get(i)[1]),2));
				if (length < shortestLength && length >= degreeofPerfection) {
					shortestLength = length;
					cordEntry = i;	
				}
			}
		}

		if (cordEntry >= 0) {
			currentLine.updateLength(shortestLength);
			double[] intersectData = new double[] {intersections.get(cordEntry)[0],intersections.get(cordEntry)[1],cordEntry};
			return intersectData;
		} else {
			return null;
		}
	}

	/**
	 * Creates a new line based off of the collission data given
	 * @param intersectPoint - The point of intersection where 
	 * 			intersectPoint[0] = x-coordinate and intersectPoint[1] = y-coordinate
	 * @param currentLine - The current PolarLine that will be creating the reflection
	 * @param collissionLine - The PolarLine that is reflected off of
	 * @return - a new PolarLine with the appropriate angle of reflection and a length > Math.sqrt(2)
	 */
	public PolarLine createCollissionLine(double[] intersectPoint, PolarLine currentLine, PolarLine collissionLine) {
		//Calculates the new angle of reflection based off of the positive x-axis
		double newAngle = 2*Math.toDegrees(collissionLine.angle) - Math.toDegrees(currentLine.angle);
		newAngle = (newAngle + 360)%360;
		PolarLine newLine = new PolarLine(newAngle,intersectPoint);
		return newLine;
	}

	/**
	 * Runs a simulation starting with a certain degree angle to start with and creates numHits line
	 * @param inputDegrees - The angle of degrees you start with
	 * @param numHits - The number of lines to be created
	 * @return - Returns ArrayList<double[]> of the intersections needed to graph the line 
	 * 				where double[0] = x-coordinate and double[1] = y-coordinate
	 */
	public ArrayList<double[]> startWithLine(double inputDegrees, int numHits) {
		ArrayList<PolarLine> lines = new ArrayList<PolarLine>();

		//Creates the lines for the square
		PolarLine squareBot = new PolarLine(1, 0, new double[] {0,0, -4});
		PolarLine squareRightSide = new PolarLine(1, 90, new double[] {1,0, -3});
		PolarLine squareTop = new PolarLine(1, 180, new double[] {1,1, -2});
		PolarLine squareLeftSide = new PolarLine(1, 270, new double[] {0,1, -1});
		lines.add(squareBot);
		lines.add(squareRightSide);
		lines.add(squareTop);
		lines.add(squareLeftSide);


		PolarLine startLine = new PolarLine(inputDegrees, new double[] {0,0, 0});
		lines.add(startLine);

		PolarLine currentLine = startLine;
		ArrayList<double[]> usefulIntersections = new ArrayList<double[]>();
		usefulIntersections.add(startLine.startpt);
		for (int i = 0; i < numHits; i++) {
			ArrayList<double[]> intersections = new ArrayList<double[]>();
			//Removes unnecessary lines when they are bouncing back and forth between two parallel lines
			/*TODO: Make sure that line information is corrected accordingly. Removing lines has intersections
			 			printing the "wrong line" that it hits because previous lines are gone*/
			if (lines.size() >= 4 && lines.get(lines.size()-5).angle == lines.get(lines.size()-3).angle
					&& lines.get(lines.size()-4).angle == lines.get(lines.size()-2).angle) {
				lines.remove(lines.size()-5);
				lines.remove(lines.size()-4);
			} 
			//Adds every line intersection into the intersections array
			for (int j = 0; j < lines.size()-1; j++) {
				intersections.add(findIntersection(currentLine, lines.get(j)));
			}
			//Grabs the closest line intersection to the current line, returning the x,y coordinate and the line it hits
			double[] cordAndLineData = findClosestIntersection(intersections, currentLine);
			//Prints the line info if told to 
			if (printLineInfo) {
				System.out.print("Line " + (i+1) + " begins at (" + currentLine.startpt[0] + ",  " + currentLine.startpt[1]
						+ ") with an angle of " + Math.toDegrees(currentLine.angle) + "\n \t and ends at (" +  currentLine.endpt[0] + ",  " + + currentLine.endpt[1] + ")"
						+ " by colliding with line: ");
				try {
					System.out.println((cordAndLineData[2]-3));
					System.out.println("");
					//Adds the end-point of the current line (which is where it intersects with the other line)
					usefulIntersections.add(currentLine.endpt);
					//Sets the current line to be the new line created by the collision
					currentLine = createCollissionLine(new double[] {cordAndLineData[0],cordAndLineData[1]},currentLine, lines.get((int)cordAndLineData[2]));
					//Adds the new current line to the array of lines 
					lines.add(currentLine);
				}
				//If an exception occurs then it simply returns the points that is has already calculated
				//*Note: The exception probably occurs because of a perpendicular intersection
				catch (Exception e) {
					return usefulIntersections;
				}
			} else {
				try {
					//Adds the end-point of the current line (which is where it intersects with the other line)
					usefulIntersections.add(currentLine.endpt);
					//Sets the current line to be the new line created by the collision
					currentLine = createCollissionLine(new double[] {cordAndLineData[0],cordAndLineData[1]},currentLine, lines.get((int)cordAndLineData[2]));
					//Adds the new current line to the array of lines 
					lines.add(currentLine);
				} 
				//If an exception occurs then it simply returns the points that is has already calculated
				//*Note: The exception probably occurs because of a perpendicular intersection
				catch (Exception e) {
					return usefulIntersections;
				}
			}
		}
		return usefulIntersections;
	}
}
