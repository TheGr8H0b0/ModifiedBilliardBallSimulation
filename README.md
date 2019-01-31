# ModifiedBilliardBallSimulation
This project is a modified verion of the mathematical Billiard Ball problem. 
This simulation has been created to better understand the mathematics behind what is happening using the four rules.

Rules:
1: Start with a unit square (eventually to be grown to any unit shape - triangle, square, pentagon, all the way up to a circle) 
2: From the corner that is closest to the x,y origin project a line at any degree angle (for a square anything outside of 0-90 is arbitrary)
3: The line reflects off of the edges of the shape
4: The line reflects off of itself

Using these four rules this simulation has been created. The way these lines interact when reflecting off of each other has already been calculated and proven that they behave the same in all situations. It has already been proven that these lines will eventually converge to a point after an infinite number of reflections, so there are technically "end points" for each of these lines.
Current questions being worked on:
1 - Are the "end points" continuous?
  Hypothesis: No, though we greatly want it to be continuous
2 - Can we calculate an endpoint mathematically if we know the input angle? How?
3 - If we take the integral from each line being created to the line(s) below it (with the lower boundary being the bottom of the square) do the infintesimally small integrals converge? If so, do the sums of these integrals add up to a common number? 
  Hypothesis: Small integrals converge, and the sums of the number add up to 1.
