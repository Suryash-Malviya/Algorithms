package rna;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;



public class Test {

	private static final String[] NUCLEOTIDES = {"A", "U", "G", "C"};
    private static final Random rand = new Random();
	

    
    private static RNAStructure generateRandomRNAStructure(int length) {
        ArrayList<String> sequence = new ArrayList<>();
        for (int i = 0; i < length; i++) {
        	sequence.add(NUCLEOTIDES[rand.nextInt(NUCLEOTIDES.length)]);
        }
        return new RNAStructure(sequence);
    }
    
    public static RNAStructure[] genrateRNASequnceList(int items, int length) {
    	
    	RNAStructure[] lst = new RNAStructure[items];
    	
    	for (int i = 0; i < items; i++) {
    		lst[i] = generateRandomRNAStructure(length);
    	}
    	
    	return lst;
    }
    
    public static void main(String[] args) {
        int MAX_LENGTH = 1000; 
        int STEP_SIZE = 10; 
        int NUM_RUNS = 5; 

        try (FileWriter writer = new FileWriter("rna_time_complexity.csv")) {
            writer.write("Sequence Length, Average Generation Time (ns), Average Bottom-Up Time (ns), Average Top-Down Time (ns)\n");

            for (int length = 10; length <= MAX_LENGTH; length += STEP_SIZE) {
               
                long totalBottomUpTime = 0;
                long totalTopDownTime = 0;
                int bUpSolution = 0;
                int tDownSolution = 0;

                for (int run = 0; run < NUM_RUNS; run++) {
                    
                    RNAStructure rna = generateRandomRNAStructure(length);
                    

                    // Bottom-Up 
                    RNABottomUp bottomUp = new RNABottomUp(rna);
                    long startTime = System.nanoTime();
                    bUpSolution = bottomUp.bottomUp(); 
                    long bottomUpTime = System.nanoTime() - startTime;
                    totalBottomUpTime += bottomUpTime;

                    // Top-Down 
                    RNATopDown topDown = new RNATopDown(rna);
                    startTime = System.nanoTime();
                    topDown.topDown(0, rna.getSequence().size() - 1); 
                    long topDownTime = System.nanoTime() - startTime;
                    totalTopDownTime += topDownTime;
                    tDownSolution = topDown.returnOpt();
                }
                Boolean areSame = (bUpSolution == tDownSolution);
                long averageBottomUpTime = totalBottomUpTime / NUM_RUNS;
                long averageTopDownTime = totalTopDownTime / NUM_RUNS;

                writer.write(length + ", " + (areSame) + ", " + averageBottomUpTime + ", " + averageTopDownTime + "\n");
                System.out.println("Length: " + length +
                                   ", Validation " + areSame +
                                   ", Avg Bottom-Up Time: " + averageBottomUpTime + " ns" +
                                   ", Avg Top-Down Time: " + averageTopDownTime + " ns");
            }
        } catch (IOException e) {
            System.err.println("Error writing to CSV file");
            e.printStackTrace();
        }

        System.out.println("CSV file with time complexity analysis has been created.");
    }
	
    
}
