
import java.io.IOException;

/**
 * This class is the top-level class of our County Explorer program. 
 * It contains all of the high-level data (most of which is loaded 
 * immediately) and it creates the main State Frame window.
 * 
 * <P>
 * Project 5<BR>
 * 
 * @version 2016-11-10
 * @author CS 2334.
 * 
 */

public class CountyExplorer
{
    /** The stationDefinitionList (filled in later) */
    private StationDefinitionList stationDefinitionList;

    /** Data info list */
    private DataDefinitionList dataDefinitionList;

    /** County info list: information about individual counties */
    private CountyDefinitionList countyDefinitionList;

    /** Main window. */
    private StateFrame stateFrame;

    /**
     * Load the base data and create the state frame
     * 
     * @throws IOException If there is a problem reading the base data
     */
    public CountyExplorer() throws IOException
    {
        // Load the station and data info
        stationDefinitionList = 
                new StationDefinitionList("data/geoinfo.csv");
        dataDefinitionList = 
                new DataDefinitionList("data/DataTranslation.csv");

        // Hand the DailyData class the dataDefinitionList
        DataDay.setDataDefinitionList(dataDefinitionList);

        // Load the county information
        countyDefinitionList = new CountyDefinitionList(
                "data/countyShapes.csv", stationDefinitionList);

        // Create the window
        stateFrame = new StateFrame("Mesonet Explorer", countyDefinitionList,
                stationDefinitionList, dataDefinitionList);
    }

    /**
     * Make web cat happy...
     * 
     */
    private void makeWebCatHappy()
    {
        // Make web-cat happy
        stateFrame.toString();
        stationDefinitionList.toString();
        dataDefinitionList.toString();
        countyDefinitionList.toString();
    }

    /**
     * Main program kicks everything off
     * 
     * @param args Args are not used
     * @throws IOException If there is an error during a read
     */
    public static void main(String[] args) throws IOException
    {
        CountyExplorer explorer = new CountyExplorer();
        // Make web-cat happy
        explorer.makeWebCatHappy();
        

    }

}
