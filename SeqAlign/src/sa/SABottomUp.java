package sa;

import java.util.ArrayList;

public class SABottomUp {

    public ArrayList<String> sequence1;
    public ArrayList<String> sequence2;
    
    public int[][] A;
    public int alpha;
    public int gapCost;
    
    public SABottomUp(SequenceStructure ss, int gapCost, int alpha) {
        this.sequence1 = ss.getSequence1();
        this.sequence2 = ss.getSequence2();
        
        this.alpha = alpha;
        this.gapCost = gapCost;
        
        int m = this.sequence1.size();
        int n = this.sequence2.size();
        
        this.A = new int[m + 1][n + 1]; 
        
        // Initializing part
        for (int i = 0; i <= m; i++) {
            A[i][0] = i * gapCost;
        }
        for (int j = 0; j <= n; j++) {
            A[0][j] = j * gapCost;
        }
    }
    
    public int score(String x, String y) {
        if(x.equals(y)) return 1;
        else return -1;
    }
    
    public void bottomUp() {
        int m = this.sequence1.size();
        int n = this.sequence2.size();
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int matchMismatch = alpha * score(this.sequence1.get(i-1), this.sequence2.get(j-1));
                A[i][j] = Math.min(
                            matchMismatch + A[i-1][j-1],
                            Math.min(
                                this.gapCost + A[i-1][j],
                                this.gapCost + A[i][j-1]
                            ));
            }
        }
    }
    
    public int optSolution() {
        return A[this.sequence1.size()][this.sequence2.size()];
    }
}
