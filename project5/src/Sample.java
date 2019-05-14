
/**
 * @author CS2334.  Modified by: Kyle Roller, Nigel Mansell
 * <P>
 * Date: 2016-09-10 <BR>
 * Project 1
 * <P>
 * This class represents individual, real-valued samples.  This class
 * explicitly addresses the fact that some samples are invalid.
 * @version 9/20/2016
 */
public class Sample 
{
    /** The observed value. */
    private double value;

    /** Indicates whether the observation is a valid one */
    private boolean valid;

    /**
     * Construct an empty sample that is invalid
     */
    public Sample()
    {
        // sets valid to false
        valid = false;
    }

    /**
     * Construct a sample and check to see if its value is greater 
     * than -900
     * @param value taken from data
     */
    public Sample(double value)
    {
        //check to see if the value is less greater then -900
        if (value > -900)
        {
            //assign true if it is greater then -900 
            valid = true;

            //assign value if its true
            this.value = value;
        }

        else
        {
            //assign false its less then -900
            valid = false;
        }
    }

    /**
     * @return the value
     */
    public double getValue() 
    {
        return value;
    }

    /**
     * @return whether its valid
     */
    public boolean isValid() 
    {
        return valid;
    }

    /**
     *  Display the value of sample or invalid if its false
     * @return String of data
     */
    public String toString() 
    {
        //check whether the Sample is valid
        if (valid)
        {
            return String.format("%.04f", value);
        }
        else 
        {
            return "invalid";
        }
    }

    /**
     * compares two samples and states whether the first one is less
     * then the comparer
     * 
     * @param s is the Sample we are comparing
     * @return whether the sample is less than the sample s
     */
    public boolean isLessThan(Sample s)
    {
        //checks to see if the comparing sample is invalid
        if (!s.isValid())
        {
            return true;
        }

        //checks to see if the original sample is invalid
        if (!this.isValid())
        {
            return this.isValid();
        }

        //checks to see if the original sample is greater then comparer
        else
        {
            return (s.getValue() > this.getValue());  
        }
    }

    /**
     * compares two samples and states whether the first one is
     * greater then the comparer
     * 
     * @param s is the sample we are comparing
     * @return whether the sample is greater than the sample s
     */
    public boolean isGreaterThan(Sample s)
    {
        //checks to see if the comparing sample is invalid
        if (!s.isValid())
        {
            return true;
        }

        //checks to see if the original sample is invalid
        if (!this.isValid())
        {
            return this.isValid();
        }

        //checks to see if the original sample is less then the comparer
        else
        {
            return (s.getValue() < this.getValue());
        }
    }

}
