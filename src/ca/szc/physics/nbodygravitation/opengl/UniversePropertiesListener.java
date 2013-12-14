package ca.szc.physics.nbodygravitation.opengl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class UniversePropertiesListener
    implements ActionListener, ChangeListener
{
    private final ConcurrentHashMap<String, Object> liveUniverseGUIProperties;

    private final HashMap<String, Object> stagedUniverseGUIProperties;

    public UniversePropertiesListener( ConcurrentHashMap<String, Object> universeGUIProperties )
    {
        liveUniverseGUIProperties = universeGUIProperties;
        stagedUniverseGUIProperties = new HashMap<>();
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        if ( "apply".equals( e.getActionCommand() ) )
            liveUniverseGUIProperties.putAll( stagedUniverseGUIProperties );
    }

    @Override
    public void stateChanged( ChangeEvent e )
    {
        Object source = e.getSource();
        if ( source instanceof JSpinner )
        {
            JSpinner spinner = (JSpinner) source;
            stagedUniverseGUIProperties.put( spinner.getName(), spinner.getValue() );
        }
    }
}
