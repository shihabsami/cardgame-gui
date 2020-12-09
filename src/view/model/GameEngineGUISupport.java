package view.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Deque;
import java.util.Collection;

import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineSupport;
import view.interfaces.GameEngineCallback;
import view.interfaces.GameEngineCallbackSupport;

/**
 * Auxiliary {@link GameEngine} to aid the previous GameEngine in several additional operations.
 * Plays the role of the ViewModel. Holds additional player related information.
 * Uses the GameEngine passed during constructor for most of the operations.
 * Aids in additional callbacks using the {@link GameEngineCallbackSupport}.
 */
public class GameEngineGUISupport implements GameEngineSupport
{
    private int delay;
    private GameEngine gameEngine;
    private GameEngineCallbackSupport callbackSupport;

    // collection to store the players' states
    private Map<String, PlayerState> playerStates = new HashMap<>();

    // house player that is used throughout the program
    private final Player HOUSE = new SimplePlayer("", "House", 0);

    public GameEngineGUISupport(GameEngine gameEngine, int delay)
    {
        this.delay = delay;
        this.gameEngine = gameEngine;
        playerStates.put(HOUSE.getPlayerId(), new PlayerState());
    }

    @Override
    public int getDelay()
    {
        return delay;
    }

    @Override
    public Player getHousePlayer()
    {
        return HOUSE;
    }

    @Override
    public PlayerState getPlayerState(Player player)
    {
        return playerStates.get(player.getPlayerId());
    }

    @Override
    public void dealPlayer(Player player, int delay) throws IllegalArgumentException
    {
        // deal for the player and update the player's state
        getPlayerState(player).resetHand();
        getPlayerState(player).setHasBeenDealt(true);
        gameEngine.dealPlayer(player, delay);
        getPlayerState(player).setPreviousBet(player.getBet());
    }

    @Override
    public void dealHouse(int delay) throws IllegalArgumentException
    {
        // deal for the house and update the house's state
        getPlayerState(HOUSE).resetHand();
        getPlayerState(HOUSE).setHasBeenDealt(true);
        gameEngine.dealHouse(delay);
        updatePlayerStates();
        callbackSupport.roundEnded();
    }

    @Override
    public void cardDealt(Player player, PlayingCard card, boolean busted)
    {
        getPlayerState(player).setHasBusted(busted);
        getPlayerState(player).addPlayingCard(card);
    }

    @Override
    public boolean allPlayersDealt()
    {
        boolean dealt = (getAllPlayers().size() != 0);

        for (Player player : getAllPlayers())
            dealt &= getPlayerState(player).hasBeenDealt();

        return dealt;
    }

    /**
     * Method that updates all the players' final {@link PlayerState} once round ends.
     */
    private void updatePlayerStates()
    {
        for (Player player : getAllPlayers())
        {
            PlayerState state = getPlayerState(player);

            if (player.getPoints() > state.getPreviousPoints())
                state.setResultSummary(PlayerState.ResultSummary.WON);
            else if (player.getPoints() < state.getPreviousPoints())
                state.setResultSummary(PlayerState.ResultSummary.LOST);
            else
                state.setResultSummary(PlayerState.ResultSummary.DREW);

            state.setHasBet(false);
            state.setHasBeenDealt(false);
            state.setPreviousPoints(player.getPoints());
        }
    }

    @Override
    public void applyWinLoss(Player player, int houseResult)
    {
        gameEngine.applyWinLoss(player, houseResult);
    }

    @Override
    public void addPlayer(Player player)
    {
        // add the player and associate a new state with the player
        gameEngine.addPlayer(player);
        PlayerState state = new PlayerState();
        state.setPreviousPoints(player.getPoints());
        playerStates.put(player.getPlayerId(), state);
        callbackSupport.playerAdded(player);
    }

    @Override
    public boolean removePlayer(Player player)
    {
        // remove player and state associated with the player
        if (gameEngine.removePlayer(player))
        {
            playerStates.remove(player.getPlayerId());
            callbackSupport.playerRemoved(player);
            return true;
        }
        return false;
    }

    @Override
    public Player getPlayer(String id)
    {
        return gameEngine.getPlayer(id);
    }

    @Override
    public boolean placeBet(Player player, int bet)
    {
        // place bet and update the player's state
        if (gameEngine.placeBet(player, bet))
        {
            getPlayerState(player).setHasBet(true);
            callbackSupport.playerBetPlaced(player, bet);
            return true;
        }
        return false;
    }

    @Override
    public void resetBet(Player player)
    {
        // reset bet and update the player's state
        player.resetBet();
        getPlayerState(player).setHasBet(false);
        callbackSupport.playerBetReset(player);
    }
    
	@Override
	public void resetGame()
	{
		Iterator<String> iterator = playerStates.keySet().iterator();
		while (iterator.hasNext())
		{
			Player player = getPlayer(iterator.next());
			if (player != null) gameEngine.removePlayer(player);
			iterator.remove();
		}
        playerStates.put(HOUSE.getPlayerId(), new PlayerState());
		callbackSupport.gameReset();
	}

    @Override
    public void addGameEngineCallbackSupport(GameEngineCallbackSupport callbackSupport)
    {
        this.callbackSupport = callbackSupport;
    }

    @Override
    public void addGameEngineCallback(GameEngineCallback gameEngineCallback)
    {
        gameEngine.addGameEngineCallback(gameEngineCallback);
    }

    @Override
    public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback)
    {
        return gameEngine.removeGameEngineCallback(gameEngineCallback);
    }

    @Override
    public Collection<Player> getAllPlayers()
    {
        return gameEngine.getAllPlayers();
    }

    @Override
    public Deque<PlayingCard> getShuffledHalfDeck()
    {
        return gameEngine.getShuffledHalfDeck();
    }
}
