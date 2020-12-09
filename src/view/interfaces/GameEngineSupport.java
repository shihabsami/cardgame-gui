package view.interfaces;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.model.PlayerState;

/**
 * Interface for the class that aids the {@link GameEngine}.
 */
public interface GameEngineSupport extends GameEngine
{	
    /**
     * Getter for the delay attribute.
     * @return the delay between cards being dealt
     */
    int getDelay();

    /**
     * Getter for the house player.
     * @return the house Player object
     */
    Player getHousePlayer();

    /**
     * Getter for an individual player's state.
     * @param player the player whose state is to be returned
     * @return the state of the player
     */
    PlayerState getPlayerState(Player player);

    /**
     * Method to reset a players bet, explicit method to reset the bet aids the supporting callback.
     * @param player the player whose bet is to be reset
     */
    void resetBet(Player player);
    
    /**
     * Method to reset the game i.e. start a new game.
     */
    void resetGame();

    /**
     * Method to evaluate whether all the players in the {@link GameEngine} have been dealt.
     * @return true if all existing players have been dealt, false otherwise
     */
    boolean allPlayersDealt();
    
    /**
     * Method to update player's state during deal.
     * @param player the Player to be updated
     * @param card the dealt PlayingCard
     * @param busted true if the player busted, false otherwise
     */
    void cardDealt(Player player, PlayingCard card, boolean busted);

    /**
     * Method to add a supporting callback that holds additional notification methods.
     * @param callbackSupport the GameEngineCallbackSupport
     */
    void addGameEngineCallbackSupport(GameEngineCallbackSupport callbackSupport);
}
