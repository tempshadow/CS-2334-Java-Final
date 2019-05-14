/**
 * @author Kyle Roller, Nigel Mansell
 * @version 10/12/2016 Abstract class used to implement StatisticsAbstract
 *          abstract methods
 */
public abstract class MultiStatisticsAbstract extends StatisticsAbstract
        implements Iterable<Integer>
{

    /**
     * Abstract method that returns a item from a array
     * 
     * @param key
     *            integer location of a item
     * @return StatisticsAbstract item in a array
     */
    protected abstract StatisticsAbstract getItem(Integer key);

    /**
     * Iterates through the children's treeMaps keys with use of getItem to find
     * the most minimum day value for days
     * 
     * @return DataDay for most minimum day
     * @param statisticId
     *            containing statistic ID
     * @param keyLimit
     *            that is the limit for data
     */
    public DataDay getStatisticMinDay(String statisticId,
            KeyConstraints keyLimit)
    {
        DataDay theDay = new DataDay();
        // keyLimit = new KeyConstraints();

        // iterates through the keys to find the minimum value
        for (int key : this)
        {

            if (keyLimit == null)
            {
                DataDay tempDay = getItem(key).getStatisticMinDay(statisticId,
                        keyLimit);

                if (tempDay.getStatisticAverage(statisticId, keyLimit)
                        .isLessThan(theDay.getStatisticAverage(statisticId,
                                keyLimit)))
                {
                    theDay = tempDay;
                }
            }
            else
            {
                if (keyLimit.contains(key))
                {
                    DataDay tempDay = getItem(key).getStatisticMinDay(
                            statisticId, keyLimit.getNext());

                    if (tempDay.getStatisticAverage(statisticId, keyLimit)
                            .isLessThan(theDay.getStatisticAverage(statisticId,
                                    keyLimit)))
                    {
                        theDay = tempDay;
                    }
                    keyLimit.getNext();
                }
            }
        }
        return theDay;
    }

    /**
     * Iterates through the children's treeMaps keys with use of getItem to find
     * the most maximum day value for days
     * 
     * @param statisticId
     *            containing the statisticID
     * @param keyLimit
     *            that limits the data
     * @return DataDay containing most maximum day
     */
    public DataDay getStatisticMaxDay(String statisticId,
            KeyConstraints keyLimit)
    {
        DataDay theDay = new DataDay();
        // keyLimit = new KeyConstraints();

        // iterates through the keys to fine the greatest value
        for (int key : this)
        {

            if (keyLimit == null)
            {
                DataDay tempDay = getItem(key).getStatisticMaxDay(statisticId,
                        keyLimit);
                if (tempDay.getStatisticAverage(statisticId, keyLimit)
                        .isGreaterThan(theDay.getStatisticAverage(statisticId,
                                keyLimit)))
                {
                    theDay = tempDay;
                }
            }
            else
            {
                if (keyLimit.contains(key))
                {
                    DataDay tempDay = getItem(key).getStatisticMaxDay(
                            statisticId, keyLimit.getNext());
                    if (tempDay.getStatisticAverage(statisticId, keyLimit)
                            .isGreaterThan(theDay.getStatisticAverage(
                                    statisticId, keyLimit)))
                    {
                        theDay = tempDay;
                    }
                    keyLimit.getNext();
                }
            }
        }
        return theDay;

    }

    /**
     * Iterates through the children's treeMaps keys with use of getItem,
     * divides over all averages and returns the average of averages
     * 
     * @param statisticId
     *            containing the statistic ID
     * @param keyLimit
     *            that limits data input
     * @return Sample containing the average
     */
    public Sample getStatisticAverage(String statisticId,
            KeyConstraints keyLimit)
    {
        // keyLimit = new KeyConstraints();
        double returnValue = 0.0;
        int counter = 0;
        // iterates through the keys, checks if samples are valid,
        // counts each sample
        for (Integer key : this)
        {

            if (keyLimit == null)
            {
                Sample tempSample = getItem(key)
                        .getStatisticAverage(statisticId, keyLimit);
                if (tempSample.isValid())
                {
                    returnValue += tempSample.getValue();
                    counter++;
                }
            }
            else
            {
                if (keyLimit.contains(key))
                {
                    Sample tempSample = getItem(key).getStatisticAverage(
                            statisticId, keyLimit.getNext());
                    if (tempSample.isValid())
                    {
                        returnValue += tempSample.getValue();
                        counter++;
                    }
                    keyLimit.getNext();
                }
            }
        }
        // gets the average
        Sample returnSample = new Sample(returnValue / counter);
        return returnSample;

    }
}
