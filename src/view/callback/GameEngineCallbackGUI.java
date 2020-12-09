package view.callback;

import javax.swing.SwingUtilities;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.GameFrame;
import view.model.GameEngineGUISupport;
import view.interfaces.GameEngineSupport;
import view.interfaces.GameEngineCallback;

/**
 * The second {@link GameEngineCallback}, the origin of all GUI components.
 * The methods of this class simply update the view ({@link GameFrame}) components.
 */
public class GameEngineCallbackGUI implements GameEngineCallback
{
    private GameFrame gameFrame;
    private GameEngineSupport gameEngineSupport;

    private final boolean BUSTED = true;
    private final boolean DEAL_ONGOING = true;

    /**
     * The GUI related callback, updates the view based on the changes in the model.
     * @param gameEngine the GameEngine which plays the role of the model
     * @param delay the delay between cards being dealt
     */
    public GameEngineCallbackGUI(GameEngine gameEngine, int delay)
    {
        // create the supporting GameEngine (ViewModel) that sits on top of the basic GameEngine
        gameEngineSupport = new GameEngineGUISupport(gameEngine, delay);

        // initialise the GameFrame on the event thread
        SwingUtilities.invokeLater(() ->
        {
            // the JFrame which is the origin of everything GUI
            gameFrame = new GameFrame(gameEngineSupport);
            gameEngineSupport.addGameEngineCallbackSupport(new GameEngineCallbackGUISupport(gameFrame));
        });
    }

    @Override
    public void nextCard(Player player, PlayingCard card, GameEngine engine)
    {
        // update player's hand with the card being dealt
        gameEngineSupport.cardDealt(player, card, !BUSTED);

        // view updates are run on the event thread
        SwingUtilities.invokeLater(() ->
        {
            // turn off certain functionalities when a deal is ongoing, e.g. a second deal
            gameFrame.notifyDealStateChange(DEAL_ONGOING);
            gameFrame.updateCardPanel();
            gameFrame.updateGameStatusLabelText(
                    String.format("Dealing cards to %s.", player.getPlayerName()));
        });
    }

    @Override
    public void bustCard(Player player, PlayingCard card, GameEngine engine)
    {
    	gameEngineSupport.cardDealt(player, card, BUSTED);
        SwingUtilities.invokeLater(() ->
        {
            gameFrame.updateCardPanel();
            gameFrame.updateGameStatusLabelText(
                    String.format("%s busted!", player.getPlayerName()));
        });
    }

    @Override
    public void result(Player player, int result, GameEngine engine)
    {
        SwingUtilities.invokeLater(() ->
        {
            // turn functionalities previously turned off, back on
            gameFrame.notifyDealStateChange(!DEAL_ONGOING);
            gameFrame.updateSummaryPanel();
            gameFrame.updateGameStatusLabelText(
                    String.format("%s's round result is %d.", player.getPlayerName(), result));
        });
    }

    @Override
    public void nextHouseCard(PlayingCard card, GameEngine engine)
    {
        // update house's hand with the card being dealt
        gameEngineSupport.cardDealt(gameEngineSupport.getHousePlayer(), card, !BUSTED);
        SwingUtilities.invokeLater(() ->
        {
            gameFrame.notifyDealStateChange(DEAL_ONGOING);
            gameFrame.updateCardPanel();
            gameFrame.updateGameStatusLabelText("Dealing cards to House.");
        });
    }

    @Override
    public void houseBustCard(PlayingCard card, GameEngine engine)
    {
        gameEngineSupport.cardDealt(gameEngineSupport.getHousePlayer(), card, BUSTED);
        SwingUtilities.invokeLater(() ->
        {
            gameFrame.updateCardPanel();
            gameFrame.updateGameStatusLabelText("House busted!");
        });
    }

    @Override
    public void houseResult(int result, GameEngine engine)
    {
    	SwingUtilities.invokeLater(() ->
        {
            gameFrame.notifyDealStateChange(!DEAL_ONGOING);
            gameFrame.updateSummaryPanel();
            gameFrame.updateGameStatusLabelText(
                    String.format("House's round result is %d.", result));
        });
    }
}
