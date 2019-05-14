import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author CS2334.  Modified by: Kyle Roller, Nigel Mansell
 * <P>
 * Date: 10/25/2016 <BR>
 * Project 3
 * <P>
 * This class represents a summary of one day's data from a 
 * single Mesonet station.
 * @version 10/05/2016
 */
public class DataDay extends StatisticsAbstract
{
    /**
     *  Year in which the data were sampled 
     */
    private int year;

    /**
     *  Month in which the data were sampled (1=January, 2=February, etc)
     */
    private int month;

    /**
     *  The day on which the data were sampled 
     */
    private int day;

    /**
     *  The ID of the station which the data was gathered 
     */
    private String stationID;

    /**
     * The HashMap containing the samples of data for a DataDay
     */
    private HashMap<String, Sample> samples;

    /**
     *  The DataDefinitionList that contains the dataDefinitions
     */
    private static DataDefinitionList dataDefinitionList;

    /**
     *  dataFirlds array list to used for index 
     */
    private static ArrayList<String> dataFields;

    /**
     *  Index for year 
     */
    private static int yearIndex;

    /**
     *  Index for month 
     */
    private static int monthIndex;

    /**
     *  Index for day 
     */
    private static int dayIndex;

    /** 
     * Index for staitonid 
     */
    private static int stationIdIndex;

    /**
     *  DataDay constructor that sets the Indexes and 
     *  populates the hashmap
     * @param args an array of arguments
     * @throws IOException for the file reader
     */
    public DataDay(String[] args) throws IOException
    {
        this();

        //initilizes variable
        String variable;
        Sample value;

        // sets the year indexes
        year = Integer.valueOf(args[yearIndex]);
        month = Integer.valueOf(args[monthIndex]);
        day = Integer.valueOf(args[dayIndex]);
        stationID = args[stationIdIndex];
        
        // loops through and populates the hashMap
        for (int i = 0; i < dataFields.size(); i++) 
        {
            variable = dataFields.get(i);
            if (dataDefinitionList.isValidStat(variable)) 
            {
                value = new Sample(Double.valueOf(
                        args[dataFields.indexOf(variable)]));
                samples.put(variable, value);
            }
        }


    }

    /**
     * Creates an empty DataDay 
     */
    public DataDay() 
    {
        //sets all the parameters to 0 or null
        year = 0;
        month = 0;
        day = 0;
        stationID = null;
        samples = new HashMap<String, Sample>();
        
    }

    /**
     * Gets the minimum statistic for a given day
     * @param statisticId String containing the statisticId
     * @param key KeyConstraints that limits the range for data
     * @return itself
     */
    @Override
    public DataDay getStatisticMinDay(String statisticId, KeyConstraints key) 
    {
        return this;
    }

    /**
     * Gets the maximum statistic for a given day
     * @param statisticId String containing statisticId
     * @param key KeyConstraints that limits the range for data
     * @return itself
     */
    @Override
    public DataDay getStatisticMaxDay(String statisticId, KeyConstraints key) 
    {
        return this;
    }

    /**
     * Gets the average statistic for a given day
     * @param statisticId String containing statisticId
     * @param key KeyConstraints that limits the range for data
     * @return itself
     */
    @Override
    public Sample getStatisticAverage(String statisticId, KeyConstraints key) 
    {
        // checks for the key then checks if valid
        if (samples.containsKey(statisticId) &&
                samples.get(statisticId).isValid())
        {
            return samples.get(statisticId);
            
        }        
        return new Sample();       
    }

    /**
     * Get year
     * @return the year
     */
    public int getYear() 
    {
        return year;
    }

    /**
     * Get month
     * @return the month
     */
    public int getMonth() 
    {
        return month;
    }

    /**
     * Get day
     * @return the day
     */
    public int getDay() 
    {
        return day;
    }

    /**
     * Get stationId
     * @return the stationID
     */
    public String getStationID() 
    {
        return stationID;
    }

    /**
     * Describe the data for the day
     * 
     * @return String describing the day
     */
    public String toString()
    {
        return String.format("%04d-%02d-%02d", year, month, day);
    }
    /**
     * sets the static method to the parameter
     * @param dataDefinitions object to be set
     */
    public static void setDataDefinitionList(DataDefinitionList 
            dataDefinitions)
    {
        dataDefinitionList = dataDefinitions;
    }
    /**
     * populates the array with each index
     * @param dataFieldList an arraylist of datafields
     */
    public static void setDataFields(String[] dataFieldList)
    {
        //loops through and sets the Indexes to the dataFields index
        dataFields = new ArrayList<String>();
        for (int i = 0; i < dataFieldList.length; i++)
        {
            dataFields.add(dataFieldList[i]);
        }
        yearIndex = dataFields.indexOf("YEAR") ;
        monthIndex = dataFields.indexOf("MONTH");
        dayIndex = dataFields.indexOf("DAY");
        stationIdIndex = dataFields.indexOf("STID");

    }

}
