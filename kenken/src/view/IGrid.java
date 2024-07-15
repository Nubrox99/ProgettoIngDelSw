package view;

import controller.Solver;
import model.Block;
import controller.Observer;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.LinkedList;

public abstract class IGrid extends JPanel implements Observer {

    protected JButton[][] grid;
    protected int[][] intGrid; //0:libero , 1:selezionato, 2:occupato
    protected int d;
    private Color color;
    protected boolean liveCheck;


    public IGrid(int d, LinkedList<Block> b, Color color){
        super(new GridLayout(d,d));
        this.d = d;
        this.color=color;
        createGrid(d);
        drawGrid(b);
    }
    //getter and setter

    public int getD(){return d;}

    public void setLiveCheck(boolean liveCheck){this.liveCheck=liveCheck;}

    public int[][] getGridValue(){
        int[][] g = new int[d][d];
        for(int i=0;i<d;i++){
            for(int j=0;j<d;j++){
                if(grid[i][j].getText().equals("")) g[i][j]=0;
                else g[i][j] = Integer.parseInt(grid[i][j].getText());
            }
        }
        return g;
    }

    public void resetGrid() {
        for (JButton[] row : grid) {
            for (JButton c : row) {
                c.setEnabled(true);
                c.setText("");
                c.setBackground(Color.white);
            }
        }
    }

    //private and protected method for struct drawing

    private void drawGrid(LinkedList<Block> blocks){
        int[][] borders = new int[d][d]; //rappresentazione dei blocchi per calcolare i bordi
        int counter = 0;
        for(Block b : blocks){
            counter ++;
            boolean drawRule = false;
            for(int c : b.getCells()){
                int i = c/d;
                int j = c%d;
                borders[i][j] = counter;

                grid[i][j].setBackground(color);
                intGrid[i][j]=2;
                if(!drawRule){
                    SwingUtilities.invokeLater(() -> {
                        JLabel label = new JLabel(b.getResult() + "" + b.getStringOperation());
                        label.setForeground(Color.BLACK);
                        label.setFont(new Font("Arial", Font.BOLD, 22));
                        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                        panel.setOpaque(false);
                        panel.add(label);
                        grid[i][j].add(panel, BorderLayout.NORTH);
                        grid[i][j].revalidate();
                        grid[i][j].repaint();
                    });
                    drawRule=true;
                }
            }
        }
        drawBorder(borders);
    }

    private void drawBorder(int[][] b){
        int[] borderb = new int[4];  //border black
        int[] borderg = new int[4];  //border gray
        for(int i=0; i<d; i++){
            for(int j=0; j<d; j++){
                borderb = new int[]{0,0,0,0};
                borderg = new int[]{1,1,1,1};
                if(i != d-1 && b[i][j] != b[i+1][j]) {
                    borderb[2] = 4;
                    borderg[2] = 0;
                }
                if(i != 0 && b[i][j] != b[i-1][j]) {
                    borderb[0] = 4;
                    borderg[0] = 0;
                }
                if(j != d-1 && b[i][j] != b[i][j+1]) {
                    borderb[3] = 4;
                    borderg[3] = 0;
                }
                if(j != 0 && b[i][j] != b[i][j-1]) {
                    borderb[1] = 4;
                    borderg[1] = 0;
                }
                CompoundBorder combinedBorder = new CompoundBorder(
                        new MatteBorder(borderg[0], borderg[1], borderg[2], borderg[3], Color.GRAY),
                        new MatteBorder(borderb[0], borderb[1], borderb[2], borderb[3], Color.BLACK));
                grid[i][j].setBorder(combinedBorder);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // larghezza e altezza dello spazio occupato dal Grid Panel (this)
        int width = getWidth();
        int height = getHeight();

        int availableHeight = height - 40; //padding di 20px
        int availableWidth = width - 40; //padding di 20px

        //calcolo della dimensione minima tra la larghezza e l'altezza disponibile, per poter disegnare un quadrato
        int gridSize = Math.min(availableHeight, availableWidth);

        //coordinate x,y punto di inizio per tracciare il contenitore
        int x = (width - gridSize) / 2;
        int y = (height - gridSize) / 2;

        //Creazione quadrato contenitore della griglia
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(8));
        g2.drawRect(x, y, gridSize, gridSize);

        //if nel caso la griglia si riferisce ad una creazione o ad un caricamento : creazione(d=0) | caricamento (d!=0)
        if(d!=0) {
            //Aggiornamento posizione dei pulsanti nel quadrato
            int buttonSize = gridSize / d;
            for (int i = 0; i < d; i++) {
                for (int j = 0; j < d; j++) {
                    int buttonX = x + j * buttonSize;
                    int buttonY = y + i * buttonSize;
                    grid[i][j].setBounds(buttonX, buttonY, buttonSize, buttonSize);

                    grid[i][j].setFont(new Font("Arial",Font.BOLD,buttonSize/3));
                }
            }
        }
    }

    protected abstract void createGrid(int d);

    //public method for updating of block and solution

    public void updateBlocks(LinkedList<Block> blocks){
        for(int i =0;i<d;i++){
            for(int j =0;j<d;j++){
                intGrid[i][j]=0;
                grid[i][j].setBackground(Color.white);
                grid[i][j].removeAll();
            }
        }
        drawGrid(blocks);

    }

    public void drawSolution(int[][] solution){
        for(int i=0;i<d;i++) {
            for(int j=0; j<d; j++) {
                intGrid[i][j] = solution[i][j];
                grid[i][j].setText(""+solution[i][j]);
                grid[i][j].setEnabled(false);
                grid[i][j].setBackground(Color.white);
            }
        }
    }


    /*observer method*/
    @Override
    public void update(boolean[][] liveSolution) {return;}

    public void setSolver(Solver solver) {
        solver.addObserver(this); // Registrati come osservatore
    }

}
