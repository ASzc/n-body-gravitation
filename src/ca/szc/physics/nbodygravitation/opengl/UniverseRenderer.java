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
import java.util.concurrent.TimeUnit;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import ca.szc.physics.nbodygravitation.model.TwoDimValue;
import ca.szc.physics.nbodygravitation.model.Universe;
import ca.szc.physics.nbodygravitation.model.method.DirectMethod;
import ca.szc.physics.nbodygravitation.model.populator.SolSystemPopulator;

public class UniverseRenderer
    implements GLEventListener
{
    private final List<TwoDimValue<Double>> bodyPositions;

    private final GLU glu;

    private final InputHandler inputHandler;

    private final double maxBound;

    private final Universe universe;

    private int viewportHeight;

    private int viewportWidth;

    private double worldHeight;

    private double worldWidth;

    public UniverseRenderer( InputHandler inputHandler )
    {
        universe = new Universe( SolSystemPopulator.class, DirectMethod.class, TimeUnit.HOURS.toSeconds( 12 ) );
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
        gl.glPointSize( 2 );
        gl.glColor3f( 255, 128, 0 );

        gl.glBegin( GL.GL_POINTS );
        for ( TwoDimValue<Double> position : bodyPositions )
        {
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

            // Convert velocity pixel coords (relative to position pixel coords) into a model velocity.
            final double velocityMultiplier = 1e3; // Units: ms⁻¹
            double velocityX = ( velocityPixelX - positionPixelX ) / viewportWidth * velocityMultiplier;
            double velocityY = ( velocityPixelY - positionPixelY ) / viewportHeight * velocityMultiplier;

            // Translate position pixel coords to model position.
            double positionWorldX = ( ( viewportWidth / 2 ) - positionPixelX ) * -( worldWidth / viewportWidth );
            double positionWorldY = ( ( viewportHeight / 2 ) - positionPixelY ) * ( worldHeight / viewportHeight );

            gl.glBegin( GL.GL_POINTS );
            gl.glColor3f( 0, 128, 255 );

            gl.glVertex2d( positionWorldX, positionWorldY );

            TwoDimValue<Double> predPos = new TwoDimValue<>( positionWorldX, positionWorldY );
            TwoDimValue<Double> predVel = new TwoDimValue<>( velocityX, velocityY );
            Universe predictionUniverse = universe.makePredictionUniverse( 1e3, predPos, predVel );

            gl.glColor3f( 0, 255, 0 );

            for ( int i = 0; i < 100; i++ )
            {
                predictionUniverse.simulate();
                gl.glVertex2d( predPos.getX(), predPos.getY() );
            }

            gl.glEnd();
        }
    }

    @Override
    public void reshape( GLAutoDrawable drawable, int x, int y, int width, int height )
    {
        viewportWidth = width;
        viewportHeight = height;

        // http://www3.ntu.edu.sg/home/ehchua/programming/opengl/CG_Introduction.html
        // >1 if width is larger, <1 is height is larger, =1 if they are the same
        double aspectRatio = ( (double) width ) / Math.max( height, 1 );

        GL2 gl = drawable.getGL().getGL2();

        gl.glMatrixMode( GL2.GL_PROJECTION );
        gl.glLoadIdentity();

        double left = -maxBound;
        double right = maxBound;
        double bottom = -maxBound;
        double top = maxBound;
        if ( aspectRatio >= 1 )
        {
            left *= aspectRatio;
            right *= aspectRatio;
        }
        else if ( aspectRatio < 1 )
        {
            bottom /= aspectRatio;
            top /= aspectRatio;
        }
        glu.gluOrtho2D( left, right, bottom, top );

        worldWidth = Math.abs( left - right );
        worldHeight = Math.abs( bottom - top );
    }
}
