import java.awt.Graphics2D;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;

public class Lumberjack extends Villageois
{
    int     etape;              //reussir de couper l'arbre etape chercher/couper/finir 0 1 2
    int     indice_path = 0;    //indice de l'element dans le path pour Astar
    Arbre   target;
    ArrayList<Point> path;      //path pour aller a l'arbre
    AstarSearchtest astar;      //astar pour trouver le chemin

    Image   bucheron_droit_mov1, bucheron_droit_mov2;
    Image   bucheron_gauche_mov1, bucheron_gauche_mov2;
    Image   bucheron_dos_mov1, bucheron_dos_mov2;
    Image   bucheron_face_mov1, bucheron_face_mov2;

    public Lumberjack(GameLoop _world )
    {
        super(_world);
        this.age  = (int)(Math.random()*(25 - 18 + 1) + 18);
        this.sexe = "homme";
        this.metier  =  "bucheron";
        target  =  null;
        etape   =  0;   //pas trouver l'arbre encore
        path    = new ArrayList<Point>();
        bucheronImage();
    }

    public Lumberjack(GameLoop _world, int posx, int posy , int age)
    {
        super(_world);
        this.posx = posx;
        this.posy = posy;
        this.age  = age;
        this.sexe = "homme";
        this.metier  =  "bucheron";
        target  =  null;
        etape   =  0;   //pas trouver l'arbre encore
        path    = new ArrayList<Point>();
        bucheronImage();
    }

    public void choisirTarget(Arbre a) {
        target =  a;
        a.cible=true;
        etape  =  1;// trouver l'arbre mais pas encore couper
    } 

    public void bucheronImage()
    {
        try 
        {
            bucheron_droit_mov1 = ImageIO.read(new File("personnage/bucheron/bucheron_droit_mov1.png"));
            bucheron_droit_mov2 = ImageIO.read(new File("personnage/bucheron/bucheron_droit_mov2.png"));

            bucheron_gauche_mov1 = ImageIO.read(new File("personnage/bucheron/bucheron_gauche_mov1.png"));
            bucheron_gauche_mov2 = ImageIO.read(new File("personnage/bucheron/bucheron_gauche_mov2.png"));

            bucheron_dos_mov1 = ImageIO.read(new File("personnage/bucheron/bucheron_dos_mov1.png"));
            bucheron_dos_mov2 = ImageIO.read(new File("personnage/bucheron/bucheron_dos_mov2.png"));

            bucheron_face_mov1 = ImageIO.read(new File("personnage/bucheron/bucheron_face_mov1.png"));
            bucheron_face_mov2 = ImageIO.read(new File("personnage/bucheron/bucheron_face_mov2.png"));
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
                    image = bucheron_dos_mov1;
                if ( _world.numeroMov == 2)
                    image = bucheron_dos_mov2;
                break;
            case 1: //droit
                if ( _world.numeroMov == 1)
                    image = bucheron_droit_mov1;
                if ( _world.numeroMov == 2)
                    image = bucheron_droit_mov2;
                break;
            case 2: //face
                if ( _world.numeroMov == 1)
                    image = bucheron_face_mov1;
                if ( _world.numeroMov == 2)
                    image = bucheron_face_mov2;
                break;
            case 3: //gauche
                if ( _world.numeroMov == 1)
                    image = bucheron_gauche_mov1;
                if ( _world.numeroMov == 2)
                    image = bucheron_gauche_mov2;
                break;
        }
        g2.drawImage(image, posx, posy, _world.sizeImage, _world.sizeImage, null);
    }

