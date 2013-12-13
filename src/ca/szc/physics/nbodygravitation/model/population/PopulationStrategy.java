package ca.szc.physics.nbodygravitation.model.population;

import java.util.List;

import ca.szc.physics.nbodygravitation.model.Body;
import ca.szc.physics.nbodygravitation.model.UniversalConstants;

/**
 * Abstract class for a universe population strategy
 */
public abstract class PopulationStrategy
{
    protected final UniversalConstants constants;

    public PopulationStrategy( UniversalConstants constants )
    {
        this.constants = constants;
    }

    /**
     * Populate a list with bodies
     * 
     * @param bodies The list to insert the new bodies into
     */
    public abstract void populate( List<Body> bodies );
}
