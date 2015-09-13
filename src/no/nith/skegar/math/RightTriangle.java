package no.nith.skegar.math;

import java.awt.geom.Point2D;

/**
 * Class that simulates a right triangle
 */
public class RightTriangle 
{
	private static Point2D.Double catheti = new Point2D.Double();
	private static double hypotenuse = 0;
	
	public static Point2D.Double getCatheti()
	{
		return catheti;
	}
	
	public static double getHypotenuse()
	{
		return hypotenuse;
	}
	
	/**
	 * Calculates the cathetis of a right triangle
	 */
	public static void calculateCathetis(double distance, double absBearing, Point2D.Double myPos)
	{
		
		catheti.x = myPos.getX() + distance * Math.sin(Math.toRadians(absBearing));
		catheti.y = myPos.getY() + distance * Math.cos(Math.toRadians(absBearing));
	}
	
	/**
	 * Calculates the hypotenuse of a right triangle
	 */
	public static void calculateHypotenuse(Point2D.Double catheti)
	{
		hypotenuse = Math.sqrt(Math.pow(catheti.getX(), 2) + Math.pow(catheti.getY(), 2));
	}
}