    public void findtree() //rechercher les arbresX en voisinnage de moore d'ordre 3
    {
        for (Arbre tree : _world.arbresX)
            if ( (this.posx > (2 * _world.sizeImage)) && (this.posx < ((_world.maxScreenCol*_world.sizeImage) - (2 * _world.sizeImage))) && (this.posy > (2 * _world.sizeImage)) && (this.posy < ((_world.maxScreenRow*_world.sizeImage) - (2 * _world.sizeImage))) )
            {    
                if ( (tree.posx == this.posx - (_world.sizeImage) || tree.posx == this.posx - (2 * _world.sizeImage)) && (tree.posy == this.posy - (_world.sizeImage) || tree.posy == this.posy - (2 * _world.sizeImage)) )
                    choisirTarget(tree); 
                else if ( (tree.posx == this.posx + (_world.sizeImage) || tree.posx == this.posx + (2 * _world.sizeImage)) && (tree.posy == this.posy - (_world.sizeImage) || tree.posy == this.posy - (2 * _world.sizeImage)) )
                    choisirTarget(tree);
                else if ( (tree.posx == this.posx - (_world.sizeImage) || tree.posx == this.posx - (2 * _world.sizeImage)) && (tree.posy == this.posy + (_world.sizeImage) || tree.posy == this.posy + (2 * _world.sizeImage)) )
                    choisirTarget(tree);
                else if ( (tree.posx == this.posx + (_world.sizeImage) || tree.posx == this.posx + (2 * _world.sizeImage)) && (tree.posy == this.posy + (_world.sizeImage) || tree.posy == this.posy + (2 * _world.sizeImage)) )
                    choisirTarget(tree);
                
                else if ( (tree.posy == this.posy - (_world.sizeImage) || tree.posy == this.posy - (2 * _world.sizeImage)) && tree.posx == this.posx )
                    choisirTarget(tree);
                else if ( (tree.posy == this.posy + (_world.sizeImage) || tree.posy == this.posy + (2 * _world.sizeImage)) && tree.posx == this.posx )
                    choisirTarget(tree);
                else if ( (tree.posx == this.posx - (_world.sizeImage) || tree.posx == this.posx - (2 * _world.sizeImage)) && tree.posy == this.posy )
                    choisirTarget(tree);
                else if ( (tree.posx == this.posx + (_world.sizeImage) || tree.posx == this.posx + (2 * _world.sizeImage)) && tree.posy == this.posy )
                    choisirTarget(tree);
            }
    }

    public void step()
    {
        System.out.println(target==null);
        System.out.println("etape:"+etape);
        if ( _world.nombreJour % anniversaire == 0)
            step_vieillir();
        
        if ( etape == 0 )   //si pas trouver l'arbre encore
        {
            step_deplacement_alea();
            for(int i=0;i<_world.arbresX.size();i++){
                if((_world.arbresX.get(i).treeHeight==4 || _world.arbresX.get(i).treeHeight==4 )&& _world.arbresX.get(i).cible == false){
                    if(Math.sqrt(Math.pow(_world.arbresX.get(i).posx-this.posx,2)+Math.pow(_world.arbresX.get(i).posy-this.posy,2))<500){
                        choisirTarget(_world.arbresX.get(i));
                    }
                }
            }
        }
        else if (etape == 1) //si l'arbre est trouve mais pas encore couper -> commence a suivre l'arbre
        {
            Point startPoint = new Point(this.posx/32, this.posy/32);
            Point endPoint = new Point(target.posx/32, target.posy/32);

            ArrayList<Point> patht = AstarSearchtest.Path(startPoint, endPoint, _world);
            this.path.addAll(patht); 
            patht.clear();
            indice_path = path.size() - 1;
            etape=2;


        }
        else if(etape == 2)
        {
            if(indice_path>0){
                posx=(path.get(indice_path).getx())*32;
                posy=(path.get(indice_path).gety())*32;
                indice_path--;
            }
            
            else if(indice_path==0){
                posx=target.posx;
                posy=target.posy;
                etape=3;
                indice_path=0;
                path.clear();
            }
        }
        else if(etape == 3){

            target.cuting();
            setproche();
            etape=0;
            target=null;
            
        }
        /* else if(etape ==4){
            if(home==null){
                for(int i=0;i<_world.maisonX.size();i++){
                    if(_world.maisonX.get(i).posx==posx && _world.maisonX.get(i).posy==posy){
                        choisirhome(_world.maisonX.get(i));
                    }
                }
            }else{
                int x =(home.posx)/32;
                int y =(home.posy)/32;
                Point endPoint  =  new Point(x, y);
                Point startPoint  =  new Point(home.posx/32, home.posy/32);
                ArrayList<Point>patht = AstarSearchtest.Path(startPoint, endPoint,_world);
                this.path.addAll(patht);
                patht.clear();

                indice_path=path.size()-1;
                etape=5;

            }
        }
        else if(etape == 5){
            if(indice_path > 0){
                posx=(path.get(indice_path).getx())*32;
                posy=(path.get(indice_path).gety())*32;
                indice_path--;
            }
            else if(indice_path==0){
                indice_path=0;
                path.clear();

                home.addarbre(proche);
                proche=0;
            }
            etape=0;
        } */
    }
}
