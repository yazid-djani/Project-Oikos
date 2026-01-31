import java.util.ArrayList;
import java.util.List;

public class Chasseur extends Villageois {

    int etape;// 0 1 2
    
    //Loup target;

    ArrayList<Point> path;
    int indice_path=0;
    Maison home;

    double proba_tirer=0.4;

    public Chasseur(MinecosysPanel world) {
        super(world);
        etape=0;
        proche=0;
        //target=null;
        contaminer=false;
        path = new ArrayList<Point>();
    }
    

    public void choisirTarget(Mouton m){
        //target=m;
        etape=1;// trouver un mouton mais pas encore tirer
    }

    void choisirhome(Maison m) {
        this.home= m;
    }

    /*public void step(){
        if(etape==0){//chercher mouton
           step_deplacement_alea();
            for(int i=0;i<_world.moutonX.size();i++){
                if(Math.sqrt(Math.pow(_world.moutonX.get(i).posx-this.posx,2)+Math.pow(_world.moutonX.get(i).posy-this.posy,2))<=50){
                    choisirTarget((Mouton)_world.moutonX.get(i));
                }
            }
        
        }

        else if(etape==1){
            if(Math.random()<proba_tirer){//tirer
                target.mort();
                etape=2;
            }
            if(target.getAlive()==true){//miss shoot ->suivre target
                int x = target.getX()/32;// position x de mouton
                int y = target.getY()/32;//position y de mouton
                Point endPoint = new Point(x, y);
                Point startPoint = new Point(posx/32, posy/32);
                path = AstarSearchtest.Path(startPoint, endPoint, _world);
                
                posx=path.get(0).getx();
                posy=path.get(0).gety();
            }
            else{//target est mort
                etape=2;
            }
        }
        else if(etape==2){
                int x = target.getX()/32;// position x de mouton
                int y = target.getY()/32;//position y de mouton
                Point endPoint = new Point(x, y);
                Point startPoint = new Point(posx/32, posy/32);
                ArrayList<Point> path = AstarSearchtest.Path(startPoint, endPoint, _world);
                etape=3;
                
                posx=path.get(0).getx()*32;
                posy=path.get(0).gety()*32;

                indice_path=path.size()-1;
            
        }
        else if(etape==3){
            if(indice_path>0){
                posx=path.get(indice_path).getx()*32;
                posy=path.get(indice_path).gety()*32;
                indice_path--;
            }
            else{
                posy=target.getY();
                posx=target.getX();
                path.clear();
                indice_path=0;
                etape=4;
            }
        }
        else if(etape==4){
            setproche();
            _world.moutoncurr.add(target);

            if(proche==getmaxproche()){
                etape=5;
            }
            else{
                etape=0;
                target=null;
            }            
        }
        else if(etape==5){
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
                etape=6;
            }
        }
        else if(etape==6){
            if(indice_path>0){
                posx=path.get(indice_path).getx()*32;
                posy=path.get(indice_path).gety()*32;
                indice_path--;
            }
            else{
                posy=home.posy;
                posx=home.posx;
                path.clear();
                indice_path=0;
                etape=7;
            }
        }
        else if(etape==7){
            home.addnourriture(proche);
            etape=0;
            target=null;
        }

        niveau_faim--;
        if(niveau_faim<=0){
            mort();
            _world.chasseurcurr.add(this);
        }

    }
    */
}
