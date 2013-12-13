package ca.szc.physics.nbodygravitation.gui;

import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

import ca.szc.physics.nbodygravitation.model.Body;
import ca.szc.physics.nbodygravitation.model.executor.ModelExecutor;
import ca.szc.physics.nbodygravitation.model.method.ModelMethod;
import ca.szc.physics.nbodygravitation.util.Pair;

public class GuiModelExecutor
    extends ModelExecutor
{
    private DrawPanel drawPanel;

    public GuiModelExecutor( List<Body> bodies, ModelMethod method )
    {
        super( bodies, method );
    }

    public void setDrawPanel( DrawPanel drawPanel )
    {
        this.drawPanel = drawPanel;
    }

    @Override
    public void run()
    {
        // The farthest out body will define the square area scaling for the drawPanel
        Body mostDistantFromOrigin = null;
        double greatestDistanceFromOrigin = 0.0d;
        for ( Body body : bodies )
        {
            double x = body.getPosition()[0];
            double y = body.getPosition()[1];
            double distance = Math.sqrt( Math.pow( x, 2 ) + Math.pow( y, 2 ) );
            if ( distance > greatestDistanceFromOrigin )
            {
                mostDistantFromOrigin = body;
                greatestDistanceFromOrigin = distance;
            }
        }
        double bigLeg = Math.max( mostDistantFromOrigin.getPosition()[0], mostDistantFromOrigin.getPosition()[1] );
        // Scaling will be performed for a square of side-length bigLeg * 2, centred on the origin

        while ( !Thread.interrupted() )
        {
            // Draw all the bodies inside the drawPanel
            Rectangle bounds = drawPanel.getBounds();

            List<Pair<Integer>> points = new LinkedList<>();
            for ( Body body : bodies )
            {
                // Perform scaling and translation to fit the Cartesian position coordinates into ones that fit the
                // right/down (left/top) coordinates of the panel
                double x = body.getPosition()[0] / bigLeg * ( bounds.width / 2 ) + ( bounds.width / 2 );
                double y = body.getPosition()[1] / bigLeg * ( bounds.height / 2 ) + ( bounds.height / 2 );

                points.add( new Pair<Integer>( (int) Math.round( x ), (int) Math.round( y ) ) );
            }

            // Enqueue a repaint for the panel
            drawPanel.repaint();

            // Run the model for one step
            method.run();
        }
    }
}
