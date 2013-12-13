package ca.szc.physics.nbodygravitation.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
        JSpinner spinner = new JSpinner();
        spinner.setModel( new SpinnerNumberModel( 8, 2, 100, 1 ) );
        frame.add( spinner, constraints );

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        JButton button = new JButton();
        button.setText( "Simulate" );
        frame.add( button, constraints );

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.fill = GridBagConstraints.BOTH;
        JPanel drawPanel = new JPanel();
        drawPanel.setBackground( Color.BLACK );
        drawPanel.setPreferredSize( new Dimension( 400, 300 ) );
        frame.add( drawPanel, constraints );

        frame.pack();
        frame.setVisible( true );
    }
}
