package model;

import model.interfaces.PlayingCard;

public class PlayingCardImpl implements PlayingCard
{
    private final Suit SUIT;
    private final Value VALUE;
    private final int SCORE;

    public PlayingCardImpl(Suit suit, Value value)
    {
        SUIT = suit;
        VALUE = value;
        SCORE = evaluateScore(value);
    }
    
    
   /**
    * Utility method to evaluate a card's score based on the face value.
    * @param value the Value enum of the PlayingCard
    * @return the score for that value
    */
    private int evaluateScore(Value value)
	{
    	// score for ACE = 11, KING, QUEEN, JACK = 10 and the rest of their face value
		switch (value)
		{
			case ACE:
				return 11;
			case EIGHT:
				return 8;
			case NINE:
				return 9;
			default:
				return 10;
		}
	}
    
	@Override
	public Suit getSuit()
	{
		return SUIT;
	}

	@Override
	public Value getValue()
	{
		return VALUE;
	}

	@Override
	public int getScore()
	{
		return SCORE;
	}
	
    @Override
    public String toString()
    {
        return String.format("Suit: %s, Value: %s, Score: %d",
                toTitleCase(SUIT), toTitleCase(VALUE), SCORE);
    }

    /**
     * Utility method to retrieve a PlayingCard's enum attributes in title case.
     *
     * @param e the enum constant
     * @return a title cased String representation of the enum
     */
    private String toTitleCase(Enum<?> e)
    {
    	return e.name().charAt(0) + e.name().substring(1).toLowerCase();
    }

	@Override
	public boolean equals(PlayingCard card)
	{
        return (VALUE == card.getValue() && SUIT == card.getSuit());
	}

    @Override
    public boolean equals(Object card)
    {
        if (card instanceof PlayingCard)
            return equals((PlayingCard) card);
        return false;
    }

    @Override
    public int hashCode()
    {
        // hashCode generated based on the card's suit and value attributes
        return SUIT.hashCode() + VALUE.hashCode();
    }
}
