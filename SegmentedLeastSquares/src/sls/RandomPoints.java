package sls;

import java.util.ArrayList;
import java.util.List;

public class RandomPoints {
	
	public static List<Point> SinPoints(double wavelength, double amp, double num){
        List<Point> points = new ArrayList<>();
		double step = 100 / num;
		
		
		for(double x = 0; x <= 100; x+=step ) {
			
			double y = Math.sin(2 * Math.PI / wavelength * x);
			
			for(int r = 0; r < 10; r++) {
				double randomness = (Math.random() - 0.5) * 2 * amp;
				double yWithRandomness = y + randomness;
				
				points.add(new Point(x, yWithRandomness));
			}
		}
		
		return points;
		
		
	}

}
