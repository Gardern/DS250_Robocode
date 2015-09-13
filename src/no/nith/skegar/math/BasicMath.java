package no.nith.skegar.math;

import java.awt.geom.Point2D;

/**
 * Class that contains som basic math functions
 */
public class BasicMath 
{
	/**
	 * Normalizes the bearing
	 */
	public static double normalizeBearing(double ang) 
	{
		if (ang > Math.PI)
		{
			ang -= 2 * Math.PI;
		}
		if (ang < -Math.PI)
		{
			ang += 2 * Math.PI;
		}
		return ang;
	}
	
	/**
	 * Returns the absolute bearing between two coordinates
	 */
	public static double absoluteBearing(double x1, double y1, double x2, double y2)
	{
		double h = Point2D.Double.distance(x1, y1, x2, y2);
		double xo = x2-x1;
		double yo = y2-y1;
		
		if(xo > 0 && yo > 0)
		{
			return Math.asin(xo / h);
		}
		if(xo > 0 && yo < 0)
		{
			return Math.PI - Math.asin(xo / h);
		}
		if(xo < 0 && yo < 0)
		{
			return Math.PI + Math.asin(-xo / h);
		}
		if(xo < 0 && yo > 0)
		{
			return 2.0*Math.PI - Math.asin(-xo / h);
		}
		
		return 0;
	}
}
