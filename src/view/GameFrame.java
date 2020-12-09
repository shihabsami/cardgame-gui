package view;

import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.JMenuBar;

import view.interfaces.GameEngineSupport;
import controller.DealStateController;
import controller.GameFrameController;
import controller.SelectionChangeController;

/**
 * The main JFrame class where all {@link view} components are created.
 */
public class GameFrame extends JFrame
{
    private GameMenu gameMenu;
    private GameToolBar gameToolBar;
    private CardPanel cardPanel;
    private SummaryPanel summaryPanel;
    private GameStatusLabel gameStatusLabel;

    // constants used for the sizing
    private final double FRAME_HEIGHT_RATIO = 3/2f;
    private final double SUMMARY_PANEL_RATIO = 1/4f;
    private final int GAP = 5;
    
    public static final String DEAL_PROPERTY = "dealProperty";

    public GameFrame(GameEngineSupport gameEngineSupport)
    {
        // set the properties of the component
        super("CardGameGUI");
        setLayout(new BorderLayout(0, GAP));
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int initialHeight = screenSize.height / 2;
        int initialWidth = (int) (initialHeight * FRAME_HEIGHT_RATIO);
        setMinimumSize(new Dimension(initialWidth, initialHeight));
        setBounds(screenSize.width/2 - initialWidth/2, screenSize.height/2 - initialHeight/2, initialWidth, initialHeight);

        // the child components
        gameToolBar = new GameToolBar(gameEngineSupport);
        cardPanel = new CardPanel(gameEngineSupport);
        summaryPanel = new SummaryPanel(gameEngineSupport);
        gameStatusLabel = new GameStatusLabel();
        gameMenu = new GameMenu(gameToolBar, gameEngineSupport);

        // add controllers that control the frame's resize, closing event
        GameFrameController gameFrameController = new GameFrameController(this);
        addComponentListener(gameFrameController);
        addWindowListener(gameFrameController);
        
        // add listeners for the classes interested in the change in deal state
        addPropertyChangeListener(new DealStateController(gameMenu));
        addPropertyChangeListener(new DealStateController(gameToolBar));

        // add listeners for the classes interested in the selection change
        gameToolBar.addSelectedChangeListener(new SelectionChangeController(cardPanel));
        gameToolBar.addSelectedChangeListener(new SelectionChangeController(gameStatusLabel));

        // intermediate panel to hold the toolbar and the menubar
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BorderLayout(0, GAP));
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(gameMenu);
        actionPanel.add(menuBar, BorderLayout.NORTH);
        actionPanel.add(gameToolBar, BorderLayout.SOUTH);

        // intermediate panel to hold the card panel and the summary panel
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout(0, GAP));
        gamePanel.add(cardPanel, BorderLayout.CENTER);
        gamePanel.add(summaryPanel, BorderLayout.SOUTH);
        updateSummaryPanelSize();

        // add all the components
        add(actionPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
        add(gameStatusLabel, BorderLayout.SOUTH);
        setVisible(true);
    }
    
    /**
     * Notifies all {@link view.interfaces.DealStateChangeFollower} of the change in the deal state.
     * @param dealOngoing true if a deal is ongoing, false otherwise
     */
    public void notifyDealStateChange(boolean dealOngoing)
    {
    	firePropertyChange(DEAL_PROPERTY, !dealOngoing, dealOngoing);
    }
    
    /**
     * Method to update the {@link GameToolBar}, specifically the {@link PlayerSelectionBox}.
     */
    public void updateGameToolBar()
    {
        gameToolBar.updatePlayerSelectionBox();
    }

    /**
     * Method to update the {@link CardPanel}'s graphics.
     */
    public void updateCardPanel()
    {
        cardPanel.repaint();
    }

    /**
     * Method to update the {@link SummaryPanel}'s table information.
     */
    public void updateSummaryPanel()
    {
        summaryPanel.updateSummaryPanel();
    }

    /**
     * Method to update the {@link SummaryPanel}'s size.
     */
    public void updateSummaryPanelSize()
    {
        summaryPanel.setPreferredSize(new Dimension(getWidth(), (int) (getHeight() * SUMMARY_PANEL_RATIO)));
    }

    /**
     * Method that updates the status text on the {@link GameStatusLabel}.
     * @param status the status String
     */
    public void updateGameStatusLabelText(String status)
    {
        gameStatusLabel.setText(status);
    }
}
