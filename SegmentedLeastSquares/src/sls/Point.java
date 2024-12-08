package sls;

public class Point implements Comparable<Point> {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    
    @Override
    public int compareTo(Point other) {
        return Double.compare(this.x, other.x);
    }
    
}