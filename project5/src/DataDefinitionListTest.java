import java.io.IOException;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

/**
 * Testing class for DataDefinition and DataDefinitionList
 * @author Kyle Roller, Nigell Mansell
 * @version 10/17/2016
 */
public class DataDefinitionListTest 
{

    /**
     * completely tests DataDefinitionList
     * @throws IOException if file is not found
     */
    @Test
    public void dataDefinitionListTest() throws IOException 
    {
        //reads in the test file and creates a DataDefinitionList
        DataDefinitionList testList = new DataDefinitionList(
                "data/DataDefinitionListTest.csv");
        //creates an ArrayList of the keys in the testList
        ArrayList<String> lol = testList.getVariableIds();

        //tests the strings contained in the ArrayList
        Assert.assertEquals("[9AVG, TAVG, TMAX, TMIN]", lol.toString());

        //test all the getters and toString for DataDefinitionList
        Assert.assertEquals(true, testList.isValidStat("9AVG"));
        Assert.assertEquals(false, testList.isValidStat("KAVG"));
        Assert.assertEquals("9AVG, Average Air Temperature at 9m"
                + " (degrees Fahrenheit)", testList.getDataInfo(
                        "9AVG").toString());
        Assert.assertEquals("9AVG, Average Air Temperature at "
                + "9m (degrees Fahrenheit)\n"
                + "TAVG, Average Air Temperature ("
                + "degrees Fahrenheit)\n"
                + "TMAX, Maximum Daily Air Temperature ("
                + "degrees Fahrenheit)\n"
                + "TMIN, Minimum Daily Air Temperature ("
                + "degrees Fahrenheit)\n",
                testList.toString());
    }

    /**
     * Tests the class DataDefinition completely
     * @throws IOException if the file is not found
     */
    @Test
    public void dataDefinitionTest() throws IOException
    {
        //creates a DataDefinitionList from the test file
        DataDefinitionList testList = new DataDefinitionList(
                "data/DataDefinitionListTest.csv");

        //creates a DataDefinition
        DataDefinition testTMAX = testList.getDataInfo("TMAX");

        //checks all the getters and toString of the DataDefinition class
        Assert.assertEquals("Maximum Daily Air Temperature",
                testTMAX.getVariableName());
        Assert.assertEquals("TMAX", testTMAX.getVariableId());
        Assert.assertEquals("degrees Fahrenheit", testTMAX.getUnit());
        Assert.assertEquals(true, testTMAX.isPositive());
        Assert.assertEquals("Highest 5-minute averaged temperature observation"
                + " reported each day.",
                testTMAX.getDescription());
    }
}

