package ca.szc.physics.nbodygravitation.model.executor;

import java.util.List;

import ca.szc.physics.nbodygravitation.model.Body;
import ca.szc.physics.nbodygravitation.model.method.ModelMethod;

/**
 * Abstract class for a model executor. Reads from the bodies list in between calls to the ModelMethod.
 */
public abstract class ModelExecutor
{
    protected final List<Body> bodies;

    protected final ModelMethod method;

    public ModelExecutor( List<Body> bodies, ModelMethod method )
    {
        this.bodies = bodies;
        this.method = method;
    }
}
