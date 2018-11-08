package model.screen;

import java.util.ArrayList;
import java.util.List;

import model.ModelParameters.Direction;
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
	}
	
	void keep() {};//The method is here to represent the choice of keeping the cell
	
	/////////////////////// Inner Classes /////////////////////
	abstract class Action { abstract void execute(); }
	
	class CreateBorder extends Action{
		void execute() { 
			screen.addBorderCell(self.initBorderCell(toward));; 
		}
	}
	
	class DeleteSelf extends Action{
		void execute() { 
			screen.removeBorderCell(self);	
		}
	}
	
	class DeleteScreen extends Action{
		void execute() { 
			Cell sc = self.getNeighborCell(toward, true);
			if(sc.getClass() == ScreenCell.class) {
				screen.removeCharacters(sc);//tests characters for removal
				screen.removeScreenCell((ScreenCell)sc);//remove screen cell.
			}
			else {
				System.err.println("Error in DeleteScreen: ScreenCell not selected.");
			}
		}
	}
	
	class Transform extends Action{
		void execute() {
			Node selfNode = self.getNode();
			screen.removeBorderCell(self);
			screen.addScreenCell(new ScreenCell(selfNode));
			}
	}
}//End of Instruction
