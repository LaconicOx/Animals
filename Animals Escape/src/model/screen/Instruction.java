package model.screen;

import java.util.ArrayList;
import java.util.List;

import model.Directions.Direction;
import units.CellKey;

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
			CellKey key = self.getNeighborKey(toward);
			screen.addBorderCell(key);; 
		}
	}
	
	class DeleteSelf extends Action{
		void execute() { 
			screen.removeBorderCell(self.getKey());	
		}
	}
	
	class DeleteScreen extends Action{
		void execute() { 
			CellKey key = self.getNeighborKey(toward);
			screen.removeCharacters(key);//tests characters for removal
			screen.removeScreenCell(key);//remove screen cell.
		}
	}
	
	class Transform extends Action{
		void execute() {
			CellKey key = self.getKey();
			screen.removeBorderCell(key);
			screen.addScreenCell(key);
			}
	}
}//End of Instruction
