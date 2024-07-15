package controller;

import model.Block;
import backtracking.Backtracking;
import javax.swing.*;
import java.util.Arrays;
import java.util.LinkedList;

public class Solver {
    private int[][] gridValue;
    private int d;

    /*Singleton*/
    private static Solver instance;

    private Solver(){}

    public static Solver getInstance(){
        if(instance==null){
            instance = new Solver();
        }
        return instance;
    }
    /*end-Singleton*/

    /*Subject of controller.Observer*/
    private LinkedList<Observer> observers = new LinkedList<>();

    private boolean[][] liveSolution;

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(liveSolution);
        }
    }
    /*end-Subject*/

    public void check(int d, LinkedList<Block> blocks, int[][] gridValue){
        this.gridValue = gridValue;
        this.d = d;

        boolean checked = checkEmptyCells();
        for(Block b : blocks){
            if(checked && !b.isValid(gridValue))  checked=false;
        }

        if(checked && !checkRowCol()) checked =false;
        if(checked) JOptionPane.showMessageDialog(null, "Soluzione Valida");
        else JOptionPane.showMessageDialog(null, "Soluzione non valida");

    }

    public void checkLive(int d, LinkedList<Block> blocks, int[][]gridValue){

        this.gridValue = gridValue;
        this.d = d;

        boolean[][] errorGrid = new boolean[d][d];

        for(Block b : blocks){
            if(!b.isValid(gridValue)) {
//                System.out.println("blocco " + b.getCells() + " - "+b.getStringOperation()+b.getResult());
                for (int c : b.getCells()) {
                    errorGrid[c / d][c % d] = true;
                }
            }
        }

        //check Row and Col
        for(int i=0;i<d;i++){
            boolean[] seenRowElement = new boolean[d+1];
            boolean[] seenColElement = new boolean[d+1];
            for(int j=0;j<d;j++){
                //controlla se è un numero gia visto nella riga apparte lo 0
                if(gridValue[i][j]!=0 && seenRowElement[gridValue[i][j]]) {
//                    System.out.println("riga "+i);
                    for(int k=0;k<d;k++) errorGrid[i][k]=true;
                }
                seenRowElement[gridValue[i][j]] = true;
                //controlla se è un numero gia visto nella colonna apparte lo 0
                if(gridValue[j][i]!=0 && seenColElement[gridValue[j][i]]) {
//                    System.out.println("colonna "+i);
                    for(int k=0;k<d;k++) errorGrid[k][i]=true;
                }
                seenColElement[gridValue[j][i]] = true;
            }
        }

        liveSolution =  errorGrid;
        notifyObservers(); //Start Observer pattert
    }

    //punto di accesso per il backtracking
    public  LinkedList<int[][]> solve(int d, LinkedList<Block> blocks,int maxSol){
        this.gridValue = new int[d][d];
        this.d = d;
        Backtracking b = new Backtracking(d,blocks);
        return b.solve(maxSol);
    }

    private boolean checkEmptyCells(){
        for(int[] row :gridValue){
            for(int e : row){
                if(e==0) return false;
            }
        }
        return true;
    }

    private boolean checkRowCol(){
        for(int i=0;i<d;i++){
            boolean[] seenRowElement = new boolean[d+1];
            boolean[] seenColElement = new boolean[d+1];
            for(int j=0;j<d;j++){
                if(seenRowElement[gridValue[i][j]]) {return false;}
                seenRowElement[gridValue[i][j]] = true;

                if(seenColElement[gridValue[j][i]]) {return false;}
                seenColElement[gridValue[j][i]] = true;
            }
        }
        return true;
    }

}
