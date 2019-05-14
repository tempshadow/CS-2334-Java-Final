import org.junit.Assert;
import org.junit.Test;
/**
 * Test StationDefinition
 * @author nigel, kyle
 * @version 10/2016
 */
public class StationDefinitionTest 
{
    /**
     * test each getter method
     */
    @SuppressWarnings("deprecation")
    @Test
    public void testGetMethods() 
    {
        StationDefinition test = new StationDefinition("FITT", "Fittstown",
                "Fittstown", 34.5520, -99.7178);
        Assert.assertEquals("FITT", test.getStationId().toString());
        Assert.assertEquals("Fittstown", test.getCity().toString());
        Assert.assertEquals("Fittstown", test.getName().toString());

    }

}
