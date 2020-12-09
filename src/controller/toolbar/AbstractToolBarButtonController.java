package controller.toolbar;

import java.awt.event.ActionListener;

import view.GameToolBar;
import view.interfaces.GameEngineSupport;

/**
 * Abstract controller for the toolbar button controllers to extend from.
 * Holds reference to the {@link GameToolBar} and the {@link GameEngineSupport}.
 */
public abstract class AbstractToolBarButtonController implements ActionListener
{
    protected GameToolBar gameToolBar;
    protected GameEngineSupport gameEngine;

    /**
     * Sets the fields on behalf of the extending classes.
     * @param gameToolBar the toolbar on which the button resides
     * @param gameEngine the auxiliary game engine
     */
    public AbstractToolBarButtonController(GameToolBar gameToolBar, GameEngineSupport gameEngine)
    {
        this.gameToolBar = gameToolBar;
        this.gameEngine = gameEngine;
    }
}
