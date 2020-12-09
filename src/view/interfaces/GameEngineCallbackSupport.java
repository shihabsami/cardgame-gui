package view.interfaces;

import model.interfaces.Player;

/**
 * Interface for the class that aids the {@link GameEngineCallback}.
 * Provides additional functionality for logging of events in the {@link model.interfaces.GameEngine}.
 */
public interface GameEngineCallbackSupport
{
	/**
	 * Method to notify {@link view} classes when a Player is added to the {@link model.interfaces.GameEngine}.
	 * @param player the added Player
	 */
	void playerAdded(Player player);

	/**
	 * Method to notify {@link view} classes when a Player is removed from the {@link model.interfaces.GameEngine}.
	 * @param player the removed Player
	 */
	void playerRemoved(Player player);

	/**
	 * Method to notify {@link view} classes when a Player places a bet.
	 * @param player the Player who placed the bet
	 * @param bet the bet amount
	 */
	void playerBetPlaced(Player player, int bet);

    /**
     * Method to notify {@link view} classes when a Player resets bet.
     * @param player the Player who reset the bet
     */
	void playerBetReset(Player player);

    /**
     * Method to notify {@link view} classes when the round ends.
     */
	void roundEnded();
    
    /**
     * Method to notify {@link view} classes when the game is reset.
     */
	void gameReset();
}
