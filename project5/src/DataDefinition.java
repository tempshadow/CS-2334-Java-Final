/**
 * Class that defines a definition for a data type be categorizing it
 * into different variable types, allows you to construct a DataDefinition,
 * Retrieve the description, the unit type, the variable id, the variable name, 
 * whether the data is positive or negative in value, and represent the data
 * in a String.
 * @author Kyle Roller, Nigell Mansell
 * @version 10/17/2016
 */
public class DataDefinition 
{
    /**
     * String containing the variable name
     */
    private String variableName;

    /**
     * String containing the variable ID
     */
    private String variableId;

    /**
     * String containing the units of measurement for the data
     */
    private String unit;

    /**
     * Boolean representing whether the data is positive or negative.
     */
    private boolean positive;

    /**
     * String containing the description for the Data
     */
    private String description;

    /**
     * Constructor for a DataDefinition
     * 
     * @param variableName String containing the name 
     * @param id String containing the ID
     * @param unit String containing the Unit type
     * @param positive Boolean telling whether its a positive or negative value
     * @param description String describing the DataDefinition
     */
    public DataDefinition(String variableName, String id, String unit,
            boolean positive, String description) 
    {
        //assigns all the variables to the correct places
        this.variableName = variableName;
        this.unit = unit;
        this.variableId = id;
        this.positive = positive;
        this.description = description;
    }

    /**
     * @return the variableName
     */
    public String getVariableName() 
    {
        return variableName;
    }

    /**
     * @return the variableId
     */
    public String getVariableId() 
    {
        return variableId;
    }

    /**
     * @return the unit
     */
    public String getUnit() 
    {
        return unit;
    }

    /**
     * @return if it's positive
     */
    public boolean isPositive() 
    {
        return positive;
    }

    /**
     * @return the description
     */
    public String getDescription() 
    {
        return description;
    }

    /**
     * @return String representation of a DataDefinition
     */
    public String toString()
    {
        return String.format("%s, %s (%s)", this.variableId,
                this.variableName, this.unit);
    }

}