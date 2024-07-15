package model;

import controller.Configuration;
import view.IGrid;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class CreationGrid extends IGrid {

    public CreationGrid(int d, LinkedList<Block> b) {
        super(d, b, Color.lightGray);
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

                grid[i][j] = new JButton("B" + (i*d+j));
                grid[i][j].addActionListener(e -> {
                    buttonAction(finalI,finalJ);
                });

                grid[i][j].setBackground(Color.white);
                grid[i][j].setFocusPainted(false); // Disabilita la parte evidenziata durante la digitazione del numero

                this.add(grid[i][j]);
            }
        }
    }

    private void buttonAction(int i, int j){
        if(intGrid[i][j] == 0) {
            grid[i][j].setBackground(Color.CYAN);
            intGrid[i][j]=1;
            Configuration.getInstance().addTemporaryButton(i *d+j);
        }
        else if(intGrid[i][j] == 1){
            grid[i][j].setBackground(Color.white);
            intGrid[i][j]=0;
            Configuration.getInstance().addTemporaryButton(i *d+j);
        }
        else if(intGrid[i][j] == 2){

        }
    }
}