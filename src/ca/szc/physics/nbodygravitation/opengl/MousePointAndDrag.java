package ca.szc.physics.nbodygravitation.opengl;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseEvent;

public class MousePointAndDrag
    extends MouseAdapter
{
    private final int buttonToMonitor;

    // Using ConcurrentLinkedQueue instead of NEWTEventFiFo, since NEWTEventFiFo is just a simple synchronized class
    // with no special offerings. Also, this way we can specify the generic type to save casting in the render loop.
    private final ConcurrentLinkedQueue<MouseEvent> eventQueue;

    public MousePointAndDrag( ConcurrentLinkedQueue<MouseEvent> eventQueue, int buttonToMonitor )
    {
        this.buttonToMonitor = buttonToMonitor;
        this.eventQueue = eventQueue;
    }

    @Override
    public void mouseDragged( MouseEvent e )
    {
        if ( e.getButton() == buttonToMonitor )
            eventQueue.offer( e );
    }

    @Override
    public void mousePressed( MouseEvent e )
    {
        if ( e.getButton() == buttonToMonitor )
            eventQueue.offer( e );
    }

    @Override
    public void mouseReleased( MouseEvent e )
    {
        if ( e.getButton() == buttonToMonitor )
            eventQueue.offer( e );
    }
}
