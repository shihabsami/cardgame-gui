package view;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import model.interfaces.Player;
import view.model.PlayerState;
import view.interfaces.GameEngineSupport;

/**
 * The JPanel to display a {@link JTable} containing player's information.
 */
public class SummaryPanel extends JPanel
{
	private GameEngineSupport gameEngineSupport;
    private DefaultTableModel tableModel;
    private final String[] HEADERS = { "Player ID", "Player Name", "Bet Amount", "Points", "Round Result", "Result Summary" };

    public SummaryPanel(GameEngineSupport gameEngineSupport)
    {
        // default GridLayout used so that the JTable takes up the entire space
        setLayout(new GridLayout());
        setBorder(BorderFactory.createTitledBorder("Summary Panel"));
        
        this.gameEngineSupport = gameEngineSupport;

        // custom table model used with pre-defined headers
        tableModel = new DefaultTableModel(HEADERS, 0)
        {
            // the column classes, aids in comparison/alignment in cell
            Class<?>[] types = new Class[] {
                    Integer.class, String.class, Integer.class, Integer.class, Integer.class, String.class };

            @Override
            public Class<?> getColumnClass(int columnIndex)
            {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int row, int column)
            {
                // prevent cell to be edited
                return false;
            }
        };

        // sorter to provide the ability to sort table by columns
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(tableModel);

        // anonymous custom comparator for the Player ID column
        rowSorter.setComparator(0, (object1, object2) ->
        {
            try
            {
                // compare as int, rather than String
                Integer int1 = Integer.parseInt(object1.toString());
                Integer int2 = Integer.parseInt(object2.toString());
                return int1.compareTo(int2);
            }
            catch (NumberFormatException exception)
            {
                return 0;
            }
        });

        // setup JTable and by default sort by Player ID column
        JTable summaryTable = new JTable(tableModel);
        summaryTable.setRowSorter(rowSorter);
        rowSorter.toggleSortOrder(0);

        // custom cell renderer to centre align the cell values
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        summaryTable.setDefaultRenderer(String.class, centerRenderer);
        summaryTable.setDefaultRenderer(Integer.class, centerRenderer);

        // wrap using a scroll pane to allow make table scrollable
        add(new JScrollPane(summaryTable));
    }

    /**
     * Method to update the summary panel based on the auxiliary game engine.
     */
    public void updateSummaryPanel()
    {
        removeAllRows();

        // repopulate
        for (Player player : gameEngineSupport.getAllPlayers())
            tableModel.addRow(getPlayerDetails(player));
    }

    /**
     * Utility method to remove all rows of the table model.
     */
    private void removeAllRows()
    {
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--)
            tableModel.removeRow(i);
    }

    /**
     * Utility method to get all players details for a row in the table.
     * @param player the player who's details are to be retrieved
     * @return all of the player's public attributes as an array
     */
    private Object[] getPlayerDetails(Player player)
    {
    	List<Object> playerDetails = new ArrayList<>(Arrays.asList(player.getPlayerId(), player.getPlayerName(),
                player.getBet(), player.getPoints(), player.getResult()));
    	
        PlayerState.ResultSummary resultSummary = gameEngineSupport.getPlayerState(player).getResultSummary();
        playerDetails.add((resultSummary != PlayerState.ResultSummary.NONE && resultSummary != PlayerState.ResultSummary.DREW) ?
                String.format("%s %d", resultSummary, gameEngineSupport.getPlayerState(player).getPreviousBet()) : resultSummary.toString());
        
        return playerDetails.toArray();
    }
}
