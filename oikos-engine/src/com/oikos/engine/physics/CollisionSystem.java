
public class CollisionManager
{
    MinecosysPanel _world;

    public CollisionManager ( MinecosysPanel _world )
    {
        this._world = _world;
    }

    public void verificationImage(Agent agent)
    {
        //cote des zones solides (ou zones pleines) des agents
        int coteCollisionGaucheX = agent.posx + agent.zoneSolide.x;
        int coteCollisionDroitX = agent.posx + agent.zoneSolide.x + agent.zoneSolide.width;
        int coteCollisionHautY = agent.posy + agent.zoneSolide.y;
        int coteCollisionBasY = agent.posy + agent.zoneSolide.y + agent.zoneSolide.height;

        //emplacement des zones dans la fenetre d'affichage
        int emplacementColGauche = coteCollisionGaucheX / _world.sizeImage;
        int emplacementColDroit = coteCollisionDroitX / _world.sizeImage;
        int emplacementLigHaut = coteCollisionHautY / _world.sizeImage;
        int emplacementLigBas = coteCollisionBasY / _world.sizeImage;

        // Lors du deplacement, seulement 4 orient possibles (haut, bas, gauche, droite)
        // On verifie alors seulement les deux coins de orient en mouvement : par exemple pour le haut on verifie les deux coins de collision du haut ...
        // On utilise alors deux variables pour nous permettre cela 
        int imageNum1, imageNum2;
        
        if ( agent.posx > (agent.speed*2) && agent.posx < (_world.maxScreenCol*_world.sizeImage - (agent.speed*2)) && agent.posy > (agent.speed*2) && agent.posy < (_world.maxScreenRow*_world.sizeImage - (agent.speed*2)) )
        {
            switch ( agent._orient )
            {
                case 0: // haut
                    emplacementLigHaut = ((coteCollisionHautY - agent.speed) / _world.sizeImage) ; //on predit la destination d'arrivé si le cas d'aller en haut est choisi
                    imageNum1 = _world.imaM.mapImageNum[emplacementColGauche][emplacementLigHaut];
                    imageNum2 = _world.imaM.mapImageNum[emplacementColDroit][emplacementLigHaut];
                    if ( _world.imaM.image[imageNum1].collision == true || _world.imaM.image[imageNum2].collision == true ) //si au moin une des deux images a les collisions activer
                        agent.collisionON = true;

                    break;
                case 1:	// droit
                    emplacementColDroit = ((coteCollisionDroitX + agent.speed) / _world.sizeImage) ; //on predit la destination d'arrivé si le cas d'aller en haut est choisi
                    imageNum1 = _world.imaM.mapImageNum[emplacementColDroit][emplacementLigHaut];
                    imageNum2 = _world.imaM.mapImageNum[emplacementColDroit][emplacementLigBas];

                    if ( _world.imaM.image[imageNum1].collision == true || _world.imaM.image[imageNum2].collision == true ) //si au moin une des deux images a les collisions activer
                        agent.collisionON = true;
                        
                    break;
                case 2:	// bas
                    emplacementLigBas = ((coteCollisionBasY + agent.speed) / _world.sizeImage) ; //on predit la destination d'arrivé si le cas d'aller en haut est choisi
                    imageNum1 = _world.imaM.mapImageNum[emplacementColGauche][emplacementLigBas];
                    imageNum2 = _world.imaM.mapImageNum[emplacementColDroit][emplacementLigBas];

                    if ( _world.imaM.image[imageNum1].collision == true || _world.imaM.image[imageNum2].collision == true ) //si au moin une des deux images a les collisions activer
                        agent.collisionON = true;

                    break;
                case 3:	// gauche
                    emplacementColGauche = ((coteCollisionHautY - agent.speed ) / _world.sizeImage); //on predit la destination d'arrivé si le cas d'aller en haut est choisi
                    imageNum1 = _world.imaM.mapImageNum[emplacementColGauche][emplacementLigHaut];
                    imageNum2 = _world.imaM.mapImageNum[emplacementColGauche][emplacementLigBas];

                    if ( _world.imaM.image[imageNum1].collision == true || _world.imaM.image[imageNum2].collision == true ) //si au moin une des deux images a les collisions activer
                        agent.collisionON = true;

                    break;
            }
        }
        else if ( agent.posx > (agent.speed*2) && agent.posx < (_world.maxScreenCol*_world.sizeImage - (agent.speed*2)) && agent.posy >= (_world.maxScreenRow*_world.sizeImage - (agent.speed*2)) )
        {
            switch ( agent._orient )
            {
                case 0: // haut
                    emplacementLigHaut = ((coteCollisionHautY - agent.speed) / _world.sizeImage) ; //on predit la destination d'arrivé si le cas d'aller en haut est choisi
                    imageNum1 = _world.imaM.mapImageNum[emplacementColGauche][emplacementLigHaut];
                    imageNum2 = _world.imaM.mapImageNum[emplacementColDroit][emplacementLigHaut];
                    if ( _world.imaM.image[imageNum1].collision == true || _world.imaM.image[imageNum2].collision == true ) //si au moin une des deux images a les collisions activer
                        agent.collisionON = true;

                    break;
                case 1:	// droit
                    emplacementColDroit = ((coteCollisionDroitX + agent.speed) / _world.sizeImage) ; //on predit la destination d'arrivé si le cas d'aller en haut est choisi
                    imageNum1 = _world.imaM.mapImageNum[emplacementColDroit][emplacementLigHaut];
                    imageNum2 = _world.imaM.mapImageNum[emplacementColDroit][emplacementLigBas];

                    if ( _world.imaM.image[imageNum1].collision == true || _world.imaM.image[imageNum2].collision == true ) //si au moin une des deux images a les collisions activer
                        agent.collisionON = true;
                        
                    break;
                case 3:	// gauche
                    emplacementColGauche = ((coteCollisionHautY - agent.speed ) / _world.sizeImage); //on predit la destination d'arrivé si le cas d'aller en haut est choisi
                    imageNum1 = _world.imaM.mapImageNum[emplacementColGauche][emplacementLigHaut];
                    imageNum2 = _world.imaM.mapImageNum[emplacementColGauche][emplacementLigBas];

                    if ( _world.imaM.image[imageNum1].collision == true || _world.imaM.image[imageNum2].collision == true ) //si au moin une des deux images a les collisions activer
                        agent.collisionON = true;

                    break;
            }
        }
        else if ( agent.posx <= (agent.speed*2) && agent.posy > (agent.speed*2) && agent.posy < (_world.maxScreenRow*_world.sizeImage - (agent.speed*2)) )
        {
            switch ( agent._orient )
            {
                case 0: // haut
                    emplacementLigHaut = ((coteCollisionHautY - agent.speed) / _world.sizeImage) ; //on predit la destination d'arrivé si le cas d'aller en haut est choisi
                    imageNum1 = _world.imaM.mapImageNum[emplacementColGauche][emplacementLigHaut];
                    imageNum2 = _world.imaM.mapImageNum[emplacementColDroit][emplacementLigHaut];
                    if ( _world.imaM.image[imageNum1].collision == true || _world.imaM.image[imageNum2].collision == true ) //si au moin une des deux images a les collisions activer
                        agent.collisionON = true;

                    break;
                case 1:	// droit
                    emplacementColDroit = ((coteCollisionDroitX + agent.speed) / _world.sizeImage) ; //on predit la destination d'arrivé si le cas d'aller en haut est choisi
                    imageNum1 = _world.imaM.mapImageNum[emplacementColDroit][emplacementLigHaut];
                    imageNum2 = _world.imaM.mapImageNum[emplacementColDroit][emplacementLigBas];

                    if ( _world.imaM.image[imageNum1].collision == true || _world.imaM.image[imageNum2].collision == true ) //si au moin une des deux images a les collisions activer
                        agent.collisionON = true;
                        
                    break;
                case 2:	// bas
                    emplacementLigBas = ((coteCollisionBasY + agent.speed) / _world.sizeImage) ; //on predit la destination d'arrivé si le cas d'aller en haut est choisi
                    imageNum1 = _world.imaM.mapImageNum[emplacementColGauche][emplacementLigBas];
                    imageNum2 = _world.imaM.mapImageNum[emplacementColDroit][emplacementLigBas];

                    if ( _world.imaM.image[imageNum1].collision == true || _world.imaM.image[imageNum2].collision == true ) //si au moin une des deux images a les collisions activer
                        agent.collisionON = true;

                    break;
            }
        }
        else if ( agent.posx <= (_world.maxScreenCol*_world.sizeImage - (agent.speed*2)) && agent.posy > (agent.speed*2) && agent.posy < (_world.maxScreenRow*_world.sizeImage - (agent.speed*2)) )
        {
            switch ( agent._orient )
            {
                case 0: // haut
                    emplacementLigHaut = ((coteCollisionHautY - agent.speed) / _world.sizeImage) ; //on predit la destination d'arrivé si le cas d'aller en haut est choisi
                    imageNum1 = _world.imaM.mapImageNum[emplacementColGauche][emplacementLigHaut];
                    imageNum2 = _world.imaM.mapImageNum[emplacementColDroit][emplacementLigHaut];
                    if ( _world.imaM.image[imageNum1].collision == true || _world.imaM.image[imageNum2].collision == true ) //si au moin une des deux images a les collisions activer
                        agent.collisionON = true;

                    break;
                case 2:	// bas
                    emplacementLigBas = ((coteCollisionBasY + agent.speed) / _world.sizeImage) ; //on predit la destination d'arrivé si le cas d'aller en haut est choisi
                    imageNum1 = _world.imaM.mapImageNum[emplacementColGauche][emplacementLigBas];
                    imageNum2 = _world.imaM.mapImageNum[emplacementColDroit][emplacementLigBas];

                    if ( _world.imaM.image[imageNum1].collision == true || _world.imaM.image[imageNum2].collision == true ) //si au moin une des deux images a les collisions activer
                        agent.collisionON = true;

                    break;
                case 3:	// gauche
                    emplacementColGauche = ((coteCollisionHautY - agent.speed ) / _world.sizeImage); //on predit la destination d'arrivé si le cas d'aller en haut est choisi
                    imageNum1 = _world.imaM.mapImageNum[emplacementColGauche][emplacementLigHaut];
                    imageNum2 = _world.imaM.mapImageNum[emplacementColGauche][emplacementLigBas];

                    if ( _world.imaM.image[imageNum1].collision == true || _world.imaM.image[imageNum2].collision == true ) //si au moin une des deux images a les collisions activer
                        agent.collisionON = true;

                    break;
            }
        }
        else if ( agent.posx > (agent.speed*2) && agent.posx < (_world.maxScreenCol*_world.sizeImage - (agent.speed*2)) && agent.posy <= (agent.speed*2))
        {
            switch ( agent._orient )
            {
                case 1:	// droit
                    emplacementColDroit = ((coteCollisionDroitX + agent.speed) / _world.sizeImage) ; //on predit la destination d'arrivé si le cas d'aller en haut est choisi
                    imageNum1 = _world.imaM.mapImageNum[emplacementColDroit][emplacementLigHaut];
                    imageNum2 = _world.imaM.mapImageNum[emplacementColDroit][emplacementLigBas];

                    if ( _world.imaM.image[imageNum1].collision == true || _world.imaM.image[imageNum2].collision == true ) //si au moin une des deux images a les collisions activer
                        agent.collisionON = true;
                        
                    break;
                case 2:	// bas
                    emplacementLigBas = ((coteCollisionBasY + agent.speed) / _world.sizeImage) ; //on predit la destination d'arrivé si le cas d'aller en haut est choisi
                    imageNum1 = _world.imaM.mapImageNum[emplacementColGauche][emplacementLigBas];
                    imageNum2 = _world.imaM.mapImageNum[emplacementColDroit][emplacementLigBas];

                    if ( _world.imaM.image[imageNum1].collision == true || _world.imaM.image[imageNum2].collision == true ) //si au moin une des deux images a les collisions activer
                        agent.collisionON = true;

                    break;
                case 3:	// gauche
                    emplacementColGauche = ((coteCollisionHautY - agent.speed ) / _world.sizeImage); //on predit la destination d'arrivé si le cas d'aller en haut est choisi
                    imageNum1 = _world.imaM.mapImageNum[emplacementColGauche][emplacementLigHaut];
                    imageNum2 = _world.imaM.mapImageNum[emplacementColGauche][emplacementLigBas];

                    if ( _world.imaM.image[imageNum1].collision == true || _world.imaM.image[imageNum2].collision == true ) //si au moin une des deux images a les collisions activer
                        agent.collisionON = true;

                    break;
            }
        }
        else if ( agent.posx <= (agent.speed*2) && agent.posy <= (agent.speed*2))
        {
            switch ( agent._orient )
            {
                case 1:	// droit
                    emplacementColDroit = ((coteCollisionDroitX + agent.speed) / _world.sizeImage) ; //on predit la destination d'arrivé si le cas d'aller en haut est choisi
                    imageNum1 = _world.imaM.mapImageNum[emplacementColDroit][emplacementLigHaut];
                    imageNum2 = _world.imaM.mapImageNum[emplacementColDroit][emplacementLigBas];

                    if ( _world.imaM.image[imageNum1].collision == true || _world.imaM.image[imageNum2].collision == true ) //si au moin une des deux images a les collisions activer
                        agent.collisionON = true;
                        
                    break;
                case 2:	// bas
                    emplacementLigBas = ((coteCollisionBasY + agent.speed) / _world.sizeImage) ; //on predit la destination d'arrivé si le cas d'aller en haut est choisi
                    imageNum1 = _world.imaM.mapImageNum[emplacementColGauche][emplacementLigBas];
                    imageNum2 = _world.imaM.mapImageNum[emplacementColDroit][emplacementLigBas];

                    if ( _world.imaM.image[imageNum1].collision == true || _world.imaM.image[imageNum2].collision == true ) //si au moin une des deux images a les collisions activer
                        agent.collisionON = true;

                    break;
            }
        }
        else if ( agent.posx >= (_world.maxScreenCol*_world.sizeImage - (agent.speed*2)) && agent.posy <= (agent.speed*2))
        {
            switch ( agent._orient )
            {
                case 2:	// bas
                    emplacementLigBas = ((coteCollisionBasY + agent.speed) / _world.sizeImage) ; //on predit la destination d'arrivé si le cas d'aller en haut est choisi
                    imageNum1 = _world.imaM.mapImageNum[emplacementColGauche][emplacementLigBas];
                    imageNum2 = _world.imaM.mapImageNum[emplacementColDroit][emplacementLigBas];

                    if ( _world.imaM.image[imageNum1].collision == true || _world.imaM.image[imageNum2].collision == true ) //si au moin une des deux images a les collisions activer
                        agent.collisionON = true;

                    break;
                case 3:	// gauche
                    emplacementColGauche = ((coteCollisionHautY - agent.speed ) / _world.sizeImage); //on predit la destination d'arrivé si le cas d'aller en haut est choisi
                    imageNum1 = _world.imaM.mapImageNum[emplacementColGauche][emplacementLigHaut];
                    imageNum2 = _world.imaM.mapImageNum[emplacementColGauche][emplacementLigBas];

                    if ( _world.imaM.image[imageNum1].collision == true || _world.imaM.image[imageNum2].collision == true ) //si au moin une des deux images a les collisions activer
                        agent.collisionON = true;

                    break;
            }
        }
        else if ( agent.posx == (agent.speed*2) && agent.posy == (_world.maxScreenRow*_world.sizeImage - (agent.speed*2)) )
        {
            switch ( agent._orient )
            {
                case 0: // haut
                    emplacementLigHaut = ((coteCollisionHautY - agent.speed) / _world.sizeImage) ; //on predit la destination d'arrivé si le cas d'aller en haut est choisi
                    imageNum1 = _world.imaM.mapImageNum[emplacementColGauche][emplacementLigHaut];
                    imageNum2 = _world.imaM.mapImageNum[emplacementColDroit][emplacementLigHaut];
                    if ( _world.imaM.image[imageNum1].collision == true || _world.imaM.image[imageNum2].collision == true ) //si au moin une des deux images a les collisions activer
                        agent.collisionON = true;

                    break;
                case 1:	// droit
                    emplacementColDroit = ((coteCollisionDroitX + agent.speed) / _world.sizeImage) ; //on predit la destination d'arrivé si le cas d'aller en haut est choisi
                    imageNum1 = _world.imaM.mapImageNum[emplacementColDroit][emplacementLigHaut];
                    imageNum2 = _world.imaM.mapImageNum[emplacementColDroit][emplacementLigBas];

                    if ( _world.imaM.image[imageNum1].collision == true || _world.imaM.image[imageNum2].collision == true ) //si au moin une des deux images a les collisions activer
                        agent.collisionON = true;
                        
                    break;
            }
        }
        else if ( agent.posx >= (_world.maxScreenCol*_world.sizeImage - (agent.speed*2)) && agent.posy >= (_world.maxScreenRow*_world.sizeImage - (agent.speed*2)) )
        {
            switch ( agent._orient )
            {
                case 0: // haut
                    emplacementLigHaut = ((coteCollisionHautY - agent.speed) / _world.sizeImage) ; //on predit la destination d'arrivé si le cas d'aller en haut est choisi
                    imageNum1 = _world.imaM.mapImageNum[emplacementColGauche][emplacementLigHaut];
                    imageNum2 = _world.imaM.mapImageNum[emplacementColDroit][emplacementLigHaut];
                    if ( _world.imaM.image[imageNum1].collision == true || _world.imaM.image[imageNum2].collision == true ) //si au moin une des deux images a les collisions activer
                        agent.collisionON = true;

                    break;
                case 3:	// gauche
                    emplacementColGauche = ((coteCollisionHautY - agent.speed ) / _world.sizeImage); //on predit la destination d'arrivé si le cas d'aller en haut est choisi
                    imageNum1 = _world.imaM.mapImageNum[emplacementColGauche][emplacementLigHaut];
                    imageNum2 = _world.imaM.mapImageNum[emplacementColGauche][emplacementLigBas];

                    if ( _world.imaM.image[imageNum1].collision == true || _world.imaM.image[imageNum2].collision == true ) //si au moin une des deux images a les collisions activer
                        agent.collisionON = true;

                    break;
            }
        }
        
    }
}