/**
 * Abstract Class that initializes all abstract methods
 * @author Kyle Roller, Nigell Mansell
 * @version 10/17/2016
 */
public abstract class StatisticsAbstract 
{ 
    /**
     * Abstract method that returns the DataDay with the Min statistic
     * @param statisticId String that identifies the Data to be used
     * @param key that limits the data input
     * @return DataDay with the min value
     */
    public abstract DataDay getStatisticMinDay(String statisticId,
            KeyConstraints key);
    
    /**
     * Abstract method that returns average Sample Statistic
     * @param statisticId String that identifies the Data to be used
     * @param key that limits the data input
     * @return Sample containing the average value 
     */
    public abstract Sample getStatisticAverage(String statisticId,
            KeyConstraints key);
    
    /**
     * Abstract method that returns the DataDay with the max statistic
     * @param statisticId String that identifies the Data to be used
     * @param key that limits the data input
     * @return DataDay with the max value
     */
    public abstract DataDay getStatisticMaxDay(String statisticId,
            KeyConstraints key);
}
