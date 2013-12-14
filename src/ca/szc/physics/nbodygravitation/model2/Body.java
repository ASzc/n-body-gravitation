package ca.szc.physics.nbodygravitation.model2;

public class Body
{
    private final Double mass;

    private final TwoDimValue<Double> position;

    private final Universe universe;

    private final TwoDimValue<Double> velocity;

    public Body( Universe universe, Double mass, TwoDimValue<Double> position, TwoDimValue<Double> velocity )
    {
        this.mass = mass;
        this.position = position;
        this.universe = universe;
        this.velocity = velocity;
    }
}
