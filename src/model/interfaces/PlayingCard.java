package model.interfaces;

/**
 * <pre>
 * Interface for representing a PlayingCard.
 * Setting card score is handled by the implementing class constructor(s).
 * </pre>
 */
public interface PlayingCard
{
   /**
    * The possible suits for the cards.
    */
   enum Suit
   {
      HEARTS, SPADES, CLUBS, DIAMONDS
   }

   /**
    * The possible values for the cards.
    */
   enum Value
   {
      EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
   }

   /**
    * 28 cards as specified.
    */
   int DECK_SIZE = Suit.values().length * Value.values().length;

   /**
    * Getter for the card's suit attribute.
    * @return the suit of this card based on {@link Suit}
    */
   Suit getSuit();

   /**
    * Getter for the card's value attribute.
    * @return the face value of this card based on {@link Value}
    */
   Value getValue();

   /**
    * Getter for the card's score attribute.
    * @return the score value of this card (A = 11; J, Q, K = 10; all others of face value)
    */
   int getScore();

   /**
    * <pre>
    * Overridden {@link Object#toString} method.
    * @return a human readable String that lists the values of this PlayingCard instance.
    *         e.g. "Suit: Clubs, Value: Five, Score: 5" for Five of Clubs.
    * <b>Note:</b> Case matches as above.
    * </pre>
    */
   @Override
   String toString();

   /**
    * Method to evaluate whether two cards are equal.
    * @param card another card to compare with
    * @return true if the face value and suit is equal
    */
   boolean equals(PlayingCard card);

   /**
    * <pre>
    * Overridden {@link Object#equals(Object)} method.
    * <b>Note:</b> The implementation should cast and call through to the type checked method above</pre>
    * @param card another card to compare with
    * @return true if the face value and suit is equal
    */
   @Override
   boolean equals(Object card);

   /**
    * Overridden {@link Object#hashCode()} method.
    * @return if {@link #equals(PlayingCard)} is true then generated hashCode should also be equal
    */
   @Override
   int hashCode();
}
