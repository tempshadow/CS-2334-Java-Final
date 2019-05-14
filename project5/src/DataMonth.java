import java.util.Iterator;
import java.util.TreeMap;

/**
 * 
 * @author CS2334.  Modified by: Kyle Roller, Nigel Mansell
 * <P>
 * Date: 2015-09-10 <BR>
 * Project 1
 * <P>
 * This class represents the data for all of the days within a single month
 * @version 9/20/2016
 */
public class DataMonth extends MultiStatisticsWithDaysAbstract
{
    /** treemap containing DataDay */
    TreeMap<Integer, DataDay> days;
    /** month in which the data was sampled */
    private int month;
    /** year in which the data sampled */
    private int year;
    /** The name of the station data was sampled */
    private String stationID;

    /**
     * The DataMonth Constructor, creates an arraylist consisting
     * of DataDay
     */
    public DataMonth()
    {
        days = new TreeMap<Integer, DataDay>();
    }

    /**
     * A protected add method that adds a day object to an array of days
     * @param day day object
     */
    protected void addDay(DataDay day)
    {
        //gets the month, year, and station id then adds the day to an array
        this.month = day.getMonth();
        this.year = day.getYear();
        this.stationID = day.getStationID();
        if (!days.containsKey(day.getDay()))
        {
            days.put(day.getDay(), day);
        }
        days.get(day.getMonth());

    }

    /**
     * Gets the day Integer
     * @return the the integer of the object
     * @param day that is the int for what day to get
     */
    protected DataDay getItem(Integer day) 
    {

        return days.get(day);
    }

    /**
     * Describe the month
     * 
     * @return A string describing all of the days 
     * and the statistics for the month
     */
    public String toString()
    {   
        return String.format("%04d-%02d, %s", year, month, stationID);

    }


    /**
     * An iterator that has an integer
     * @return iterates through all navigable keys in a keySet
     */
    public Iterator<Integer> iterator()
    {

        return days.navigableKeySet().iterator();

    }
    /**
     * Gets the Structure
     * @return anything
     */
    public String getStructure()
    {
        return null;

    }

}