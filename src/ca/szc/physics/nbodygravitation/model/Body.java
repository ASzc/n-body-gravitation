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

import ca.szc.physics.nbodygravitation.util.Pair;
import ca.szc.physics.nbodygravitation.util.Triple;

/**
 * A body that has mass and moves in a two or three dimensional way.
 */
public class Body
{
    /**
     * Conforms to the size of the {@link #position}, {@link #velocity} and {@link #force} arrays.
     */
    private final int dimensions;

    private double[] force;

    private final double gravConst;

    private final double mass;

    private double[] position;

    private double[] velocity;

    /**
     * Create a body for a two dimensional universe
     */
    public Body( double gravitationalConstant, double mass, Pair<Double> initialPosition, Pair<Double> initialVelocity )
    {
        this.gravConst = gravitationalConstant;
        this.mass = mass;

        dimensions = 2;
        force = new double[] { 0.0d, 0.0d };
        position = new double[] { initialPosition.a, initialPosition.b };
        velocity = new double[] { initialVelocity.a, initialVelocity.b };
    }

    /**
     * Create a body for a three dimensional universe
     */
    public Body( double gravitationalConstant, double mass, Triple<Double> initialPosition,
                 Triple<Double> initialVelocity )
    {
        this.gravConst = gravitationalConstant;
        this.mass = mass;

        dimensions = 3;
        force = new double[] { 0.0d, 0.0d, 0.0d };
        position = new double[] { initialPosition.a, initialPosition.b, initialPosition.c };
        velocity = new double[] { initialVelocity.a, initialVelocity.b, initialVelocity.c };
    }

    /**
     * Calculate the gravitational force acting from other to this, and update the force components of this. Newtonian
     * gravity.
     * 
     * @param other A Body of >= dimensions to this
     */
    public void applyForceFrom( Body other )
    {
        // Calculate Euclidean distance
        double[] distanceComponents = new double[dimensions];
        double distanceSquared = 0.0d;
        for ( int i = 0; i < dimensions; i++ )
        {
            distanceComponents[i] = Math.pow( other.getPosition()[i] - this.getPosition()[i], 2 );
            distanceSquared += distanceComponents[i];
        }

        // Calculate force
        double combinedForce = ( gravConst * other.getMass() * this.getMass() ) / ( distanceSquared );

        // Set force components
        double distance = Math.sqrt( distanceSquared );
        for ( int i = 0; i < dimensions; i++ )
        {
            force[i] = combinedForce * distanceComponents[i] / distance;
        }
    }

    public double getMass()
    {
        return mass;
    }

    public double[] getPosition()
    {
        return position;
    }

    public double[] getVelocity()
    {
        return velocity;
    }

    /**
     * Move for some time according to the current attributes.
     * 
     * @param deltaTime Time in seconds(?)
     */
    public void move( double deltaTime )
    {
        // Update velocity components and position coordinates.
        // There's an assumption that the movement is straight during deltaTime, which means that the values produced
        // are approximations relative to the size of deltaTime.
        for ( int i = 0; i < dimensions; i++ )
        {
            velocity[i] += deltaTime * force[i] / mass;
            position[i] += deltaTime * velocity[i];
        }
    }
}
