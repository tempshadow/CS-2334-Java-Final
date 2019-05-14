
import org.junit.Assert;
import org.junit.Test;

/**
 * Testing Class for Sample
 * @author Kyle Roller, Nigel Hansell
 * @version 10/05/2016
 */
public class SampleTest 
{
    /**
     * Checks the Sample class mostly, tests the variables, the toString
     * method, and whether its valid.
     */
    @Test
    public void mostSampleTest() 
    {
        //creates objects for testing
        Sample data = new Sample();
        Sample data2 = new Sample(350.88889);
        Sample data3 = new Sample(-999);
        
        //tests whether values are valid or not
        Assert.assertEquals(false, data.isValid());
        Assert.assertEquals(true, data2.isValid());
        Assert.assertEquals(false, data3.isValid());
        
        //tests the getter 
        Assert.assertEquals(350.88889, data2.getValue(), .0001);   
        
        //test the toString method
        Assert.assertEquals("350.8889", data2.toString());
        Assert.assertEquals("invalid", data3.toString());
    }
    
    /**
     * Checks the isLessThan method and isGreaterThan method 
     * thoroughly
     */
    @Test
    public void comparisonSampleTest()
    {
        //creates samples to use for testing
        Sample data = new Sample();
        Sample data2 = new Sample(350.8889);
        Sample data3 = new Sample(-999);
        Sample data4 = new Sample(100);
        Sample data5 = new Sample(100);
        
        //Tests all the possible isLessThan scenarios
        Assert.assertEquals(true, data4.isLessThan(data2));
        Assert.assertEquals(false, data4.isLessThan(data5));
        Assert.assertEquals(false, data2.isLessThan(data4));
        Assert.assertEquals(true, data4.isLessThan(data));
        Assert.assertEquals(false, data.isLessThan(data4));
        Assert.assertEquals(true, data.isLessThan(data3));
        
        //Tests all the possible isGreaterThan scenerios
        Assert.assertEquals(false, data4.isGreaterThan(data2));
        Assert.assertEquals(false, data4.isGreaterThan(data5));
        Assert.assertEquals(true, data2.isGreaterThan(data5));
        Assert.assertEquals(true, data5.isGreaterThan(data));
        Assert.assertEquals(false, data3.isGreaterThan(data5));
        Assert.assertEquals(true, data.isGreaterThan(data3));
    }
}