package view.model;

import java.util.List;
import java.util.ArrayList;

import model.interfaces.PlayingCard;

/**
 * Class to hold additional {@link model.interfaces.Player} related information.
 * Mostly consists of Getters/Setters.
 */
public class PlayerState
{
    private boolean hasBet;
    private boolean hasBeenDealt;
    private boolean hasBusted;
    private int previousBet;
    private int previousPoints;
    private ResultSummary resultSummary = ResultSummary.NONE;
    private List<PlayingCard> hand = new ArrayList<>();

    /**
     * Enums to represent the result summary.
     */
    public enum ResultSummary
    {
        WON, LOST, DREW, NONE
    }

    /**
     * Getter for the boolean hasBet attribute.
     * @return true if player has bet, false otherwise
     */
    public boolean hasBet()
    {
        return hasBet;
    }

    /**
     * Setter for the boolean hasBet attribute.
     * @param hasBet true if player has bet, false otherwise
     */
    public void setHasBet(boolean hasBet)
    {
        this.hasBet = hasBet;
    }

    /**
     * Getter for the boolean hasDealt attribute.
     * @return true if player has been dealt, false otherwise
     */
    public boolean hasBeenDealt()
    {
        return hasBeenDealt;
    }

    /**
     * Setter for the boolean hasDealt attribute.
     * @param hasBeenDealt true if player has been dealt, false otherwise
     */
    public void setHasBeenDealt(boolean hasBeenDealt)
    {
        this.hasBeenDealt = hasBeenDealt;
    }

    /**
     * Getter for the boolean hasBusted attribute.
     * @return true if player has busted, false otherwise
     */
    public boolean hasBusted()
    {
        return hasBusted;
    }

    /**
     * Setter for the boolean hasBusted attribute.
     * @param hasBusted true if player has busted, false otherwise
     */
    public void setHasBusted(boolean hasBusted)
    {
        this.hasBusted = hasBusted;
    }

    /**
     * Getter for the player's previous bet amount.
     * @return the previous bet
     */
    public int getPreviousBet()
    {
        return previousBet;
    }

    /**
     * Setter for the player's previous bet amount.
     * @param previousBet the previous bet
     */
    public void setPreviousBet(int previousBet)
    {
        this.previousBet = previousBet;
    }

    /**
     * Getter for the player's previous round's points.
     * @return the previous points
     */
    public int getPreviousPoints()
    {
        return previousPoints;
    }

    /**
     * Setter for the player's previous round's points.
     * @param previousPoints the previous points
     */
    public void setPreviousPoints(int previousPoints)
    {
        this.previousPoints = previousPoints;
    }

    /**
     * Getter for the player's previous round's result summary.
     * @return the {@link ResultSummary} enum
     */
    public ResultSummary getResultSummary()
    {
        return resultSummary;
    }

    /**
     * Setter for the player's previous round's result summary.
     * @param resultSummary the {@link ResultSummary} enum
     */
    public void setResultSummary(ResultSummary resultSummary)
    {
        this.resultSummary = resultSummary;
    }

    /**
     * Getter for the player's hand.
     * @return the collection of {@link PlayingCard}
     */
    public List<PlayingCard> getHand()
    {
        return hand;
    }

    /**
     * Method to reset the player's hand once round ends.
     */
    public void resetHand()
    {
        hand = new ArrayList<>();
    }

    /**
     * Method to add dealt cards to the player's hand.
     * @param card the {@link PlayingCard} to be added
     */
    public void addPlayingCard(PlayingCard card)
    {
        hand.add(card);
    }
}
