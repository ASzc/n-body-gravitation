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
package ca.szc.physics.nbodygravitation.opengl;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;

public class OpenGlSetup
    implements Runnable
{
    @Override
    public void run()
    {
        GLProfile glProfile = GLProfile.getDefault();
        GLCapabilities glCapabilities = new GLCapabilities( glProfile );

        // Use a NEWT window
        GLWindow window = GLWindow.create( glCapabilities );
        window.setTitle( "n-body-gravitation" );
        window.setSize( 800, 800 );

        window.addWindowListener( new WindowAdapter()
        {
            @Override
            public void windowDestroyed( WindowEvent e )
            {
                System.exit( 0 );
            };
        } );

        window.addGLEventListener( new UniverseRenderer() );

        FPSAnimator animator = new FPSAnimator( window, 60 );
        animator.start();

        // TODO eventually would like to do click-drag mouse for position/vector input of a new object to predict the
        // motion of

        window.setVisible( true );
    }
}
