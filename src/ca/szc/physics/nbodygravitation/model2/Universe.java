package ca.szc.physics.nbodygravitation.model2;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ca.szc.physics.nbodygravitation.model2.Body;
import ca.szc.physics.nbodygravitation.model2.method.DirectMethod;
import ca.szc.physics.nbodygravitation.model2.method.SimulationMethod;
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

    private final List<Body> bodies;

    private final List<TwoDimValue<Double>> bodyPositions;

    private final List<TwoDimValue<Double>> bodyVelocities;

    private final SimulationMethod simulationMethod;

    /**
     * A default universe
     * 
     * @see {@link SolSystemPopulator}
     * @see {@link DirectMethod}
     */
    public Universe()
    {
        this( SolSystemPopulator.class, DirectMethod.class, TimeUnit.HOURS.toSeconds( 48 ) );
    }

    /**
     * A custom universe
     * 
     * @param populator The class of the desired populator
     * @param populator The class of the desired simulationMethod
     * @param timeStep The time that will advance with each call to {@link #simulate()}. Units: s
     */
    public Universe( Class<? extends Populator> populator, Class<? extends SimulationMethod> simulationMethod,
                     double timeStep )
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

        try
        {
            Constructor<? extends SimulationMethod> constructor =
                simulationMethod.getConstructor( List.class, double.class );
            this.simulationMethod = constructor.newInstance( bodies, timeStep );
        }
        catch ( InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
                        | IllegalArgumentException | InvocationTargetException e )
        {
            throw new RuntimeException( "Unable to create an instance of simulationMethod", e );
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
        simulationMethod.simulate();
    }
}
