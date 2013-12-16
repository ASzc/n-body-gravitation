package ca.szc.physics.nbodygravitation.opengl;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.MouseEvent;

class InputHandler
{
    private final ConcurrentLinkedQueue<KeyEvent> keyEventQueue;

    private MouseEvent lastMouseDrag;

    private MouseEvent lastMousePress;

    private final ConcurrentLinkedQueue<MouseEvent> mouseEventQueue;

    private int simStepsPerFrame = 1;

    public InputHandler( ConcurrentLinkedQueue<KeyEvent> keyEventQueue,
                         ConcurrentLinkedQueue<MouseEvent> mouseEventQueue )
    {
        this.keyEventQueue = keyEventQueue;
        this.mouseEventQueue = mouseEventQueue;
    }

    public MouseEvent getLastMouseDrag()
    {
        return lastMouseDrag;
    }

    public MouseEvent getLastMousePress()
    {
        return lastMousePress;
    }

    public int getSimStepsPerFrame()
    {
        return simStepsPerFrame;
    }

    public boolean mousePressAndDrag()
    {
        return lastMouseDrag != null && lastMousePress != null;
    }

    public void processInput()
    {
        processKeyInput();
        processMouseInput();
    }

    private void processKeyInput()
    {
        // Process input events
        KeyEvent keyEvent;
        if ( ( keyEvent = keyEventQueue.poll() ) != null )
        {
            int delta = 1;
            if ( keyEvent.isShiftDown() )
                delta = 10;

            short key = keyEvent.getKeyCode();
            if ( key == KeyEvent.VK_EQUALS )
                simStepsPerFrame += delta;
            else if ( key == KeyEvent.VK_MINUS && simStepsPerFrame >= delta )
                simStepsPerFrame -= delta;
        }
    }

    private void processMouseInput()
    {
        // Process input events
        MouseEvent mouseEvent;
        if ( ( mouseEvent = mouseEventQueue.poll() ) != null )
        {
            short eventType = mouseEvent.getEventType();
            if ( eventType == MouseEvent.EVENT_MOUSE_PRESSED )
            {
                lastMousePress = mouseEvent;
            }
            else if ( eventType == MouseEvent.EVENT_MOUSE_DRAGGED )
            {
                // Clear through to the latest drag event, since they are produced faster than 60Hz
                // Safe to not do it atomically since this method is the only consumer
                while ( !mouseEventQueue.isEmpty()
                    && mouseEventQueue.peek().getEventType() == MouseEvent.EVENT_MOUSE_DRAGGED )
                {
                    mouseEvent = mouseEventQueue.poll();
                }

                lastMouseDrag = mouseEvent;
            }
            else if ( eventType == MouseEvent.EVENT_MOUSE_RELEASED )
            {
                lastMousePress = null;
                lastMouseDrag = null;
            }
        }
    }
}