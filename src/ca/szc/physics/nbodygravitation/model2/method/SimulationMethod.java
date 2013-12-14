package ca.szc.physics.nbodygravitation.model2.method;

import java.util.List;

import ca.szc.physics.nbodygravitation.model2.Body;

/**
 * Abstract class for a model simulation method
 */
public abstract class SimulationMethod
{
    protected final List<Body> bodies;

    protected final double timeStep;

    public SimulationMethod( List<Body> bodies, double timeStep )
    {
        this.bodies = bodies;
        this.timeStep = timeStep;
    }

    /**
     * Simulate one step
     */
    public abstract void simulate();
}
