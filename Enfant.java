import java.awt.Graphics2D;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Enfant extends Villageois 
{
    int     lequel;
    Image   bilal_droit_mov1, bilal_droit_mov2, bilal_gauche_mov1, bilal_gauche_mov2, bilal_dos_mov1, bilal_dos_mov2, bilal_face_mov1, bilal_face_mov2;
    Image   justine_droit_mov1, justine_droit_mov2, justine_gauche_mov1, justine_gauche_mov2, justine_dos_mov1, justine_dos_mov2, justine_face_mov1, justine_face_mov2;
    Image   louis_droit_mov1, louis_droit_mov2, louis_gauche_mov1, louis_gauche_mov2, louis_dos_mov1, louis_dos_mov2, louis_face_mov1, louis_face_mov2;
    Image   rose_droit_mov1, rose_droit_mov2, rose_gauche_mov1, rose_gauche_mov2, rose_dos_mov1, rose_dos_mov2, rose_face_mov1, rose_face_mov2;
    Image   theaud_droit_mov1, theaud_droit_mov2, theaud_gauche_mov1, theaud_gauche_mov2, theaud_dos_mov1, theaud_dos_mov2, theaud_face_mov1, theaud_face_mov2;
    Image   clara_droit_mov1, clara_droit_mov2, clara_gauche_mov1, clara_gauche_mov2, clara_dos_mov1, clara_dos_mov2, clara_face_mov1, clara_face_mov2;   

    public Enfant( MinecosysPanel _world ) {
        //sexe defini de manière aleatoire
        //position de base déclarer dans villageois de manière random sur une zone donnée
        super(_world);
        metier  =  "enfant";

        //definition du sexe 
        if ( sexe == "homme" )
            lequel = (int)(Math.random()*(3-1+1)+1);
        else if ( sexe == "femme" )
            lequel = (int)(Math.random()*(6-4+1)+4);
        
        enfantImage();
    }

    public void enfantImage()
    {
        try 
        {
            bilal_droit_mov1 = ImageIO.read(new File("personnage/enfant/bilal/bilal_droit_mov1.png"));
            bilal_droit_mov2 = ImageIO.read(new File("personnage/enfant/bilal/bilal_droit_mov2.png"));
            bilal_gauche_mov1 = ImageIO.read(new File("personnage/enfant/bilal/bilal_gauche_mov1.png"));
            bilal_gauche_mov2 = ImageIO.read(new File("personnage/enfant/bilal/bilal_gauche_mov2.png"));
            bilal_dos_mov1 = ImageIO.read(new File("personnage/enfant/bilal/bilal_dos_mov1.png"));
            bilal_dos_mov2 = ImageIO.read(new File("personnage/enfant/bilal/bilal_dos_mov2.png"));
            bilal_face_mov1 = ImageIO.read(new File("personnage/enfant/bilal/bilal_face_mov1.png"));
            bilal_face_mov2 = ImageIO.read(new File("personnage/enfant/bilal/bilal_face_mov2.png"));

            justine_droit_mov1 = ImageIO.read(new File("personnage/enfant/justine/justine_droit_mov1.png"));
            justine_droit_mov2 = ImageIO.read(new File("personnage/enfant/justine/justine_droit_mov2.png"));
            justine_gauche_mov1 = ImageIO.read(new File("personnage/enfant/justine/justine_gauche_mov1.png"));
            justine_gauche_mov2 = ImageIO.read(new File("personnage/enfant/justine/justine_gauche_mov2.png"));
            justine_dos_mov1 = ImageIO.read(new File("personnage/enfant/justine/justine_dos_mov1.png"));
            justine_dos_mov2 = ImageIO.read(new File("personnage/enfant/justine/justine_dos_mov2.png"));
            justine_face_mov1 = ImageIO.read(new File("personnage/enfant/justine/justine_face_mov1.png"));
            justine_face_mov2 = ImageIO.read(new File("personnage/enfant/justine/justine_face_mov2.png"));

            louis_droit_mov1 = ImageIO.read(new File("personnage/enfant/louis/louis_droit_mov1.png"));
            louis_droit_mov2 = ImageIO.read(new File("personnage/enfant/louis/louis_droit_mov2.png"));
            louis_gauche_mov1 = ImageIO.read(new File("personnage/enfant/louis/louis_gauche_mov1.png"));
            louis_gauche_mov2 = ImageIO.read(new File("personnage/enfant/louis/louis_gauche_mov2.png"));
            louis_dos_mov1 = ImageIO.read(new File("personnage/enfant/louis/louis_dos_mov1.png"));
            louis_dos_mov2 = ImageIO.read(new File("personnage/enfant/louis/louis_dos_mov2.png"));
            louis_face_mov1 = ImageIO.read(new File("personnage/enfant/louis/louis_face_mov1.png"));
            louis_face_mov2 = ImageIO.read(new File("personnage/enfant/louis/louis_face_mov2.png"));

            rose_droit_mov1 = ImageIO.read(new File("personnage/enfant/rose/rose_droit_mov1.png"));
            rose_droit_mov2 = ImageIO.read(new File("personnage/enfant/rose/rose_droit_mov2.png"));
            rose_gauche_mov1 = ImageIO.read(new File("personnage/enfant/rose/rose_gauche_mov1.png"));
            rose_gauche_mov2 = ImageIO.read(new File("personnage/enfant/rose/rose_gauche_mov2.png"));
            rose_dos_mov1 = ImageIO.read(new File("personnage/enfant/rose/rose_dos_mov1.png"));
            rose_dos_mov2 = ImageIO.read(new File("personnage/enfant/rose/rose_dos_mov2.png"));
            rose_face_mov1 = ImageIO.read(new File("personnage/enfant/rose/rose_face_mov1.png"));
            rose_face_mov2 = ImageIO.read(new File("personnage/enfant/rose/rose_face_mov2.png"));

            clara_droit_mov1 = ImageIO.read(new File("personnage/enfant/clara/clara_droit_mov1.png"));
            clara_droit_mov2 = ImageIO.read(new File("personnage/enfant/clara/clara_droit_mov2.png"));
            clara_gauche_mov1 = ImageIO.read(new File("personnage/enfant/clara/clara_gauche_mov1.png"));
            clara_gauche_mov2 = ImageIO.read(new File("personnage/enfant/clara/clara_gauche_mov2.png"));
            clara_dos_mov1 = ImageIO.read(new File("personnage/enfant/clara/clara_dos_mov1.png"));
            clara_dos_mov2 = ImageIO.read(new File("personnage/enfant/clara/clara_dos_mov2.png"));
            clara_face_mov1 = ImageIO.read(new File("personnage/enfant/clara/clara_face_mov1.png"));
            clara_face_mov2 = ImageIO.read(new File("personnage/enfant/clara/clara_face_mov2.png"));

            clara_droit_mov1 = ImageIO.read(new File("personnage/enfant/clara/clara_droit_mov1.png"));
            clara_droit_mov2 = ImageIO.read(new File("personnage/enfant/clara/clara_droit_mov2.png"));
            clara_gauche_mov1 = ImageIO.read(new File("personnage/enfant/clara/clara_gauche_mov1.png"));
            clara_gauche_mov2 = ImageIO.read(new File("personnage/enfant/clara/clara_gauche_mov2.png"));
            clara_dos_mov1 = ImageIO.read(new File("personnage/enfant/clara/clara_dos_mov1.png"));
            clara_dos_mov2 = ImageIO.read(new File("personnage/enfant/clara/clara_dos_mov2.png"));
            clara_face_mov1 = ImageIO.read(new File("personnage/enfant/clara/clara_face_mov1.png"));
            clara_face_mov2 = ImageIO.read(new File("personnage/enfant/clara/clara_face_mov2.png"));
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
                if ( _world.numeroMov == 1)
                    switch( lequel )
                    {
                        case 1: image = bilal_dos_mov1; break;
                        case 2: image = theaud_dos_mov1; break; 
                        case 3: image = louis_dos_mov1; break;
                        case 4: image = justine_dos_mov1; break;
                        case 5: image = rose_dos_mov1; break;
                        case 6: image = clara_dos_mov1; break;
                    }
                if ( _world.numeroMov == 2)
                    switch( lequel )
                    {
                        case 1: image = bilal_dos_mov2; break;
                        case 2: image = theaud_dos_mov2; break;
                        case 3: image = louis_dos_mov2; break;
                        case 4: image = justine_dos_mov2; break;
                        case 5: image = rose_dos_mov2; break;
                        case 6: image = clara_dos_mov2; break;
                    }
                break;
            case 1: //droit
                if ( _world.numeroMov == 1)
                    switch( lequel )
                    {
                        case 1: image = bilal_droit_mov1; break;
                        case 2: image = theaud_droit_mov1; break;
                        case 3: image = louis_droit_mov1; break;
                        case 4: image = justine_droit_mov1; break;
                        case 5: image = rose_droit_mov1; break;
                        case 6: image = clara_droit_mov1; break;
                    }
                if ( _world.numeroMov == 2)
                    switch( lequel )
                    {
                        case 1: image = bilal_droit_mov2; break;
                        case 2: image = theaud_droit_mov2; break;
                        case 3: image = louis_droit_mov2; break;
                        case 4: image = justine_droit_mov2; break;
                        case 5: image = rose_droit_mov2; break;
                        case 6: image = clara_droit_mov2; break;
                    }
                break;
            case 2: //face
                if ( _world.numeroMov == 1)
                    switch( lequel )
                    {
                        case 1: image = bilal_face_mov1; break;
                        case 2: image = theaud_face_mov1; break;
                        case 3: image = louis_face_mov1; break;
                        case 4: image = justine_face_mov1; break;
                        case 5: image = rose_face_mov1; break;
                        case 6: image = clara_face_mov1; break;
                    }
                if ( _world.numeroMov == 2)
                    switch( lequel )
                    {
                        case 1: image = bilal_face_mov2; break;
                        case 2: image = theaud_face_mov2; break;
                        case 3: image = louis_face_mov2; break;
                        case 4: image = justine_face_mov2; break;
                        case 5: image = rose_face_mov2; break;
                        case 6: image = clara_face_mov2; break;
                    }
                break;
            case 3: //gauche
                if ( _world.numeroMov == 1)
                    switch( lequel )
                    {
                        case 1: image = bilal_gauche_mov1; break;
                        case 2: image = theaud_gauche_mov1; break;
                        case 3: image = louis_gauche_mov1; break;
                        case 4: image = justine_gauche_mov1; break;
                        case 5: image = rose_gauche_mov1; break;
                        case 6: image = clara_gauche_mov1; break;
                    }
                if ( _world.numeroMov == 2)
                    switch( lequel )
                    {
                        case 1: image = bilal_gauche_mov2; break;
                        case 2: image = theaud_gauche_mov2; break;
                        case 3: image = louis_gauche_mov2; break;
                        case 4: image = justine_gauche_mov2; break;
                        case 5: image = rose_gauche_mov2; break;
                        case 6: image = clara_gauche_mov2; break;
                    }
                break;
        }
        g2.drawImage(image, posx, posy, _world.sizeImage, _world.sizeImage, null);
    }

    public void step() {
        if ( _world.nombreJour % anniversaire == 0)
            step_vieillir();
        
        step_deplacement_alea();
    }
}
