package ca.szc.physics.nbodygravitation.model.population;

import java.util.List;

import ca.szc.physics.nbodygravitation.model.Body;
import ca.szc.physics.nbodygravitation.model.UniversalConstants;
import ca.szc.physics.nbodygravitation.util.Pair;

public class RandomCircularOrbitsStrategy
    extends PopulationStrategy
{
    public static double exp( double lambda )
    {
        return -Math.log( 1 - Math.random() ) / lambda;
    }

    public RandomCircularOrbitsStrategy( UniversalConstants constants )
    {
        super( constants );
    }

    /**
     * Calculate the required velocity vector for a circular orbit around the origin. Based on the formula v=(Gm/r)^0.5
     * 
     * @param massOfFocus The mass of the object at the origin
     * @param positionOfSatellite The coordinates of the orbiter
     * @return Velocity vectors for the orbiter
     */
    private Pair<Double> getCircleOrbitVelocity( double massOfFocus, Pair<Double> positionOfSatellite )
    {
        double distance = Math.sqrt( Math.pow( positionOfSatellite.a, 2 ) + Math.pow( positionOfSatellite.b, 2 ) );

        double velocityMagnitude = Math.sqrt( constants.gravConst * massOfFocus / distance );

        double absangle = Math.atan( Math.abs( positionOfSatellite.b / positionOfSatellite.a ) );
        double thetav = Math.PI / 2 - absangle;
        double vx = -1 * Math.signum( positionOfSatellite.b ) * Math.cos( thetav ) * velocityMagnitude;
        double vy = Math.signum( positionOfSatellite.a ) * Math.sin( thetav ) * velocityMagnitude;

        return new Pair<Double>( vx, vy );
    }

    private Body newBody( Pair<Double> initialPosition, Pair<Double> initialVelocity )
    {
        return new Body( constants.gravConst, constants.solarMass, initialPosition, initialVelocity );
    }

    @Override
    public void populate( List<Body> bodies )
    {
        // Place a star in the centre
        bodies.add( newBody( new Pair<Double>( 0.0d, 0.0d ), new Pair<Double>( 0.0d, 0.0d ) ) );

        double earthMass = 3.003467e-6 * constants.solarMass;

        for ( int i = 0; i < 8; i++ )
        {
            double px = 1e18 * exp( -1.8 ) * ( .5 - Math.random() );
            double py = 1e18 * exp( -1.8 ) * ( .5 - Math.random() );
            Pair<Double> position = new Pair<Double>( px, py );

            bodies.add( new Body( constants.gravConst, earthMass, position,
                                  getCircleOrbitVelocity( constants.solarMass, position ) ) );
        }
    }
}
