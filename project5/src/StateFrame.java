import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Define the main interaction window for the state-based information
 * <P>
 * 
 * @author CS 2334. Modified by Kyle Roller, Nigell Mansell
 * @version 2016-11-04
 *          <P>
 *          Project 5<BR>
 * 
 */

public class StateFrame extends JFrame
{
    /** Necessary for serializable objects. */
    private static final long serialVersionUID = 1L;
    /** County info list. */
    private CountyDefinitionList countyDefinitionList;
    /** Panel for displaying the set of counties. */
    protected CountyPanel countyPanel;
    /** Connection between stations and counties. */
    // private CountyConnector countyConnector;
    /** Station info list: all stations */
    private StationDefinitionList stationDefinitionList;
    /** Data info list: all measurements made at a station. */
    private DataDefinitionList dataDefinitionList;
    /** Menu bar for files. */
    protected FileMenuBar fileMenuBar;
    /** Panel for selecting what to display. */
    protected SelectionPanel selectionPanel;

    // ///////////////////
    // Transformation from long/lat to pixels
    /** Horizontal scale: longitude to pixels */
    public static final double LONGITUDE_SCALE = 80.0;
    /** Vertical scale: latitude to pixels */
    public static final double LATITUDE_SCALE = -80.0;
    /** Horizontal offset in pixels. */
    private static int offsetX = 10;
    /** Vertical offset in pixels. */
    private static int offsetY = 50;

    // ///////////////////
    /** Radius of the station circles */
    public static final int STATION_RADIUS = 6;

    // //////////////////////////////////////////
    /**
     * Menu bar
     * <P>
     * 
     * @author CS 2334. Modified by Nigel Mansell, Kyle Roller
     * 
     * @version 2016-11-04
     * 
     */
    public class FileMenuBar extends JMenuBar
    {
        /** Makes Eclipse happy */
        private static final long serialVersionUID = 1L;
        /** Menu object. */
        protected JMenu menu;
        /** Exit menu item. */
        protected JMenuItem menuExit;
        /** File open menu item. */
        protected JMenuItem menuOpen;
        /** Pop-up file chooser. */
        protected JFileChooser fileChooser;

