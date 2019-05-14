import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

/**
 * Class for drawing a color bar. The colorBar itself is a set of filled-in
 * rectangles that illustrate the range of possible values.
 * <P>
 * 
 * Provides the following:
 * <UL>
 * <LI>A renderable colorbar
 * <LI>Given that a range for possible variable values has been set, and a
 * variable value, translates this value into an appropriate color
 * </UL>
 * 
 * <P>
 * 
 * @version 2016-11-10
 * @author Andrew H. Fagg
 * 
 * 
 */

public class ColorBar
{
    /** Minimum value encoded in the color bar */
    private double minValue;
    /** Maximum value encoded in the color bar */
    private double maxValue;
    /** UL corner of the bar: x */
    private int x;
    /** UL corner of the bar: y */
    private int y;
    /** Height of the bar. */
    private int height;
    /** Width of the bar */
    //private int width;
    /** Number of displayed divisions. */
    private int divisions;
    /** Width in pixels per division. */
    private int divisionWidth;
    /** Component rectangles */
    private Rectangle[] rectangle;
    /** Rectangle colors */
    private Color[] color;

    /* Color model start point. */
    /** Value for red given an input value of zero */
    private double redStart;
    /** Value for green given an input value of zero */
    private double greenStart;
    /** Value for blue given an input value of zero */
    private double blueStart;

    /* Color model scale. */
    /**
     * How much does red change with a change in value; 0 = no change; 1 = value
     * that moves with the statistic
     */
    private double redScale;
    /** How much does green change with a change in value */
    private double greenScale;
    /** How much does blue change with a change in value */
    private double blueScale;
    /** Font to use for range labels. */
    private Font font = new Font(Font.SANS_SERIF, Font.BOLD, 24);

    /**
     * Constructor
     * 
     * @param x
     *            Upper-left corner of the colorbar
     * @param y
     *            UL corner of the colorbar
     * @param width
     *            Width of the colorbar
     * @param height
     *            Height of the colorbar
     * @param divisions
     *            Number of divisions to use for the colorbar
     * @param redStart
     *            The value of the red channel when the value is zero
     * @param greenStart
     *            The value of the green channel when the value is zero
     * @param blueStart
     *            The value of the blue channel when the value is zero
     * @param redScale
     *            The degree to which red brightness related to the value
     *            (usually 0 or 1)
     * @param greenScale
     *            The degree to which green brightness related to the value
     *            (usually 0 or 1)
     * @param blueScale
     *            The degree to which blue brightness related to the value
     *            (usually 0 or 1)
     */
    public ColorBar(int x, int y, int width, int height, int divisions,
            double redStart, double greenStart, double blueStart,
            double redScale, double greenScale, double blueScale)
    {
        // Use default min/max values
        this.minValue = 0.0;
        this.maxValue = 1.0;
        // Fill in the instance variables
        this.x = x;
        this.y = y;
        this.height = height;
        //this.width = width;
        this.divisions = divisions;
        this.redStart = redStart;
        this.greenStart = greenStart;
        this.blueScale = blueStart;
        this.redScale = redScale;
        this.greenScale = greenScale;
        this.blueScale = blueScale;

        // The width of each division
        divisionWidth = width / this.divisions;

        // Create the rectangles
        rectangle = new Rectangle[divisions];
        color = new Color[divisions];
        for (int i = 0; i < divisions; ++i)
        {
            rectangle[i] = new Rectangle(x + i * divisionWidth, y,
                    divisionWidth, height);
            color[i] = computeColor(((double) i) / (divisions - 1));
        }

    }

    /**
     * Translate a scaler value into a color based on the defined 
     * color model.
     * 
     * @param value
     *            Variable value
     * @return A color that corresponds to the value
     * 
     */
    public Color computeColor(double value)
    {
        // Bound the value in the legal range
        if (value < minValue)
        {
            value = minValue;
        }
        if (value > maxValue)
        {
            value = maxValue;
        }

        // Range
        double range = maxValue - minValue;

        /*
         * (value-minValue)/range is a number that is between 0 and 1
         * 
         * r,g,b are also values that are between 0 and 1
         * 
         * When scale = 1, r is:
         * 
         *         1            __________
         *                     /
         *                    /:
         *    start -------- / :
         * r                |  :
         *         0 _______|  :
         *                  :  :
         *                min  max 
         *                 
         *                
         */
        // Compute continuous value (0..1)
        double r = (redScale * (1.0 - redStart) * 
                (value - minValue) / range + redStart);
        double g = (greenScale * (1.0 - greenStart) * 
                (value - minValue) / range + greenStart);
        double b = (blueScale * (1.0 - blueStart) * 
                (value - minValue) / range + blueStart);

        // Transform from 0..1 to 0..155
        return new Color((int)(r * 255), 
                (int)(g * 255), 
                (int)(b * 255));
    }

    /**
     * Draw the color bar
     * 
     * @param g2 Graphics2D  context in which to draw.
     */
    public void draw(Graphics2D g2)
    {
        // Loop over the rectangles & draw each one
        for (int i = 0; i < divisions; ++i)
        {
            g2.setColor(color[i]);
            g2.fill(rectangle[i]);
        }

        // Draw the min and max value text
        g2.setFont(font);
        g2.setColor(Color.BLACK);

        // Figure out how big this string will be
        FontMetrics fm = g2.getFontMetrics();
        String str = String.format("%.6s", minValue);
        Rectangle2D rect = fm.getStringBounds(str, g2);

        // Draw the string centered horizontally about the 
        // left hand side of the colorBar
        g2.drawString(str, (int) (x - rect.getWidth() / 2.0), 
                (int) (y + height + rect.getHeight()));

        // Figure out how big the max is
        str = String.format("%.6s", maxValue);
        rect = fm.getStringBounds(str, g2);

        // Draw the string centered horizontally about the 
        // right hand side of the  colorBar
        g2.drawString(str, 
                (int) (x + divisionWidth * divisions - rect.getWidth() / 2.0),
                (int) (y + height + rect.getHeight()));

    }

    /**
     * Set the range of the represented by the colorBar
     * 
     * @param newMin Minimum value
     * @param newMax Maximum value
     */
    public void setRange(double newMin, double newMax)
    {
        minValue = newMin;
        maxValue = newMax;
    }
}
