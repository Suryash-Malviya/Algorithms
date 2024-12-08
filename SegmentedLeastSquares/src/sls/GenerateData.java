package sls;

import java.util.*;

public class GenerateData {

    private static Random random = new Random();

    public static List<Point> generateRandomData(int numPoints, double xMin, double xMax, double yMin, double yMax) {
        List<Point> points = new ArrayList<>();

        // Generate random points
        for (int i = 0; i < numPoints; i++) {
            double x = xMin + (xMax - xMin) * random.nextDouble();
            double y = yMin + (yMax - yMin) * random.nextDouble();
            points.add(new Point(x, y));
        }

        // Sort points based on x values
        Collections.sort(points);

        return points;
    }
	
}