        /**
         * Constructor
         */
        public FileMenuBar() {
            // Add the menu to the menu bar
            menu = new JMenu("File");
            add(menu);

            // Create and add menu items
            menuOpen = new JMenuItem("Open Text File");
            // menuOpenBinary = new JMenuItem("Open Binary File");
            // menuSaveBinary = new JMenuItem("Save Binary File");
            menuExit = new JMenuItem("Exit");
            menu.add(menuOpen);
            // menu.add(menuOpenBinary);
            // menu.add(menuSaveBinary);
            menu.add(menuExit);

            // Exit menu item behavior
            menuExit.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    System.exit(0);
                }
            });

            // Create the file chooser
            fileChooser = new JFileChooser(new File("./data"));

            // Text file open behavior
            menuOpen.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    // Choose the file
                    int returnVal = fileChooser.showOpenDialog(menuOpen);
                    if (returnVal == JFileChooser.APPROVE_OPTION)
                    {
                        // User gave a file
                        File file = fileChooser.getSelectedFile();
                        try
                        {

                            // Set to a "busy" cursor
                            StateFrame.this.setCursor(Cursor
                                    .getPredefinedCursor(Cursor.WAIT_CURSOR));

                            StateFrame.this.loadData(file.getPath());

                        }
                        catch (FileNotFoundException e2)
                        {
                            JOptionPane.showMessageDialog(fileChooser,
                                    "File not found");
                        }
                        catch (IOException e2)
                        {
                            JOptionPane.showMessageDialog(fileChooser,
                                    "File load error");
                        }
                        catch (Exception e2)
                        {
                            JOptionPane.showMessageDialog(fileChooser,
                                    "File load error");
                        }
                        finally
                        {
                            // Set cursor back to pointer
                            StateFrame.this.setCursor(null);
                        }
                    }
                }

            });

        }
    }

    // //////////////////////////////////////////////
    /**
     * Panel for presenting a set of options to the user. Specifically:
     * variableIDs, statistic type (min, max or average), years, months, days.
     * 
     * @author CS 2334. Modified by ???
     * @version 2016-11-04
     * 
     */
    public class SelectionPanel extends JPanel
    {

        /** List display of variables. */
        protected JList<String> variableList;
        /** List display of years */
        protected JList<String> yearList;
        /** List display of months. */
        protected JList<String> monthList;
        /** List display of days. */
        protected JList<String> dayList;
        /** Model for storing the displayed years. */
        protected DefaultListModel<String> yearListModel;

        /** Makes Eclipse happy. */
        private static final long serialVersionUID = 1L;

        // Scrollers for the JLists
        private JScrollPane variableListScroller;
        private JScrollPane yearListScroller;
        private JScrollPane monthListScroller;
        private JScrollPane dayListScroller;

        // Labels
        private JLabel variableLabel;
        private JLabel yearListLabel;
        private JLabel monthListLabel;
        private JLabel dayListLabel;
        private JLabel statisticLabel;

        // Buttons
        /** minimum statistic radio button */
        protected JRadioButton minButton;
        /** maximum statistic radio button. */
        protected JRadioButton maxButton;
        /** average statistic radio button. */
        protected JRadioButton averageButton;
        /** Panel to place all of the buttons in. */
        protected JPanel buttonPanel;

        // Update flag
        // boolean updateFlag;

        /**
         * Constructor
         */
        public SelectionPanel() {
            // Don't call the data update yet.
            // updateFlag = false;

            // Configure the panel
            this.setLayout(new GridBagLayout());

            // Statistics selection using JRadioButtons
            minButton = new JRadioButton("Minimum");
            maxButton = new JRadioButton("Maximum");
            averageButton = new JRadioButton("Average");
            ButtonGroup group = new ButtonGroup();
            group.add(minButton);
            group.add(averageButton);
            group.add(maxButton);
            // Create the action listener for all of these buttons
            ActionListener al = new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    // if (updateFlag)
                    // {
                    StateFrame.this.countyPanel.updateData();
                    // }
                }

            };
            // Default button mode
            averageButton.setSelected(true);

            // Connect the action listener to all of these buttons
            minButton.addActionListener(al);
            maxButton.addActionListener(al);
            averageButton.addActionListener(al);

            // Put the panel together
            buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout());
            buttonPanel.add(minButton);
            buttonPanel.add(averageButton);
            buttonPanel.add(maxButton);

            // Variable selection

            variableList = new JList<String>(
                    dataDefinitionList.getVariableIds().toArray(new String[0]));
            variableListScroller = new JScrollPane(variableList);
            variableListScroller.setPreferredSize(new Dimension(300, 150));
            variableList.setVisibleRowCount(-1);
            variableList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
            variableList.setSelectedIndex(0);
            variableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            variableList.addListSelectionListener(new ListSelectionListener()
            {
                public void valueChanged(ListSelectionEvent e)
                {
                    if (!e.getValueIsAdjusting()) // && updateFlag)
                    {
                        StateFrame.this.countyPanel.updateData();
                    }
                }

            });
            variableList.setPrototypeCellValue("ACME--");

            // Year selection
            yearListModel = new DefaultListModel<String>();
            yearListModel.add(0, "All");
            yearList = new JList<String>(yearListModel);
            yearListScroller = new JScrollPane(yearList);
            yearListScroller.setPreferredSize(new Dimension(300, 80));
            yearList.setPrototypeCellValue("2001--");
            yearList.setVisibleRowCount(-1);
            yearList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
            yearList.setSelectedIndex(0);
            yearList.setSelectionMode(
                    ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            // yearListValues = new ArrayList<Integer>();

            yearList.addListSelectionListener(new ListSelectionListener()
            {
                public void valueChanged(ListSelectionEvent e)
                {
                    if (!e.getValueIsAdjusting()) // && updateFlag)
                    {
                        StateFrame.this.countyPanel.updateData();
                    }
                }

            });

            // Month list
            String[] mList =
            { "All", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
                "Sept", "Oct", "Nov", "Dec" };
            monthList = new JList<String>(mList);
            monthListScroller = new JScrollPane(monthList);
            monthListScroller.setPreferredSize(new Dimension(300, 80));
            monthList.setVisibleRowCount(-1);
            monthList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
            monthList.setSelectedIndex(0);
            monthList.setSelectionMode(
                    ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            monthList.addListSelectionListener(new ListSelectionListener()
            {
                public void valueChanged(ListSelectionEvent e)
                {
                    if (!e.getValueIsAdjusting()) // && updateFlag)
                    {
                        StateFrame.this.countyPanel.updateData();
                    }
                }

            });
            monthList.setPrototypeCellValue("12345");

            // Day list

            // Create the set of entries
            String[] dList = new String[32];
            dList[0] = "All";
            for (Integer i = 1; i <= 31; ++i)
            {
                dList[i] = i.toString();
            }
            // Create the list within a scroller
            dayList = new JList<String>(dList);
            dayListScroller = new JScrollPane(dayList);
            dayListScroller.setPreferredSize(new Dimension(300, 80));
            dayList.setVisibleRowCount(-1);
            dayList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
            dayList.setSelectedIndex(0);
            dayList.setSelectionMode(
                    ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            dayList.addListSelectionListener(new ListSelectionListener()
            {
                public void valueChanged(ListSelectionEvent e)
                {
                    if (!e.getValueIsAdjusting()) // && updateFlag)
                    {
                        StateFrame.this.countyPanel.updateData();
                    }
                }

            });
            dayList.setPrototypeCellValue("All ");

            // Labels
            variableLabel = new JLabel("Select Variable: ");
            yearListLabel = new JLabel("Select Year(s): ");
            monthListLabel = new JLabel("Select Month(s): ");
            dayListLabel = new JLabel("Select Day(s): ");
            statisticLabel = new JLabel("Select statistic: ");

            // Layout of panel
            GridBagConstraints layoutConst;

            layoutConst = new GridBagConstraints();
            layoutConst.gridx = 0;
            layoutConst.gridy = 0;
            layoutConst.insets = new Insets(10, 10, 10, 10);
            add(variableLabel, layoutConst);

            layoutConst = new GridBagConstraints();
            layoutConst.gridx = 1;
            layoutConst.gridy = 0;
            layoutConst.insets = new Insets(10, 10, 10, 10);
            add(variableListScroller, layoutConst);

            layoutConst = new GridBagConstraints();
            layoutConst.gridx = 0;
            layoutConst.gridy = 1;
            layoutConst.insets = new Insets(10, 10, 10, 10);
            add(statisticLabel, layoutConst);

            layoutConst = new GridBagConstraints();
            layoutConst.gridx = 1;
            layoutConst.gridy = 1;
            layoutConst.insets = new Insets(10, 10, 10, 10);
            add(buttonPanel, layoutConst);

            layoutConst = new GridBagConstraints();
            layoutConst.gridx = 0;
            layoutConst.gridy = 2;
            layoutConst.insets = new Insets(10, 10, 10, 10);
            add(yearListLabel, layoutConst);

            layoutConst = new GridBagConstraints();
            layoutConst.gridx = 1;
            layoutConst.gridy = 2;
            layoutConst.insets = new Insets(10, 10, 10, 10);
            add(yearListScroller, layoutConst);

            layoutConst = new GridBagConstraints();
            layoutConst.gridx = 0;
            layoutConst.gridy = 3;
            layoutConst.insets = new Insets(10, 10, 10, 10);
            add(monthListLabel, layoutConst);

            layoutConst = new GridBagConstraints();
            layoutConst.gridx = 1;
            layoutConst.gridy = 3;
            layoutConst.insets = new Insets(10, 10, 10, 10);
            add(monthListScroller, layoutConst);

            layoutConst = new GridBagConstraints();
            layoutConst.gridx = 0;
            layoutConst.gridy = 4;
            layoutConst.insets = new Insets(10, 10, 10, 10);
            add(dayListLabel, layoutConst);

            layoutConst = new GridBagConstraints();
            layoutConst.gridx = 1;
            layoutConst.gridy = 4;
            layoutConst.insets = new Insets(10, 10, 10, 10);
            add(dayListScroller, layoutConst);

            this.setBackground(new Color(200, 220, 200));

            /////////////////////
            // DO NOT CHANGE
            variableList.setName("Variable List");
            yearList.setName("Year List");
            monthList.setName("Month List");
            dayList.setName("Day List");
            minButton.setName("Min Button");
            maxButton.setName("Max Button");
            averageButton.setName("Average Button");

        }

    }

    // /////////////////////////////////////////////////////////////
    /**
     * Panel for displaying the state (all of the counties)
     * 
     * @author CS 2334. Modified by ???
     * @version 2016-11-04
     * 
     */
    public class CountyPanel extends JPanel
    {
        /** Listener for mouse events. */
        // private CountyListener listener;
        /** Color bar to indicate the relationship between colors and values. */
        protected ColorBar colorBar;
        /** Font to use to display variable information */
        private Font font = new Font(Font.SANS_SERIF, Font.BOLD, 18);
        /** Currently-selected variable */
        protected String variableId;
        /** Location of the Variable name in panel. */
        public static final int XVARIABLE = 100;
        /** Location of the Variable name in panel. */
        public static final int YVARIABLE = 370;
        /** Location of the Variable units in panel. */
        public static final int XUNITS = 100;
        /** Location of the Variable units in panel. */
        public static final int YUNITS = 510;

        /** Makes eclipse happy */
        private static final long serialVersionUID = 1L;

        /** Constraint set to use for queries. */
        private KeyConstraints constraintsYear;
        private KeyConstraints constraintsMonth;
        private KeyConstraints constraintsDay;
        private KeyConstraints constraintsStations;

        /**
         * Constructor
         * 
         */
        public CountyPanel() {
            // enable mouse events
            this.setFocusable(true);
            // Listener for events
            // listener = new CountyListener();
            // Connect the listener to the panel
            this.addMouseListener(new MouseAdapter()
            {
                /**
                 * Deal with a click event by opening a window describing the
                 * selected county.
                 * 
                 * @param e
                 *            The mouse event
                 */
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    // Get the location
                    int x = e.getX();
                    int y = e.getY();

                    // check and retrieve what county has been clicked
                    CountyDefinition tempCounty =
                            StateFrame.this.countyDefinitionList.findCounty(x,
                                    y);

                    // open the county if one was selected
                    if (tempCounty != null)
                    {
                        CountyFrame cF = new CountyFrame(tempCounty);

                        // make webcat happy
                        cF.toString();
                    }
                }

            });
            // Create color bar
            colorBar = new ColorBar(100, 375, 500, 50, 250, 0, 0.3, 0, 0, 1, 0);

            // Create the constraints list:
            // NOTE that we now have 4 constraints that are to be in the
            // following order:
            // Stations -> Years -> Months -> Days
            //
            // The stations constraints is necessary because we are going to
            // be using the constraints to query a county (which is composed of
            // multiple stations). The station keys are indexed: 0, 1, ...
            // You may safely assume that there are no more than 10 stations
            // in a county
            constraintsStations = new KeyConstraints();
            constraintsYear = new KeyConstraints();
            constraintsMonth = new KeyConstraints();
            constraintsDay = new KeyConstraints();

            // add the constraints
            constraintsYear.addEnd(constraintsMonth);
            constraintsYear.addEnd(constraintsDay);
            constraintsStations.addEnd(constraintsYear);

            // Initialize the constraints
            for (int i = 0; i < 10; ++i)
            {
                constraintsStations.add(i);
            }

        }

        /**
         * Paint the full panel.
         * 
         * @param g
         *            Graphics context
         */
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            // draw the counties
            StateFrame.this.countyDefinitionList.draw(g2);

            // draw the color bar
            colorBar.draw(g2);

            // check to see if a variableId is valid
            if (variableId == null)
            {
                return;
            }

            // Draw name of statistic
            DataDefinition dataDefinition =
                    StateFrame.this.dataDefinitionList.getDataInfo(variableId);

            // check to see if the data is valid
            if (dataDefinition != null)
            {
                g2.setFont(font);

                // Draw the name of the variable
                String str = dataDefinition.getVariableName();
                g2.drawString(str, XVARIABLE, YVARIABLE);

                // draw the units string
                g2.drawString(dataDefinition.getUnit(), XVARIABLE,
                        YVARIABLE + 125);
            }

        }

        /**
         * Return the preferred size of the panel. Determined by the county
         * display bounding box.
         * 
         * @return Dimension containing the panel's preferred size.
         */
        public Dimension getPreferredSize()
        {
            Rectangle rec = countyDefinitionList.getBounds();
            return new Dimension(rec.width + 50, rec.height + 50);
        }

        /**
         * Update what is displayed in the panel. This method is called any time
         * something new has been selected
         */
        public synchronized void updateData()
        {
            // Clear the old constraints
            constraintsYear.clear();
            constraintsMonth.clear();
            constraintsDay.clear();

            // Get selected variable
            variableId = StateFrame.this.selectionPanel.variableList
                    .getSelectedValue();

            // Get the years
            List<String> selectedYears = StateFrame.this.selectionPanel.yearList
                    .getSelectedValuesList();

            // Give up if there is nothing (must be in the middle of update)
            if (selectedYears.isEmpty())
            {
                return;
            }

            // check to see if all is selected
            if (selectedYears.get(0).equals("All"))
            {
                // if it is add all the constraints
                constraintsYear.addAll(DataYear.getYearList());
            }

            // if anything is selected besides all
            else
            {
                // add only the selected to the constraints
                for (int i = 0; i < selectedYears.size(); ++i)
                {
                    constraintsYear.add(Integer.parseInt(selectedYears.get(i)));
                }
            }

            // /////////////////////////////
            // Get the months
            int[] selectedMonths = StateFrame.this.selectionPanel.monthList
                    .getSelectedIndices();
            // Give up if there is nothing (must be in the middle of update)
            if (selectedMonths.length == 0)
            {
                return;
            }

            // check if all is selected
            if (selectedMonths[0] == 0)
            {
                // add all the months to the constraints
                for (int i = 1; i < 13; ++i)
                {
                    constraintsMonth.add(i);
                }
            }

            // add all the selected to the constraints
            else
            {

                for (int selectedMonth : selectedMonths)
                {
                    constraintsMonth.add(selectedMonth);
                }
            }

            // get the Days
            int[] selectedDays =
                    StateFrame.this.selectionPanel.dayList.getSelectedIndices();

            // Give up if there is nothing (must be in the middle of update)
            if (selectedDays.length == 0)
            {
                return;
            }

            // check if all is selected
            if (selectedDays[0] == 0)
            {
                // add all the days to constraints
                for (int i = 1; i < 32; ++i)
                {
                    constraintsDay.add(i);

                }
            }

            // if all is not selected execute
            else
            {
                // add all the selectged days
                for (int selectedDay : selectedDays)
                {
                    constraintsDay.add(selectedDay);
                }
            }

            // Check which statistic has been selected
            // set statType to one of MAXIMUM, AVERAGE, MINIMUM, depending
            // on what was selected by the user
            StatType statType = StatType.AVERAGE;
            if (selectionPanel.maxButton.isSelected())
            {
                statType = StatType.MAXIMUM;
            }
            else
                if (selectionPanel.minButton.isSelected())
                {
                    statType = StatType.MINIMUM;
                }
                else
                    if (selectionPanel.averageButton.isSelected())
                    {
                        statType = StatType.AVERAGE;
                    }

            // Tell the counties to update their colors
            StateFrame.this.countyDefinitionList.colorize(variableId,
                    constraintsStations, statType);

            // Force a repaint
            repaint();
        }

    }

    // /////////////////////////////////////////////////////////////
    /**
     * Constructor for the window
     * 
     * @param name
     *            Name of the window
     * @param countyDefinitionList
     *            County info list: all county information
     * @param stationDefinitionList
     *            List of all stations
     * @param dataDefinitionList
     *            List of all measures (variables)
     */
    public StateFrame(String name, CountyDefinitionList countyDefinitionList,
            StationDefinitionList stationDefinitionList,
            DataDefinitionList dataDefinitionList) {
        super(name);
        // Remember the key structures
        this.countyDefinitionList = countyDefinitionList;
        // this.countyConnector = countyConnector;
        this.stationDefinitionList = stationDefinitionList;
        this.dataDefinitionList = dataDefinitionList;

        // Menu bar
        fileMenuBar = new FileMenuBar();
        this.setJMenuBar(fileMenuBar);

        // Create the panels
        this.setLayout(new BorderLayout());

        // Selection panel
        selectionPanel = new SelectionPanel();
        this.add(selectionPanel, BorderLayout.WEST);

        // County panel
        countyPanel = new CountyPanel();
        this.add(countyPanel, BorderLayout.CENTER);

        // Configure window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 600);
        setVisible(true);

        // Tell the countyConnector about the colorBar
        countyDefinitionList.setColorBar(countyPanel.colorBar);

        ////////////////////
        // Do not Edit
        countyPanel.setName("County Panel");
        selectionPanel.setName("Selection Panel");
        fileMenuBar.setName("File Menu Bar");
        ////////////////////
    }

    /**
     * Linear offset from long/lat to screen coordinates
     * 
     * @return X dimension of linear offset
     */
    public static int getOffsetX()
    {
        return offsetX;
    }

    /**
     * Linear offset from long/lat to screen coordinates
     * 
     * @return Y dimension of linear offset
     */
    public static int getOffsetY()
    {
        return offsetY;
    }

    /**
     * Set the linear offset from long/lat to screen coordinates
     * 
     * @param offsetX
     *            Offset along the X dimension
     */
    public static void setOffsetX(int offsetX)
    {
        StateFrame.offsetX = offsetX;
    }

    /**
     * Set the linear offset from long/lat to screen coordinates
     * 
     * @param offsetY
     *            Offset along the Y dimension
     */
    public static void setOffsetY(int offsetY)
    {
        StateFrame.offsetY = offsetY;
    }

    /**
     * Load a data file into the main data structure. After the data are loaded,
     * the yearListModel is updated to reflect the current set of years.
     * 
     * @param fileName
     *            File from which the data will be loaded
     * @throws IOException
     *             If some for of error occurs during the opening or reading
     *             from the file.
     */
    public void loadData(String fileName) throws IOException
    {
        // Do the loading work
        stationDefinitionList.loadData(fileName);

        // Update the displayed set of available years
        StateFrame.this.selectionPanel.yearListModel.removeAllElements();

        StateFrame.this.selectionPanel.yearListModel.addElement("All");
        for (Integer i : DataYear.getYearList())
        {
            StateFrame.this.selectionPanel.yearListModel
                    .addElement(i.toString());
        }

        // Select ALL by default
        StateFrame.this.selectionPanel.yearList.setSelectedIndex(0);

        // Update the display
        StateFrame.this.countyPanel.updateData();
    }

}
