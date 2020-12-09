package controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import view.PlayerSelectionBox;

/**
 * Controller for the {@link PlayerSelectionBox} class.
 */
public class PlayerSelectionBoxController implements ItemListener
{
    private PlayerSelectionBox comboBox;

    public PlayerSelectionBoxController(PlayerSelectionBox comboBox)
    {
        this.comboBox = comboBox;
    }

    @Override
    public void itemStateChanged(ItemEvent event)
    {
        // notifies all followers of the change.
        comboBox.notifySelectionChange();
    }
}
