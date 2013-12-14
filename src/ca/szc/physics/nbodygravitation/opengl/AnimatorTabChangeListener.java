package ca.szc.physics.nbodygravitation.opengl;

import javax.media.opengl.awt.GLCanvas;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jogamp.opengl.util.FPSAnimator;

/**
 * Start/stop canvas animator with tab changes
 */
class AnimatorTabChangeListener
    implements ChangeListener
{
    private final FPSAnimator animator;

    private final int canvasPaneIndex;

    public AnimatorTabChangeListener( GLCanvas glCanvas, int canvasPaneIndex )
    {
        this.canvasPaneIndex = canvasPaneIndex;
        animator = new FPSAnimator( glCanvas, 60 );
        animator.start();
        animator.pause();
    }

    @Override
    public void stateChanged( ChangeEvent e )
    {
        if ( e.getSource() instanceof JTabbedPane )
        {
            JTabbedPane pane = (JTabbedPane) e.getSource();
            if ( pane.getSelectedIndex() == canvasPaneIndex )
            {
                animator.resume();
            }
            else
            {
                animator.pause();
            }
        }
    }
}
