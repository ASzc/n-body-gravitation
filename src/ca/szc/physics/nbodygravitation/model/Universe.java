/**
 * Copyright 2013 Alex Szczuczko
 *
 * This file is part of n-body-gravitation.
 *
 * n-body-gravitation is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * n-body-gravitation is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * n-body-gravitation.  If not, see <http://www.gnu.org/licenses/>.
 */
package ca.szc.physics.nbodygravitation.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import ca.szc.physics.nbodygravitation.model.method.SimulationMethod;
import ca.szc.physics.nbodygravitation.model.populator.Populator;

/**
 * Model of a universe containing a finite number of gravitational bodies.
 */
public class Universe
{
    private final List<Body> bodies;

    private final List<TwoDimValue<Double>> bodyPositions;

    private final List<TwoDimValue<Double>> bodyVelocities;

    /**
     * The Newtonian gravitational constant. Units: Nm²kg⁻²
     */
    public final Double gravConst = 6.673e-11;

    private final SimulationMethod simulationMethod;

    private final double timeStep;

    /**
     * A constructor for clones
     */
    private Universe( List<Body> bodies, List<TwoDimValue<Double>> bodyPositions,
                      List<TwoDimValue<Double>> bodyVelocities, SimulationMethod simulationMethod, double timeStep )
    {
        this.bodies = bodies;
        this.bodyPositions = bodyPositions;
        this.bodyVelocities = bodyVelocities;

        this.simulationMethod = simulationMethod;
        this.timeStep = timeStep;
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

        this.timeStep = timeStep;

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

    public Universe clone()
    {
        LinkedList<Body> cloneBodies = new LinkedList<>();
        List<TwoDimValue<Double>> cloneBodyPositions = new LinkedList<>();
        List<TwoDimValue<Double>> cloneBodyVelocities = new LinkedList<>();

        SimulationMethod cloneSimulationMethod;
        try
        {
            Class<? extends SimulationMethod> simulationMethodClass = simulationMethod.getClass();
            Constructor<? extends SimulationMethod> constructor =
                simulationMethodClass.getConstructor( List.class, double.class );
            cloneSimulationMethod = constructor.newInstance( cloneBodies, timeStep );
        }
        catch ( InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
                        | IllegalArgumentException | InvocationTargetException e )
        {
            throw new RuntimeException( "Unable to create an instance of simulationMethod", e );
        }

        Universe clone =
            new Universe( cloneBodies, cloneBodyPositions, cloneBodyVelocities, cloneSimulationMethod, timeStep );

        for ( Body body : bodies )
        {
            TwoDimValue<Double> bodyPos =
                new TwoDimValue<Double>( body.getPosition().getX(), body.getPosition().getY() );
            TwoDimValue<Double> bodyVel =
                new TwoDimValue<Double>( body.getVelocity().getX(), body.getVelocity().getY() );

            cloneBodies.add( new Body( clone, body.getMass(), bodyPos, bodyVel ) );
            cloneBodyPositions.add( bodyPos );
            cloneBodyVelocities.add( bodyVel );
        }

        return clone;
    }

    public Universe makePredictionUniverse( Double bodyMass, TwoDimValue<Double> bodyPos, TwoDimValue<Double> bodyVel )
    {
        Universe clone = clone();
        clone.bodies.add( new Body( clone, bodyMass, bodyPos, bodyVel ) );
        clone.bodyPositions.add( bodyPos );
        clone.bodyVelocities.add( bodyVel );

        return clone;
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
