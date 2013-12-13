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

import ca.szc.physics.nbodygravitation.model.executor.ModelExecutor;
import ca.szc.physics.nbodygravitation.model.method.DirectMethod;
import ca.szc.physics.nbodygravitation.model.method.ModelMethod;
import ca.szc.physics.nbodygravitation.model.population.PopulationStrategy;
import ca.szc.physics.nbodygravitation.model.population.RandomCircularOrbitsStrategy;

/**
 * A universe containing bodies that is subject to constants
 */
public class Universe
{
    /**
     * The default universal constants
     */
    public static final UniversalConstants DEFAULT_CONSTANTS = new UniversalConstants();

    /**
     * The default model simulation method
     */
    public static final Class<? extends ModelMethod> DEFAULT_METHOD = DirectMethod.class;

    /**
     * The default universe population strategy
     */
    public static final Class<? extends PopulationStrategy> DEFAULT_POPULATOR = RandomCircularOrbitsStrategy.class;

    /**
     * The list of Body instances in the universe
     */
    private final List<Body> bodies;

    /**
     * The model simulation executor
     */
    private final ModelExecutor executor;

    /**
     * The model simulation method
     */
    private final ModelMethod method;

    /**
     * A Universe with default values
     * 
     * @param modelExecutor The class of the model executor.
     * @see #DEFAULT_CONSTANTS
     * @see #DEFAULT_POPULATOR
     * @see #DEFAULT_METHOD
     */
    public Universe( Class<? extends ModelExecutor> modelExecutor )
    {
        this( DEFAULT_CONSTANTS, DEFAULT_POPULATOR, modelExecutor, DEFAULT_METHOD );
    }

    /**
     * A Universe with customized values
     * 
     * @param constants The applicable universal constants.
     * @param populator The class of the universe populator.
     * @param modelExecutor The class of the model executor.
     * @param modelMethod The class of the model method.
     */
    public Universe( UniversalConstants constants, Class<? extends PopulationStrategy> populator,
                     Class<? extends ModelExecutor> modelExecutor, Class<? extends ModelMethod> modelMethod )
    {
        try
        {
            Constructor<? extends PopulationStrategy> constructor = populator.getConstructor( UniversalConstants.class );
            PopulationStrategy populatorInstance = constructor.newInstance( constants );
            bodies = new LinkedList<Body>();
            populatorInstance.populate( bodies );
        }
        catch ( InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
                        | IllegalArgumentException | InvocationTargetException e )
        {
            throw new RuntimeException( "Unable to create an instance of populator", e );
        }

        try
        {
            Constructor<? extends ModelMethod> constructor = modelMethod.getConstructor( List.class );
            this.method = constructor.newInstance( bodies );
        }
        catch ( InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
                        | IllegalArgumentException | InvocationTargetException e )
        {
            throw new RuntimeException( "Unable to create an instance of modelMethod", e );
        }

        try
        {
            Constructor<? extends ModelExecutor> constructor =
                modelExecutor.getConstructor( List.class, ModelMethod.class );
            executor = constructor.newInstance( bodies, method );
        }
        catch ( InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
                        | IllegalArgumentException | InvocationTargetException e )
        {
            throw new RuntimeException( "Unable to create an instance of modelMethod", e );
        }
    }

    /**
     * Get the instance of the modelExecutor class passed to the Universe constructor
     * 
     * @return A ModelExecutor instantiated with variables encapsulated in this Universe
     */
    public ModelExecutor getExecutor()
    {
        return executor;
    }
}
