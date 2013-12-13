package ca.szc.physics.nbodygravitation.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSpinner;

import ca.szc.physics.nbodygravitation.model.Universe;

public class SimulateButtonListener
    implements ActionListener
{
    private final JSpinner numBodiesSpinner; // TODO need to make a custom populator to use this?

    private final DrawPanel drawPanel;

    private Thread simulationThread;

    public SimulateButtonListener( JSpinner numBodiesSpinner, DrawPanel drawPanel )
    {
        this.numBodiesSpinner = numBodiesSpinner;
        this.drawPanel = drawPanel;
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        if ( "simulate".equals( e.getActionCommand() ) )
        {
            Universe universe = new Universe( GuiModelExecutor.class );

            GuiModelExecutor executor = (GuiModelExecutor) universe.getExecutor();
            executor.setDrawPanel( drawPanel );

            killSimulationThread();
            simulationThread = new Thread( executor );
            simulationThread.setDaemon( true );
            simulationThread.start();
        }
        else if ( "stop".equals( e.getActionCommand() ) )
        {
            killSimulationThread();
        }
    }

    private void killSimulationThread()
    {
        if ( simulationThread != null )
        {
            simulationThread.interrupt();
            try
            {
                simulationThread.join( 250 );
            }
            catch ( InterruptedException e )
            {
            }
        }
    }
}
