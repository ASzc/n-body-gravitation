package ca.szc.physics.nbodygravitation.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import ca.szc.physics.nbodygravitation.util.Pair;

// Created with reference to: http://zetcode.com/gfx/java2d/basicdrawing/

public class DrawPanel
    extends JPanel
{
    private static final long serialVersionUID = 5216407365365072397L;

    private List<Pair<Integer>> points;

    public synchronized void setPoints( List<Pair<Integer>> points )
    {
        this.points = points;
    }

    private synchronized List<Pair<Integer>> getPoints()
    {
        return points;
    }

    private void drawPoints( Graphics2D g )
    {
        g.setColor( Color.ORANGE );

        for ( Pair<Integer> coords : getPoints() )
        {
            g.drawLine( coords.a, coords.b, coords.a, coords.b );
        }
    }

    @Override
    protected void paintComponent( Graphics g )
    {
        super.paintComponent( g );
        drawPoints( (Graphics2D) g );
    }

}
