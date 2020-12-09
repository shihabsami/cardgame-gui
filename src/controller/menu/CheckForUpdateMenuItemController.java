package controller.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import javax.swing.Action;
import javax.swing.AbstractAction;

import java.io.File;

import view.utility.GameOptionPane;

/**
 * Controller for the "Check For Update" menu item.
 */
public class CheckForUpdateMenuItemController extends WindowAdapter implements ActionListener
{
    private JDialog dialog;
    private Timer timer;

    private final String ICON_NAME = "loading.gif";
    private final String ICON_PATH = String.format("img%s%s", File.separator, ICON_NAME);
    private final int TIME_VISIBLE = 2000;

    /**
     * Setup the views for the controller in the constructor.
     */
    public CheckForUpdateMenuItemController()
    {
        // the icon that is displayed on the loading dialog
        JLabel loadingIconLabel = new JLabel(new ImageIcon(ICON_PATH));

        // dialog without any options to be displayed for a predefined amount of time
        JOptionPane dialogPane = new JOptionPane(new Object[] { "Checking for updates...", loadingIconLabel },
                JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[] {}, null);
        
        // set up the dialog
        dialog = new JDialog();
        dialog.setTitle("Update");
        dialog.setModal(true);
        dialog.setResizable(false);
        dialog.setContentPane(dialogPane);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.addWindowListener(this);

        // the action (not a listener) that's invoked upon the timer ending
        Action dialogCloseAction = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                // dispose the dialog after the timer ends and show a final dialog
                dialog.dispose();
                GameOptionPane.updateCompleteDialog();
            }
        };

        // set up the timer
        timer = new Timer(TIME_VISIBLE, dialogCloseAction);
        timer.setRepeats(false);
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
        // display the update dialog and start the timer
        timer.start();
        dialog.setVisible(true);
    }

    @Override
    public void windowClosing(WindowEvent event)
    {
        // prevent displaying the final dialog if closed
        timer.stop();
    }
}
