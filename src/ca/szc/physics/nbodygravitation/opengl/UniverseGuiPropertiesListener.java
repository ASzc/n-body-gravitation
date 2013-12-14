package ca.szc.physics.nbodygravitation.opengl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class UniverseGuiPropertiesListener
    implements ActionListener, ChangeListener
{
    private final ConcurrentHashMap<String, Object> liveUniverseGuiProperties;

    private final HashMap<String, Object> stagedUniverseGuiProperties;

    public UniverseGuiPropertiesListener( ConcurrentHashMap<String, Object> universeGuiProperties )
    {
        liveUniverseGuiProperties = universeGuiProperties;
        stagedUniverseGuiProperties = new HashMap<>();
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        if ( "apply".equals( e.getActionCommand() ) )
            liveUniverseGuiProperties.putAll( stagedUniverseGuiProperties );
    }

    @Override
    public void stateChanged( ChangeEvent e )
    {
        if ( e.getSource() instanceof JSpinner )
        {
            JSpinner spinner = (JSpinner) e.getSource();
            stagedUniverseGuiProperties.put( spinner.getName(), spinner.getValue() );
        }
    }
}
