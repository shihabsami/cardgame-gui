package model.interfaces;

import java.util.Deque;
import java.util.Collection;

import view.interfaces.GameEngineCallback;

/**
 * Interface for providing main model functionality.
 */
public interface GameEngine
{
   int BUST_LEVEL = 42;

   /**
    * <pre>
    * Deal cards to the player as follows.
    * 1. Deal a card to the player.
    * 2. Call {@link view.interfaces.GameEngineCallback#nextCard(Player, PlayingCard , GameEngine)}.
    * 3. Continue looping until the player busts (default value of {@link GameEngine#BUST_LEVEL}).
    * 4. Call {@link view.interfaces.GameEngineCallback#bustCard(Player, PlayingCard, GameEngine)}.
    * 5. Call {@link view.interfaces.GameEngineCallback#result(Player, int, GameEngine)}
    *    with final result for player (the pre bust total).
    * 6. Update the player with final result so it can be retrieved later.
    * </pre>
    * @param player the current player who will have their result set at the end of the hand
    * @param delay the delay between cards being dealt (in milliseconds (ms))
    * @throws IllegalArgumentException thrown when delay param is {@literal <} 0 or {@literal >} 1000
    */
   void dealPlayer(Player player, int delay) throws IllegalArgumentException;

   /**
    * <pre>
    * Same as {@link #dealPlayer(Player, int)} other than the two notes below but deals for the house and calls the
    * house versions of the callback methods on {@link GameEngineCallback}, no player parameter is required.
    * 
    * <b>Note:</b>
    * 1. At the end of the round but before calling calling {@link GameEngineCallback#houseResult(int, GameEngine)}
    * this method should iterate all players and call {@link GameEngine#applyWinLoss(Player, int)} 
    * to update each player's points.
    * 2. After calling {@link GameEngineCallback#houseResult(int, GameEngine)}
    * this method should also call {@link Player#resetBet()} on each player in preparation for 
    * the next round.
    * </pre>
    * @param delay the delay between cards being dealt (in milliseconds (ms))
    * @throws IllegalArgumentException thrown when delay param is {@literal <} 0
    */
   void dealHouse(int delay) throws IllegalArgumentException;

   /**
    * <pre>
    * A player's bet is settled by this method i.e. win or loss is applied to update betting points
    * based on a comparison of the player result and the provided houseResult.
    * <b>Note:</b>
    * This method is usually called from {@link GameEngine#dealHouse(int)}
    * as described above but is included in the public interface to facilitate testing.
    * </pre>
    * @param player the Player to apply win/loss to
    * @param houseResult contains the calculated house result
    */
   void applyWinLoss(Player player, int houseResult);

   /**
    *  <b>Note:</b> Player id is unique and if another player with same id is added it replaces the previous player.
    * @param player to add to the {@link GameEngine}
    */
   void addPlayer(Player player);

   /**
    * Method to access players in the {@link GameEngine}.
    * @param id id of player to retrieve.
    * @return the Player or null if Player does not exist
    */
   Player getPlayer(String id);

   /**
    * Method to remove players in the {@link GameEngine}.
    * @param player to remove
    * @return true if the player existed and was removed, false otherwise
    */
   boolean removePlayer(Player player);

   /**
    * The implementation forwards the call to the player class so the bet is set per player.
    * @param player the player who is placing the bet
    * @param bet the bet in points
    * @return true if the player had sufficient points and the bet was valid and placed
    * @see Player#setBet(int)
    */
   boolean placeBet(Player player, int bet);

   /**
    * <pre>
    * Method to add a client specific implementation of {@link GameEngineCallback} used to perform display updates etc.
    * Callbacks are called in the order they were added.
    * <b>Note:</b> Used different implementation of the callback for the console and GUI versions.
    * </pre>
    * @param gameEngineCallback the callback object
    */
   void addGameEngineCallback(GameEngineCallback gameEngineCallback);

   /**
    * Method to remove an implementation of {@link GameEngineCallback}.
    * @param gameEngineCallback instance to be removed if no longer needed
    * @return true if the gameEngineCallback existed
    */
   boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback);

   /**
    * Method to retrieve the collection of players sorted in ascending order by player id.
    * @return an unmodifiable collection (or a shallow copy) of all Players.
    */
   Collection<Player> getAllPlayers();

   /**
    * A debug method to return a "half" deck of cards containing 28 unique cards (8 through to Ace) in<br>
    * random/shuffled order (i.e. should return a new deck that is random WRT previous one).
    * @return a Deque (specific type of collection) of PlayingCard
    */
   Deque<PlayingCard> getShuffledHalfDeck();
}
