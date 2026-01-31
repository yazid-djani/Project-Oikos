import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.io.*;

public class ImageManager
{
    public int      mapImageNum[][];
    public Image[]  image;
    GameLoop _world;

    public ImageManager(GameLoop _world)
    {
        this._world = _world;
        
        image = new Image[48];
        mapImageNum = new int[_world.maxScreenCol][_world.maxScreenRow];
        getImage();
        loadMap("maps/map01.txt");
    }

    public void getImage()
    {
        try
        {
            // ========== ========== |  JOUR  | ========== ==========
            // ===== |  Element de base | =====
            image[0] = new Image();
            image[0].draw = ImageIO.read(getClass().getResourceAsStream("decors/jour/grass.png")); //grass
            image[1] = new Image();
            image[1].draw = ImageIO.read(getClass().getResourceAsStream("decors/jour/hauteur/_hDB.png")); //hauteur centre
            image[1].collision = true;
            image[2] = new Image();
            image[2].draw = ImageIO.read(getClass().getResourceAsStream("decors/jour/eau/_wD.png")); //water
            image[2].collision = true;
            // ===== | Hauteur | =====
            image[3] = new Image();
            image[3].draw = ImageIO.read(getClass().getResourceAsStream("decors/jour/hauteur/_hDG.png")); //hauteur gauche
            image[3].collision = true;
            image[4] = new Image();
            image[4].draw = ImageIO.read(getClass().getResourceAsStream("decors/jour/hauteur/_hDBD.png")); //hauteur bas droit
            image[4].collision = true;
            image[5] = new Image();
            image[5].draw = ImageIO.read(getClass().getResourceAsStream("decors/jour/hauteur/_hDGH.png")); //hauteur haut gauche
            image[5].collision = true;
            image[6] = new Image();
            image[6].draw = ImageIO.read(getClass().getResourceAsStream("decors/jour/hauteur/_hDD.png")); //hauteur droite
            image[6].collision = true;
            // ===== |  water  | =====
            image[7] = new Image();
            image[7].draw = ImageIO.read(getClass().getResourceAsStream("decors/jour/eau/_wD_b.png")); //water
            image[7].collision = true;
            image[8] = new Image();
            image[8].draw = ImageIO.read(getClass().getResourceAsStream("decors/jour/eau/_wD_bd.png")); //_wD
            image[8].collision = true;
            image[9] = new Image();
            image[9].draw = ImageIO.read(getClass().getResourceAsStream("decors/jour/eau/_wD_bg.png")); //_wD
            image[9].collision = true;
            image[10] = new Image();
            image[10].draw = ImageIO.read(getClass().getResourceAsStream("decors/jour/eau/_wD_c0.png")); //_wD
            image[10].collision = true;
            image[11] = new Image();
            image[11].draw = ImageIO.read(getClass().getResourceAsStream("decors/jour/eau/_wD_c1.png")); //_wD
            image[11].collision = true;
            image[12] = new Image();
            image[12].draw = ImageIO.read(getClass().getResourceAsStream("decors/jour/eau/_wD_c1b.png")); //_wD
            image[12].collision = true;
            image[13] = new Image();
            image[13].draw = ImageIO.read(getClass().getResourceAsStream("decors/jour/eau/_wD_c1d.png")); //water
            image[13].collision = true;
            image[14] = new Image();
            image[14].draw = ImageIO.read(getClass().getResourceAsStream("decors/jour/eau/_wD_c1g.png")); //water
            image[14].collision = true;
            image[15] = new Image();
            image[15].draw = ImageIO.read(getClass().getResourceAsStream("decors/jour/eau/_wD_c1h.png")); //water
            image[15].collision = true;
            image[16] = new Image();
            image[16].draw = ImageIO.read(getClass().getResourceAsStream("decors/jour/eau/_wD_cb.png")); //water
            image[16].collision = true;
            image[17] = new Image();
            image[17].draw = ImageIO.read(getClass().getResourceAsStream("decors/jour/eau/_wD_cbd.png")); //water
            image[17].collision = true;
            image[18] = new Image();
            image[18].draw = ImageIO.read(getClass().getResourceAsStream("decors/jour/eau/_wD_cbg.png")); //water
            image[18].collision = true;
            image[19] = new Image();
            image[19].draw = ImageIO.read(getClass().getResourceAsStream("decors/jour/eau/_wD_d.png")); //water
            image[19].collision = true;
            image[20] = new Image();
            image[20].draw = ImageIO.read(getClass().getResourceAsStream("decors/jour/eau/_wD_g.png")); //water
            image[20].collision = true;
            image[21] = new Image();
            image[21].draw = ImageIO.read(getClass().getResourceAsStream("decors/jour/eau/_wD_h.png")); //water
            image[21].collision = true;
            image[22] = new Image();
            image[22].draw = ImageIO.read(getClass().getResourceAsStream("decors/jour/eau/_wD_hd.png")); //water
            image[22].collision = true;
            image[23] = new Image();
            image[23].draw = ImageIO.read(getClass().getResourceAsStream("decors/jour/eau/_wD_hg.png")); //water
            image[23].collision = true;

            // ========== ========== |  NUIT  | ========== ==========
            // ===== |  Element de base | =====
            image[24] = new Image();
            image[24].draw = ImageIO.read(getClass().getResourceAsStream("decors/nuit/grassN.png")); //grass night
            image[25] = new Image();
            image[25].draw = ImageIO.read(getClass().getResourceAsStream("decors/nuit/hauteur/_hNB.png")); //hauteur centre night
            image[25].collision = true;
            image[26] = new Image();
            image[26].draw = ImageIO.read(getClass().getResourceAsStream("decors/nuit/eau/_wN.png")); //water night
            image[26].collision = true;
            // ===== | Hauteur | =====
            image[27] = new Image();
            image[27].draw = ImageIO.read(getClass().getResourceAsStream("decors/nuit/hauteur/_hNG.png")); //hauteur gauche night
            image[27].collision = true;
            image[28] = new Image();
            image[28].draw = ImageIO.read(getClass().getResourceAsStream("decors/nuit/hauteur/_hNBD.png")); //hauteur bas droit night
            image[28].collision = true;
            image[29] = new Image();
            image[29].draw = ImageIO.read(getClass().getResourceAsStream("decors/nuit/hauteur/_hNGH.png")); //hauteur haut gauche night
            image[29].collision = true;
            image[30] = new Image();
            image[30].draw = ImageIO.read(getClass().getResourceAsStream("decors/nuit/hauteur/_hND.png")); //hauteur droite night
            image[30].collision = true;
            // ===== |  water  | =====
            image[31] = new Image();
            image[31].draw = ImageIO.read(getClass().getResourceAsStream("decors/nuit/eau/_wN_b.png")); //water night
            image[31].collision = true;
            image[32] = new Image();
            image[32].draw = ImageIO.read(getClass().getResourceAsStream("decors/nuit/eau/_wN_bd.png")); //_wD night
            image[32].collision = true;
            image[33] = new Image();
            image[33].draw = ImageIO.read(getClass().getResourceAsStream("decors/nuit/eau/_wN_bg.png")); //_wD night
            image[33].collision = true;
            image[34] = new Image();
            image[34].draw = ImageIO.read(getClass().getResourceAsStream("decors/nuit/eau/_wN_c0.png")); //_wD night
            image[34].collision = true;
            image[35] = new Image();
            image[35].draw = ImageIO.read(getClass().getResourceAsStream("decors/nuit/eau/_wN_c1.png")); //_wD night
            image[35].collision = true;
            image[36] = new Image();
            image[36].draw = ImageIO.read(getClass().getResourceAsStream("decors/nuit/eau/_wN_c1b.png")); //_wD night
            image[36].collision = true;
            image[37] = new Image();
            image[37].draw = ImageIO.read(getClass().getResourceAsStream("decors/nuit/eau/_wN_c1d.png")); //water night
            image[37].collision = true;
            image[38] = new Image();
            image[38].draw = ImageIO.read(getClass().getResourceAsStream("decors/nuit/eau/_wN_c1g.png")); //water night
            image[38].collision = true;
            image[39] = new Image();
            image[39].draw = ImageIO.read(getClass().getResourceAsStream("decors/nuit/eau/_wN_c1h.png")); //water night
            image[39].collision = true;
            image[40] = new Image();
            image[40].draw = ImageIO.read(getClass().getResourceAsStream("decors/nuit/eau/_wN_cb.png")); //water night
            image[40].collision = true;
            image[41] = new Image();
            image[41].draw = ImageIO.read(getClass().getResourceAsStream("decors/nuit/eau/_wN_cbd.png")); //water night
            image[41].collision = true;
            image[42] = new Image();
            image[42].draw = ImageIO.read(getClass().getResourceAsStream("decors/nuit/eau/_wN_cbg.png")); //water night
            image[42].collision = true;
            image[43] = new Image();
            image[43].draw = ImageIO.read(getClass().getResourceAsStream("decors/nuit/eau/_wN_d.png")); //water night
            image[43].collision = true;
            image[44] = new Image();
            image[44].draw = ImageIO.read(getClass().getResourceAsStream("decors/nuit/eau/_wN_g.png")); //water night
            image[44].collision = true;
            image[45] = new Image();
            image[45].draw = ImageIO.read(getClass().getResourceAsStream("decors/nuit/eau/_wN_h.png")); //water night
            image[45].collision = true;
            image[46] = new Image();
            image[46].draw = ImageIO.read(getClass().getResourceAsStream("decors/nuit/eau/_wN_hd.png")); //water night
            image[46].collision = true;
            image[47] = new Image();
            image[47].draw = ImageIO.read(getClass().getResourceAsStream("decors/nuit/eau/_wN_hg.png")); //water night
            image[47].collision = true;
        }
        catch ( IOException e )
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void loadMap(String fileName) 
    {
        try 
        {
            int col = 0;
            int lig = 0;

            //fichier d'entrée
            InputStream file = getClass().getResourceAsStream(fileName);
            BufferedReader scanner = new BufferedReader(new InputStreamReader(file));

            //renvoie true s'il y a une autre lig à lire
            while(col < _world.maxScreenCol && lig < _world.maxScreenRow)
            {
                String line = scanner.readLine();
                //tant que la lig a un autre entier
                while(col < _world.maxScreenCol)
                {
                    String nombres[] = line.split(" ");         //pour ne pas prendre en compte les espaces
                    int num = Integer.parseInt(nombres[col]);   //recupere les nombres sous forme de integer et non d'un caractère
                    mapImageNum[col][lig] = num;
                    col++;
                }

                if (col == _world.maxScreenCol) // quand la fin de la lig est atteinte
                {
                    col = 0;
                    lig++;
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

    public void drawG(Graphics2D g2)
    {
        int col = 0;
        int lig = 0;
        int x = 0;
        int y = 0;

        while (col < _world.maxScreenCol && lig < _world.maxScreenRow)
        {
            int imageNumber = mapImageNum[col][lig];

           	/* ======= | WATER | ======= */
            if ( imageNumber == 2 )
            {
                if ( col > 0 && col < _world.maxScreenCol-1 && lig > 1 && lig < _world.maxScreenRow-1 )
                {
                    if ( mapImageNum[col-1][lig] == 2 && mapImageNum[col+1][lig] == 2 && mapImageNum[col][lig-1] == 2 && mapImageNum[col][lig+1] == 2 ){
                        if ( _world.night )
                            g2.drawImage(image[(2 + 24)].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[2].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                    else if ( mapImageNum[col-1][lig] != 2 && mapImageNum[col+1][lig] == 2 && mapImageNum[col][lig-1] == 2 && mapImageNum[col][lig+1] == 2 ){
                        if ( _world.night )
                            g2.drawImage(image[(20 + 24)].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[20].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                    else if ( mapImageNum[col-1][lig] == 2 && mapImageNum[col+1][lig] == 2 && mapImageNum[col][lig-1] == 2 && mapImageNum[col][lig+1] != 2 ){
                        if ( _world.night )
                            g2.drawImage(image[(7 + 24)].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[7].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                    else if ( mapImageNum[col-1][lig] == 2 && mapImageNum[col+1][lig] != 2 && mapImageNum[col][lig-1] == 2 && mapImageNum[col][lig+1] == 2 ){
                        if ( _world.night )
                            g2.drawImage(image[(19 + 24)].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[19].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                    else if ( mapImageNum[col-1][lig] == 2 && mapImageNum[col+1][lig] == 2 && mapImageNum[col][lig-1] != 2 && mapImageNum[col][lig+1] == 2 ){
                        if ( _world.night )
                            g2.drawImage(image[(21 + 24)].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[21].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                    else if ( mapImageNum[col-1][lig] == 2 && mapImageNum[col+1][lig] != 2 && mapImageNum[col][lig-1] != 2 && mapImageNum[col][lig+1] == 2 ){
                        if ( _world.night )
                            g2.drawImage(image[(22 + 24)].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[22].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                    else if ( mapImageNum[col-1][lig] != 2 && mapImageNum[col+1][lig] == 2 && mapImageNum[col][lig-1] != 2 && mapImageNum[col][lig+1] == 2 ){
                        if ( _world.night )
                            g2.drawImage(image[(23 + 24)].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[23].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                    else if ( mapImageNum[col-1][lig] != 2 && mapImageNum[col+1][lig] == 2 && mapImageNum[col][lig-1] == 2 && mapImageNum[col][lig+1] != 2 ){
                        if ( _world.night )
                            g2.drawImage(image[(9 + 24)].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[9].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                    else if ( mapImageNum[col-1][lig] == 2 && mapImageNum[col+1][lig] != 2 && mapImageNum[col][lig-1] == 2 && mapImageNum[col][lig+1] != 2 ){
                        if ( _world.night )
                            g2.drawImage(image[(8 + 24)].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[8].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                }
                else if ((lig == 0) || (lig == _world.maxScreenRow-1))
                {
                    if ( mapImageNum[col -1][lig] != 2 ){
                        if ( _world.night )
                            g2.drawImage(image[(18 + 24)].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[18].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                    else if ( mapImageNum[col-1][lig] == 2 && mapImageNum[col+1][lig] == 2 && mapImageNum[col-2][lig] == 2 && mapImageNum[col+2][lig] == 2 ){
                        if ( _world.night )
                            g2.drawImage(image[(12 + 24)].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[12].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                    else if ( mapImageNum[col +1][lig] != 2 ){
                        if ( _world.night )
                            g2.drawImage(image[(17 + 24)].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[17].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                    else {
                        if ( _world.night )
                            g2.drawImage(image[(16 + 24)].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[16].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                }
                else if (lig == 1)
                {
                    if (( mapImageNum[col+1][lig] == 1 )){
                        if ( _world.night )
                            g2.drawImage(image[13 + 24].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[13].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                    else if ( mapImageNum[col-1][lig] == 1 ){
                        if ( _world.night )
                            g2.drawImage(image[14 + 24].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[14].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                    else if ( mapImageNum[col-1][lig] == 2 && mapImageNum[col+1][lig] == 2 && mapImageNum[col-2][lig] == 2 && mapImageNum[col+2][lig] == 2 ){
                        if ( _world.night )
                            g2.drawImage(image[15 + 24].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[15].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                    else{
                        if ( _world.night )
                            g2.drawImage(image[11 + 24].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[11].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                }
                else if (lig == 3)
                {
                    if ( _world.night )
                        g2.drawImage(image[10 + 24].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                    else
                        g2.drawImage(image[10].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                }
            }
            else if ( imageNumber == 1 )
            {
                if ( col > 0 && col < _world.maxScreenCol-1 && lig > 0 && lig < _world.maxScreenRow-1 )
                {
                    if ( mapImageNum[col-1][lig] == 1 && mapImageNum[col+1][lig] != 1 && mapImageNum[col][lig-1] != 1 && mapImageNum[col][lig+1] == 1 ){
                        if ( _world.night )
                            g2.drawImage(image[5 + 24].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[5].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                    else if ( mapImageNum[col-1][lig] != 1 && mapImageNum[col+1][lig] == 1 && mapImageNum[col][lig-1] == 1 && mapImageNum[col][lig+1] != 1 ){
                        if ( _world.night )
                            g2.drawImage(image[4 + 24].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[4].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                    else if ( mapImageNum[col-1][lig] == 1 && mapImageNum[col+1][lig] != 1 && mapImageNum[col][lig-1] == 1 && mapImageNum[col][lig+1] != 1 ){
                        if ( _world.night )
                            g2.drawImage(image[6 + 24].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[6].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                    else if ( mapImageNum[col-1][lig] == 3 && mapImageNum[col+1][lig] != 1 && mapImageNum[col][lig-1] == 1 && mapImageNum[col][lig+1] != 1 ){
                        if ( _world.night )
                            g2.drawImage(image[6 + 24].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[6].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                    else if ( mapImageNum[col-1][lig] != 1 && mapImageNum[col+1][lig] == 1 && mapImageNum[col][lig-1] != 1 && mapImageNum[col][lig+1] == 1 ){
                        if ( _world.night )
                            g2.drawImage(image[3 + 24].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[3].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                    else if ( mapImageNum[col-1][lig] != 1 && mapImageNum[col+1][lig] == 3 && mapImageNum[col][lig-1] != 1 && mapImageNum[col][lig+1] == 1 ){
                        if ( _world.night )
                            g2.drawImage(image[3 + 24].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[3].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                    else {
                        if ( _world.night )
                            g2.drawImage(image[1 + 24].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[1].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                }
                else if ( col > 0 && col < _world.maxScreenCol-1 && lig == 0 )
                {
                    if ( mapImageNum[col-1][lig] != 1 && mapImageNum[col+1][lig] == 1 && mapImageNum[col][lig+1] == 1 ){
                        if ( _world.night )
                            g2.drawImage(image[3 + 24].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[3].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                    else if ( mapImageNum[col-1][lig] == 1 && mapImageNum[col+1][lig] != 1 && mapImageNum[col][lig+1] != 1 ){
                        if ( _world.night )
                            g2.drawImage(image[6 + 24].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[6].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                    else{
                        if ( _world.night )
                            g2.drawImage(image[1 + 24].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                        else
                            g2.drawImage(image[1].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                    }
                }
                else{
                    if ( _world.night )
                        g2.drawImage(image[1 + 24].draw,x,y,_world.sizeImage,_world.sizeImage, null);
                    else
                        g2.drawImage(image[1].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                }
            }
            else
            {
                if ( _world.night )
                    g2.drawImage(image[imageNumber + 24].draw, x, y,_world.sizeImage, _world.sizeImage, null);
                else
                    g2.drawImage(image[imageNumber].draw, x, y,_world.sizeImage, _world.sizeImage, null);
            }
            col++;
            x += _world.sizeImage;
            
            if (col == _world.maxScreenCol) 
            {
                col = 0;
                x = 0;
                lig++;
                y += _world.sizeImage;
            }
        }
    }
}