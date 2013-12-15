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
package ca.szc.physics.nbodygravitation.model.populator;

import java.util.List;

import ca.szc.physics.nbodygravitation.model.Body;
import ca.szc.physics.nbodygravitation.model.TwoDimValue;
import ca.szc.physics.nbodygravitation.model.Universe;

/**
 * An abstract class of a universe populator
 */
public abstract class Populator
{
    private final List<Body> bodies;

    private final List<TwoDimValue<Double>> bodyPositions;

    private final List<TwoDimValue<Double>> bodyVelocities;

    protected final Universe universe;

    public Populator( Universe universe, List<Body> bodies, List<TwoDimValue<Double>> bodyPositions,
                      List<TwoDimValue<Double>> bodyVelocities )
    {
        this.universe = universe;
        this.bodies = bodies;
        this.bodyPositions = bodyPositions;
        this.bodyVelocities = bodyVelocities;
    }

    /**
     * Calculate the velocity components, for a satellite of certain position, to orbit a focus of a certain mass and
     * position. Assume a circular orbit. Uses the formula v=(Gm/r)^0.5
     * 
     * @param posFocus The Cartesian position of the focus. Units: m
     * @param massFocus The mass of the focus. Units: kg
     * @param posSat The Cartesian position of the satellite. Units: m
     * @return The components of the required velocity to maintain orbit
     */
    protected TwoDimValue<Double> calcCircularOrbitVelocity( TwoDimValue<Double> posFocus, Double massFocus,
                                                             TwoDimValue<Double> posSat )
    {
        double distanceX = posFocus.getX() - posSat.getX();
        double distanceY = posFocus.getY() - posSat.getY();
        double radius = Math.sqrt( Math.pow( distanceX, 2 ) + Math.pow( distanceY, 2 ) );

        double tangentialSpeed = Math.sqrt( universe.gravConst * massFocus / radius );

        double standardAngle = Math.atan( Math.abs( distanceY / distanceX ) );
        double thetaV = Math.PI / 2 - standardAngle;

        double velocityX = -1 * Math.signum( distanceY ) * Math.cos( thetaV ) * tangentialSpeed;
        double velocityY = Math.signum( distanceX ) * Math.sin( thetaV ) * tangentialSpeed;

        return new TwoDimValue<Double>( velocityX, velocityY );
    }

    public abstract void createBodies();

    /**
     * Place a new body and its attributes into the lists provided in the constructor
     */
    protected void makeBody( Double mass, Double positionX, Double positionY, Double velocityX, Double velocityY )
    {
        TwoDimValue<Double> velocity = new TwoDimValue<>( velocityX, velocityY );
        makeBody( mass, positionX, positionY, velocity );
    }

    /**
     * Place a new body and its attributes into the lists provided in the constructor
     */
    protected void makeBody( Double mass, Double positionX, Double positionY, TwoDimValue<Double> velocity )
    {
        TwoDimValue<Double> position = new TwoDimValue<>( positionX, positionY );
        makeBody( mass, position, velocity );
    }

    /**
     * Place a new body and its attributes into the lists provided in the constructor
     */
    protected void makeBody( Double mass, TwoDimValue<Double> position, Double velocityX, Double velocityY )
    {
        TwoDimValue<Double> velocity = new TwoDimValue<>( velocityX, velocityY );
        makeBody( mass, position, velocity );
    }

    /**
     * Place a new body and its attributes into the lists provided in the constructor
     */
    protected void makeBody( Double mass, TwoDimValue<Double> position, TwoDimValue<Double> velocity )
    {
        Body body = new Body( universe, mass, position, velocity );
        bodies.add( body );
        bodyPositions.add( position );
        bodyVelocities.add( position );
    }
}
