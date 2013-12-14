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
        glCanvas.setPreferredSize( new Dimension( 640, 480 ) );
        frame.add( glCanvas, BorderLayout.CENTER );

        // TODO eventually would like to do click-drag mouse for position/vector input of a new object to predict the
        // motion of ^^^^

        FPSAnimator animator = new FPSAnimator( glCanvas, 60 );
        animator.start();

        frame.pack();
        frame.setVisible( true );
    }
}
