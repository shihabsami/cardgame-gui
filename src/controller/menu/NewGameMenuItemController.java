package controller.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.interfaces.GameEngineSupport;
import view.utility.GameOptionPane;

/**
 * Controller for the "New Game" menu item.
 */
public class NewGameMenuItemController implements ActionListener
{
	private GameEngineSupport gameEngineSupport;
	
	public NewGameMenuItemController(GameEngineSupport gameEngineSupport)
	{
		this.gameEngineSupport = gameEngineSupport;
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		// reset game and show a dialog
		gameEngineSupport.resetGame();
		GameOptionPane.newGameDialog();
	}
}
