package controller;

import model.Block;
import view.IMenu;
import view.IGrid;
import view.Frame;

import javax.swing.*;
import java.io.*;
import java.util.LinkedList;

public class Configuration {
    private IMenu menuSx,menuDx;
    private IGrid grid;
    private LinkedList<Block> blocks;
    private LinkedList<Integer> tempBtn;
    private boolean liveCheck = false;

    /*Singleton*/
    private static Configuration instance;

    private Configuration(){}

    public static Configuration getInstance(){
        if(instance==null){
            instance = new Configuration();
        }
        return instance;
    }
    /*end-Singleton*/

    public void setGame(IGrid g, IMenu mSx, IMenu mDx, LinkedList<Block> b){
        grid=g;
        menuSx=mSx;
        menuDx=mDx;
        tempBtn = new LinkedList<Integer>();
        blocks = b;
    }

    public LinkedList<Block> getBlocks(){return blocks;}

    public LinkedList<Integer> getTempBtn(){return tempBtn;}

    public void setLiveCheck(boolean liveCheck) {
        this.liveCheck = liveCheck;
        grid.setLiveCheck(liveCheck);
    }

    //----- Funzioni Creazione -----//
    public void createNewGame(){
        Frame.getInstance().initCreateGame();
    }

    public void addTemporaryButton(int nBtn){
        if(tempBtn.contains(nBtn)) {
            tempBtn.removeFirstOccurrence(nBtn);
        }
        else{
            tempBtn.add(nBtn);
        }
        menuSx.updateTempButton(tempBtn);
    }

    public void createBlock(int operation,int result){
        Block tmpBlock = Block.create((LinkedList<Integer>)tempBtn.clone(),operation,result);
        if(tmpBlock==null) JOptionPane.showMessageDialog(null, "Blocco non valido");
        else blocks.add(tmpBlock);
        grid.updateBlocks(blocks);
        tempBtn.clear();
        menuDx.updateListBlock(blocks);
    }


    public void removeBlock(Block block){
        blocks.removeFirstOccurrence(block);
        grid.updateBlocks(blocks);
        tempBtn.clear();
        menuDx.updateListBlock(blocks);
    }

    //controlla se sono stati utilizzati tutti i blocchi nella configurazione di creazione
    public boolean checkAllBlocks(){
        int counter=0;
        for(Block b : blocks){
            for(int c:b.getCells()){
                counter++;
            }
        }
        return counter==Math.pow(grid.getD(),2);
    }

    public void sendConfiguration(){
        saveFile();
        Frame.getInstance().initLoadedGame(grid.getD(),blocks);
    }


    //----- Funzioni Game -----//
    public void checkSolution(){
        Solver.getInstance().check(grid.getD(), blocks, grid.getGridValue());
    }

    public void checkLiveSolution(){
        grid.setSolver(Solver.getInstance());
        Solver.getInstance().checkLive(grid.getD(),blocks, grid.getGridValue());
    }

    public LinkedList<int[][]> calculateSolution(int maxSol){
        return Solver.getInstance().solve(grid.getD(), blocks, maxSol);
    }

    public void resetGrid() {
        grid.resetGrid();
    }
    public void fillGrid(int[][] solution){
        grid.drawSolution(solution);
    }

    private void saveFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("puzzles/last.txt"));
            int last = Integer.parseInt(br.readLine());
            last++;

            String path = "puzzles/puzz" + last + "_" + grid.getD()+"x"+grid.getD()+".dat";
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(blocks);
            oos.close();

            PrintWriter pw = new PrintWriter(new FileOutputStream("puzzles/last.txt"), true);
            pw.println(last);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void importFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")+"\\puzzles"));
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(selectedFile));

                blocks = (LinkedList<Block>) ois.readObject();
                ois.close();

                Frame.getInstance().initLoadedGame(getGridDimension(blocks), blocks);

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //funzione che da una lista di blocchi restituisce il numero di dimensione della griglia.
    private int getGridDimension(LinkedList<Block> blocks){
        int maxCell = 0;
        for(Block b: blocks)
            for(int c : b.getCells())
                if(maxCell<c) maxCell=c;

        return (int)Math.sqrt(maxCell+1);
    }

}
