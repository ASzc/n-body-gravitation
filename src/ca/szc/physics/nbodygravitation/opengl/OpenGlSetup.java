package ca.szc.physics.nbodygravitation.opengl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.concurrent.ConcurrentHashMap;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import com.jogamp.opengl.util.FPSAnimator;

public class OpenGlSetup
    implements Runnable
{
    @Override
    public void run()
    {
        ConcurrentHashMap<String, Object> universeGUIProperties = new ConcurrentHashMap<>();
        UniversePropertiesListener upListener = new UniversePropertiesListener( universeGUIProperties );

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setLayout( new BorderLayout() );
        frame.setTitle( "n-body-gravitation" );

        // Properties panel
        JPanel propertiesPanel = new JPanel();
        propertiesPanel.setLayout( new GridLayout( 2, 2, 5, 5 ) );
        frame.add( propertiesPanel, BorderLayout.NORTH );

        JLabel numBodiesLabel = new JLabel();
        numBodiesLabel.setText( "Number of bodies:" );
        numBodiesLabel.setHorizontalAlignment( SwingConstants.RIGHT );
        propertiesPanel.add( numBodiesLabel );

        JSpinner numBodiesSpinner = new JSpinner();
        numBodiesSpinner.setName( "numBodies" );
        numBodiesSpinner.setModel( new SpinnerNumberModel( 8, 2, 100, 1 ) );
        numBodiesSpinner.addChangeListener( upListener );
        propertiesPanel.add( numBodiesSpinner );

        propertiesPanel.add( new JPanel() );
        JButton applyButton = new JButton();
        applyButton.setText( "Apply" );
        applyButton.setActionCommand( "apply" );
        applyButton.addActionListener( upListener );
        propertiesPanel.add( applyButton );

        // OpenGL canvas
        GLProfile glProfile = GLProfile.getDefault();
        GLCapabilities glCapabilities = new GLCapabilities( glProfile );
        GLCanvas glCanvas = new GLCanvas( glCapabilities );
        glCanvas.addGLEventListener( new UniverseRenderer( universeGUIProperties ) );
        glCanvas.setPreferredSize( new Dimension( 640, 480 ) );
        frame.add( glCanvas, BorderLayout.CENTER );

        FPSAnimator animator = new FPSAnimator( glCanvas, 60 );
        animator.start();

        frame.pack();
        frame.setVisible( true );
    }
}
