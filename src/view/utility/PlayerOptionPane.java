package view.utility;

import javax.swing.JLabel;
import javax.swing.JTextField;

import model.SimplePlayer;
import model.interfaces.Player;

import java.util.List;

/**
 * Utility class to receive player details as input and display player related dialogs.
 */
public class PlayerOptionPane extends AbstractInputPane
{
    private static final String EMPTY = "";

    /**
     * Prompt the user to insert the details for the player to be added.
     * @return the Player created from the user input, null if the prompt was closed/cancelled
     */
    public static Player addPlayerPrompt()
    {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField pointsField = new JTextField();

        // the components to be displayed by the prompt
        Object[] queryComponents = new Object[] { "Enter player details.", "Player ID:", idField,
            "Player name:", nameField, "Initial points:", pointsField };

        while (true)
        {
            try
            {
                int answer = showConfirmDialog(null, queryComponents, "Add Player", OK_CANCEL_OPTION);

                if (answer == CANCEL_OPTION || answer == CLOSED_OPTION)
                {
                    showMessageDialog(null, "Player add cancelled.", "Cancel", INFORMATION_MESSAGE);
                    return null;
                }
                // validate the input fields
                else if (strip(nameField.getText()).equals(EMPTY))
                    showMessageDialog(null, "Player name cannot be empty. Try Again.",
                    "Error", ERROR_MESSAGE);
                else if (strip(idField.getText()).equals(EMPTY))
                    showMessageDialog(null, "Player ID cannot be empty. Try Again.",
                    "Error", ERROR_MESSAGE);
                else if (Integer.parseInt(strip(pointsField.getText())) <= 0)
                    throw new NumberFormatException();

                return new SimplePlayer(strip(idField.getText()), nameField.getText(),
                    Integer.parseInt(strip(pointsField.getText())));
            }
            catch (NumberFormatException exception)
            {
                showMessageDialog(null, "Invalid value entered for initial points. Try again.",
            		"Error", ERROR_MESSAGE);
            }
        }
    }

    /**
     * Dialog to be displayed if player add was a success.
     * @param player the Player who was added
     */
    public static void playerAddedDialog(Player player)
    {
        showMessageDialog(null, String.format("Player %s successfully added.", player.getPlayerName()), "Success",
            INFORMATION_MESSAGE);
    }

    /**
     * Dialog to be displayed if player remove was a success.
     * @param player the Player who was removed
     */
    public static void playerRemovedDialog(Player player)
    {
        showMessageDialog(null, String.format("Player %s successfully removed.", player.getPlayerName()), "Success",
            INFORMATION_MESSAGE);
    }

    /**
     * Dialog to be displayed when player(s) run out of points and is/are kicked from the game.
     * @param players the collection of players to be kicked
     */
    public static void playersKickedDialog(List<Player> players)
    {
        StringBuilder dialogMessage = new StringBuilder("<html>");
        dialogMessage.append(players.get(0).getPlayerName());
        
        for (int i = 1; i < players.size(); i++)
            dialogMessage.append(String.format(", %s", players.get(i).getPlayerName()));
       
        dialogMessage.append(String.format(" %s kicked from the game for running out of points.</html>",
    		(players.size() == 1) ? "was" : "were"));

        showMessageDialog(null, new JLabel(dialogMessage.toString()), "Players Kicked",
            INFORMATION_MESSAGE);
    }
}
