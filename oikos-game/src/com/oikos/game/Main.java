import javax.swing.JFrame;

public class main
{
    public static void main (String[] args)
    {
        // ===== | affichage de la fenetre graphique | =====
		JFrame frame = new JFrame("Minecosys");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        GameLoop minecosys = new GameLoop();
        
        System.out.println(minecosys.imaM.mapImageNum.length);
        System.out.println(minecosys.imaM.mapImageNum[0].length);
        for(int j=0;j<minecosys.imaM.mapImageNum[0].length-1;j++){
            System.out.print("{");
        for(int i=0;i<minecosys.imaM.mapImageNum.length-1;i++){
                System.out.print(minecosys.imaM.mapImageNum[i][j]+",");
            }
            System.out.print("},");
            System.out.println();
        }
        
        frame.add(minecosys);
        frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

        minecosys.startAnimation();
        
    }
}