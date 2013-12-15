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

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;

public class OpenGlSetup
    implements Runnable
{
    private final boolean printFps;

    public OpenGlSetup( boolean printFps )
    {
        this.printFps = printFps;
    }

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

        ConcurrentLinkedQueue<MouseEvent> mouseEventQueue = new ConcurrentLinkedQueue<>();
        window.addMouseListener( new MousePointAndDrag( mouseEventQueue, MouseEvent.BUTTON1 ) );

        ConcurrentLinkedQueue<KeyEvent> keyEventQueue = new ConcurrentLinkedQueue<>();
        window.addKeyListener( new KeyboardSimulationControl( keyEventQueue ) );

        window.addGLEventListener( new UniverseRenderer( mouseEventQueue, keyEventQueue ) );

        FPSAnimator animator = new FPSAnimator( window, 60 );
        animator.setUpdateFPSFrames( 120, printFps ? System.out : null );
        animator.start();

        window.setVisible( true );
    }
}
