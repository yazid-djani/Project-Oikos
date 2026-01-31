import java.awt.Graphics2D;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Fermier extends Villageois
{
    Image   fermiere_droit_mov1, fermiere_droit_mov2;
    Image   fermiere_gauche_mov1, fermiere_gauche_mov2;
    Image   fermiere_dos_mov1, fermiere_dos_mov2;
    Image   fermiere_face_mov1, fermiere_face_mov2;
    
    public Fermier( MinecosysPanel _world)
    {
        super(_world);
        this.age    = (int)(Math.random()*(25 - 18 + 1) + 18);
        this.metier = "fermier";
        this.sexe   = "femme";
        fermierImage();
    }

    public Fermier( MinecosysPanel _world, int posx, int posy , int age)
    {
        super(_world);
        this.posx   = posx;
        this.posy   = posy;
        this.age    = age;
        this.metier = "fermier";
        this.sexe   = "femme";

        fermierImage();
    }

    public void fermierImage()
    {
        try 
        {
            fermiere_droit_mov1 = ImageIO.read(new File("personnage/fermiere/fermiere_droit_mov1.png"));
            fermiere_droit_mov2 = ImageIO.read(new File("personnage/fermiere/fermiere_droit_mov2.png"));

            fermiere_gauche_mov1 = ImageIO.read(new File("personnage/fermiere/fermiere_gauche_mov1.png"));
            fermiere_gauche_mov2 = ImageIO.read(new File("personnage/fermiere/fermiere_gauche_mov2.png"));

            fermiere_dos_mov1 = ImageIO.read(new File("personnage/fermiere/fermiere_dos_mov1.png"));
            fermiere_dos_mov2 = ImageIO.read(new File("personnage/fermiere/fermiere_dos_mov2.png"));

            fermiere_face_mov1 = ImageIO.read(new File("personnage/fermiere/fermiere_face_mov1.png"));
            fermiere_face_mov2 = ImageIO.read(new File("personnage/fermiere/fermiere_face_mov2.png"));
        } 
        catch ( Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void display(Graphics2D g2)
    {
        Image image = null;
        if (sexe == "femme")
        {
            switch( _orient )
            {
                case 0: //dos
                    if ( _world.numeroMov == 1)
                        image = fermiere_dos_mov1;
                    if ( _world.numeroMov == 2)
                        image = fermiere_dos_mov2;
                    break;
                case 1: //droit
                    if ( _world.numeroMov == 1)
                        image = fermiere_droit_mov1;
                    if ( _world.numeroMov == 2)
                        image = fermiere_droit_mov2;
                    break;
                case 2: //face
                    if ( _world.numeroMov == 1)
                        image = fermiere_face_mov1;
                    if ( _world.numeroMov == 2)
                        image = fermiere_face_mov2;
                    break;
                case 3: //gauche
                    if ( _world.numeroMov == 1)
                        image = fermiere_gauche_mov1;
                    if ( _world.numeroMov == 2)
                        image = fermiere_gauche_mov2;
                    break;
            }
        }
        g2.drawImage(image, posx, posy, _world.sizeImage, _world.sizeImage, null);
    }

    public void step(){
        if ( _world.nombreJour % anniversaire == 0)
            step_vieillir();
        
        step_deplacement_alea();
    }
    
}
