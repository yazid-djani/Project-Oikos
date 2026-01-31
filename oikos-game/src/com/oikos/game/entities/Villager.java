public class Villager extends Agent
{
    //Pour le vieillissement et la faim
    public final int max_niveau_faim   = 100;
    public final int majorite   = 18;
    public final int max_proche = 5;
    
    GameLoop _world;
    String  metier;
    String  sexe;
    boolean contaminer;
    int     niveau_faim;
    int     proche;
    
    public Villager(GameLoop _world )
    {
        this._world     = _world;
        this.contaminer = false;

        // definition du sexe de maniere aleatoire( 1/2 chance )
        if (Math.random() < 0.5)
            this.sexe = "femme";
        else
            this.sexe = "homme";
        
        defautSettings();
    }

    public void defautSettings()
    {
        //zone de spawn des villageois
        posx = (int)(Math.random() * ( 704 - 416 ) + 416);
        posy = (int)(Math.random() * ( 576 - 352 ) + 352);
    }

    public void step_deplacement_alea() // Deplacement aleatoire
    {
        jourAvantAnniversaire++;
        if ( Math.random() > 0.5 ){  
            _orient = (_orient+1) %4;
        }
        else if ( Math.random() <= 0.5 ){
            _orient = (_orient-1+4) %4;
        }
        
        collisionON = false;
        _world.colTesteur.verificationImage(this); // on verifie les collisions

        // si collisionON == false alors le villageois peut avancer 
        if ( collisionON == false )
        {
            if ( posx > (speed * 2) && posx < (_world.maxScreenCol*_world.sizeImage - (speed * 2)) && posy > (speed * 2) && posy < (_world.maxScreenRow*_world.sizeImage - (speed * 2)) )
            {
                switch ( _orient ) 
                {
                    case 0: // nord	
                        posy -= speed;
                        break;
                    case 1:	// est
                        posx += speed;
                        break;
                    case 2:	// sud
                        posy += speed;
                        break;
                    case 3:	// ouest
                        posx -= speed;
                        break;
                }
            }
        }
    }

    public void setContaminer()
    {
        this.contaminer = true;
    }

    public boolean getContaminer()
    {
        return this.contaminer;
    }

    public void setproche()
    {
        this.proche++;
    }

    /* public void step_avancementFaim()
    {
        if( niveau_faim >= max_niveau_faim )
            mort();
        else 
            niveau_faim ++;
    } */

    public void step() {}
}
