package controller;

import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import view.GameFrame;
import view.utility.GameOptionPane;

/**
 * Controller for the {@link GameFrame} class.
 */
public class GameFrameController extends WindowAdapter implements ComponentListener
{
    private GameFrame gameFrame;

    public GameFrameController(GameFrame gameFrame)
    {
        this.gameFrame = gameFrame;
    }

    @Override
    public void windowClosing(WindowEvent event)
    {
        // display the confirmation dialog if the frame is closing.
        GameOptionPane.exitGameDialog();
    }

    @Override
    public void componentResized(ComponentEvent event)
    {
        // resize the summary panel when the frame is resized.
        gameFrame.updateSummaryPanelSize();
    }

    /*
     * Methods left unused since the use of multiple interface and delegate classes to achieve
     * multi inheritance for a task as small as this (a single method) is believed to be not worth it
     */
    @Override
    public void componentMoved(ComponentEvent event) {}

    @Override
    public void componentShown(ComponentEvent event) {}

    @Override
    public void componentHidden(ComponentEvent event) {}
}
