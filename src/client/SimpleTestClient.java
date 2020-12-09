package client;

import java.util.Deque;
import java.util.logging.Level;

import model.GameEngineImpl;
import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.callback.GameEngineCallbackImpl;

/**
 * Simple console client to launch the console version of the game.
 */
public class SimpleTestClient
{
   public static void main(String[] args)
   {
      final GameEngine gameEngine = new GameEngineImpl();

      // create two players
      Player[] players = new Player[] { new SimplePlayer("2", "The Shark", 1000), new SimplePlayer(
         "1", "The Loser", 500) };

      // add logging callback
      gameEngine.addGameEngineCallback(new GameEngineCallbackImpl());

      // uncomment this to debug deck of cards creation
      Deque<PlayingCard> shuffledDeck = gameEngine.getShuffledHalfDeck();
      printCards(shuffledDeck);

      // main loop to add players, place a bet and receive hand
      for (Player player : players)
      {
         gameEngine.addPlayer(player);
         gameEngine.placeBet(player, 100);
         gameEngine.dealPlayer(player, 100);
      }

      // all players have played so now house deals
      gameEngine.dealHouse(10);
   }

   /**
    * Debug method to print all the cards of a deck.
    * @param deck the collection comprised of 28 cards
    */
   private static void printCards(Deque<PlayingCard> deck)
   {
      for (PlayingCard card : deck)
         GameEngineCallbackImpl.logger.log(Level.INFO, card.toString());
   }
}
