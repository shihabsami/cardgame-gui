package controller.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import view.utility.GameOptionPane;

/**
 * Controller for the "About" menu item.
 */
public class AboutMenuItemController extends MouseAdapter implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent event)
	{
		// display the about dialog
		GameOptionPane.aboutSectionDialog();
	}
}
