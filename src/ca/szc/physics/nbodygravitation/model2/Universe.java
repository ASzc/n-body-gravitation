package ca.szc.physics.nbodygravitation.model2;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import ca.szc.physics.nbodygravitation.model.Body;
import ca.szc.physics.nbodygravitation.model2.populator.Populator;
import ca.szc.physics.nbodygravitation.model2.populator.SolSystemPopulator;

/**
 * Model of a universe containing a finite number of gravitational bodies.
 */
public class Universe
{
    /**
     * The Newtonian gravitational constant. Units: Nm²kg⁻²
     */
    public final Double gravConst = 6.673e-11;

    // Is a bodies list needed?
    private final List<Body> bodies;

    private final List<TwoDimValue<Double>> bodyPositions;

    private final List<TwoDimValue<Double>> bodyVelocities;

    /**
     * A default universe
     * 
     * @see {@link SolSystemPopulator}
     */
    public Universe()
    {
        this( SolSystemPopulator.class );
    }

    /**
     * A custom universe
     * 
     * @param populator The class of the desired populator
     */
    public Universe( Class<? extends Populator> populator )
    {
        bodies = new LinkedList<>();
        bodyPositions = new LinkedList<>();
        bodyVelocities = new LinkedList<>();

        try
        {
            Constructor<? extends Populator> constructor =
                populator.getConstructor( Universe.class, List.class, List.class, List.class );
            Populator populatorInstance = constructor.newInstance( this, bodies, bodyPositions, bodyVelocities );

            populatorInstance.createBodies();
        }
        catch ( InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
                        | IllegalArgumentException | InvocationTargetException e )
        {
            throw new RuntimeException( "Unable to create an instance of populator", e );
        }
    }

    /**
     * Get a reference to the List of Cartesian positions of the bodies in the universe. Only need to call once.
     * 
     * @return A List of two dimensional position coordinates
     */
    public List<TwoDimValue<Double>> getBodyPositions()
    {
        return bodyPositions;
    }

    /**
     * Simulate one step of the Universe model
     */
    public void simulate()
    {

    }
}
