import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Agent
{
    MinecosysPanel _world;
    public int     posx, posy;
    public final int max_age    = 80;
	boolean        alive;
    int            age;
    int            speed = 6;
    int 	       _orient;
    int            anniversaire = 50;
    int            jourAvantAnniversaire = 0;
    
    //Pour les collisions
    public Rectangle  zoneSolide;
    boolean           collisionON = false;

    public Agent() 
    {
        _orient = 0;
        alive   = true;

        // zone de collision avec les objets de l'environnement
        zoneSolide = new Rectangle(); // x,y,largueur, hauteur
        zoneSolide.x = 10;
        zoneSolide.y = 18;
        zoneSolide.width = 11;
        zoneSolide.height = 12; 
    }

    public void step_vieillir()
    {
        if ( age >= max_age )
            mort();
        else if (jourAvantAnniversaire == anniversaire) {
            age++;
            jourAvantAnniversaire = 0;
        }
    }

    public int getX() {
        return posx;
    }

    public int getY() {
        return posy;
    }

    public void mort() {
        alive = false;
    }
    
    public abstract void step();

}
