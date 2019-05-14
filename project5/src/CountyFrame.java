import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class creates a pop-up window that displays information about all 
 * of the stations in the county. The new window is non-modal so multiple 
 * such windows can be created.
 * <P>
 * Project 5 <BR>
 * 
 * 
 * @version 2016-11-10
 * 
 * @author CS 2334.   Modified by ????
 *
 */

public class CountyFrame extends JFrame 
{
    
    private static final long serialVersionUID = 1L;

    /**
     * Window constructor for a single county.
     * 
     * <P>
     * NORTH: County name <BR>
     * CENTER: columns for the stations <BR>
     * SOUTH: Close button
     * 
     * <P>
     * 
     * @param countyDefinition The county that we are describing in 
     * this window
     */
    public CountyFrame(CountyDefinition countyDefinition)
    {
        super("County Viewer");
        this.setLayout(new BorderLayout());

        //////////////////////////////////////////////////////
        // Header Panel: displays county name
        JPanel headerPanel;
        headerPanel = new JPanel();
        headerPanel.add(new JLabel("County: " + countyDefinition.getName()));
        this.add(headerPanel, BorderLayout.NORTH);
        Color topColor = new Color(240, 200, 200);
        headerPanel.setBackground(topColor);

        /////////////////////////////////////////////////////
        // Station panel: Two sub-panels: one for the labels (left)
        //  and the other for describing the stations.
        JPanel stationPanel;
        stationPanel = new JPanel();
        stationPanel.setLayout(new GridLayout(1, 0));

        // Label panel
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridBagLayout());
        stationPanel.add(labelPanel);
        Color labelColor = new Color(200, 200, 230);
        labelPanel.setBackground(labelColor);

        // Place one label per row 
        GridBagConstraints layoutConst;    /**
         * 
         */
        layoutConst = new GridBagConstraints();
        layoutConst.gridx = 0;
        layoutConst.gridy = 0;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        layoutConst.anchor = GridBagConstraints.WEST;

        labelPanel.add(new JLabel("Station ID"), layoutConst);

        layoutConst.gridy++;
        labelPanel.add(new JLabel("Name"), layoutConst);

        layoutConst.gridy++;
        labelPanel.add(new JLabel("City"), layoutConst);

        layoutConst.gridy++;
        labelPanel.add(new JLabel("Latitude"), layoutConst);

        layoutConst.gridy++;
        labelPanel.add(new JLabel("Longitude"), layoutConst);

        //////////////////////////////////////
        // One column per station

        // Two different colors for the columns: makes each column 
        // visually distinct.  We will alternate colors
        Color[] colors = 
        {new Color(200, 220, 200), new Color(200, 240, 200)}; 

        JLabel field;

        // Loop over all stations in the list
        int i = 0;
        for (Integer key: countyDefinition)
        {
            // Extract the station for this key
            StationDefinition station = countyDefinition.getItem(key);

            // Create a new panel for the column
            JPanel sPanel = new JPanel();
            sPanel.setLayout(new GridBagLayout());
            // Select a color
            sPanel.setBackground(colors[i % 2]);
            // Add the panel to the stationPanel
            stationPanel.add(sPanel);

            // Now add the data
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;

            // Station ID
            field = new JLabel(station.getStationId());
            sPanel.add(field, layoutConst);

            // Station Name
            layoutConst.gridy++;
            field = new JLabel(station.getName());
            sPanel.add(field, layoutConst);

            // Station city
            layoutConst.gridy++;
            field = new JLabel(station.getCity());
            field.setBackground(colors[layoutConst.gridx % 2]);
            sPanel.add(field, layoutConst);

            // Latitude
            layoutConst.gridy++;
            field = new JLabel(Double.toString(station.getNlat()));
            field.setBackground(colors[layoutConst.gridx % 2]);
            sPanel.add(field, layoutConst);

            // Longitude
            layoutConst.gridy++;
            field = new JLabel(Double.toString(station.getElon()));
            field.setBackground(colors[layoutConst.gridx % 2]);
            sPanel.add(field, layoutConst);

            ++i;
        }

        // Add the station panel to the frame
        this.add(stationPanel, BorderLayout.CENTER);

        // Close button: forces the window to properly close
        JButton button = new JButton("Close");
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                CountyFrame.this.dispatchEvent(
                        new WindowEvent(CountyFrame.this, 
                                WindowEvent.WINDOW_CLOSING));

            }

        });
        
        // Add the button to the frame
        this.add(button, BorderLayout.SOUTH);

        // Complete the frame and display it
        this.pack();
        // Open in the middle of the screen
        this.setLocationRelativeTo(null);
        setVisible(true);
    }
}
