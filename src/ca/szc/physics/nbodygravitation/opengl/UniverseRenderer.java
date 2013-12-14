package ca.szc.physics.nbodygravitation.opengl;

import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

import ca.szc.physics.nbodygravitation.model2.TwoDimValue;
import ca.szc.physics.nbodygravitation.model2.Universe;

public class UniverseRenderer
    implements GLEventListener
{
    private final List<TwoDimValue<Double>> bodyPositions;

    private final Universe universe;

    public UniverseRenderer()
    {
        universe = new Universe();
        bodyPositions = universe.getBodyPositions();
    }

    @Override
    public void display( GLAutoDrawable drawable )
    {
        universe.simulate();
        render( drawable );
    }

    @Override
    public void dispose( GLAutoDrawable drawable )
    {
    }

    @Override
    public void init( GLAutoDrawable drawable )
    {
    }

    private void render( GLAutoDrawable drawable )
    {
        GL2 gl = drawable.getGL().getGL2();

        gl.glClear( GL.GL_COLOR_BUFFER_BIT );

        gl.glBegin( GL.GL_POINTS ); // ??? is GL_POINTS right?
        for ( TwoDimValue<Double> position : bodyPositions )
        {
            // TODO scale/translate position to fit in canvas area, then render a point there.
            Double x = position.getX();
            Double y = position.getY();

        }
        gl.glEnd();

        // draw a triangle filling the window
        // gl.glBegin( GL.GL_TRIANGLES );
        // gl.glColor3f( 1, 0, 0 );
        // gl.glVertex2d( -c, -c );
        // gl.glColor3f( 0, 1, 0 );
        // gl.glVertex2d( 0, c );
        // gl.glColor3f( 0, 0, 1 );
        // gl.glVertex2d( s, -s );
        // gl.glEnd();
    }

    @Override
    public void reshape( GLAutoDrawable drawable, int x, int y, int width, int height )
    {
    }
}
