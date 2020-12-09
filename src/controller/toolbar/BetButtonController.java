package controller.toolbar;

import java.awt.event.ActionEvent;

import model.interfaces.Player;
import view.interfaces.GameEngineSupport;
import view.GameToolBar;
import view.utility.BetOptionPane;

/**
 * Controller for the "Bet" button.
 * @see AbstractToolBarButtonController
 */
public class BetButtonController extends AbstractToolBarButtonController
{
    public BetButtonController(GameToolBar gameToolBar, GameEngineSupport gameEngine)
    {
        super(gameToolBar, gameEngine);
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
        // prompt for the bet amount
        int bet = BetOptionPane.placeBetPrompt();

        if (bet != BetOptionPane.BET_CANCELLED)
        {
            // place the bet amount for the currently selected player and display a dialog
            Player player = gameToolBar.getSelectedPlayer();

            if (gameEngine.placeBet(player, bet))
            {
                gameToolBar.updateButtonState(player);
                BetOptionPane.betSuccessDialog();
            }
            else BetOptionPane.betFailureDialog();
        }
    }
}
