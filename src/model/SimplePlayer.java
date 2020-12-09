package model;

import model.interfaces.Player;

public class SimplePlayer implements Player
{
    private String id;
    private String playerName;
    private int initialPoints;
    private int bet;
    private int result;

    public SimplePlayer(String id, String playerName, int initialPoints)
    {
        if (id == null || playerName == null || initialPoints < 0)
            throw new IllegalArgumentException();

        this.id = id;
        this.playerName = playerName;
        this.initialPoints = initialPoints;
    }

	@Override
	public String getPlayerName()
	{
		return playerName;
	}

	@Override
	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}

	@Override
	public int getPoints()
	{
		return initialPoints;
	}

	@Override
	public void setPoints(int points)
	{
		this.initialPoints = points;
	}

	@Override
	public String getPlayerId()
	{
		return id;
	}

	@Override
	public boolean setBet(int bet)
	{
        // place the bet if player has sufficient points to bet
        if (bet > 0 && initialPoints >= bet)
        {
            this.bet = bet;
            return true;
        }
        return false;
	}

	@Override
	public int getBet()
	{
		return bet;
	}

	@Override
	public void resetBet()
	{
        bet = 0;
	}

	@Override
	public int getResult()
	{
		return result;
	}

	@Override
	public void setResult(int result)
	{
        this.result = result;
	}

	@Override
	public boolean equals(Player player)
	{
        return id.equals(player.getPlayerId());
	}

	@Override
	public boolean equals(Object player)
	{
        if (player instanceof Player)
            return equals((SimplePlayer) player);

        return false;
	}
	
	@Override
	public int hashCode()
	{
        // hashCode generated based on the player's id attribute
		return id.hashCode();
	}
	
	@Override
	public int compareTo(Player player)
	{
        // compare players based on id
        return id.compareTo(player.getPlayerId());
	}

	@Override
	public String toString()
	{
        return String.format("Player: id=%s, name=%s, bet=%d, points=%d, RESULT .. %d",
                id, playerName, bet, initialPoints, result);
	}
}
