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

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;

public class OpenGlSetup
    implements Runnable
{
    @Override
    public void run()
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setLayout( new BorderLayout() );
        frame.setTitle( "n-body-gravitation" );

        // OpenGL canvas
        GLProfile glProfile = GLProfile.getDefault();
        GLCapabilities glCapabilities = new GLCapabilities( glProfile );
        GLCanvas glCanvas = new GLCanvas( glCapabilities );
        glCanvas.addGLEventListener( new UniverseRenderer() );
        glCanvas.setPreferredSize( new Dimension( 800, 800 ) );
        frame.add( glCanvas, BorderLayout.CENTER );

        // TODO eventually would like to do click-drag mouse for position/vector input of a new object to predict the
        // motion of ^^^^

        FPSAnimator animator = new FPSAnimator( glCanvas, 60 );
        animator.start();

        frame.pack();
        frame.setVisible( true );
    }
}
