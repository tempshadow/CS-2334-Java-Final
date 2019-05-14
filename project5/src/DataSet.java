import java.util.Iterator;
import java.util.TreeMap;

/**
 * DataSet class represents all data and averages
 * @author Nigel Mansell, Kyle Roller
 * @version 10/11/2016
 */
public class DataSet extends MultiStatisticsWithDaysAbstract 
{
    /**
     * TreeMap of DataYear
     */
    private TreeMap<Integer, DataYear> years;

    /** String field stationid */
    private String stationId;

    /**
     * DataSet constructor reads every file for each year
     */
    public DataSet() 
    {
        years = new TreeMap<Integer, DataYear>();
    }

    /**
     * Gets the year Integer
     * @param year containing the year to get
     * @return the the integer of the object
     */
    @Override
    protected DataYear getItem(Integer year)
    {
        return  years.get(year);
    }

    /**
     * Checks if there is a key. If no key, populates hashMap, the gets it.
     * @param d DataDay to be added
     */
    protected void addDay(DataDay d)
    {
        if (!years.containsKey(d.getYear()))
        {
            years.put(d.getYear(), new DataYear());
        }
        years.get(d.getYear()).addDay(d);

        this.stationId = d.getStationID();
    }


    /**
     * A String describing the DataSet
     * @return the statistics for data set
     */
    public String toString()
    {
        return String.format("DataSet: %s", stationId);
    }

    /**
     * An iterator that has an integer
     * @return iterates through all navigable keys in a keySet
     */
    public Iterator<Integer> iterator()
    {
        return years.navigableKeySet().iterator();
    }

    /**
     * Gets the structure
     * @return anything
     */
    public String getStructure()
    {
        return "";
    }
}
