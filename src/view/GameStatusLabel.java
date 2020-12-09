package view;

import java.awt.Toolkit;
import java.awt.Dimension;

import javax.swing.JLabel;

import javax.swing.BorderFactory;

import model.interfaces.Player;
import view.interfaces.SelectionChangeFollower;

/**
 * The JLabel used to display the status of the game.
 */
public class GameStatusLabel extends JLabel implements SelectionChangeFollower
{
    public GameStatusLabel()
    {
    	// parent container's BorderLayout ignores the set width, but respects the estimated line height
        setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 40));
        setBorder(BorderFactory.createTitledBorder("Game Status"));
        setText("Idle.");
    }

    @Override
    public void selectionChange(Player player)
    {
        // update the status text on selection change
        setText(String.format("Currently selected player is %s.", player.getPlayerName()));
    }
}
