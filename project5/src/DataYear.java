import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

/** 
 * DataYear class represents data for all months in a year.
 * @author Nigel Mansell, Kyle Roller
 * @version 10/12/2016
 */
public class DataYear extends MultiStatisticsWithDaysAbstract
{
    /** Treemap containing DataMonths */
    TreeMap<Integer, DataMonth> months;
    private static TreeSet<Integer> yearList = new TreeSet<Integer>();

    /** field for year */
    private int year;


    /** field for stationID */
    private String stationID;

    /**
     * DataYear constructor reads in the file, adds data to each 
     * month in the array
     */
    public DataYear() 
    {
        months = new TreeMap<Integer, DataMonth>();

    }

    /**
     * Checks if there is a key. If no key, populates hashMap, the gets it.
     * @param day DataDay to be added
     */
    @Override
    protected void addDay(DataDay day) 
    {       
        this.year = day.getYear();
        this.stationID = day.getStationID();
        if (!months.containsKey(day.getMonth()))
        {
            months.put(day.getMonth(), new DataMonth());
        }
        months.get(day.getMonth()).addDay(day);  
        yearList.add(day.getYear());
    }

    /**
     * Gets the month Integer
     * @param month which is the Integer to be got
     * @return the the integer of the object
     */
    @Override
    protected DataMonth getItem(Integer month) 
    {
        return months.get(month);
    }


    /**
     * Output to describe year
     * @return outputs statistics for year
     */
    public String toString()
    {
        return String.format("%04d, %s", year, stationID);
    }

    /**
     * An iterator that has an integer
     * @return iterates through all navigable keys in a keySet
     */
    public Iterator<Integer> iterator()
    {
        return months.navigableKeySet().iterator();
    }

    /**
     * Gets the structure
     * @return anything
     */
    public String getStructure()
    {
        return "";
    }
    
    /**
     * A method that returns an arrayLisr of years
     * @return ArrayList of years listed
     */
    public static ArrayList<Integer> getYearList()
    {
        return new ArrayList<Integer>(yearList) ;
    }
}
