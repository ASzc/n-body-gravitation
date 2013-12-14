package ca.szc.physics.nbodygravitation.opengl;

import java.util.concurrent.ConcurrentHashMap;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class UniverseRenderer
    implements GLEventListener
{
    private double c = 0;

    private double s = 0;

    private double theta = 0;

    private ConcurrentHashMap<String, Object> universeGuiProperties;

    public UniverseRenderer( ConcurrentHashMap<String, Object> universeGuiProperties )
    {
        this.universeGuiProperties = universeGuiProperties;
    }

    @Override
    public void display( GLAutoDrawable drawable )
    {
        update();
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

        // draw a triangle filling the window
        gl.glBegin( GL.GL_TRIANGLES );
        gl.glColor3f( 1, 0, 0 );
        gl.glVertex2d( -c, -c );
        gl.glColor3f( 0, 1, 0 );
        gl.glVertex2d( 0, c );
        gl.glColor3f( 0, 0, 1 );
        gl.glVertex2d( s, -s );
        gl.glEnd();
    }

    @Override
    public void reshape( GLAutoDrawable drawable, int x, int y, int width, int height )
    {
    }

    private void update()
    {
        theta += 0.01;
        s = Math.sin( theta );
        c = Math.cos( theta );
    }
}
