package controller.toolbar;

import java.util.List;
import java.util.ArrayList;

import java.lang.reflect.InvocationTargetException;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import model.interfaces.Player;
import view.interfaces.GameEngineSupport;
import view.GameToolBar;
import view.utility.GameOptionPane;
import view.utility.PlayerOptionPane;

/**
 * Controller for the "Deal" button.
 * @see AbstractToolBarButtonController
 */
public class DealButtonController extends AbstractToolBarButtonController
{
    private List<Player> playersToKick;

    public DealButtonController(GameToolBar toolBar, GameEngineSupport gameEngine)
    {
        super(toolBar, gameEngine);
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
    	// below are a set of predefined Runnable inner classes to run within the event thread
        
    	// runs before the house deal occurs
        Runnable earlyDealHouseUpdate = () ->
        {
            // first display a dialog to inform the user of the house deal
            GameOptionPane.houseReadyDialog();
            gameToolBar.setSelectedPlayer(gameToolBar.getHousePlayer());
        };

        // runs if players were to be kicked after house deal finished
        Runnable finalDealHouseUpdate = () ->
        {
            // the dialog once house deal ends
            PlayerOptionPane.playersKickedDialog(playersToKick);
        };

        // runs after the round ends
        // the dialog once house deal ends
        Runnable roundEndUpdate = GameOptionPane::playAgainDialog;


        // run the deal methods on a new thread
        new Thread(() ->
        {
            // additional check for when forcing a house deal
            if (!gameEngine.allPlayersDealt())
            {
                // deal for the currently selected player and update view accordingly
                gameEngine.dealPlayer(gameToolBar.getSelectedPlayer(), gameEngine.getDelay());
            }

            // house deal occurs if all players are dealt
            if (gameEngine.allPlayersDealt())
            {
                try
                {
                    // run all GUI updates on the event thread
                    SwingUtilities.invokeAndWait(earlyDealHouseUpdate);
                }
                catch (InterruptedException | InvocationTargetException exception)
                {
                    exception.printStackTrace();
                }

                // deal for the house player
                gameEngine.dealHouse(gameEngine.getDelay());

                // players that ran out of points and are to be kicked
                playersToKick = new ArrayList<>();
                for (Player player : gameEngine.getAllPlayers())
                    if (player.getPoints() == 0) playersToKick.add(player);

                try
                {
                    if (!playersToKick.isEmpty())
                    {
                        // kick players out of the game
                        for (Player player : playersToKick)
                            gameEngine.removePlayer(player);

                        SwingUtilities.invokeAndWait(finalDealHouseUpdate);
                    }

                    SwingUtilities.invokeAndWait(roundEndUpdate);
                }
                catch (InterruptedException | InvocationTargetException exception)
                {
                    exception.printStackTrace();
                }
            }
        }).start();
    }
}
