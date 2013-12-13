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

import ca.szc.physics.nbodygravitation.model.method.DirectMethod;
import ca.szc.physics.nbodygravitation.model.method.ModelMethod;

/**
 * A universe containing bodies that is subject to constants
 */
public class Universe
{
    /**
     * The default number of dimensions
     */
    public static final int DEFAULT_DIMENSIONS = 2;

    /**
     * The default Newtonian gravitational constant. Units: Nm²kg⁻²
     */
    public static final double DEFAULT_GRAV_CONST = 6.673e-11;

    /**
     * The default model simulation method
     */
    public static final Class<? extends ModelMethod> DEFAULT_METHOD = DirectMethod.class;

    /**
     * The default mass of Sol. Units: kg
     */
    public static final double DEFAULT_SOLAR_MASS = 1.988435e30;

    /**
     * The list of Body instances in the universe
     */
    private final List<Body> bodies;

    /**
     * The number of dimensions (2 or 3)
     */
    private final int dimensions;

    /**
     * Newtonian gravitational constant. Units: Nm²kg⁻²
     */
    private final double gravitationalConstant;

    /**
     * The model simulation method
     */
    private final ModelMethod method;

    /**
     * Sol's mass. Units: kg
     */
    private final double solarMass;

    /**
     * A Universe with default values
     * 
     * @see #DEFAULT_DIMENSIONS
     * @see #DEFAULT_GRAV_CONST
     * @see #DEFAULT_SOLAR_MASS
     * @see #DEFAULT_METHOD
     */
    public Universe()
    {
        this( DEFAULT_DIMENSIONS, DEFAULT_GRAV_CONST, DEFAULT_SOLAR_MASS, DEFAULT_METHOD );
    }

    /**
     * A Universe with customized values
     * 
     * @param gravitationalConstant The Newtonian gravitational constant. Units: Nm²kg⁻²
     * @param solarMass The mass of Sol. Units: kg
     * @param updater The class of the model updater.
     */
    public Universe( int dimensions, double gravitationalConstant, double solarMass,
                     Class<? extends ModelMethod> updater )
    {
        bodies = new LinkedList<>();
        this.dimensions = dimensions;
        this.gravitationalConstant = gravitationalConstant;

        try
        {
            Constructor<? extends ModelMethod> constructor = updater.getConstructor( List.class );
            this.method = constructor.newInstance( bodies );
        }
        catch ( InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
                        | IllegalArgumentException | InvocationTargetException e )
        {
            throw new RuntimeException( "Unable to create an instance of updater", e );
        }

        this.solarMass = solarMass;
    }

    public List<Body> getBodies()
    {
        return bodies;
    }

    public ModelMethod getMethod()
    {
        return method;
    }
}
