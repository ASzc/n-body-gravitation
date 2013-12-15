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
 * Populator for a universe similar to the Sol solar system
 */
public class SolSystemPopulator
    extends Populator
{
    /**
     * The distance from the sun to the Earth. Units: m
     */
    private static final Double astroUnit = 1.496e11;

    /**
     * The mass of Earth. Units: kg
     */
    private static final Double earthMass = 5.9721986e24;

    /**
     * The mass of Sol. Units: kg
     */
    private static final Double solarMass = 1.988435e30;

    public SolSystemPopulator( Universe universe, List<Body> bodies, List<TwoDimValue<Double>> bodyPositions,
                               List<TwoDimValue<Double>> bodyVelocities )
    {
        super( universe, bodies, bodyPositions, bodyVelocities );
    }

    /**
     * Populate the universe with bodies similar to the Sol solar system
     */
    @Override
    public void createBodies()
    {
        // Sol
        TwoDimValue<Double> posFocus = new TwoDimValue<>( 0.0d, 0.0d );
        makeBody( solarMass, posFocus, 0.0d, 0.0d );

        TwoDimValue<Double> posSat;

        // Mercury
        posSat = new TwoDimValue<>( 0.0d, astroUnit * 0.4d );
        makeBody( earthMass * 0.055d, posSat, calcCircularOrbitVelocity( posFocus, solarMass, posSat ) );
        // Venus
        posSat = new TwoDimValue<>( 0.0d, astroUnit * 0.7d );
        makeBody( earthMass * 0.815d, posSat, calcCircularOrbitVelocity( posFocus, solarMass, posSat ) );
        // Earth
        posSat = new TwoDimValue<>( 0.0d, astroUnit );
        TwoDimValue<Double> posEarth = posSat;
        TwoDimValue<Double> earthVelocity = calcCircularOrbitVelocity( posFocus, solarMass, posSat );
        makeBody( earthMass, posSat, calcCircularOrbitVelocity( posFocus, solarMass, posSat ) );
        // Luna
        posSat = new TwoDimValue<>( 0.0d, astroUnit + 3.98842e8 );
        TwoDimValue<Double> velocity = calcCircularOrbitVelocity( posEarth, earthMass, posSat );
        velocity.setX( velocity.getX() + earthVelocity.getX() );
        velocity.setY( velocity.getY() + earthVelocity.getY() );
        makeBody( earthMass * 0.0123d, posSat, velocity );
        // Mars
        posSat = new TwoDimValue<>( 0.0d, astroUnit * 1.5d );
        makeBody( earthMass * 0.107d, posSat, calcCircularOrbitVelocity( posFocus, solarMass, posSat ) );
        // Jupiter
        posSat = new TwoDimValue<>( 0.0d, astroUnit * 5.2d );
        makeBody( earthMass * 317.8d, posSat, calcCircularOrbitVelocity( posFocus, solarMass, posSat ) );
        // Saturn
        posSat = new TwoDimValue<>( 0.0d, astroUnit * 9.5d );
        makeBody( earthMass * 95.152d, posSat, calcCircularOrbitVelocity( posFocus, solarMass, posSat ) );
        // Uranus
        posSat = new TwoDimValue<>( 0.0d, astroUnit * 19.2d );
        makeBody( earthMass * 14.536d, posSat, calcCircularOrbitVelocity( posFocus, solarMass, posSat ) );
        // Neptune
        posSat = new TwoDimValue<>( 0.0d, astroUnit * 30d );
        makeBody( earthMass * 17.147d, posSat, calcCircularOrbitVelocity( posFocus, solarMass, posSat ) );
    }
}
