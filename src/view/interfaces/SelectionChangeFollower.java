package view.interfaces;

import model.interfaces.Player;

/**
 * Implemented by classes interested in the selection change in {@link view.PlayerSelectionBox}.
 */
public interface SelectionChangeFollower
{
	/**
	 * Method that's called on a selection change event.
	 * @param player the {@link Player} who is selected
	 */
    void selectionChange(Player player);
}
