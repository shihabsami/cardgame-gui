package view.callback;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;

/**
 * Implementation of {@link GameEngineCallback} for console logging behaviour.
 */
public class GameEngineCallbackImpl implements GameEngineCallback
{
   public static final Logger logger = Logger.getLogger(GameEngineCallbackImpl.class.getName());

   /**
    * Utility method to set output level of logging handlers.
    * @param level the Level ({@link Level#OFF} / {@link Level#INFO} / {@link Level#FINE})  to set all loggers to
    * @param logger the Logger object
    */
   public static void setAllHandlers(Level level, Logger logger)
   {
      if (logger != null)
      {
         logger.setLevel(level);
         for (Handler handler : logger.getHandlers())
            handler.setLevel(level);

         setAllHandlers(level, logger.getParent());
      }
   }

   public GameEngineCallbackImpl()
   {
      setAllHandlers(Level.INFO, logger);
   }

   @Override
   public void nextCard(Player player, PlayingCard card, GameEngine engine)
   {
      // intermediate results logged at Level.FINE
      logger.log(Level.FINE, String.format("Card Dealt to %s .. %s", player.getPlayerName(), card.toString()));
   }

   @Override
   public void bustCard(Player player, PlayingCard card, GameEngine engine)
   {
       logger.log(Level.INFO, String.format("Card Dealt to %s .. %s ... YOU BUSTED!", player.getPlayerName(), card.toString()));
   }

   @Override
   public void result(Player player, int result, GameEngine engine)
   {
       // final results logged at Level.INFO
       logger.log(Level.INFO, String.format("%s, final result=%d", player.getPlayerName(), result));
   }

   @Override
   public void nextHouseCard(PlayingCard card, GameEngine engine)
   {
       logger.log(Level.FINE, String.format("Card Dealt to House .. %s", card.toString()));
   }

   @Override
   public void houseBustCard(PlayingCard card, GameEngine engine)
   {
       logger.log(Level.INFO, String.format("Card Dealt to House .. %s ... HOUSE BUSTED!", card.toString()));
   }

   @Override
   public void houseResult(int result, GameEngine engine)
   {
       StringBuilder finalResult = new StringBuilder();

       for (Player player : engine.getAllPlayers())
           finalResult.append(String.format("%s\n", player.toString()));

       logger.log(Level.INFO, String.format("House, final result=%d", result));
       logger.log(Level.INFO, String.format("Final Player Results\n%s", finalResult));
   }
}
