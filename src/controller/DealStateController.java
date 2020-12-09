package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import view.GameFrame;
import view.interfaces.DealStateChangeFollower;

/**
 * Controller for classes that are interested in deal state change events, i.e. {@link DealStateChangeFollower}.
 */
public class DealStateController implements PropertyChangeListener
{
	private DealStateChangeFollower follower;
	
	public DealStateController(DealStateChangeFollower follower)
	{
		this.follower = follower;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event)
	{
		// notifies followers of the change, the new value is the current boolean state of the deal
		if (event.getPropertyName().equals(GameFrame.DEAL_PROPERTY) && event.getNewValue() != null)
			follower.dealStateChange((boolean) event.getNewValue());
	}
}
