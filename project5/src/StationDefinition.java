import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Class that contains information for station and all of its information,
 * allows you to add a day to the dataset. Retrieve the stationID, name, city,
 * latitude, and longitude.
 * 
 * @author Kyle Roller
 * @version 10/19/2016
 */
public class StationDefinition extends StatisticsAbstract
{
    /**
     * String representing the station ID
     */
    private String stationId;

    /**
     * String representing the station name
     */
    private String name;

    /**
     * String representing the city the station is in
     */
    private String city;

    /**
     * double that is the northern lattitude for the station
     */
    private double nlat;

    /**
     * double that is the eastern longitude for the station
     */
    private double elon;

    /**
     * DataSet for the station
     */
    private DataSet dataSet;

    /**
     * Constructs a StationDefinition object
     * 
     * @param stationId
     *            String containing the stationID
     * @param name
     *            String containing the name of the station
     * @param city
     *            String containing the city the station is in
     * @param nlat
     *            double that is the latitude for the station
     * @param elon
     *            double that is the longitude for the station
     */
    public StationDefinition(String stationId, String name, String city,
            double nlat, double elon) {
        // sets all the variables to the correct inputs
        this.stationId = stationId;
        this.name = name;
        this.city = city;
        this.nlat = nlat;
        this.elon = elon;
        // creates a new DataSet for every StationDefinition
        this.dataSet = new DataSet();
    }

    /**
     * adds a DataDay to the DataSet for this StationDefinition
     * 
     * @param day
     *            DataDay to be added
     */
    protected void addDay(DataDay day)
    {
        this.dataSet.addDay(day);
    }

    /**
     * @return the stationId
     */
    public String getStationId()
    {
        return stationId;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return the city
     */
    public String getCity()
    {
        return city;
    }

    /**
     * @return the nlat
     */
    public double getNlat()
    {
        return nlat;
    }

    /**
     * @return the elon
     */
    public double getElon()
    {
        return elon;
    }

    /**
     * Gets the statistic Average
     * 
     * @param statisticId
     *            the statistic
     * @param key
     *            that limits the data input
     * @return the dataSets average
     */
    public Sample getStatisticAverage(String statisticId, KeyConstraints key)
    {
        return dataSet.getStatisticAverage(statisticId, key);

    }

    /**
     * Gets the statistic max
     * 
     * @param statisticId
     *            the statistic
     * @param key
     *            that limits the data input
     * @return the dataSets max
     */
    public DataDay getStatisticMaxDay(String statisticId, KeyConstraints key)
    {
        return dataSet.getStatisticMaxDay(statisticId, key);
    }

    /**
     * Gets the statistic min
     * 
     * @param statisticId
     *            the statistic
     * @param key
     *            that limtis the data input
     * @return the dataSets min
     */
    public DataDay getStatisticMinDay(String statisticId, KeyConstraints key)
    {
        return dataSet.getStatisticMinDay(statisticId, key);
    }

    /**
     * String representation of a stationDefinition
     * 
     * @return String containing information about the Station
     */
    public String toString()
    {
        return String.format("%s %s: %s", this.name, this.stationId, this.city);
    }

    /**
     * String representation of a Stations information
     * 
     * @return String containing all info on a station
     */
    public String getStructure()
    {
        return String.format("Station: ");
    }

    /**
     * Draws the stations dots
     * 
     * @param g2 Graphics2D for the stations dots
     */
    public void draw(Graphics2D g2)
    {
        //set the color to black
        g2.setColor(Color.BLACK);
        
        //draw the circles for the stations
        g2.fillOval(
                (int) (this.elon * StateFrame.LONGITUDE_SCALE)
                        + StateFrame.getOffsetX(),
                (int) (this.nlat * StateFrame.LATITUDE_SCALE)
                        + StateFrame.getOffsetY(),
                StateFrame.STATION_RADIUS, StateFrame.STATION_RADIUS);

    }
}
