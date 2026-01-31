public class Point {
    private int x;
    private int y;
    private int startcost;
    private int endcost;
    private int totalcost;
    private Point parent;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.startcost = 0;//prix de commencer par le point de depart
        this.endcost = 0;//prix de commencer par le point d'arrive
        this.totalcost = 0;//prix total
        this.parent = null;//noter le dernier point de la route
    }

    public void initialpoint(Point parents, Point end) {
        this.parent = parents;
        this.startcost = parents.startcost+1;
        this.endcost = Math.abs(this.x - end.getx()) + Math.abs(this.y - end.gety());
        this.totalcost = this.startcost + this.endcost;
    }

    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }

    public int getstartcost() {
        return startcost;
    }

    public int getendcost() {
        return endcost;
    }

    public int gettotalcost() {
        return totalcost;
    }

    public Point getparent() {
        return parent;
    }

    

}
