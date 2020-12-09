package controller.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.utility.GameOptionPane;

/**
 * Controller for the "Exit" menu item.
 */
public class ExitMenuItemController implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent event)
    {
    	// display the exit confirmation dialog
        GameOptionPane.exitGameDialog();
    }
}
