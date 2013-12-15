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
        makeBody( earthMass, posSat, calcCircularOrbitVelocity( posFocus, solarMass, posSat ) );
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
