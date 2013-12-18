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

public class Body
{
    private final TwoDimValue<Double> force;

    private final Double mass;

    private final TwoDimValue<Double> position;

    private final Universe universe;

    private final TwoDimValue<Double> velocity;

    public Body( Universe universe, Double mass, TwoDimValue<Double> position, TwoDimValue<Double> velocity )
    {
        this.mass = mass;
        this.position = position;
        this.universe = universe;
        this.velocity = velocity;

        force = new TwoDimValue<Double>( 0.0d, 0.0d );
    }

    public void applyForceFrom( Body other )
    {
        // Shouldn't be able to affect itself
        if ( this == other )
            return;

        // Calculate Euclidean distance
        double distanceX = other.getPosition().getX() - this.getPosition().getX();
        double distanceY = other.getPosition().getY() - this.getPosition().getY();
        double distanceSquared = Math.pow( distanceX, 2 ) + Math.pow( distanceY, 2 );
        double distance = Math.sqrt( distanceSquared );

        // Calculate force
        double forceMagnitude = ( universe.gravConst * other.getMass() * this.getMass() ) / ( distanceSquared );

        // Set force components
        force.setX( force.getX() + ( forceMagnitude * distanceX / distance ) );
        force.setY( force.getY() + ( forceMagnitude * distanceY / distance ) );
    }

    public Double getMass()
    {
        return mass;
    }

    public TwoDimValue<Double> getPosition()
    {
        return position;
    }

    public TwoDimValue<Double> getVelocity()
    {
        return velocity;
    }

    public void move( double timeStep )
    {
        // Update velocity components and position coordinates.
        // There's an assumption that the movement is straight during deltaTime, which means that the values produced
        // are approximations relative to the size of deltaTime.

        velocity.setX( velocity.getX() + ( timeStep * force.getX() / mass ) );
        velocity.setY( velocity.getY() + ( timeStep * force.getY() / mass ) );

        position.setX( position.getX() + ( timeStep * velocity.getX() ) );
        position.setY( position.getY() + ( timeStep * velocity.getY() ) );
    }

    public void resetForce()
    {
        force.setX( 0.0d );
        force.setY( 0.0d );
    }
}
