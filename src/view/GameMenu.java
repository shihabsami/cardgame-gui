package view;

import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import model.interfaces.Player;
import view.interfaces.DealStateChangeFollower;
import view.interfaces.GameEngineSupport;
import view.interfaces.SelectionChangeFollower;
import controller.SelectionChangeController;
import controller.menu.AboutMenuItemController;
import controller.menu.CheckForUpdateMenuItemController;
import controller.menu.ExitMenuItemController;
import controller.menu.NewGameMenuItemController;

/**
 * The JMenu with a few {@link JMenuItem}s.
 */
public class GameMenu extends JMenu implements SelectionChangeFollower, DealStateChangeFollower
{
	private boolean dealOngoing;
	private GameToolBar gameToolBar;

	private JMenuItem newGameMenuItem;
	private JMenuItem addPlayerMenuItem;
	private JMenuItem removePlayerMenuItem;
	private JMenuItem checkForUpdatesMenuItem;
	private JMenuItem aboutMenuItem;
	private JMenuItem exitMenuItem;

    /**
     * Construct a basic menu with a "Exit" and "Check For Updates" menu item.
     * @param gameToolBar the GameToolBar from which it inherits add/remove player functionality
     * @param gameEngineSupport the GameEngineSupport to be called to reset the game
     */
    public GameMenu(GameToolBar gameToolBar, GameEngineSupport gameEngineSupport)
    {
        super("Game Menu");        
        
        this.gameToolBar = gameToolBar;
        gameToolBar.addSelectedChangeListener(new SelectionChangeController(this));
        
        // below are the menu items
        newGameMenuItem = new JMenuItem("New Game");
        newGameMenuItem.addActionListener(new NewGameMenuItemController(gameEngineSupport));
        
        addPlayerMenuItem = new JMenuItem("Add Player");
        addPlayerMenuItem.addActionListener(gameToolBar.getAddPlayerController());
        
        removePlayerMenuItem = new JMenuItem("Remove Player");
        removePlayerMenuItem.addActionListener(gameToolBar.getRemovePlayerController());
        
        checkForUpdatesMenuItem = new JMenuItem("Check For Updates");
        checkForUpdatesMenuItem.addActionListener(new CheckForUpdateMenuItemController());
        
        aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(new AboutMenuItemController());
        
        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ExitMenuItemController());
        
        // set suitable accelerators
        newGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        addPlayerMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, InputEvent.SHIFT_DOWN_MASK));
        removePlayerMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, InputEvent.SHIFT_DOWN_MASK));

        // add the menu items
        add(newGameMenuItem);
        add(addPlayerMenuItem);
        add(removePlayerMenuItem);
        add(checkForUpdatesMenuItem);
        add(aboutMenuItem);
        add(exitMenuItem);
        updateItemState(gameToolBar.getSelectedPlayer());
    }
    
    /**
     * Utility method to update the items' enabled state.
     * @param player the Player for which state should be determined
     */
	private void updateItemState(Player player)
	{
		// enable or disable menu items
		newGameMenuItem.setEnabled(!dealOngoing);
		checkForUpdatesMenuItem.setEnabled(!dealOngoing);
		aboutMenuItem.setEnabled(!dealOngoing);
		exitMenuItem.setEnabled(!dealOngoing);
		addPlayerMenuItem.setEnabled(!dealOngoing);
		removePlayerMenuItem.setEnabled(!player.equals(gameToolBar.getHousePlayer()) && !dealOngoing);
	}

	@Override
	public void selectionChange(Player player)
	{
		// update item states on selection change
		updateItemState(player);
	}
	
	@Override
	public void dealStateChange(boolean dealOngoing)
	{
		// update item states on deal state change
		this.dealOngoing = dealOngoing;
        updateItemState(gameToolBar.getSelectedPlayer());
	}
}
