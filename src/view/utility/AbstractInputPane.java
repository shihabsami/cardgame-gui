package view.utility;

import javax.swing.JOptionPane;

/**
 * Abstract class for input related classes to extend from.
 */
public abstract class AbstractInputPane extends JOptionPane
{
    /**
     * Utility method to strip a {@link String} of all whitespaces.
     * @param string the String to remove spaces from
     * @return the stripped String
     */
    public static String strip(String string)
    {
        return string.replaceAll("\\s+","");
    }
}
