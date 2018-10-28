package model.screen;

import java.util.ArrayList;
import java.util.List;

import model.ModelUtility.Direction;
import model.board.Node;

class Instruction {
	
	private BorderCell self;
	private List<Action> actions;
	private Direction toward;
	private Screen screen;
	
	Instruction(BorderCell self, Direction toward){
		this.self = self;
		this.toward = toward;
		this.screen = Screen.getInstance();
		actions = new ArrayList<>();
	}
	
	///////////////////// Accessor Method ////////////////////
	
	void execute() {
		actions.forEach(action -> action.execute());
	}
	
	///////////////////// Builder Methods ////////////////////
	void screenToBorder() {
		actions.add(new DeleteScreen());
		actions.add(new CreateBorder());
	}
	
	void nullToBorder() {
		actions.add(new CreateBorder());
	}
	
	void selfToNull(){
		actions.add(new DeleteSelf());
	}
	
	void selfToScreen() {
		actions.add(new Transform());
		actions.add(new DeleteSelf());
	}
	
	void keep() {};//The method is here to represent the choice of keeping the cell
	
	/////////////////////// Inner Classes /////////////////////
	abstract class Action { abstract void execute(); }
	
	class CreateBorder extends Action{
		void execute() { 
			//System.out.println("CreateBorder started");
			screen.addBorderCell(self.initBorderCell(toward));; 
			//System.out.println("CreateBorder finished");
		}
	}
	
	class CreateScreen extends Action{
		void execute() { 
			//System.out.println("CreateBorder started");
			screen.addScreenCell(self.initScreenCell(toward)); 
			//System.out.println("CreateBorder finished");
		}
	}
	
	class DeleteSelf extends Action{
		void execute() { 
			//System.out.println("DeleteSelf started for" + self);
			screen.removeBorderCell(self);	
			//System.out.println("DeleteSelf finished");
		}
	}
	
	class DeleteScreen extends Action{
		void execute() { 
			//System.out.println("DeleteScreen started for " + self);
			Cell sc = self.getNeighborCell(toward, true);
			if(sc.getClass() == ScreenCell.class)
				screen.removeScreenCell((ScreenCell)sc);
			//System.out.println("DeleteScreen finished");
			}
	}
	
	class Transform extends Action{
		void execute() {
			//System.out.println("Transform started");
			Node selfNode = self.getNode();
			screen.removeBorderCell(self);	
			screen.addScreenCell(new ScreenCell(selfNode));
			//System.out.println("Transform finished");
			}
	}
}//End of Instruction
