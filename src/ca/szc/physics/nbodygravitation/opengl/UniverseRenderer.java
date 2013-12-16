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

import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import ca.szc.physics.nbodygravitation.model.TwoDimValue;
import ca.szc.physics.nbodygravitation.model.Universe;

public class UniverseRenderer
    implements GLEventListener
{
    private final List<TwoDimValue<Double>> bodyPositions;

    private final GLU glu;

    private final InputHandler inputHandler;

    private final double maxBound;

    private final Universe universe;

    public UniverseRenderer( InputHandler inputHandler )
    {
        universe = new Universe();
        bodyPositions = universe.getBodyPositions();

        glu = new GLU();

        this.inputHandler = inputHandler;

        double farthestPositionSum = 0.0d;
        for ( TwoDimValue<Double> position : bodyPositions )
        {
            double sum = position.getX() + position.getY();
            if ( sum > farthestPositionSum )
                farthestPositionSum = sum;
        }
        maxBound = farthestPositionSum * 1.1d;
    }

    @Override
    public void display( GLAutoDrawable drawable )
    {
        inputHandler.processInput();

        // Do the simulation
        for ( int i = 0; i < inputHandler.getSimStepsPerFrame(); i++ )
        {
            universe.simulate();
        }

        GL2 gl = drawable.getGL().getGL2();
        renderPositions( gl );
        renderPrediction( gl );
    }

    @Override
    public void dispose( GLAutoDrawable drawable )
    {
    }

    @Override
    public void init( GLAutoDrawable drawable )
    {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor( 0, 0, 0, 1 ); // Black and opaque
    }

    private void renderPositions( GL2 gl )
    {
        gl.glClear( GL.GL_COLOR_BUFFER_BIT );
        gl.glPointSize( 2.0f );

        gl.glBegin( GL.GL_POINTS );
        for ( TwoDimValue<Double> position : bodyPositions )
        {
            gl.glColor3f( 255, 128, 0 );

            gl.glVertex2d( position.getX(), position.getY() );
        }
        gl.glEnd();
    }

    private void renderPrediction( GL2 gl )
    {
        // Render a prediction if we have a position point and a velocity point.
        if ( inputHandler.mousePressAndDrag() )
        {
            int positionPixelX = inputHandler.getLastMousePress().getX();
            int positionPixelY = inputHandler.getLastMousePress().getY();

            int velocityPixelX = inputHandler.getLastMouseDrag().getX();
            int velocityPixelY = inputHandler.getLastMouseDrag().getY();

            System.out.println( "(" + positionPixelX + "," + positionPixelY + ") (" + velocityPixelX + ","
                + velocityPixelY + ")" );

            // Translate position pixel coords to model position.
            // Translate velocity pixel coords (relative to position) into a model velocity.
            // TODO

            // TODO maybe can access the current opengl matrix, and use its inverse to scale the position coord to model
            // space?
        }
    }

    @Override
    public void reshape( GLAutoDrawable drawable, int x, int y, int width, int height )
    {
        // http://www3.ntu.edu.sg/home/ehchua/programming/opengl/CG_Introduction.html
        // >1 if width is larger, <1 is height is larger, =1 if they are the same
        double aspectRatio = ( (double) width ) / Math.max( height, 1 );

        GL2 gl = drawable.getGL().getGL2();

        gl.glMatrixMode( GL2.GL_PROJECTION );
        gl.glLoadIdentity();

        if ( aspectRatio >= 1 )
            glu.gluOrtho2D( -maxBound * aspectRatio, maxBound * aspectRatio, -maxBound, maxBound );
        else if ( aspectRatio < 1 )
            glu.gluOrtho2D( -maxBound, maxBound, -maxBound / aspectRatio, maxBound / aspectRatio );
    }
}
