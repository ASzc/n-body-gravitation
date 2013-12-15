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
package ca.szc.physics.nbodygravitation.model.method;

import java.util.List;

import ca.szc.physics.nbodygravitation.model.Body;

/**
 * Abstract class for a model simulation method
 */
public abstract class SimulationMethod
{
    protected final List<Body> bodies;

    protected final double timeStep;

    public SimulationMethod( List<Body> bodies, double timeStep )
    {
        this.bodies = bodies;
        this.timeStep = timeStep;
    }

    /**
     * Simulate one step
     */
    public abstract void simulate();
}
