public class Mouton extends Animaux 
{
    public Mouton( MinecosysPanel world) {
            super(world);
    }

    public void step()
    {
        if(alive==true){
            step_deplacement_alea();
        }
    }
}
