package controller.toolbar;

import java.awt.event.ActionEvent;

import model.interfaces.Player;
import view.interfaces.GameEngineSupport;
import view.GameToolBar;
import view.utility.PlayerOptionPane;

/**
 * Controller for the "Remove Player" button.
 * @see AbstractToolBarButtonController
 */
public class RemovePlayerButtonController extends AbstractToolBarButtonController
{
    public RemovePlayerButtonController(GameToolBar toolBar, GameEngineSupport gameEngine)
    {
        super(toolBar, gameEngine);
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
        // remove the currently selected player and display a dialog
        Player player = gameToolBar.getSelectedPlayer();

        if (gameEngine.removePlayer(player))
        {
            PlayerOptionPane.playerRemovedDialog(player);
            
            // if the removed player was the last player who has not been dealt, initiate house deal
            if (gameEngine.allPlayersDealt())
                gameToolBar.forceHouseDeal();
        }
    }
}
