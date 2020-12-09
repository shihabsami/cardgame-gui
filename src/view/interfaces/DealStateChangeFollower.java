package view.interfaces;

/**
 * Implemented by view classes interested in the deal state.
 */
public interface DealStateChangeFollower
{
	/**
	 * Method that's called upon receiving notification for change in deal state.
	 * @param dealOngoing true if a deal is going on, false otherwise
	 */
	void dealStateChange(boolean dealOngoing);
}
