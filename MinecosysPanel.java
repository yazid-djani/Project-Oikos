import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.*;
import java.io.*;
import java.awt.image.BufferedImage;

public class MinecosysPanel extends JPanel implements Runnable
{
    // Parametre affichage
    public final int sizeImage = 32; // 32*32 image size
    public final int maxScreenCol = 58; // 58 * 32 nb image max afficher sur la fenetre
    public final int maxScreenRow = 32; // dx & dy
    public final int screenWidth = sizeImage * maxScreenCol; //1856 pixels
    public final int screenHeight = sizeImage * maxScreenRow; //1024 pixels
    public boolean night = false;
    public int compteurMov = 0;
    public int numeroMov = 1;
    public int nombreJour = 0; // nombre de jour qui se sont écouler au fil de l'animation
    public CollisionManager colTesteur = new CollisionManager(this); //permet de tester les collisions 
    Thread animationThread; // Permet d'appeler la focntion run()
    int FPS = 15; ///Image par seconde
    int delaiDay = 500;
    int delaiNight = 400;
    int time = 0;

    //monde et arbres
    public ArrayList<Arbre> arbresX;
    ArrayList<Coord> coordonnee = new ArrayList<Coord>();
    ImageManager imaM = new ImageManager(this);

    // Bucheron
    int nbBucheron = 2;
    ArrayList<Bucheron> bucheronX;
    // Fermier
    int nbFermier = 5;
    ArrayList<Fermier> fermierX;
    // Enfant
    int nbEnfant = 10;
    ArrayList<Enfant> enfantX;
    // Loup
    int nbLoup = 2;
    ArrayList<Loup> loupX;
    // Mouton
    int nbMouton = 2;
    ArrayList<Mouton> moutonX;



    public MinecosysPanel()
    {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

        bucheronX =  new ArrayList<Bucheron>();
        fermierX =  new ArrayList<Fermier>();
        enfantX =  new ArrayList<Enfant>();
        arbresX = new ArrayList<Arbre>();
        moutonX = new ArrayList<Mouton>();
        loupX = new ArrayList<Loup>();
        loadArbre();

        for (int i = 0 ; i < nbBucheron ; i++)
            bucheronX.add(new Bucheron(this));

        for (int j = 0 ; j < nbFermier ; j++)
            fermierX.add(new Fermier(this));

        for (int e = 0 ; e < nbEnfant ; e++)
            enfantX.add(new Enfant(this));
        
        for (int e = 0 ; e < nbMouton ; e++)
            moutonX.add(new Mouton(this));
        
        for (int e = 0 ; e < nbLoup ; e++)
            loupX.add(new Loup(this));
    }

