import java.awt.Graphics2D;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Arbre extends Objet
{    
    MinecosysPanel _world;
    boolean cut;
    boolean feu;
    boolean cible;
    int     treeHeight;
    int     tempsFeu = 0;
    static int maxTempsFeu = 2;

    Image arbre_taille1, arbre_taille2, arbre_taille3, arbre_taille4_haut, arbre_taille4_bas, buche;
    Image arbre_taille1N, arbre_taille2N, arbre_taille3N, arbre_taille4_hautN, arbre_taille4_basN, bucheN;
    Image arbre_taille1Fa, arbre_taille1Fb, arbre_taille2Fa, arbre_taille2Fb, arbre_taille3Fa, arbre_taille3Fb, arbre_taille4_hautF, arbre_taille4_basF, cendre;
    
    public Arbre( int posx, int posy, MinecosysPanel _world )
    {
        this._world = _world;
        this.cut    = false;
        this.posx   = posx;
        this.posy   = posy; 
        this.feu    = false;
        this.cible  = false;
        treeHeight  = (int)(Math.random()*(4-1+1)+1);

        arbreImage();
    }

    public Arbre( int posx, int posy, MinecosysPanel _world , int treeHeight)
    {
        this._world = _world;
        this.cut    = false;
        this.posx   = posx;
        this.posy   = posy; 
        treeHeight  = treeHeight;

        arbreImage();
    }

    public void cuting()
    {
        this.cut = true;
        treeHeight = -1; //arbre couper
    }

    public void step()
    {
        if( Math.random() < 0.05 )
            feu = true;
        if( treeHeight == -1 )
        {}
        else if( treeHeight == 0 ) //arbre pas encore planter
        {}
        else if ( 1 <= treeHeight && treeHeight < 4 ) //pousse d'arbre 
        {
            if ( feu == true )
            {
                tempsFeu++;
                if (tempsFeu >= maxTempsFeu)
                {
                    treeHeight = -2; //arbre en cendre
                    feu = false;
                }
            }
            else
                treeHeight++;
        }
        else if ( treeHeight == 4 ) // arbre taille max
        {
            if ( feu == true )
            {
                tempsFeu++;
                if (tempsFeu >= maxTempsFeu)
                {
                    treeHeight = -2; //arbre en cendre
                    feu = false;
                }
            }
        }
    }

    public void getOnFire()
    {
        for (Arbre tree : _world.arbresX){
            if ( feu == false && (posx > _world.sizeImage && posx < (_world.maxScreenCol*_world.sizeImage - _world.sizeImage)))
            {
                if ((posy > _world.sizeImage && posy < (_world.maxScreenRow*_world.sizeImage - _world.sizeImage)))
                {
                    if (tree.posx == (posx - _world.sizeImage) && tree.posy == posy && tree.feu == true)
                        feu = true;
                    else if (tree.posx == (posx + _world.sizeImage) && tree.posy == posy && tree.feu == true)
                        feu = true;
                    else if (tree.posy == (posy + _world.sizeImage) && tree.posx == posx && tree.feu == true)
                        feu = true;
                    else if (tree.posy == (posy - _world.sizeImage) && tree.posx == posx && tree.feu == true)
                        feu = true;
                }
            }
        }
    }

    public void display(Graphics2D g2)
    {
        Image img = null;

        if ( feu )
        {
            switch( treeHeight )
            {
                case -1:
                    img = buche;
                    break;
                case 1: //dos
                    if ( _world.numeroMov == 1)
                        img = arbre_taille1Fa;
                    if ( _world.numeroMov == 2)
                        img = arbre_taille1Fb;
                    break;
                case 2: //droit
                    if ( _world.numeroMov == 1)
                        img = arbre_taille2Fa;
                    if ( _world.numeroMov == 2)
                        img = arbre_taille2Fb;
                    break;
                case 3: //face
                    if ( _world.numeroMov == 1)
                        img = arbre_taille3Fa;
                    if ( _world.numeroMov == 2)
                        img = arbre_taille3Fb;
                    break;
                case 4: //gauche
                    img = arbre_taille4_basF;
                    break;
            }

            if (img == arbre_taille4_basF)
            {
                if ( feu )
                    g2.drawImage(arbre_taille4_hautF, posx, posy - _world.sizeImage , _world.sizeImage, _world.sizeImage, null);
                else
                    g2.drawImage(arbre_taille4_haut, posx, posy - _world.sizeImage , _world.sizeImage, _world.sizeImage, null);
            }
        }
        else
        {
            switch( treeHeight )
            {
                case -2: //cendre
                    img = cendre;
                    break;
                case -1:
                    if ( _world.night )
                        img = buche;
                    else
                        img = bucheN;
                case 1:
                    if ( _world.night )
                        img = arbre_taille1N;
                    else 
                        img = arbre_taille1;
                    break;
                case 2: 
                    if ( _world.night )
                        img = arbre_taille2N;
                    else
                        img = arbre_taille2;
                    break;
                case 3: 
                    if ( _world.night )
                        img = arbre_taille3N;
                    else
                        img = arbre_taille3;
                    break;
                case 4: 
                    if ( _world.night )
                        img = arbre_taille4_basN;
                    else
                        img = arbre_taille4_bas;
                    break;
            }

            if (img == arbre_taille4_bas || img == arbre_taille4_basN)
            {
                if ( _world.night )
                    g2.drawImage(arbre_taille4_hautN, posx, posy - _world.sizeImage , _world.sizeImage, _world.sizeImage, null);
                else
                    g2.drawImage(arbre_taille4_haut, posx, posy - _world.sizeImage , _world.sizeImage, _world.sizeImage, null);
            }
        }
        
        g2.drawImage(img, posx, posy, _world.sizeImage, _world.sizeImage, null);
    }
    
    public void arbreImage()
    {
        try 
        {
            arbre_taille1 = ImageIO.read(new File("decors/jour/arbre_taille1.png"));
            arbre_taille2 = ImageIO.read(new File("decors/jour/arbre_taille2.png"));
            arbre_taille3 = ImageIO.read(new File("decors/jour/arbre_taille3.png"));
            arbre_taille4_bas  = ImageIO.read(new File("decors/jour/arbre_taille4_bas.png"));
            arbre_taille4_haut = ImageIO.read(new File("decors/jour/arbre_taille4_haut.png"));

            arbre_taille1N = ImageIO.read(new File("decors/nuit/arbre_taille1.png"));
            arbre_taille2N = ImageIO.read(new File("decors/nuit/arbre_taille2.png"));
            arbre_taille3N = ImageIO.read(new File("decors/nuit/arbre_taille3.png"));
            arbre_taille4_basN  = ImageIO.read(new File("decors/nuit/arbre_taille4_bas.png"));
            arbre_taille4_hautN = ImageIO.read(new File("decors/nuit/arbre_taille4_haut.png"));

            arbre_taille1Fa = ImageIO.read(new File("decors/incendie/arbre_taille1a.png"));
            arbre_taille2Fa = ImageIO.read(new File("decors/incendie/arbre_taille2a.png"));
            arbre_taille3Fa = ImageIO.read(new File("decors/incendie/arbre_taille3a.png"));
            arbre_taille1Fb = ImageIO.read(new File("decors/incendie/arbre_taille1b.png"));
            arbre_taille2Fb = ImageIO.read(new File("decors/incendie/arbre_taille2b.png"));
            arbre_taille3Fb = ImageIO.read(new File("decors/incendie/arbre_taille3b.png"));
            arbre_taille4_basF  = ImageIO.read(new File("decors/incendie/arbre_taille4_bas.png"));
            arbre_taille4_hautF = ImageIO.read(new File("decors/incendie/arbre_taille4_haut.png"));
            
            buche = ImageIO.read(new File("decors/jour/buche.png"));
            bucheN = ImageIO.read(new File("decors/nuit/buche.png"));
            cendre = ImageIO.read(new File("decors/incendie/cendre.png"));
        } 
        catch ( Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
