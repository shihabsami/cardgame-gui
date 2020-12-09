package view;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.BorderFactory;

import model.interfaces.Player;
import view.model.PlayerState;
import view.interfaces.DealStateChangeFollower;
import view.interfaces.GameEngineSupport;
import view.interfaces.SelectionChangeFollower;
import controller.SelectionChangeController;
import controller.toolbar.AddPlayerButtonController;
import controller.toolbar.BetButtonController;
import controller.toolbar.DealButtonController;
import controller.toolbar.RemovePlayerButtonController;
import controller.toolbar.ResetBetButtonController;

/**
 * The JToolBar class containing the game {@link JButton}s and the {@link PlayerSelectionBox}.
 */
public class GameToolBar extends JToolBar implements SelectionChangeFollower, DealStateChangeFollower
{
    private boolean dealOngoing;
    private Player housePlayer;
    private GameEngineSupport gameEngineSupport;

    // child components
    private JButton dealButton;
    private JButton betButton;
    private JButton resetBetButton;
    private JButton addPlayerButton;
    private JButton removePlayerButton;
    private PlayerSelectionBox comboBox;

    private DealButtonController dealButtonController;
    private AddPlayerButtonController addPlayerButtonController;
    private RemovePlayerButtonController removePlayerButtonController;

    public GameToolBar(GameEngineSupport gameEngineSupport)
    {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Tool Bar"));
        setFloatable(false);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 3, 0, 3);

        this.gameEngineSupport = gameEngineSupport;
        this.housePlayer = gameEngineSupport.getHousePlayer();

        // add the deal button and the controller
        dealButton = new JButton("Deal");
        dealButtonController = new DealButtonController(this, gameEngineSupport);
        dealButton.addActionListener(dealButtonController);
        constraints.gridx = 0;
        add(dealButton, constraints);

        // add bet button and the controller
        betButton = new JButton("Place Bet");
        betButton.addActionListener(new BetButtonController(this, gameEngineSupport));
        constraints.gridx = 1;
        add(betButton, constraints);

        // add the reset bet button and the controller
        resetBetButton = new JButton("Reset Bet");
        resetBetButton.addActionListener(new ResetBetButtonController(this, gameEngineSupport));
        constraints.gridx = 2;
        add(resetBetButton, constraints);

        // add the combo box, set house player and add the controller
        comboBox = new PlayerSelectionBox();
        comboBox.setHousePlayer(housePlayer);
        comboBox.addPropertyChangeListener(new SelectionChangeController(this));
        constraints.gridx = 3;
        constraints.weightx = 1;
        add(comboBox, constraints);

        // add the add player button and the controller
        addPlayerButton = new JButton("Add Player");
        addPlayerButtonController = new AddPlayerButtonController(this, gameEngineSupport);
        addPlayerButton.addActionListener(addPlayerButtonController);
        constraints.gridx = 4;
        constraints.weightx = 0;
        add(addPlayerButton, constraints);

        // add the remove player button and the controller
        removePlayerButton = new JButton("Remove Player");
        removePlayerButtonController = new RemovePlayerButtonController(this, gameEngineSupport);
        removePlayerButton.addActionListener(removePlayerButtonController);
        constraints.gridx = 5;
        add(removePlayerButton, constraints);

        setHouseButtonState();
    }
 
    @Override
    public void dealStateChange(boolean dealOngoing)
    {
        // update button states accordingly
        this.dealOngoing = dealOngoing;
        updateButtonState(getSelectedPlayer());
    }

    /**
     * For situations when the last not-dealt player is removed force a house deal.
     */
    public void forceHouseDeal()
    {
        dealButtonController.actionPerformed(null);
    }

    /**
     * Getter for the house player. Used when dealing for the house.
     * @return the Player object representing house
     */
    public Player getHousePlayer()
    {
        return housePlayer;
    }

    /**
     * For the components to query about the currently selected player.
     * @return the selected Player in the {@link PlayerSelectionBox}
     */
    public Player getSelectedPlayer()
    {
        return (Player) comboBox.getSelectedItem();
    }

    /**
     * Method to manually set the currently selected player.
     * @param player the Player to be selected by the {@link PlayerSelectionBox}
     */
    public void setSelectedPlayer(Player player)
    {
        comboBox.setSelectedItem(player);
    }

    @Override
    public void selectionChange(Player player)
    {
        // update buttons states based on the selected player
        updateButtonState(player);
    }

    /**
     * Method to add listeners to the {@link PlayerSelectionBox} for {@link SelectionChangeFollower}s.
     * @param listener the SelectionChangeController
     */
    public void addSelectedChangeListener(SelectionChangeController listener)
    {
        comboBox.addPropertyChangeListener(PlayerSelectionBox.SELECTION_CHANGE_PROPERTY, listener);
    }

    /**
     * Method to update the {@link PlayerSelectionBox}.
     */
    public void updatePlayerSelectionBox()
    {
        comboBox.updateComboBox(gameEngineSupport.getAllPlayers());
    }

    /**
     * Method to update the button states based on a player.
     * @param player the player for which the button state is determined
     */
    public void updateButtonState(Player player)
    {
        if (player.equals(housePlayer)) setHouseButtonState();
        else setPlayerButtonState(player);
    }

    /**
     * Set button state according to a player.
     * @param player the Player object
     */
    private void setPlayerButtonState(Player player)
    {
        PlayerState playerState = gameEngineSupport.getPlayerState(player);
        dealButton.setEnabled(!dealOngoing && playerState.hasBet() && !playerState.hasBeenDealt());
        betButton.setEnabled(!playerState.hasBet());
        resetBetButton.setEnabled(!dealOngoing && playerState.hasBet() && !playerState.hasBeenDealt());
        addPlayerButton.setEnabled(!dealOngoing);
        removePlayerButton.setEnabled(!dealOngoing);
    }

    /**
     * Set button state according to the house {@link Player}.
     */
    private void setHouseButtonState()
    {
        dealButton.setEnabled(false);
        betButton.setEnabled(false);
        resetBetButton.setEnabled(false);
        addPlayerButton.setEnabled(!dealOngoing);
        removePlayerButton.setEnabled(false);
    }
    
    /**
     * Getter for the controller that allows adding a {@link Player}.
     * @return the {@link AddPlayerButtonController}
     */
    public ActionListener getAddPlayerController()
    {
    	return addPlayerButtonController;
    }
    
    /**
     * Getter for the controller that allows removing a {@link Player}.
     * @return the {@link RemovePlayerButtonController}
     */
    public ActionListener getRemovePlayerController()
    {
    	return removePlayerButtonController;
    }
}
