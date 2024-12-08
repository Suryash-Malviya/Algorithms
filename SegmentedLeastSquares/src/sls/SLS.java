package sls;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SLS {
    private Point[] points;
    private double C;
    private double[] M;
    private double[][] errors;

    public SLS(Point[] points, double C) {
        this.points = points;
        this.C = C;
        this.M = new double[points.length];
        Arrays.fill(this.M, -1);
        this.errors = new double[points.length][points.length];
        computeErrors();
    }

    private void computeErrors() {
        for (int i = 0; i < points.length; i++) {
            for (int j = i; j < points.length; j++) {
                errors[i][j] = computeError(i, j, points);
            }
        }
    }

    private double computeError(int i, int j, Point[] points) {
        double error = 0.0;
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;
        int count = j - i + 1;

        for (int k = i; k <= j; k++) {
            sumX += points[k].x();
            sumY += points[k].y();
            sumXY += points[k].x() * points[k].y();
            sumX2 += points[k].x() * points[k].x();
        }

        double meanX = sumX / count;
        double meanY = sumY / count;
        double slope = (sumXY - sumX * meanY) / (sumX2 - sumX * meanX);
        double intercept = meanY - slope * meanX;

        for (int k = i; k <= j; k++) {
            double fittedY = slope * points[k].x() + intercept;
            error += (points[k].y() - fittedY) * (points[k].y() - fittedY);
        }

        return error;
    }

    public double topDown(int j) {
        if (j < 0) {
            return 0;
        }
        if (M[j] != -1) {
            return M[j];
        }

        double minCost = Double.MAX_VALUE;
        for (int i = 0; i <= j; i++) {
            double cost = errors[i][j] + C + topDown(i - 1);
            if (cost < minCost) {
                minCost = cost;
            }
        }
        M[j] = minCost;
        return minCost;
    }

    public double bottomUp() {
        double[] localM = new double[points.length + 1];
        localM[0] = 0;
        for (int j = 1; j <= points.length; j++) {
            double minCost = Double.MAX_VALUE;
            for (int i = 1; i <= j; i++) {
                double cost = errors[i - 1][j - 1] + C + localM[i - 1];
                if (cost < minCost) {
                    minCost = cost;
                }
            }
            localM[j] = minCost;
        }
        return localM[points.length];
    }

    
        public static void main(String[] args) {
//            
        	List<Point> pointList = RandomPoints.SinPoints(10, 2, 100);
            
            
            FileWriter writer = null;
            try {
                writer = new FileWriter("point_results.csv");
                writer.write("X,Y\n"); // Header
                
                for (Point point : pointList) {
                    writer.write(point.x() + "," + point.y() + "\n");
                }
                
                System.out.println("CSV file has been created successfully!");
                
            } catch (IOException e) {
                System.err.println("Error writing to CSV file: " + e.getMessage());
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e) {
                    System.err.println("Error closing the file: " + e.getMessage());
                }
            }
            
            Point[] points = pointList.toArray(new Point[0]);

            // Assuming a constant C for penalty in your SLS class
            double C = 10.0; 

            // Boolean array to check consistency between top down and bottom up results
            Boolean[] consistencyChecks = new Boolean[10000];  // Adjust size as needed based on range of subset sizes

            try (FileWriter writer1 = new FileWriter("C:\\Users\\surya\\Desktop\\segmented_least_squares_results___.csv")) {
                writer1.write("Subset Size, Average Bottom Up Time (ns), Average Top Down Time (ns), Consistency Check\n");

                // Loop to test multiple subset sizes
                for (int subsetSize = 10; subsetSize <= points.length; subsetSize += 10) {
                    if (subsetSize > points.length) break;

                    long totalBottomUpTime = 0;
                    long totalTopDownTime = 0;
                    boolean consistent = true;
                    int runs = 5;

                    for (int j = 0; j < runs; j++) {
                        Point[] pointSubset = new Point[subsetSize];
                        System.arraycopy(points, 0, pointSubset, 0, subsetSize);

                        SLS bottomUpSLS = new SLS(pointSubset, C);
                        long startTime = System.nanoTime();
                        double bottomUpResult = bottomUpSLS.bottomUp();
                        long bottomUpTime = System.nanoTime() - startTime;
                        totalBottomUpTime += bottomUpTime;

                        SLS topDownSLS = new SLS(pointSubset, C);
                        startTime = System.nanoTime();
                        double topDownResult = topDownSLS.topDown(pointSubset.length - 1);
                        long topDownTime = System.nanoTime() - startTime;
                        totalTopDownTime += topDownTime;

                        if (bottomUpResult != topDownResult) {
                            consistent = false;
                        }
                    }

                    long averageBottomUpTime = totalBottomUpTime / runs;
                    long averageTopDownTime = totalTopDownTime / runs;

                    writer1.write(subsetSize + ", " + averageBottomUpTime + ", " + averageTopDownTime + ", " + consistent + "\n");
                    consistencyChecks[(subsetSize / 10) - 1] = consistent;
                }
            } catch (IOException e) {
                System.err.println("Error writing to CSV file");
                e.printStackTrace();
            }

            System.out.println("CSV file with timing results has been created.");

            // Optionally print the consistency checks
            for (int i = 0; i < consistencyChecks.length; i++) {
                if (consistencyChecks[i] != null) {
                    System.out.println("Consistency check at size " + ((i + 1) * 10) + ": " + consistencyChecks[i]);
                }
            }
        }
    }
