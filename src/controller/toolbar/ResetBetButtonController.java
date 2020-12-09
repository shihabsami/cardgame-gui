package controller.toolbar;

import java.awt.event.ActionEvent;

import model.interfaces.Player;
import view.interfaces.GameEngineSupport;
import view.GameToolBar;
import view.utility.BetOptionPane;

/**
 * Controller for the "Reset Bet" button.
 * @see AbstractToolBarButtonController
 */
public class ResetBetButtonController extends AbstractToolBarButtonController
{
    public ResetBetButtonController(GameToolBar toolBar, GameEngineSupport gameEngine)
    {
        super(toolBar, gameEngine);
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
        // reset bet for the currently selected player and display a dialog
        Player player = gameToolBar.getSelectedPlayer();
        gameEngine.resetBet(player);
        gameToolBar.updateButtonState(player);
        BetOptionPane.betResetDialog();
    }
}
