import java.util.ArrayList;
import java.util.List;


public class AstarSearchtest {



    


    public static Point astarsearch(Point depart, Point finale,MinecosysPanel _world){
        ArrayList<Point> openlist = new ArrayList<Point>();
        ArrayList<Point> closelist = new ArrayList<Point>();
        Point nullPoint = new Point(600,600);

        openlist.add(depart);//commencer par le point de depart
        while(openlist.size()>0){
            Point pointcurr=findMinCostPoint(openlist);//chercher le point avec le cout total le plus petit
            openlist.remove(pointcurr);
            closelist.add(pointcurr);
            
            List<Point> neighbors=findneighbors(pointcurr,openlist,closelist,_world);//ajouter les voisins du point courant dans la openlist
            
            for(Point p:neighbors){
                p.initialpoint(pointcurr,finale);//initialiser le point
                openlist.add(p);
            }
            
            for(Point p:openlist){
                if(p.getx()==finale.getx()&&p.gety()==finale.gety()){
                    return p;
                }
            }    
        }
        System.out.println(openlist);
        System.out.println(closelist);
        
        System.out.println("pas de chemin");
        return nullPoint;//tourjours pas trouver le point d'arrive
    }

    private static Point findMinCostPoint(ArrayList<Point> openList){
        Point min= openList.get(0);
        for(Point p:openList){
            if(p.gettotalcost()<min.gettotalcost()){
                min=p;
            }
        }

        return min;
    }

    private static ArrayList<Point> findneighbors(Point p,List<Point> opeList,List<Point>closeList,MinecosysPanel _world){
        ArrayList<Point> points=new ArrayList<Point>();
        

        if(validation(p.getx()-1,p.gety(),opeList,closeList,_world)){//point voisin valide gauche
            points.add(new Point(p.getx()-1,p.gety()));
        }
        if(validation(p.getx()+1,p.gety(),opeList,closeList,_world)){//point voisin valide droite
            points.add(new Point(p.getx()+1,p.gety()));
        }
        if(validation(p.getx(),p.gety()-1,opeList,closeList,_world)){//point voisin valide haut
            points.add(new Point(p.getx(),p.gety()-1));
        }
        if(validation(p.getx(),p.gety()+1,opeList,closeList,_world)){//point voisin valide bas
            points.add(new Point(p.getx(),p.gety()+1));
        }
        return points;
    }

    private static boolean existePoint(List<Point>p, int x,int y){
        for(Point point:p){
            if(point.getx()==x&&point.gety()==y){
                return true;
            }
        }
        return false;

    }

    private static  boolean validation(int x,int y, List<Point>opeList,List<Point>closeList,MinecosysPanel _world){//tester si le(x,y) est un point possible d'arriver
        if(x<0||x>=_world.imaM.mapImageNum.length||y<0||y>=_world.imaM.mapImageNum[0].length){//point voisin hors de la map
            return false;
        }

        if(_world.imaM.mapImageNum[x][y]==1){//point voisin est un mur  
            return false;
        }
        //pas finir ici !!!!! FAIRE ATTENTION!!!!!!!!!!!!

        if(existePoint(opeList,x,y)||existePoint(closeList,x,y)){//point voisin est deja dans la openlist ou la closelist
            return false;
        }
        return true;
    }

    public static  ArrayList<Point> Path(Point start, Point end,MinecosysPanel _world) {//retourner le chemin && PAS FINIR !!!!! 
        ArrayList<Point> path = new ArrayList<Point>();
        Point resultat=astarsearch(start, end,_world);
        while(resultat!=null){
            path.add(new Point(resultat.getx(),resultat.gety()));
            resultat=resultat.getparent();

        }
        return path;
    }

}
