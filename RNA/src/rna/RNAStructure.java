package rna;
import java.util.ArrayList;


public class RNAStructure {
	
	private ArrayList<String> sequence;

	public RNAStructure(ArrayList<String> RNASequence) {
		
		this.sequence = RNASequence;
	}
	
	public ArrayList<String> getSequence(){
		return this.sequence;
	}
}
