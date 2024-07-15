package model;

import view.IGrid;
import view.Frame;
import controller.Configuration;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class GameGrid extends IGrid {

    public GameGrid(int d, LinkedList<Block> b) {
        super(d, b,Color.white);
    }

    @Override
    public void createGrid(int d) {
        grid = new JButton[d][d];
        intGrid = new int[d][d];

        for(int i=0; i<d; i++){
            for(int j=0; j<d; j++){
                intGrid[i][j]=0;
                int finalI = i;
                int finalJ = j;
                grid[i][j] = new JButton("");

                grid[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Impostare il focus sul pulsante per catturare i tasti premuti
                        grid[finalI][finalJ].requestFocusInWindow();
                        paintGrid(Color.white);
                        grid[finalI][finalJ].setBackground(Color.orange);
                    }
                });

                // Aggiunta di KeyListener per catturare i tasti premuti
                grid[i][j].addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {// Aggiornare il testo del pulsante con il tasto premuto
                        // controllo che venga premuto un numero e non altri pulsanti della tastiera
                        // 48=0, 57=9, 96=numpad0, 105=numpad9
                        if ((e.getKeyCode() >= 49 && e.getKeyCode() <= 48+d) || (e.getKeyCode() >= 97 && e.getKeyCode() <= 96+d)) {

                            //repraceAll per togliere la parte "NumPad-" quando si premono numeri del numpad
                            String tStr = KeyEvent.getKeyText(e.getKeyCode()).replaceAll("[^0-9]", "");
                            grid[finalI][finalJ].setText(tStr);
                            Frame.getInstance().requestFocusInWindow();
                            grid[finalI][finalJ].setBackground(Color.white);

                            if(liveCheck) Configuration.getInstance().checkLiveSolution();
                        }
                    }
                });


                grid[i][j].setBackground(Color.white);
                grid[i][j].setFocusPainted(false); // Disabilita la parte evidenziata durante la digitazione del numero

                this.add(grid[i][j]);
            }
        }

    }

    private void paintGrid(Color color){
        for(JButton[] row:grid) {
            for(JButton c : row) {
                c.setBackground(color);
            }
        }
    }

    private void paintError(boolean[][] errorGrid){
        for(int i=0;i<d;i++){
            for(int j=0;j<d;j++){
                if(errorGrid[i][j]){
                    grid[i][j].setBackground(Color.red);
                    grid[i][j].setForeground(Color.BLACK);
                }
                else{
                    grid[i][j].setBackground(Color.white);
                }
            }
        }
    }

    /*observer method*/
    @Override
    public void update(boolean[][] liveSolution) {
        paintError(liveSolution);
    }

}
