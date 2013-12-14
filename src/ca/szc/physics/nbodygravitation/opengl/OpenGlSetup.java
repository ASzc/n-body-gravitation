package ca.szc.physics.nbodygravitation.opengl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.concurrent.ConcurrentHashMap;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

public class OpenGlSetup
    implements Runnable
{
    public static JPanel mkSpinProp( String propertyDesc, String propertyId, SpinnerNumberModel spinnerModel,
                                     ChangeListener changeListener )
    {
        JPanel propertyPanel = new JPanel();
        propertyPanel.setLayout( new FlowLayout() );

        JLabel propertyLabel = new JLabel();
        propertyLabel.setText( propertyDesc );
        propertyPanel.add( propertyLabel );

        JSpinner propertySpinner = new JSpinner();
        propertySpinner.setName( propertyId );
        propertySpinner.setModel( spinnerModel );
        propertySpinner.addChangeListener( changeListener );
        propertyPanel.add( propertySpinner );

        return propertyPanel;
    }

    @Override
    public void run()
    {
        ConcurrentHashMap<String, Object> universeGuiProperties = new ConcurrentHashMap<>();
        UniverseGuiPropertiesListener upListener = new UniverseGuiPropertiesListener( universeGuiProperties );

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setLayout( new BorderLayout() );
        frame.setTitle( "n-body-gravitation" );

        // Properties panel
        JPanel propPanel = new JPanel();
        propPanel.setLayout( new BoxLayout( propPanel, BoxLayout.Y_AXIS ) );

        propPanel.add( mkSpinProp( "Number of bodies:", "numBodies", new SpinnerNumberModel( 8, 2, 100, 1 ), upListener ) );
        propPanel.add( mkSpinProp( "Number of bodies:", "numBodies", new SpinnerNumberModel( 8, 2, 100, 1 ), upListener ) );

        // JLabel numBodiesLabel = new JLabel();
        // numBodiesLabel.setText( "Number of bodies:" );
        // numBodiesLabel.setHorizontalAlignment( SwingConstants.RIGHT );
        // propertiesPanel.add( numBodiesLabel );
        //
        // JSpinner numBodiesSpinner = new JSpinner();
        // numBodiesSpinner.setName( "numBodies" );
        // numBodiesSpinner.setModel( new SpinnerNumberModel( 8, 2, 100, 1 ) );
        // numBodiesSpinner.addChangeListener( upListener );
        // propertiesPanel.add( numBodiesSpinner );

        propPanel.add( new JPanel() );
        JButton applyButton = new JButton();
        applyButton.setText( "Apply" );
        applyButton.setActionCommand( "apply" );
        applyButton.addActionListener( upListener );
        propPanel.add( applyButton );

        // OpenGL canvas
        GLProfile glProfile = GLProfile.getDefault();
        GLCapabilities glCapabilities = new GLCapabilities( glProfile );
        GLCanvas glCanvas = new GLCanvas( glCapabilities );
        glCanvas.addGLEventListener( new UniverseRenderer( universeGuiProperties ) );
        glCanvas.setPreferredSize( new Dimension( 640, 480 ) );

        // TODO eventually would like to do click-drag mouse for position/vector input of a new object to predict the
        // motion of ^^^^

        // Tabs
        JTabbedPane pane = new JTabbedPane();
        pane.addTab( "Properties", propPanel );
        pane.addTab( "Render", glCanvas );
        pane.addChangeListener( new AnimatorTabChangeListener( glCanvas, pane.indexOfComponent( glCanvas ) ) );
        frame.add( pane, BorderLayout.CENTER );

        frame.pack();
        frame.setVisible( true );
    }
}
