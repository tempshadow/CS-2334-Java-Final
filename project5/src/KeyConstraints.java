import java.util.ArrayList;

/**
 * Constructs and adds methods to create a KeyConstrainr for limiting
 * data selection
 * @author Kyle Roller Nigel Mansell
 * @version 11/14/2016
 */
public class KeyConstraints extends ArrayList<Integer>
{
    /**
     * serial ID for webcat
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * the next KeyConstraint in the array
     */
    private KeyConstraints next;

    /**
     * Constructs a keyConstraint
     */
    public KeyConstraints() 
    {
        next = null;
    }

    /**
     * retrieves the next keyConstraint
     * @return KeyConstraints that is next
     */
    public KeyConstraints getNext()
    {
        return next;

    }

    /**
     * Adds a KeyConstraint to the end of the Array
     * @param elem KeyConstraints to be added
     */
    public void addEnd(KeyConstraints elem)
    {
        KeyConstraints key = this;
        while (key.getNext() != null)
        {
            key = key.getNext();
        }
        key.next = elem;
    }

}
