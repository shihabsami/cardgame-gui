package view;

import java.util.Collection;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.DefaultListCellRenderer;

import model.interfaces.Player;
import controller.PlayerSelectionBoxController;

/**
 * The JComboBox class for selecting players.
 */
public class PlayerSelectionBox extends JComboBox<Player>
{
    private Player housePlayer;
    private Player lastSelectedPlayer;
    public static final String SELECTION_CHANGE_PROPERTY = "selectionChangeProperty";

    public PlayerSelectionBox()
    {	
    	// custom cell renderer to display only the player names
        ListCellRenderer<Object> cellRenderer = new DefaultListCellRenderer()
        {
        	@Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus)
            {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value != null) setText(((Player) value).getPlayerName());
                return this;
            }
        };

        setRenderer(cellRenderer);
        addItemListener(new PlayerSelectionBoxController(this));
    }

    /**
     * Method to set the initial house player.
     * @param housePlayer the Player to be added to represent the house
     */
    public void setHousePlayer(Player housePlayer)
    {
        this.housePlayer = housePlayer;
        lastSelectedPlayer = housePlayer;
        addItem(housePlayer);
    }

    /**
     * Method to update the items in the JComboBox.
     * @param players the current collection of players in the {@link model.interfaces.GameEngine}
     */
    public void updateComboBox(Collection<Player> players)
    {
        // repopulate the JComboBox
        int lastItemCount = getItemCount();
        removeAllItems();
        addItem(housePlayer); 
        for (Player player : players) addItem(player);
        setSelectedIndex((getItemCount() > lastItemCount) ? getItemCount() - 1 : 0);
    }

    /**
     * Method to notify {@link view.interfaces.SelectionChangeFollower}s of the property change.
     */
    public void notifySelectionChange()
    {
        firePropertyChange(SELECTION_CHANGE_PROPERTY, lastSelectedPlayer, getSelectedItem());
        lastSelectedPlayer = (Player) getSelectedItem();
    }
}
