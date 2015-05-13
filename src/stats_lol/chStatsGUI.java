package stats_lol;

import java.awt.Container; //Imports the container that the GUI is in
import java.awt.GridBagConstraints; //The constrains of the conatainer
import java.awt.GridBagLayout; //The layout of the container
import java.awt.Insets; //Spacements
import javax.swing.JFrame; //The frame
import javax.swing.JScrollPane;
import javax.swing.JTable; //Table
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE; //Imports the class that closes the program when you hit the "x"

/**
 *
 * @author Cristofer
 */

//Table with the champion stats
public class chStatsGUI extends JFrame{
    
    Object data[][];
    
    double[] pr, br, wr, p, b, w, g;
    int tGames;
    
    ReadWrite rw = new ReadWrite();
    
    Stats_lol ch = new Stats_lol(); //Takes the Stats_lol variables
    
    String[] columnNames =  {"champion",//Set the column Names
                        "pick %", 
                        "ban %",
                        "win ratio",};
    
    Container cp = getContentPane(); //Calls the container
    
    public chStatsGUI(){
        
        pr = new double[124];
        br = new double[124];
        wr = new double[124];
        b = new double[124];
        p = new double[124];
        w = new double[124];
        g = new double[124];
        
        for(int i = 0; i < 8; i++){
            rw.readWrite(false, 13, i);
            tGames += Integer.parseInt(rw.nB);
        }
        
        if(tGames == 0){
            tGames = 2;
        }
        
        tGames = tGames/2;
        
        for(int i = 0; i < 124; i++){
            rw.readWrite(false, -4, i);
            p[i] = Integer.parseInt(rw.nB);
            rw.readWrite(false, 0, i);
            b[i] = Integer.parseInt(rw.nB);
            rw.readWrite(false, -5, i);
            w[i] = Integer.parseInt(rw.nB);
            g[i] = p[i];
            if(g[i] == 0){
                g[i] = 1;
            }
        }
        
        for(int i = 0; i < 124; i++){
            pr[i] = 100*(p[i]/tGames);
            br[i] = 100*(b[i]/tGames);
            wr[i] = 100*(w[i]/g[i]);
        }
        
        data = new Object[124][4];
                                        //Set the content of each column in respective order
        for(int i = 0; i < 124; i++){
            for(int j = 0; j < 4; j++){
                if(j == 0){
                    data[i][j] = ch.champion[i];
                }
                else if(j == 1){
                    data[i][j] = pr[i];
                }
                else if(j == 2){
                    data[i][j] = br[i];
                }
                else if(j == 3){
                    data[i][j] = wr[i];
                }
            }
        }
        
        JTable tblCh = new JTable(data, columnNames); //Calls the table
        
        tblCh.setEnabled(false); //You can't edit the cells (false)
        
        tblCh.setAutoCreateRowSorter(true);
        
        JScrollPane scroll = new JScrollPane(tblCh); //Puts the table inside of a scroll panel
             
        checkVersion checkVersion = new checkVersion(); //To use the version variable
        
        setTitle("LoL Stats Maker " + checkVersion.version); //Sets the tile off the frame in case "LoL Stats Maker #.##"
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //Closes the frame if hit the "x"
        setVisible(true); //Now you can see the GUI
        setSize(500, 500); //Inicial size
        setLocationRelativeTo(null); //Opens in the center of the screen
        
        cp.setLayout(new GridBagLayout()); //Sets the layout of the frame
        
        GridBagConstraints c = new GridBagConstraints();
        
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 10, 0, 10); //The distance between each thing inside the frame
        c.gridx = 0; 
        c.gridy = 0;
        c.gridwidth = 0;
        
        cp.add(tblCh.getTableHeader()); //Adds the table inside the frame
        
        c.insets = new Insets(0, 10, 10, 10);
        c.gridy = 1;
        
        cp.add(scroll, c); //With "c" restrictions
        
        pack(); //Resizes the frame to the necessary size to the necessary size           
    }
}