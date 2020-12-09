package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import model.interfaces.Player;
import view.PlayerSelectionBox;
import view.interfaces.SelectionChangeFollower;

/**
 * Controller for classes that are interested in selection change events, i.e. {@link SelectionChangeFollower}.
 */
public  class SelectionChangeController implements PropertyChangeListener
{
    private SelectionChangeFollower follower;

    public SelectionChangeController(SelectionChangeFollower follower)
    {
        this.follower = follower;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event)
    {
        // notifies followers of selection change, the new value is the currently selected player
        if (event.getPropertyName().equals(PlayerSelectionBox.SELECTION_CHANGE_PROPERTY) && event.getNewValue() != null)
            follower.selectionChange((Player) event.getNewValue());
    }
}
