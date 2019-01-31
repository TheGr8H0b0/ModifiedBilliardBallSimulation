package square_simulation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class RunOverlayAngleEnds extends JComponent{

	private static final long serialVersionUID = 1L;

	// java - get screen size using the Toolkit class
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	//Set the size of the popout display
	private static int sizex = screenSize.width/2;
	private static int sizey = (int)(sizex/2 + screenSize.height/4);
	
	
	//Used to calculate where things need to be drawn
	private static ArrayList<double[]> intersectArray = new ArrayList<double[]>();
	private static ArrayList<double[]> intersectionList =  new ArrayList<double[]>();

	/**
	 * Draws the components every time any paint command is called
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Draws the square
		g.drawLine(0        , sizey - 0        , 1 * sizex, sizey - 0);
		g.drawLine(1 * sizex, sizey - 0        , 1 * sizex, sizey - 1 * sizey);
		g.drawLine(1 * sizex, sizey - 1 * sizey, 0 * sizex, sizey - 1 * sizey);
		g.drawLine(0        , sizey - 1 * sizey, 0        , sizey - 0);

		//Draws the endpoints
		g.setColor(new Color(0,0,0));
		for (int i = 1; i < intersectArray.size(); i++) {
			g.drawOval((int)(intersectArray.get(i-1)[0]*sizex), sizey - (int)(intersectArray.get(i-1)[1]*sizey), 5, 5);
		}
		
		//Draws the current line that created the last endpointS
		g.setColor(new Color(255,0,187));
		for (int j = 1; j < intersectionList.size(); j++) {
			g.drawLine((int)(intersectionList.get(j-1)[0]*sizex), sizey - (int)(intersectionList.get(j-1)[1]*sizey), (int)(intersectionList.get(j)[0]*sizex), sizey - (int)(intersectionList.get(j)[1]*sizey));
		}
	}

	/**
	 * Repaints the display
	 * @param instance The instance of RunOverlayAngleEnds to repaint
	 */
	private void paintPoints(RunOverlayAngleEnds instance) {
		instance.repaint();
	}

	/**
	 * Runs the enpoint printing with graphic line overlay
	 * @param args
	 */
	public static void main(String[] args) {
		LinesAndIntersections runSim = new LinesAndIntersections();

		//Asks the user for input values needed to run the program
		Scanner keyboard = new Scanner(System.in);
		System.out.println("What is the start angle? (Input a double from 0 to 90 degrees)");
		double startAngle = keyboard.nextDouble();
		System.out.println("What is the end angle? (Input a double from 0 to 90 that is greater than the previous value)");
		double endAngle = keyboard.nextDouble();
		System.out.println("What is degree of change? (Input a double that is less than the difference between the last two values)");
		double degreeChange = keyboard.nextDouble();
		keyboard.close();

		//Do you want to see the line info? If yes: true
		runSim.seeLineInfo(false);

		//Creates an instance of itself
		RunOverlayAngleEnds test = new RunOverlayAngleEnds();
		
		//Used to display the current angle being drawn
		JLabel currentDegrees = new JLabel("The current degrees is: ");
		currentDegrees.setFont(new Font("Serif", Font.PLAIN, 70));
		
		//Creates the Java Frame window that pops out
		JFrame testFrame = new JFrame();
		testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//Sets the size of the display
		test.setPreferredSize(new Dimension(1 * sizex + (int)sizex/100, 1 * sizey + (int)sizey/100));
		//Adds the two parts (Our drawing and the label text of current degrees)
		testFrame.getContentPane().add(test, BorderLayout.CENTER);
		testFrame.getContentPane().add(currentDegrees, BorderLayout.SOUTH);
		
		//Sets the size of the window properly
		testFrame.pack();
		//Allows us to see the frame
		testFrame.setVisible(true);

		//Runs a loop from the start angle to the end angle with degree change being the difference 
		//between each iteration
		for (double i = startAngle; i <= endAngle; i += degreeChange) {
			/*This is implemented because 1000 lines won't cover the amount of precision needed to find
			the end-points between these values. Technically the lines needed would have to be infinity
			when you are infinitesimally off of 45 on either side*/
			if (i < 44.1 || i > 45.9) {
				//Runs a simulation with the angle i and calculates 1000 lines
				intersectionList = runSim.startWithLine(i, 1000);
				//Adds the final point of the run simulation to our endpoint array
				intersectArray.add(intersectionList.get(intersectionList.size()-1));
				//Sets the current degrees text to display the current degrees
				currentDegrees.setText("Current degrees: " + i);
				//Repaints the frame
				test.paintPoints(test);
			}
		}
	} 
}
