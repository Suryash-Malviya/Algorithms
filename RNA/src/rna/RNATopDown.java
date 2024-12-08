package rna;

import java.util.ArrayList;

public class RNATopDown {

	private static final int MIN_LOOP_LENGTH = 4; // to separate the nucleotides
	
	public ArrayList<String> sequence;
	public int[][] opt;
	
	public RNATopDown(RNAStructure rna) {
		
		this.sequence = rna.getSequence();
		
		int N = this.sequence.size();
		this.opt = new int[N][N];
		
		for(int i = 0; i<N; i++) {
			for(int j = 0; j < N; j++) {
				this.opt[i][j] = -1;
			}
		}
		
	}


	public int topDown(int i, int j) {
	    if (i >= j - MIN_LOOP_LENGTH) {
	        return 0;
	    }

	    if (this.opt[i][j] != -1) {
	        return this.opt[i][j];
	    }

	    int max = 0;

	    // Case 1: Nucleotide at position i is unpaired
	    int unpaired_i = topDown(i + 1, j);
	    if (unpaired_i > max) {
	        max = unpaired_i;
	    }

	    // Case 2: Nucleotide at position j is unpaired
	    int unpaired_j = topDown(i, j - 1);
	    if (unpaired_j > max) {
	        max = unpaired_j;
	    }

	    // Case 3: Nucleotides at positions i and j are paired
	    if (isPair(sequence.get(i), sequence.get(j))) {
	        int paired_ij = topDown(i + 1, j - 1) + 1;
	        if (paired_ij > max) {
	            max = paired_ij;
	        }
	    }

	    // Case 4: Splitting the sequence between i and j
	    for (int k = i + 1; k < j; k++) {
	        int split = topDown(i, k) + topDown(k + 1, j);
	        if (split > max) {
	            max = split;
	        }
	    }

	    this.opt[i][j] = max;
	    return max;
	}


	
	public int returnOpt() {
		return this.opt[0][this.sequence.size()-1];
	}
	
	public boolean isPair(String x, String y) {
		 return ((x.equals("A") && y.equals("U")) || (x.equals("U") && y.equals("A")) || (x.equals("C") && y.equals("G")) || (x.equals("G") && y.equals("C")));
	}

	
}
