package view.callback;

import javax.swing.SwingUtilities;

import model.interfaces.Player;
import view.GameFrame;
import view.interfaces.GameEngineCallbackSupport;

/**
 * Implementation of the {@link GameEngineCallbackSupport} for the GUI logging behaviour.
 */
public class GameEngineCallbackGUISupport implements GameEngineCallbackSupport
{
    private GameFrame gameFrame;

    public GameEngineCallbackGUISupport(GameFrame gameFrame)
    {
        this.gameFrame = gameFrame;
    }

    @Override
    public void playerAdded(Player player)
    {
    	// calls update on the view components
		gameFrame.updateGameToolBar();
        gameFrame.updateSummaryPanel();
        
        // sets a status text based on the event that occurred
        gameFrame.updateGameStatusLabelText(
                String.format("%s is added to the game.", player.getPlayerName()));
    }

    @Override
    public void playerRemoved(Player player)
    {
		gameFrame.updateGameToolBar();
        gameFrame.updateSummaryPanel();
        gameFrame.updateGameStatusLabelText(
                String.format("%s is removed from the game.", player.getPlayerName()));
    }

    @Override
    public void playerBetPlaced(Player player, int bet)
    {
		gameFrame.updateSummaryPanel();
        gameFrame.updateGameStatusLabelText(
                String.format("%s bets %d%s", player.getPlayerName(), bet, (player.getPoints() == bet) ? " and is all in!" : "."));
        
    }

    @Override
    public void playerBetReset(Player player)
    {
		gameFrame.updateSummaryPanel();
        gameFrame.updateGameStatusLabelText(
                String.format("%s resets bet.", player.getPlayerName()));	
    }

    @Override
    public void roundEnded()
    {
    	// usually called from a regular thread, hence run GUI update on the event thread
    	SwingUtilities.invokeLater(() -> gameFrame.updateSummaryPanel());
    }

	@Override
	public void gameReset()
	{
		// reset all view components
		gameFrame.updateGameToolBar();
		gameFrame.updateSummaryPanel();
		gameFrame.updateCardPanel();
		gameFrame.updateGameStatusLabelText("Idle.");
	}
}
