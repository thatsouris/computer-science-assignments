package RainbowAssignment.src;
// Chapter 5 Question 27

/*
 * Modified by Aidan O'Quinn
 * October 28th, 2022
*/
/**
 * Creates a rainbow graphic by using instructions given in the textbook.
 * Draws four semi-circles, overlapping each other to create rings for
 * the rainbow.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Rainbow extends JPanel
{
  // Declare skyColor:
  Color skyColor = new Color(255, 255, 255);

  public Rainbow()
  {
    setBackground(skyColor);
  }

  // Draws the rainbow.
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    int width = getWidth();    
    int height = getHeight();

    int largeRadius = width / 4;
    int smallRadius = height / 4;
    int midRadius = (int) Math.sqrt((double) largeRadius * smallRadius);
    int xCenter = (width / 2) - (largeRadius);
    int yCenter = (int) (height * (3d/4d)) - (largeRadius);

    g.setColor(Color.RED);
    g.fillArc(xCenter, yCenter, largeRadius*2, largeRadius*2, 0, 180);

    yCenter += (largeRadius) - (midRadius);
    xCenter = (width / 2) - (midRadius);

    g.setColor(Color.GREEN);
    g.fillArc(xCenter, yCenter, midRadius * 2, midRadius * 2, 0, 180);

    yCenter += (midRadius) - (smallRadius);
    xCenter = (width / 2) - (smallRadius);

    g.setColor(Color.MAGENTA);
    g.fillArc(xCenter, yCenter, smallRadius * 2, smallRadius * 2, 0, 180);

    // Calculate the radius of the innermost (sky-color) semicircle
    // so that the width of the middle (green) ring is the
    // arithmetic mean of the widths of the red and magenta rings:
    /*
     *
     * The delta between corresponding radii determines the ring width
     * 
     * 
     * ((b-c) / 2) = ((a-b) / 2) + ((c-d) / 2)
     * Solve for d, such that a, b, c, d are the radii of the red, 
     *    green, magenta, and sky semi-circles respectively.
     * 
     * b - c = ((a-b) / 2) + ((c-d) / 2)
     * 2(b-c) = a - b + c - d
     * 
     * d = a - 3b + 3c
     * 
     * Therefore, the radius of the sky colored semi-circle is
     * the largest radius, minus three times the middle radius,
     * plus 3 times the smallest radius.
     */

    int skyRadius = largeRadius - (3*midRadius) + (3*smallRadius);

    yCenter += (smallRadius) - (skyRadius);
    xCenter = (width / 2) - (skyRadius);

    // Draw the sky-color semicircle:
    g.setColor(skyColor);
    g.fillArc(xCenter, yCenter, skyRadius*2, skyRadius*2, 0, 180);
  }

  public static void main(String[] args)
  {
    JFrame w = new JFrame("Rainbow");
    w.setBounds(300, 300, 600, 400); // Adjusted width and height to create a better viewing experience.
    w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container c = w.getContentPane();
    c.add(new Rainbow());
    w.setVisible(true);
  }
}
