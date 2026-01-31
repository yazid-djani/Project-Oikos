import java.awt.Graphics2D;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;

public class Animaux extends Agent
{
    private final int max_niveau_faim   = 100;   
    private final static int max_age    = 80;
    
    MinecosysPanel _world;
    String  sexe;

    int     niveau_faim;
    int     proche;
    int     age;
    int     anniversaire = 50;
    int     jourAvantAnniversaire = 0;
    int     speed = 6;

    
    public Animaux( MinecosysPanel _world )
    {
        this._world     = _world;

        // definition du sexe de maniere aleatoire( 1/2 chance)
        if (Math.random() < 0.5)
            this.sexe = "femme";
        else
            this.sexe = "homme";
        
        defautSettings();
    }

    public void defautSettings()
    {
        posx = (int)(Math.random() * ( 1600 - 1344 ) + 1344);
        posy = (int)(Math.random() * ( 384 - 192 ) + 192);
    }

    public void step_deplacement_alea() // Deplacement aleatoire
    {

        // si collisionON == false alors le villageois peut avancer 
        if ( collisionON == false )
        {
            if ( posx > (speed * 2) && posx < (_world.maxScreenCol*_world.sizeImage - (speed * 2)) && posy > (speed * 2) && posy < (_world.maxScreenRow*_world.sizeImage - (speed * 2)) )
            {
                switch ( _orient ) 
                {
                    case 0: // nord	
                        posy -= speed;
                        break;
                    case 1:	// est
                        posx += speed;
                        break;
                    case 2:	// sud
                        posy += speed;
                        break;
                    case 3:	// ouest
                        posx -= speed;
                        break;
                }
            }
        }
    }

    public void setproche()
    {
        this.proche++;
    }


    public void step()
    {
        //step_avancementFaim();
        //step_vieillir();
        step_deplacement_alea();
    }

    /* public void step_vieillir()
    {
        if ( age >= max_age )
            mort();
        else
            age ++;
    } */

    /* public void step_avancementFaim()
    {
        if( niveau_faim >= max_niveau_faim )
            mort();
        else 
            niveau_faim ++;
    } */
}
