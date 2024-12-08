package sa;

import java.util.ArrayList;

public class SequenceStructure {
	
	private ArrayList<String> sequence1;
	private ArrayList<String> sequence2;
	
	public SequenceStructure(ArrayList<String> StringSequence1, ArrayList<String> StringSequence2) {
		this.sequence1 = StringSequence1;
		this.sequence2 = StringSequence2;
	}

	
	public ArrayList<String> getSequence1(){
		return this.sequence1;
	}
	
	public ArrayList<String> getSequence2(){
		return this.sequence2;
	}
	
	public void setSequence1(int index, String newString) {
		if(index < this.sequence1.size() && index >= 0) {
			this.sequence1.set(index, newString);
		}
	}
	
	public void setSequence2(int index, String newString) {
		if(index < this.sequence2.size() && index >= 0) {
			this.sequence2.set(index, newString);
		}
	}
	
	
}
