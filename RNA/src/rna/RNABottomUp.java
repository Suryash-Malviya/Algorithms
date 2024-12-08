package rna;

import java.util.ArrayList;


public class RNABottomUp {
	
	private static final int MIN_LOOP_LENGTH = 4; // to separate the nucleotides

	
	public ArrayList<String> sequence;
	public int[][] M;
	
	public RNABottomUp(RNAStructure rna) {
		
		this.sequence = rna.getSequence();
		
		int N = this.sequence.size();
		this.M = new int[N][N];
		
		for(int i = 0; i<N; i++) {
			for(int j = 0; j < N; j++) {
				this.M[i][j] = 0;
			}
		}
		
	}
	

	public int bottomUp() {
	    int N = this.sequence.size();

	    for (int l = MIN_LOOP_LENGTH + 1; l < N; l++) {
	        for (int i = 0; i < N - l; i++) {
	            int j = i + l;

	            int max = 0;

	            // i is unpaired
	            max = M[i + 1][j];

	            // j is unpaired
	            if (M[i][j - 1] > max) {
	                max = M[i][j - 1];
	            }

	            // i and j are paired
	            if (isPair(sequence.get(i), sequence.get(j))) {
	                int paired_ij = M[i + 1][j - 1] + 1;
	                if (paired_ij > max) {
	                    max = paired_ij;
	                }
	            }


	            for (int k = i + 1; k < j; k++) {
	                int split = M[i][k] + M[k + 1][j];
	                if (split > max) {
	                    max = split;
	                }
	            }

	            M[i][j] = max;
	        }
	    }

	    return M[0][N - 1];
	}

	
	public boolean isPair(String x, String y) {
		 return ((x.equals("A") && y.equals("U")) || (x.equals("U") && y.equals("A")) || (x.equals("C") && y.equals("G")) || (x.equals("G") && y.equals("C")));
	}

}
