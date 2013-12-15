package ca.szc.physics.nbodygravitation.model;

/**
 * Mutable components of a two dimensional value
 * 
 * @param <T> The type of the value (and both its components)
 */
public class TwoDimValue<T>
{
    private T x;

    private T y;

    public TwoDimValue( T x, T y )
    {
        this.x = x;
        this.y = y;
    }

    public T getX()
    {
        return x;
    }

    public T getY()
    {
        return y;
    }

    public void setX( T x )
    {
        this.x = x;
    }

    public void setY( T y )
    {
        this.y = y;
    }
}
