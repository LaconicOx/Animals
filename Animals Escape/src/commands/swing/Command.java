package commands.swing;

import java.util.Objects;

public abstract class Command implements Comparable<Command>{
	private static int count = 0;
	private int creationIndex;
	
	/////////////////////////// Constructor ////////////////////////
	protected Command() {
		count++;
		creationIndex = count; 
	}
	
	/////////////////////// Public Methods /////////////////////////
	public abstract void execute();
	
	/////////////////////// Protected Methods /////////////////////
	
	protected int getCreationIndex() {
		return creationIndex;
	}
	
	////////////////////// Overrides /////////////////////////
	
	@Override
	public int compareTo(Command cmd) {
		int ind = cmd.getCreationIndex();
		
		if (0 > creationIndex - ind)
			return -1;
		else if (0 < creationIndex - ind)
			return 1;
		else {
			System.err.println("Error: two commands have samae creation index.");
			return 0;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		//Tests for different objects
		if (obj.getClass() != this.getClass())
			return false;
		
		//Tests for same creationIndex
		Command obCmd = (Command)obj;
		if(obCmd.getCreationIndex() != creationIndex)
			return false;
		else {
			System.err.println("Error: two commands have samae creation index.");
			return true;
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(creationIndex);
	}
}
