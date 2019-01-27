package model.characters;

import model.ModelFacade;
import model.nodes.Node;
import model.screen.CurrentScreen;
import view.ViewInterface;

public class CharacterFactory {
	private static CurrentScreen screen;
	private static ModelFacade model;
	private static ViewInterface view;
	private static CharacterFactory unique = null;
	
	private CharacterFactory(CurrentScreen screen, ModelFacade model, ViewInterface view) {
		CharacterFactory.screen = screen;
		CharacterFactory.model = model;
		CharacterFactory.view = view;
	}
	
	public static CharacterFactory getInstance(CurrentScreen screen, ModelFacade model, ViewInterface view) {
		if (unique == null) {
			unique = new CharacterFactory(screen, model, view);
		}
		return unique;
	}
	
	public static void initPlayer(Node node) {
		
	}
}
