package ca.szc.physics.nbodygravitation.opengl;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;

public class KeyboardSimulationControl
    extends KeyAdapter
{
    private final ConcurrentLinkedQueue<KeyEvent> keyEventQueue;

    public KeyboardSimulationControl( ConcurrentLinkedQueue<KeyEvent> keyEventQueue )
    {
        this.keyEventQueue = keyEventQueue;
    }

    @Override
    public void keyPressed( KeyEvent e )
    {
        if ( e.isPrintableKey() ) // && !e.isAutoRepeat() )
        {
            short key = e.getKeyCode();
            if ( key == KeyEvent.VK_EQUALS ) // i.e. the plus key
                keyEventQueue.offer( e );
            else if ( key == KeyEvent.VK_MINUS )
                keyEventQueue.offer( e );
        }
    }
}