    public void loadArbre() 
    {
        try 
        {
            int col = 0;
            int lig = 0;
            int x = 0;
            int y = 0;

            //fichier d'entrée
            InputStream file = getClass().getResourceAsStream("maps/mapArbre.txt");
            BufferedReader scanner = new BufferedReader(new InputStreamReader(file));

            //renvoie true s'il y a une autre lig à lire
            while(col < maxScreenCol && lig < maxScreenRow)
            {
                String line = scanner.readLine();
                //tant que la lig a un autre entier
                while(col < maxScreenCol)
                {
                    String nombres[] = line.split(" ");         //pour ne pas prendre en compte les espaces
                    int num = Integer.parseInt(nombres[col]);   //recupere les nombres sous forme de integer et non d'un caractère
                    
                    if ( num == 1 )
                        if ( Math.random() < 0.65 )
                            arbresX.add(new Arbre(x, y, this));
                        else 
                            arbresX.add(new Arbre(x, y, this, 0));

                    col++;
                    x += sizeImage;
                }

                if (col == maxScreenCol) // quand la fin de la lig est atteinte
                {
                    col = 0;
                    x = 0;
                    lig++;
                    y += sizeImage;
                }
            }
            scanner.close();
        }
        catch ( Exception e ) 
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void startAnimation()
    {
        animationThread = new Thread(this);
        animationThread.start();
    }

    public void run()
    {
        double intervalleDessin = 1000000000/FPS; // 1000000000 nano secondes = 1 seconde 
        double prochainDessin = System.nanoTime() + intervalleDessin;

        while(animationThread != null)
        {
            // 1 - Mise a jour des informations (pousse arbre, mouvement personnage ...)
            update();

            // 2 - Mettre a jour l'affichage (dessiner)
            repaint(); // appelle paintComponent

            // pour que le systeme n'aille pas trop vite (FPS image/seconde)
            try 
            {
                double delai = prochainDessin - System.nanoTime();
                delai = delai /1000000;
                
                //securite pour le delai
                if ( delai < 0 ){
                    delai = 0;
                }

				Thread.sleep((long) delai);
                prochainDessin += intervalleDessin;
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
			}
        }
    }

    public void update()
    {
        // timer jour / nuit
        time++;
        if ( night ){
            if ( time >= delaiNight)
            {
                night = false;
                time = 0;
                nombreJour++;
            }
        }
        else if ( !night ){
            if ( time >= delaiDay)
            {
                night = true;
                time = 0;
            }
        }

        // mise a jour des villageois 
        for (Bucheron a : bucheronX) {
            if ( a.alive == true )
                a.step();
        }
        for (Fermier a : fermierX) {
            if ( a.alive == true )
                a.step();
        }
        for (Enfant e : enfantX) {
            if ( e.alive == true ) {
                if ( e.age >= e.majorite) {
                    if ( e.sexe == "homme" )
                        bucheronX.add(new Bucheron(this, e.posx, e.posy, e.age));
                    else 
                        fermierX.add(new Fermier(this, e.posx, e.posy, e.age));
                    
                    e.mort();
                }
                else {
                    e.step();
                }
            }
        }

        // mise a jour des animaux
        for (Mouton a : moutonX) {
            if ( a.alive == true )
                a.step();
        }
        for (Loup a : loupX) {
            if ( a.alive == true )
                a.step();
        }

        //pour animation de la marche 
        compteurMov++;
        if ( compteurMov > 3 )
        {
            if ( numeroMov == 1 )
                numeroMov = 2;
            else if ( numeroMov == 2 )
                numeroMov = 1;
            
            compteurMov = 0;
        }

        //pour la pousse des arbres
        //mise a jour asynchrone aléatoire equitable 4 par 4

        //haut gauche
        int rx1 = (int)(Math.random()*((maxScreenCol / 2) - 0 + 1 ) + 0); 
		int ry1 = (int)(Math.random()*((maxScreenRow / 2) - 0 + 1 ) + 0);

        //bas gauche
        int rx2 = (int)(Math.random()*((maxScreenCol / 2) - 0 + 1 ) + 0);
		int ry2 = (int)(Math.random()*(maxScreenRow - (maxScreenRow / 2) + 1 ) + (maxScreenRow / 2));

        //haut droit
        int rx3 = (int)(Math.random()*(maxScreenCol - (maxScreenCol / 2) + 1 ) + (maxScreenCol / 2));
		int ry3 = (int)(Math.random()*((maxScreenRow / 2) - 0 + 1 ) + 0);

        //bas droit
        int rx4 = (int)(Math.random()*(maxScreenCol - (maxScreenCol / 2) + 1 ) + (maxScreenCol / 2));
		int ry4 = (int)(Math.random()*(maxScreenRow - (maxScreenRow / 2) + 1 ) + (maxScreenRow / 2));

        Coord c1 = new Coord(rx1, ry1);
        Coord c2 = new Coord(rx2, ry2);
        Coord c3 = new Coord(rx3, ry3);
        Coord c4 = new Coord(rx4, ry4);

        if (coordonnee.size() != (maxScreenCol * maxScreenRow))
        {
            if (coordonnee.contains(c1) == false || coordonnee.contains(c2) == false || coordonnee.contains(c3) == false || coordonnee.contains(c4) == false )
            {
                for (Arbre c : arbresX)
                {
                    if( c.posx == (rx1 * sizeImage) && c.posy == (ry1 * sizeImage) )
                        c.step();
                    if( c.posx == (rx2 * sizeImage) && c.posy == (ry2 * sizeImage) )
                        c.step();
                    if( c.posx == (rx3 * sizeImage) && c.posy == (ry3 * sizeImage) )
                        c.step();
                    if( c.posx == (rx4 * sizeImage) && c.posy == (ry4 * sizeImage) )
                        c.step();
                }
                coordonnee.add(c1);
                coordonnee.add(c2);
                coordonnee.add(c3);
                coordonnee.add(c4);
            }
        }
        else
        {
            coordonnee.clear();
        }
    }

    public void paintComponent(Graphics g) 
    {                                      
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        // Affichage du monde
        imaM.drawG(g2);
        
        // Affichage des villageois
        for(Bucheron a :  bucheronX)
            if ( a.alive )
                a.display(g2);

        for(Fermier b : fermierX)
            if ( b.alive )
                b.display(g2);

        for(Enfant e : enfantX)
            if ( e.alive )
                e.display(g2);

        /*for(Mouton e : moutonX)
            if ( e.alive )
                e.display(g2);*/

        for(Loup e : loupX)
            if ( e.alive )
                e.display(g2);

        // Affichage des Arbres
        for(Arbre c : arbresX)
            c.display(g2);
        
        g2.dispose();
    }

}