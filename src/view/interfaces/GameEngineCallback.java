package view.interfaces;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;

/**
 * Interface for notifying client of {@link GameEngine} events i.e. the state of the cards as dealt.
 */
public interface GameEngineCallback
{
   /**
    * <pre>
    * Called for each card as the house is dealing to a Player, used to
    * update display for each card or log to console.
    * </pre>
    * @param player the Player who is receiving cards
    * @param card the next PlayingCard that was dealt
    * @param engine a convenience reference to the engine so the receiver can call methods if necessary
    */
   void nextCard(Player player, PlayingCard card, GameEngine engine);

   /**<pre>
    * Called when the card causes the player to bust.
    * This method is called instead of {@link #nextCard(Player, PlayingCard, GameEngine)}.
    * This method is called before {@link #result(Player, int, GameEngine)}.
    * Used to update display for each card or log to console.
    * <b>Note:</b> If player gets 42 exactly then this method is not called.
    * </pre>
    * @param player the Player who is receiving cards
    * @param card the bust card that was dealt
    * @param engine a convenience reference to the engine so the receiver can call methods if necessary
    */
   void bustCard(Player player, PlayingCard card, GameEngine engine);

   /**
    * <pre>
    * Called after the player has bust with final result (result is score prior
    * to the last card that caused the bust).
    * Called from {@link GameEngine#dealPlayer(Player, int)}.
    * </pre>
    * @param player the current Player
    * @param result the final score of the round
    * @param engine a convenience reference to the engine so the receiver can call methods if necessary
    */
   void result(Player player, int result, GameEngine engine);

   /**
    * <pre>
    * Called as the house is dealing their own hand, used to update
    * display for each card or log to console.
    * </pre>
    * @param card the next card that was dealt
    * @param engine a convenience reference to the engine so the receiver can call methods if necessary
    */
   void nextHouseCard(PlayingCard card, GameEngine engine);

   /**
    * <pre>
    * House's version of {@link GameEngineCallback#bustCard(Player, PlayingCard, GameEngine)}.
    * <b>Note:</b> If player gets 42 exactly then this method is not called.
    * </pre>
    * @param card the bust card that was dealt
    * @param engine a convenience reference to the engine so the receiver can call methods if necessary
    */
   void houseBustCard(PlayingCard card, GameEngine engine);

   /**
    * <pre>
    * Called when the house has bust with final result (result is score prior to the last card
    * that caused the bust).
    * Called from {@link GameEngine#dealHouse(int)}.
    * <b>Note:</b> This method should only be called after bets have been updated on all Players
    * so this callback can log Player results.
    * </pre>
    * @param result the final score of the dealers (house) hand
    * @param engine a convenience reference to the engine so the receiver can call methods if necessary
    */
   void houseResult(int result, GameEngine engine);
}
