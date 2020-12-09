package view.utility;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Utility class to display general game related dialogs.
 */
public class GameOptionPane extends JOptionPane
{
    /**
     * Dialog to be displayed before the house deal begins.
     */
    public static void houseReadyDialog()
    {
        showMessageDialog(null, "All players dealt. House shall deal now.", "House Ready",
            INFORMATION_MESSAGE);
    }

    /**
     * Dialog to be displayed once round ends.
     */
    public static void playAgainDialog()
    {
        int answer = showConfirmDialog(null, "Play again?", "Game Over",
            OK_CANCEL_OPTION);

        if (answer == CANCEL_OPTION)
            System.exit(0);
    }
    
    /**
     * Dialog to be displayed for the "About" menu item.
     */
    public static void aboutSectionDialog()
    {
    	// some HTML formatting for the JLabel
        String labelText = "<html>" + "<center><b>S3823710 Presents</b>" +
                "<font size=+1><center><b><i><u>C</u>ard<u>G</u>ame<u>G</u>UI</i></b></font><br>" +
                "<font size=-1><center><i>Casino Style Card Game<br></i>" +
                "<center>Version: 1.0.0.1<br>" +
                "</html>";
        JLabel aboutLabel = new JLabel(labelText, JLabel.CENTER);
    	JOptionPane.showMessageDialog(null, aboutLabel, "About", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Dialog to be displayed for the "Check For Update" menu item.
     */
    public static void updateCompleteDialog()
    {
        JOptionPane.showMessageDialog(null, "Already on the latest version.",
    		"Update", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Dialog to be displayed when a new game begins.
     */
    public static void newGameDialog()
    {
        showMessageDialog(null, "A new game begins!", "New Game",
            INFORMATION_MESSAGE);
    }
    
    /**
     * Dialog to be displayed if an exit attempt is made, e.g. frame closing.
     */
    public static void exitGameDialog()
    {
        int answer = JOptionPane.showConfirmDialog(null, "Leave the game?", "Exit",
            JOptionPane.OK_CANCEL_OPTION);

        if (answer == JOptionPane.OK_OPTION)
            System.exit(0);
    }
}
