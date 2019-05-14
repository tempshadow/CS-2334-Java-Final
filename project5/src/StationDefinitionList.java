import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
/**
 *  Class constructs a list of StationDefinition, it allows you to access
 * pieces of the list, you can retrieve a list of Station IDs, you can 
 * retrieve Data info, check if a variable ID has is valid key, and 
 * produce a String representation of a Station Definition List.
 * @author Kyle Roller Nigell Mansenn
 * @version 10/26/16
 */
public class StationDefinitionList 
{
    /**
     * HashMap containing data information
     */
    HashMap<String, StationDefinition> stationMap;

    /**
     * Constructor for a StationDefinitionList 
     * 
     * @param fileName String containing the Data to read in
     * @throws IOException Exception if file is not found
     */
    public StationDefinitionList(String fileName) throws IOException 
    {
        //Initializes the HashMap
        stationMap = new HashMap<String, StationDefinition>();

        //Initializes the file reader
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String strg = br.readLine();

        //reads the first line of the file
        strg = br.readLine();

        //Iterates through the rest of the file while it contains 
        //information
        while (strg != null)
        {
            //constructs a easy to access String Array from the data
            String[] stationList = strg.split(",");

            //constructs the DataDefinition for the list
            StationDefinition tempStation = new StationDefinition(
                    stationList[0],
                    stationList[1],
                    stationList[2],
                    Double.parseDouble(stationList[3]),
                    Double.parseDouble(stationList[4]));

            //places the DataDefinition a object into the hashmap
            stationMap.put(stationList[0], tempStation);

            //reads the next line
            strg = br.readLine();
        }
        //closes the file reader
        br.close();
    }
    
    /**
     * Gets the stationId from the hashMap
     * @param stationId the station id
     * @return they value of key
     */
    public StationDefinition getStationInfo(String stationId)
    {
        return stationMap.get(stationId);
    }
    
    /**
     * Gets the stationIds to be turned into a string that can be 
     * printed out
     * @return the string to be printed out
     */
    public String toString()
    {
        //Initializes the return String
        String returnString = "";

        //constructs a ArrayList containing all the keys
        ArrayList<String> stationIds = getStationIds();

        //iterates through the ArrayList
        for (int i = 0; i < stationIds.size(); ++i)
        {
            //retrieves the corresponding DataDefinition and adds it to the 
            //return string
            returnString +=  stationMap.get(stationIds.get(i)) + "\n";
        }
        //returns the return String
        return returnString;
    }
    
    /**
     * Gets the stationId and adds it to a day
     * @param day object
     */
    private void addDay(DataDay day)
    {

        stationMap.get(day.getStationID()).addDay(day);

    }
    
    /**
     * Takes in a files first line for setDataFields to use 
     * @param fileName String containing filename
     * @throws IOException exception in case of error
     */
    public void loadData(String fileName) throws IOException
    {
        //Reads in from a file
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String strg = br.readLine();
        String[] loadData = strg.split(",");
        DataDay.setDataFields(loadData);
        strg = br.readLine();
        while (strg != null) 
        {
            String[] split = strg.split(",");
            DataDay temp = new DataDay(split);

            this.addDay(temp);

            strg = br.readLine();
        }
        br.close();
    }
    
    /**
     * 
     * @return ArrayList containing a list of StationIDs
     */
    public ArrayList<String> getStationIds()
    {
        ArrayList<String> stationIds = new ArrayList<String>();
        for (String key : stationMap.keySet()) {
            stationIds.add(key);
        }
        Collections.sort(stationIds);
        return stationIds;

    }
    
    /**
     * Gets the value using a key and then gets that values variable 
     * for average
     * @param stationId the stationId
     * @param variableId the variableId
     * @param key that limits data input
     * @return the hashMap value of a key then gets the variableId
     */
    public Sample getStatisticAverage(String stationId, String variableId,
            KeyConstraints key) 
    {

        return stationMap.get(stationId).getStatisticAverage(variableId, key);
    }
    
    /**
     * Gets the value using a key and then gets that values variable 
     * for average
     * @param stationId the stationId
     * @param variableId the variableId
     * @param key that limits data
     * @return the hashMap value of a key then gets the variableId
     */
    public DataDay getStatisticMaxDay(String stationId, String variableId,
            KeyConstraints key)
    {

        return stationMap.get(stationId).getStatisticMaxDay(variableId, key);

    }
    
    /**
     * Gets the value using a key and then gets that values variable 
     * for average
     * @param stationId the stationId
     * @param variableId the variableId
     * @param key that limits data
     * @return the hashMap value of a key then gets the variableId
     */
    public DataDay getStatisticMinDay(String stationId, String variableId,
            KeyConstraints key)
    {
        return stationMap.get(stationId).getStatisticMinDay(variableId, key);

    }
}
