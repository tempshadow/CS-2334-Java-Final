import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * Store information about a single county.
 * <P>
 * As part of this county representation, this class maintains a list of
 * stations associated with the county.
 * <P>
 * We are extending MultiStatisticsAbstract so that we can use the statistics
 * computation functionality. However, this requires us to store the sub-objects
 * (the stations) using a TreeMap and to implement Iterable
 * 
 * <P>
 * Project 5<BR>
 * 
 * @version 2016-11-18
 * 
 * @author CS2334. Modified by Nigel Mansell, Kyle Roller
 *
 */

public class CountyDefinition extends MultiStatisticsAbstract
{
    /** County name */
    private String name;
    /** Polygon that represents the outline of the county. */
    private Polygon polygon;
    /** Color to render the county in. */
    private Color color;
    /** A temporary value that can be stored with the county */
    private Sample tempValue;

    /** List of component stations: will will just use keys 0, 1, ... */
    private TreeMap<Integer, StationDefinition> stations;

    /**
     * Constructor.
     * <P>
     * 
     * Elements array contains:
     * <P>
     * 0. The name of the county  1/2: long/lat of first point in the
     * polygon. <BR>
     * 3/4: long/lat of the 2nd point, <BR>
     * ... as many points as are necessary
     * 
     * The number of long/lat coordinates is not assumed to be known ahead of
     * time.
     * 
     * @param elements
     *            Elements that define the county.
     */
    public CountyDefinition(String[] elements) {

        // Name
        name = elements[0].trim();

        // Create the polygon from the remaining data
        // Note that each coordinate must be multiplied by the log/lat scale
        // to bring the coordinates into the scale of your screen.

        polygon = new Polygon();

        // Loop over the points in the elements list and add to the polygon
        for (int i = 1; i < elements.length; i += 2)
        {
            int x = (int) (Double.parseDouble(elements[i])
                    * StateFrame.LONGITUDE_SCALE);
            int y = (int) (Double.parseDouble(elements[i + 1])
                    * StateFrame.LATITUDE_SCALE);
            polygon.addPoint(x, y);
        }

        // Default color
        color = CountyDefinitionList.BAD_DATA_COLOR;

        // List of stations that belong to the county
        stations = new TreeMap<Integer, StationDefinition>();
    }

    /**
     * Color getter
     * 
     * @return Return the color of the polygon
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * Color setter
     * 
     * @param color
     *            The new color of the polygon
     */
    public void setColor(Color color)
    {
        this.color = color;
    }

    /**
     * Name getter
     * 
     * @return Name of the county
     */
    public String getName()
    {
        return name;
    }

    /**
     * Draw the county in the g2 context.
     * 
     * <P>
     * The following are drawn: a fill of the polygon with the county's color,
     * an outline of the polygon in black, and the included stations
     * 
     * @param g2
     *            Graphics context in which to draw the county
     */
    protected void draw(Graphics2D g2)
    {
        // Fill with painted color
        g2.setColor(color);
        g2.fill(polygon);

        // Outline is black
        g2.setColor(Color.BLACK);
        g2.draw(polygon);

        // draw stations
        for (int station : this)
        {
            stations.get(station).draw(g2);
        }
    }

    /**
     * Move the polygon by some specified delta.
     * 
     * @param deltaX
     *            Screen units to change X position by
     * @param deltaY
     *            Screen units to change Y position by
     */
    protected void translate(int deltaX, int deltaY)
    {
        polygon.translate(deltaX, deltaY);
    }

    /**
     * Return the bounding box of the county in screen coordinates
     * 
     * @return Rectangle describing the bounding box
     */
    public Rectangle getBounds()
    {
        
        return polygon.getBounds();
        
    }

    /**
     * Determine whether a point in screen coordinates is in the the polygon
     * 
     * @param x
     *            X coordinate position of query
     * @param y
     *            Y coordinate position of query
     * @return true if the point x/y is in the polygon
     */
    public boolean contains(int x, int y)
    {
        
        return polygon.contains(x, y);

    }

    /**
     * Return the temporary observation associated with the county
     * 
     * @return Sample stored with the county
     */
    public Sample getSample()
    {
        return tempValue;
    }

    /**
     * Set the observation associated with the county.
     * 
     * @param o
     *            New value for the sample
     */
    public void setSample(Sample o)
    {
        tempValue = o;
    }

    /**
     * Add a station to the list of stations. The keys for the stations are in
     * numeric order: 0, 1, 2, ...
     * 
     * @param station
     *            The station to be added
     */
    public void addStation(StationDefinition station)
    {
        stations.put(stations.size(), station);
        
    }

    /**
     * Provide an iterator over the Integer keys of the stations
     * 
     * @return Iterator over integer keys
     */
    public Iterator<Integer> iterator()
    {
        return stations.navigableKeySet().iterator();
    }

    /**
     * Get a component station.
     * 
     * @param i
     *            Key to find the station
     * @return The station at key i
     */
    public StationDefinition getItem(Integer i)
    {
        return stations.get(i);
    }
}
