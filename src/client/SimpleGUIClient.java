package client;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.GameEngineImpl;
import model.interfaces.GameEngine;
import view.callback.GameEngineCallbackImpl;
import view.callback.GameEngineCallbackGUI;

/**
 * Client to launch the GUI version of the game.
 */
public class SimpleGUIClient
{
    // the pre-defined delay in between cards being dealt
    private static final int DELAY = 100;

    public static void main(String[] args)
    {
        // turn off specific loggers to tidy up the console
        Logger.getLogger("java.awt").setLevel(Level.OFF);
        Logger.getLogger("sun.awt").setLevel(Level.OFF);
        Logger.getLogger("sun.lwawt").setLevel(Level.OFF);
        Logger.getLogger("javax.swing").setLevel(Level.OFF);

        try
        {
            // set the cross-platform look & feel
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException exception)
        {
           exception.printStackTrace();
        }

        final GameEngine gameEngine = new GameEngineImpl();

        // add both the console and GUI logger callbacks
        gameEngine.addGameEngineCallback(new GameEngineCallbackImpl());
        gameEngine.addGameEngineCallback(new GameEngineCallbackGUI(gameEngine, DELAY));
    }
}
