import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Information about the entire set of counties.
 * <P>
 * Project 5 <BR>
 * 
 * @version 2016-11-10
 * @author CS 2334. Modified by Nigel Mansell, Kyle Roller
 * 
 */

public class CountyDefinitionList
{
    /** List of all counties. */
    private ArrayList<CountyDefinition> countyList;

    /** Color used when we do not have data for a county. */
    public static final Color BAD_DATA_COLOR = new Color(255, 255, 109);

    /** Reference to the colorBar object that is being rendered */
    private ColorBar colorBar;

    /**
     * Constructor
     * 
     * @param fileName
     *            File containing the county information
     * @param stations
     *            The set of stations that has already been loaded
     * @throws IOException
     *             If there is an error loading the data
     */
    public CountyDefinitionList(String fileName, StationDefinitionList stations)
        throws IOException {

        // Create space for the county list
        countyList = new ArrayList<CountyDefinition>();

        // Open the file
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        // Read the first line
        String strg = br.readLine();

        // Loop over lines and create the individual counties
        while (strg != null)
        {
            String[] county = strg.split(",");
            CountyDefinition counties = new CountyDefinition(county);
            countyList.add(counties);
            strg = br.readLine();
        }

        br.close();

        // Correct bounding so top left is 0,0
        Rectangle rec = this.getBounds();

        // Update the transformation offset
        StateFrame.setOffsetX(-rec.x + StateFrame.getOffsetX());
        StateFrame.setOffsetY(-rec.y + StateFrame.getOffsetY());

        // Move all component polygons

        translate(StateFrame.getOffsetX(), StateFrame.getOffsetY());

        // Connect the stations to the counties
        this.addStations(stations);
    }

    /**
     * Compute the bounding box that contains all of the polygons for the
     * component counties
     * 
     * @return The Rectangle that describes the bounding box for all counties
     */
    public Rectangle getBounds()
    {
        // Initial bounding box: first county
        Rectangle rec = countyList.get(0).getBounds();

        // loops over all the counties, gets the bounds, and adds it to rec
        for (int i = 1; i < countyList.size(); ++i)
        {
            rec.add(countyList.get(i).getBounds());
        }

        return rec;
    }

    /**
     * Translate all of the component counties by some delta position
     * 
     * @param deltaX
     *            Change along X dimension
     * @param deltaY
     *            Change along Y dimension
     */
    private void translate(int deltaX, int deltaY)
    {
        // Loop through all counties and add the new delta

        for (CountyDefinition county : countyList)
        {
            county.translate(deltaX, deltaY);
        }

    }

    /**
     * Draw all of the counties given the graphics context
     * 
     * @param g2
     *            Graphics context in which to draw
     */
    protected void draw(Graphics2D g2)
    {
        // draw each county
        for (CountyDefinition blarg : countyList)
        {
            blarg.draw(g2);
        }

    }

    /**
     * Given a coordinate on the screen, return the corresponding county
     * 
     * @param x
     *            Query coordinate: X dimension
     * @param y
     *            Query coordinate: Y dimension
     * @return CountyDefinition object that contains the query point. null if
     *         there is no county that contains the query
     */
    public CountyDefinition findCounty(int x, int y)
    {

        // Search through all of the counties for one that contains x/y

        for (CountyDefinition blarg : countyList)
        {
            if (blarg.contains(x, y))
            {
                return blarg;
            }
        }
        // None found
        return null;
    }

    /**
     * Given a long/lat, return the corresponding county
     * 
     * @param lon
     *            Query longitude
     * @param lat
     *            Query latitude
     * @return CountyDefinition object that contains the query point. null if
     *         there is no county that contains the query
     */
    public CountyDefinition findCounty(double lon, double lat)
    {
        // Find the screen coordinates of the query
        int x = (int) (lon * StateFrame.LONGITUDE_SCALE
                + StateFrame.getOffsetX());
        int y = (int) (lat * StateFrame.LATITUDE_SCALE
                + StateFrame.getOffsetY());

        // Now check using screen coordinates
        return findCounty(x, y);
    }

    /**
     * Set the color of all the counties
     * 
     * @param c
     *            Color to which to set all the counties
     */
    public void setColor(Color c)
    {
        // Loop over all counties.
        for (int i = 0; i < countyList.size(); ++i)
        {
            countyList.get(i).setColor(c);
        }
    }

    /**
     * Add each station to the county to which it belongs
     * 
     * @param stations
     *            The set of stations
     */
    private void addStations(StationDefinitionList stations)
    {

        // Loop over each station
        for (String stationId : stations.getStationIds())
        {
            // x and y are equal to elon and nlat
            double x = stations.getStationInfo(stationId).getElon();
            double y = stations.getStationInfo(stationId).getNlat();
            // creates a temp county and sets it to the correct county
            CountyDefinition tempCounty = this.findCounty(x, y);
            // if the county is null, add the station to the county
            if (tempCounty != null)
            {
                tempCounty.addStation(stations.getStationInfo(stationId));
            }
            else
            {
                break;
            }

        }
    }

    /**
     * Provide a reference to the displayed colorBar. This will allow the
     * CountyConnector to look up colors given values.
     * 
     * @param colorBar
     *            A reference to an existing ColorBar
     */
    public void setColorBar(ColorBar colorBar)
    {
        this.colorBar = colorBar;
    }

    /**
     * Paint the counties given a variableId, the time constraints and the
     * statistic to examine.
     * 
     * @param variableId
     *            Variable used to pain the counties
     * @param constraints
     *            : the year, months and days over which the statistic is
     *            computed
     * @param stat
     *            The type of statistic to compute: min, max or average
     */
    public void colorize(String variableId, KeyConstraints constraints,
            StatType stat)
    {
        Sample s = null;
        //DataDay d;
        Sample sampleMin = new Sample();
        Sample sampleMax = new Sample();

        // Loop over every county and compute the appropriate statistic
        for (CountyDefinition county : countyList)
        {
            // Compute the statistic for this county

            // set s to be a Sample that corresponds to the statistic value
            // average, maximum, minimum.

            if (stat == StatType.AVERAGE)
            {
                s = county.getStatisticAverage(variableId, constraints);
            }
            if (stat == StatType.MAXIMUM)
            {
                s = county.getStatisticMaxDay(variableId, constraints)
                        .getStatisticAverage(variableId, constraints);
            }
            if (stat == StatType.MINIMUM)
            {
                s = county.getStatisticMinDay(variableId, constraints)
                        .getStatisticAverage(variableId, constraints);
            }

            // Store the sample value with the county
            county.setSample(s);

            // Check to see whether s is a new max or min
            if (s.isGreaterThan(sampleMax))
            {
                sampleMax = s;
            }
            else
                if (s.isLessThan(sampleMin))
                {
                    sampleMin = s;
                }

        }

        // Any samples valid?
        if (sampleMax.isValid())
        {
            // Yes

            // Compute the range
            double max = sampleMax.getValue();
            double min = sampleMin.getValue();

            // Catch case where there is no variation.
            if (min == max)
            {
                max = min + 0.1;
            }
            // Configure the color bar
            colorBar.setRange(min, max);

            // Loop over the counties and set each to the appropriate color

            for (CountyDefinition county : countyList)
            {
                if (county.getSample().isValid())
                {
                    county.setColor(colorBar
                            .computeColor(county.getSample().getValue()));
                }
                // invalid samples are painted with BAD_DATA_COLOR
                else
                {
                    county.setColor(BAD_DATA_COLOR);
                }
            }
        }
        else
        {

            
            // paints all counties using the BAD_DATA_COLOR
            this.setColor(BAD_DATA_COLOR);
        }

    }

}
