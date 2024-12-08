package sa;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class Test {
	
	private static final Random rand = new Random();
	private static String[] letters = { 
			"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", 
		    "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
		    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", 
		    "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
		    " "
		    };
	
	private static SequenceStructure randomSequence(int length, int variations) {
		ArrayList<String> sequence1 = new ArrayList<>();
		ArrayList<String> sequence2 = new ArrayList<>();
		for (int i = 0; i < length; i++) {
			sequence1.add(letters[rand.nextInt(letters.length)]);
			sequence2.add(letters[rand.nextInt(letters.length)]);
		}
		
		for(int j = 0; j < variations; j++) {
			int randIndex = rand.nextInt(sequence2.size());
			sequence2.set(randIndex, letters[rand.nextInt(letters.length)]);
		}
		
		return new SequenceStructure(sequence1, sequence2);
	}
	
	public static SequenceStructure[] randomListOfSequence(int total, int length, int variations){
		
		SequenceStructure[] lst = new SequenceStructure[total];
		
		for(int k = 0; k < total; k++) {
			
			lst[k] = randomSequence(length, variations);
		
		}
		
		return lst;		
	}
	
	
	 public static void main(String[] args) {
		 
	        int MAX_LENGTH = 10000; 
	        int STEP_SIZE = 10;
	        int NUM_RUNS = 5; 
	        int VARIATIONS = 10; 
	        int GAP_COST = -1; 
	        int ALPHA = 1; 

	        try (FileWriter writer = new FileWriter("sa_time_complexity_more.csv")) {
	            writer.write("Sequence Length, Consistency Check, Average Bottom-Up Time (ns), Average Top-Down Time (ns)\n");

	            for (int length = 10; length <= MAX_LENGTH; length += STEP_SIZE) {

	                long totalBottomUpTime = 0;
	                long totalTopDownTime = 0;
	                int bUpSolution = 0;
	                int tDownSolution = 0;

	                for (int run = 0; run < NUM_RUNS; run++) {
	                    SequenceStructure ss = randomSequence(length, VARIATIONS);

	                    // Bottom-Up 
	                    SABottomUp bottomUp = new SABottomUp(ss, GAP_COST, ALPHA);
	                    long startTime = System.nanoTime();
	                    bUpSolution = bottomUp.optSolution(); 
	                    long bottomUpTime = System.nanoTime() - startTime;
	                    totalBottomUpTime += bottomUpTime;

	                    // Top-Down 
	                    SATopDown topDown = new SATopDown(ss, GAP_COST, ALPHA);
	                    startTime = System.nanoTime();
	                    topDown.topDown(ss.getSequence1().size() - 1, ss.getSequence2().size() - 1); 
	                    long topDownTime = System.nanoTime() - startTime;
	                    totalTopDownTime += topDownTime;
	                    tDownSolution = topDown.optSolution();
	                }

	                Boolean areSame = (bUpSolution == tDownSolution);
	                long averageBottomUpTime = totalBottomUpTime / NUM_RUNS;
	                long averageTopDownTime = totalTopDownTime / NUM_RUNS;

	                writer.write(length + ", " + areSame + ", " + averageBottomUpTime + ", " + averageTopDownTime + "\n");
	                System.out.println("Sequence Alignment");
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
