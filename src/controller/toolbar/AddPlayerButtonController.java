package controller.toolbar;

import java.awt.event.ActionEvent;

import model.interfaces.Player;
import view.interfaces.GameEngineSupport;
import view.GameToolBar;
import view.utility.PlayerOptionPane;

/**
 * Controller for the "Add Player" button.
 * @see AbstractToolBarButtonController
 */
public class AddPlayerButtonController extends AbstractToolBarButtonController
{
    public AddPlayerButtonController(GameToolBar gameToolBar, GameEngineSupport gameEngine)
    {
        super(gameToolBar, gameEngine);
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
        // prompt for the creation of a new player
        Player player = PlayerOptionPane.addPlayerPrompt();

        if (player != null)
        {
            // add the player and display a dialog
            gameEngine.addPlayer(player);
            gameToolBar.setSelectedPlayer(player);
            PlayerOptionPane.playerAddedDialog(player);
        }
    }
}
