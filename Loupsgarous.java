import java.util.ArrayList;
import java.util.List;

public class Loupsgarous extends Villageois {

    Villageois target;

    int finir;//situations de loup garou chercher/contaiminer/finir 0 1 2
    ArrayList<Point> path;
    
    public Loupsgarous(MinecosysPanel world) {
        super(world);
        target=null;
        contaminer=true;
        finir = 0;
        target=null;
        path = new ArrayList<Point>();
    }

    public Loupsgarous(int x, int y, MinecosysPanel world) {
        super(world);
        posx=x;
        posy=y;
        target=null;
        contaminer=true;
        finir = 0;
        path = new ArrayList<Point>();
    }

    public void choisirTarget(Villageois v){
        target=v;
        finir=1;
    }
    
    /*public void step(){
        //chercher les villageoisVillageoisX en voisinnage de moore d'ordre 3
        if(finir==0){//si pas trouver le villageoisVillageoisX encore
            step_deplacement_alea();
                for(int i=0; i<_world.villageoisX.size();i++){
                    if(Math.sqrt(Math.pow(_world.villageoisX.get(i).posx-this.posx,2)+Math.pow(_world.villageoisX.get(i).posy-this.posy,2))<=300){
                        choisirTarget((Villageois)_world.villageoisX.get(i));
                    }
                }
            }
        else if(finir==1){
            int x = target.getX()/32;// position x de villageoisVillageoisX
            int y = target.getY()/32;//position y de villageoisVillageoisX
            Point endPoint = new Point(x, y);
            Point startPoint = new Point(posx/32, posy/32);
            this.path = AstarSearchtest.Path(startPoint, endPoint, _world);
            
            if(path.size()>6){
                posx=path.get(5).getx()*32;
                posy=path.get(5).gety()*32;
            }else{
                posx=target.getX();
                posy=target.getY();
                finir=2;
            }
        }

        if(finir==2){
            target.setContaminer();
            Loupsgarous loup=new Loupsgarous(target.getX(),target.getY(),_world);
            _world.loupcurrplus.add(loup);
            _world.villageoiscurr.add(target);
            finir=0;
        }
            

    }*/
}   


