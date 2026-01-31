import java.awt.Image;
import java.awt.image.BufferedImage;

public abstract class Objet
{
    public int      posx, posy;
    public BufferedImage  draw;
    public boolean  collision;
    
    public Objet() 
    {
        collision = false;
    }

    public abstract void step();
}

