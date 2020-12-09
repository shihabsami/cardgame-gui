package model;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Deque;
import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;

public class GameEngineImpl implements GameEngine
{
    private Deque<PlayingCard> deck;
    private Map<String, Player> players = new TreeMap<>();
    private List<GameEngineCallback> callbacks = new ArrayList<>();

    public GameEngineImpl()
    {
		// initialize the deck of cards
        deck = getShuffledHalfDeck();
    }

	@Override
	public void dealPlayer(Player player, int delay) throws IllegalArgumentException
	{
		// exception thrown for invalid delay value
        if (delay < 0 || delay > 1000)
            throw new IllegalArgumentException();

        // prevent dealing players who do not exist in the collection
        if (players.containsValue(player))
        {
	        int playerScore = deal(player, delay);
	        
	        // log the player's result of the round
	        for (GameEngineCallback callback : callbacks)
	            callback.result(player, playerScore, this);
	        
	        // update the result of the player's most recent hand
	        player.setResult(playerScore);
        }
	}

	@Override
	public void dealHouse(int delay) throws IllegalArgumentException
	{	
        if (delay < 0)
            throw new IllegalArgumentException();

        int houseScore = deal(null, delay);

        // determine the win/loss of players and update the attributes
        for (Player player : players.values())
            applyWinLoss(player, houseScore);

        // log final results once round ends
        for (GameEngineCallback callback : callbacks)
            callback.houseResult(houseScore, this);

        // reset players' previous bet for next round
        for (Player player : players.values())
            player.resetBet();
	}
	
    /**
     * <pre>
     * Utility method to deal cards for a single round regardless of player/house.
     * <b>Note:</b>
     * Only the {@link GameEngine#dealHouse(int)} is allowed to pass null as the player parameter
     * to distinguish deal behavior for the house, since a player with null value is never added to the collection.
     * </pre>
     * @param delay - the delay in between dealing a card
     * @param player - the player object (player/house) the card is being dealt to
     * @return the final score for this round
     */
    private int deal(Player player, int delay)
    {
        int score = 0;
        PlayingCard card;
        
        while (score < BUST_LEVEL)
        {
            try
            {
                // get a card from the top of the deck
                card = deck.pop();
                score += card.getScore();
                
            	// log the events of this round
                if (player != null)
                    logPlayer(player, card, score);
                else
                    logHouse(card, score);
            	
                Thread.sleep(delay);
                
                if (score > BUST_LEVEL)
                {
                    score -= card.getScore();
                    break;
                }
            }
            catch (InterruptedException exception)
            {
                exception.printStackTrace();
            }
            catch (NoSuchElementException exception)
            {
                // get a new deck if the current deck runs out of cards
                deck = getShuffledHalfDeck();
            }
        }

        return score;
    }
    
   /** Utility method to log the player's round events.
    *
    * @param player - the Player to whom the card is dealt
    * @param card - the dealt PlayingCard
    * @param playerScore - the score the player obtained from the round
    */
   private void logPlayer(Player player, PlayingCard card, int playerScore)
   {
       for (GameEngineCallback callback : callbacks)
       {
           if (playerScore > BUST_LEVEL)
               // log the details of the card that caused the bust
               callback.bustCard(player, card, this);
           else
               // log the details of the dealt card
               callback.nextCard(player, card, this);
       }
   }

   /**
    * House's version of the logger method to log the house's round events.
    *
    * @param card - the dealt PlayingCard
    * @param houseScore - the score the house obtained from the round
    */
   private void logHouse(PlayingCard card, int houseScore)
   {
       for (GameEngineCallback callback : callbacks)
       {
           if (houseScore > BUST_LEVEL)
               callback.houseBustCard(card, this);
           else
               callback.nextHouseCard(card, this);
       }
   }

	@Override
	public void applyWinLoss(Player player, int houseResult)
	{
        // compare the points of player and house
        if (player.getResult() > houseResult)
            player.setPoints(player.getPoints() + player.getBet());
        else if (player.getResult() < houseResult)
            player.setPoints(player.getPoints() - player.getBet());
	}

	@Override
	public void addPlayer(Player player)
	{
		if (player == null)
			throw new IllegalArgumentException();
		
		// if player with the same id exists, replace the player
		players.put(player.getPlayerId(), player);
	}

	@Override
	public Player getPlayer(String id)
	{
        // if the player exists in the collection
        return players.getOrDefault(id, null);
	}

	@Override
	public boolean removePlayer(Player player)
	{
		// remove if the player exists in the collection
        if (players.containsValue(player))
        {
            players.remove(player.getPlayerId());
            return true;
        }
        return false;
	}

	@Override
	public boolean placeBet(Player player, int bet)
	{
		// prevent placing bet if the player is not added in the collection
		if (!players.containsValue(player))
			throw new IllegalArgumentException();
		
		return player.setBet(bet);
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback)
	{
		// add game engine callback
		if (gameEngineCallback != null)
			callbacks.add(gameEngineCallback);
	}

	@Override
	public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback)
	{
        // remove game engine callback if it exists in the collection
        if (callbacks.contains(gameEngineCallback))
        {
            callbacks.remove(gameEngineCallback);
            return true;
        }
        return false;
	}

	@Override
	public Collection<Player> getAllPlayers()
	{
        // the collection containing all the players
        return Collections.unmodifiableCollection(players.values());
	}

	@Override
	public Deque<PlayingCard> getShuffledHalfDeck()
	{   
		LinkedList<PlayingCard> deck = new LinkedList<>();
		
        // nested loops to populate the deck of cards
        for (PlayingCard.Suit suit : PlayingCard.Suit.values())
            for (PlayingCard.Value value : PlayingCard.Value.values())
            	deck.add(new PlayingCardImpl(suit, value));
        
        // shuffle the deck of cards
        Collections.shuffle(deck);
        return deck;
	}
}
