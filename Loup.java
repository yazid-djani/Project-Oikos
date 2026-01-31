import java.awt.Graphics2D;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;

public class Loup extends Animaux 
{
    
    Image   loup_droit, loup_gauche;

    public Loup(MinecosysPanel _world) {
        super(_world);

        // zone de collision avec les objets de l'environnement
        zoneSolide.x = 12;
        zoneSolide.y = 9;
        zoneSolide.width = 17;
        zoneSolide.height = 13; 

        loupImage();
    }

    public void loupImage()
    {
        try 
        {
            loup_gauche = ImageIO.read(new File("personnage/animaux/loup_gauche.png"));
            loup_droit = ImageIO.read(new File("personnage/animaux/loup_droit.png"));
        } 
        catch ( Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void display(Graphics2D g2)
    {
        Image image = null;
        switch( _orient )
        {
            case 0: //dos
                image = loup_droit;
                break;
            case 1: //droit
                image = loup_droit;
                break;
            case 2: //face
                image = loup_gauche;
                break;
            case 3: //gauche
                image = loup_gauche;
                break;
        }
        g2.drawImage(image, posx, posy, _world.sizeImage, _world.sizeImage, null);
    }
    
    public void step() 
    {
        jourAvantAnniversaire++;
        
        if ( Math.random() > 0.5 ){  
            _orient = (_orient+1) %4;
        }
        else if ( Math.random() <= 0.5 ){
            _orient = (_orient-1+4) %4;
        }
        
        collisionON = false;
        _world.colTesteur.verificationImage(this); // on verifie les collisions

        step_deplacement_alea();
    }

        //chercher mouton
}
