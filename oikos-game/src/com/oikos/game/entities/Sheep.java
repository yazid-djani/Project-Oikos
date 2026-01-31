public class Mouton extends Animaux 
{
    public Mouton( GameLoop world) {
            super(world);
    }

    public void step()
    {
        if(alive==true){
            step_deplacement_alea();
        }
    }
}
