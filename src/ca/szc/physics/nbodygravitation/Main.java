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
package ca.szc.physics.nbodygravitation;

import java.util.Arrays;

import ca.szc.physics.nbodygravitation.opengl.OpenGlSetup;

// Created with reference to http://physics.princeton.edu/~fpretori/Nbody

public class Main
{
    /**
     * GUI only for now
     */
    public static void main( String[] args )
    {
        new OpenGlSetup( Arrays.asList( args ).contains( "--fps" ) ).run();
    }
}
