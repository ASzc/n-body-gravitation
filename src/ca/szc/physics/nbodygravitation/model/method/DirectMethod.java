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
 * Direct method of model simulation. Uses no optimizations to trim the number of calculations. Slow, but is as accurate
 * as possible. O(n²) complexity.
 */
public class DirectMethod
    extends ModelMethod
{
    private long lastRun;

    public DirectMethod( List<Body> bodies )
    {
        super( bodies );

        lastRun = System.currentTimeMillis();
    }

    @Override
    public void run()
    {
        // Update forces
        for ( Body body1 : bodies )
        {
            body1.resetForce();
            for ( Body body2 : bodies )
            {
                body1.applyForceFrom( body2 );
            }
        }

        // Update positions
        // Scale a constant by the time between calls for FPS compensation
        long currentTime = System.currentTimeMillis();
        double deltaTime = 1e11 * ( currentTime - lastRun );
        lastRun = currentTime;
        for ( Body body : bodies )
        {
            body.move( deltaTime );
        }
    }
}
