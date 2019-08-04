# ModifiedBilliardBallSimulation
This project is a modified verion of the mathematical Billiard Ball problem (http://mathworld.wolfram.com/Billiards.html).
This simulation has been created to better understand the mathematics behind what is happening using the following rules.

Rules:

1: Start with any regular polygon. (We modified the shapes to make sure they always fit within a 1 by 1 square in the x,y plane)

2: From one of the corners, project a line into the shape and mark down it's angle. (We measure the angles of all lines off of the positive x-axis)

3: The line reflects off of the edges of the shape, the same way it does in the Billiard Ball Problem. The difference we've added is the fourth rule below:

4: The line reflects off of itself

Using these four rules this simulation has been created. The way these lines interact when reflecting off of each other has already been calculated and proven that they behave the same in all situations (2 * Ø - µ where Ø is the line being reflected off of and µ is the incidence ray). It has also been proven that the area created by these reflections converges, which means that the reflections eventually converge to a single point inside the shape. 

All of this was coded from scratch and was done in our free time. Many different sections of code were tacked on as different ideas to test came up, so the next step is to go back through the code and rewrite it all to be significantly more efficient. Initially we only cared about coming up with a small simulation to see if our math was correct, but it has become clear we now need to be able to run simulations of this project overnight to a high degree of accuracy in order to get back large quantities of reflection points and end points.
