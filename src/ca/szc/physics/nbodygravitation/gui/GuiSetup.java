package ca.szc.physics.nbodygravitation.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class GuiSetup
    implements Runnable
{
    @Override
    public void run()
    {
        JFrame frame = new JFrame( "n-body-gravitiation" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setLayout( new GridBagLayout() );

        GridBagConstraints constraints;

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        JSpinner numBodiesSpinner = new JSpinner();
        numBodiesSpinner.setModel( new SpinnerNumberModel( 8, 2, 100, 1 ) );
        frame.add( numBodiesSpinner, constraints );

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        JButton simulateButton = new JButton();
        simulateButton.setText( "Simulate" );
        simulateButton.setActionCommand( "simulate" );
        frame.add( simulateButton, constraints );

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.fill = GridBagConstraints.BOTH;
        DrawPanel drawPanel = new DrawPanel();
        drawPanel.setBackground( Color.BLACK );
        drawPanel.setPreferredSize( new Dimension( 400, 300 ) );
        frame.add( drawPanel, constraints );

        simulateButton.addActionListener( new SimulateButtonListener( numBodiesSpinner, drawPanel ) );

        frame.pack();
        frame.setVisible( true );
    }
}
