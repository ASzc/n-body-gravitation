package ca.szc.physics.nbodygravitation.model;

/**
 * Constants applicable to a particular universe
 */
public class UniversalConstants
{
    /**
     * The default number of dimensions
     */
    public static final int DEFAULT_DIMENSIONS = 2;

    /**
     * The default Newtonian gravitational constant. Units: Nm²kg⁻²
     */
    public static final double DEFAULT_GRAV_CONST = 6.673e-11;

    /**
     * The default mass of Sol. Units: kg
     */
    public static final double DEFAULT_SOLAR_MASS = 1.988435e30;

    /**
     * The number of dimensions (2 or 3)
     */
    public final int dimensions;

    /**
     * Newtonian gravitational constant. Units: Nm²kg⁻²
     */
    public final double gravitationalConstant;

    /**
     * Sol's mass. Units: kg
     */
    public final double solarMass;

    /**
     * The default group of constants
     * 
     * @see #DEFAULT_DIMENSIONS
     * @see #DEFAULT_GRAV_CONST
     * @see #DEFAULT_SOLAR_MASS
     */
    public UniversalConstants()
    {
        this( DEFAULT_DIMENSIONS, DEFAULT_GRAV_CONST, DEFAULT_SOLAR_MASS );
    }

    /**
     * A customized group of constants
     * 
     * @param dimensions The number of dimensions the universe has (2 or 3)
     * @param gravitationalConstant The Newtonian gravitational constant. Units: Nm²kg⁻²
     * @param solarMass The mass of Sol. Units: kg
     */
    public UniversalConstants( int dimensions, double gravitationalConstant, double solarMass )
    {
        this.dimensions = dimensions;
        this.gravitationalConstant = gravitationalConstant;
        this.solarMass = solarMass;
    }
}
