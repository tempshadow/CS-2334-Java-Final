/**
 * Abstract class: - contains sub-objects over which statistics can be computed
 * (MultiStatisticsAbstract) - Requires a method for adding a day.
 * 
 * @author CS2334
 * @version 2016-11-10
 *
 */
public abstract class MultiStatisticsWithDaysAbstract
        extends MultiStatisticsAbstract
{
    
    /**
     * abstract method for adding a day
     * @param day an object
     */
    protected abstract void addDay(DataDay day);
}
