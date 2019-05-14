import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
/**
 * Class constructs a list of DataDefinitions, it allows you to access
 * pieces of the list, you can retrieve a list of variable IDs, you can 
 * retrieve Data info, check if a variable ID has is valid key, and 
 * produce a String representation of a Data Definition List.
 * @author Kyle Roller, Nigell Mansell
 * @version 10/17/2016
 */
public class DataDefinitionList 
{

    /**
     * HashMap containing data information
     */
    private HashMap<String, DataDefinition> dataInfoMap;

    /**
     * Constructor for a DataDefinitionList 
     * 
     * @param fileName String containing the Data to read in
     * @throws IOException Exception if file is not found
     */
    public DataDefinitionList(String fileName) throws IOException 
    {
        //Initializes the HashMap
        dataInfoMap = new HashMap<String, DataDefinition>();

        //Initializes the file reader
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String strg = br.readLine();

        //reads the first line of the file
        strg = br.readLine();

        //Iterates through the rest of the file while it contains information
        while (strg != null)
        {
            //constructs a easy to access String Array from the data
            String[] definitionList = strg.split(",");

            //constructs the DataDefinition for the list
            DataDefinition tempDefinition = new DataDefinition(
                    definitionList[0],
                    definitionList[1],
                    definitionList[2],
                    Boolean.parseBoolean(definitionList[3]),
                    definitionList[4]);

            //places the DataDefinition a object into the hashmap
            dataInfoMap.put(definitionList[1], tempDefinition);

            //reads the next line
            strg = br.readLine();
        }
        //closes the file reader
        br.close();
    }

    /**
     * 
     * @return ArrayList containing a list of variableIDs
     */
    public ArrayList<String> getVariableIds()
    {
        //initialize a ArrayList<String> variable
        ArrayList<String> variableIds = new ArrayList<String>();
        
        //iterate though all map keys and add them to the arrayList
        for (String key : dataInfoMap.keySet())
        {
            variableIds.add(key);
        }
        Collections.sort(variableIds);
        //returns the arrayList
        return variableIds;

    }

    /**
     * checks to see if the passed in String is connected to
     * a valid key in the hashMap
     * @param variableId String of a id to look up
     * @return boolean whether the stat is valid
     */
    public boolean isValidStat(String variableId)
    {
        return  dataInfoMap.containsKey(variableId);
    }

    /**
     * Retrieves the DataDefinition associated to the variableID
     * @param variableId String that is used to retrieve the DataDefinition
     * associated with the key in HashMap
     * @return DataDefinition retrieved from parameter
     */
    public DataDefinition getDataInfo(String variableId)
    {
        return dataInfoMap.get(variableId);
    }

    /**
     * @return String representation of the DataDefinitionList
     */
    public String toString()
    {   
        //Initializes the return String
        String returnString = "";
        
        //constructs a ArrayList containing all the keys
        ArrayList<String> variableIds = getVariableIds();
        
        //iterates through the ArrayList
        for (int i = 0; i < variableIds.size(); ++i)
        {
            //retrieves the corresponding DataDefinition and adds it to the 
            //return string
            returnString +=  dataInfoMap.get(variableIds.get(i)) + "\n";
        }
        //returns the return String
        return returnString;
    }


}
