package view.utility;

import javax.swing.JTextField;

/**
 * Utility class to receive bet input as well as display bet related dialogs.
 */
public class BetOptionPane extends AbstractInputPane
{
    public static final int BET_CANCELLED = -1;

    /**
     * Prompt the user to insert the bet amount.
     * @return the bet amount, {@link BetOptionPane#BET_CANCELLED} if the prompt was closed/cancelled
     */
    public static int placeBetPrompt()
    {
        JTextField betField = new JTextField();

        while (true)
        {
            try
            {
                // show the initial prompt
                int answer = showConfirmDialog(null, new Object[] { "Enter the bet amount.", betField },
            		"Place Bet", OK_CANCEL_OPTION);

                if (answer == CANCEL_OPTION || answer == CLOSED_OPTION)
                {
                    showMessageDialog(null, "Placing bet cancelled.", "Cancel",
                        INFORMATION_MESSAGE);
                    return BET_CANCELLED;
                }
                // validate the bet amount
                else if (Integer.parseInt(strip(betField.getText())) <= 0)
                    throw new NumberFormatException();

                return Integer.parseInt(strip(betField.getText()));
            }
            catch (NumberFormatException exception)
            {
                showMessageDialog(null, "Invalid value entered. Try Again.", "Error",
                    ERROR_MESSAGE);
            }
        }
    }

    /**
     * Dialog to be displayed if bet placement was a success.
     */
    public static void betSuccessDialog()
    {
        showMessageDialog(null, "Bet placed successfully.", "Success",
            INFORMATION_MESSAGE);
    }

    /**
     * Dialog to be displayed if bet placement failed.
     */
    public static void betFailureDialog()
    {
        showMessageDialog(null, "Player does not have sufficient points to place bet.", "Failure",
            ERROR_MESSAGE);
    }

    /**
     * Dialog to be displayed if bet was reset.
     */
    public static void betResetDialog()
    {
        showMessageDialog(null, "Bet reset successfully.", "Success",
            INFORMATION_MESSAGE);
    }
}
