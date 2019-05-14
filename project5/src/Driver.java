
import java.io.IOException;

/**
 * Driver Class uses the DataMonth class and displays
 * data from a file
 * @author Kyle Roller, Nigel Hansell
 * @version 9/20/2016
 */
public class Driver 
{
    /**
     * An outPut method that gets all the information and statistics and 
     * puts them in order for reportStation.
     * @param station station object
     * @param dataDefinitionList list object
     * @param variableId string for variable
     */
    public static void reportVariable(StationDefinition station,
            DataDefinitionList dataDefinitionList, String variableId)
    {
        KeyConstraints key = new KeyConstraints();
        String report = dataDefinitionList.getDataInfo(variableId)
                .toString() + "\n";
        report += "Max: " + station.getStatisticMaxDay(variableId, key)
            .toString() + 
            ": " + station.getStatisticMaxDay(variableId, key)
                .getStatisticAverage(variableId, key) + "\n";
        report += "Avg: " + station.getStatisticAverage(variableId, key)
            .toString() + "\n";
        report += "Min: " + station.getStatisticMinDay(variableId, key)
            .toString() + 
            ": " + station.getStatisticMinDay(variableId, key)
                .getStatisticAverage(variableId, key) + "\n";
        System.out.print(report);

    }

    /**
     * Uses a string format and takes in the information passed in from
     * reportVariable to be printed out in a way the user can read.
     * @param list object
     * @param dataDefinitionList object 
     * @param stationId the stationId
     */
    public static void reportStation(StationDefinitionList list,
            DataDefinitionList dataDefinitionList, String stationId) 
    {
        System.out.println(String.format("Station: %s, %s, %s",
                stationId, list.getStationInfo(stationId).getName(),
                list.getStationInfo(stationId).getCity()));
        for (String variable : dataDefinitionList.getVariableIds())
        {
            reportVariable(list.getStationInfo(stationId),
                    dataDefinitionList, variable);
        }
    }

    /**
     * The main method takes a file and reads the data,
     * then computates data from the file to derive new
     * data to be displayed to the user
     * @param args String array taking command line prompts
     * @throws IOException if parsing error
     */
    public static void main(String[] args) throws IOException 
    { 
        StationDefinitionList tempStation = new StationDefinitionList(
                "data/geoinfo.csv");
        DataDefinitionList tempData = new DataDefinitionList(
                "data/DataTranslation.csv");
        DataDay.setDataDefinitionList(tempData);
        //    tempStation.loadData("data/alldata_2011.csv");
        //    tempStation.loadData("data/alldata_2012.csv");
        //    tempStation.loadData("data/alldata_2013.csv");
        //    tempStation.loadData("data/alldata_2014.csv");
        tempStation.loadData("data/alldata_2015.csv");
        //   tempStation.loadData("data/alldata_2016.csv");
        reportStation(tempStation, tempData, "TISH");
        //System.out.println("done");


    }


}

