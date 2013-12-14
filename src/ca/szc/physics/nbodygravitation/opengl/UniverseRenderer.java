package ca.szc.physics.nbodygravitation.opengl;

import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import ca.szc.physics.nbodygravitation.model2.TwoDimValue;
import ca.szc.physics.nbodygravitation.model2.Universe;

public class UniverseRenderer
    implements GLEventListener
{
    private final List<TwoDimValue<Double>> bodyPositions;

    private final Universe universe;

    private final double maxBound;

    public UniverseRenderer()
    {
        universe = new Universe();
        bodyPositions = universe.getBodyPositions();

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
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor( 0.0f, 0.0f, 0.0f, 1.0f ); // Black and opaque
    }

    private void render( GLAutoDrawable drawable )
    {
        GL2 gl = drawable.getGL().getGL2();

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

    @Override
    public void reshape( GLAutoDrawable drawable, int x, int y, int width, int height )
    {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();

        // Compute aspect ratio of the new window
        float aspect;
        if ( height != 0 )
            aspect = width / height;
        else
            aspect = width / 1;

        // JOGL already calls this before calling this method
        // glViewport(0, 0, width, height);

        // Set the aspect ratio of the clipping area to match the viewport
        gl.glMatrixMode( GL2.GL_PROJECTION ); // To operate on the Projection matrix
        gl.glLoadIdentity(); // Reset the projection matrix
        if ( width >= height )
            // aspect >= 1, set the height from -1 to 1, with larger width
            glu.gluOrtho2D( -maxBound * aspect, maxBound * aspect, -maxBound, maxBound );
        else
            // aspect < 1, set the width to -1 to 1, with larger height
            glu.gluOrtho2D( -maxBound, maxBound, -maxBound / aspect, maxBound / aspect );
    }
}
