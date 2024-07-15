package backtracking;

import model.Block;
import java.util.LinkedList;

public class Backtracking implements Problema<int[],Integer>{
    private int[][] grid;
    private int d, maxSolution, nSol=0;
    private LinkedList<Block> blocks;
    private LinkedList<int[][]> allSolutions;


    public Backtracking(int d, LinkedList<Block> blocks){
        this.grid = new int[d][d];
        this.d = d;
        this.blocks=blocks;
    }

    public LinkedList<int[][]> solve(int m){
        allSolutions = new LinkedList<int[][]>();
        if(m==0) maxSolution = Integer.MAX_VALUE;
        else maxSolution=m;
        risolvi(maxSolution);
        return allSolutions;
    }

    @Override
    public int[] primoPuntoDiScelta() {
        return new int[]{0,0};
    }

    @Override
    public int[] prossimoPuntoDiScelta(int[] ps) {
        int row=ps[0], col=ps[1];
        if(col < d-1){
            return new int[]{row, col+1};
        }else if(row < d-1){
            return new int[]{row+1, 0};
        }
        return null;
    }

    @Override
    public int[] ultimoPuntoDiScelta() {
        return new int[]{d-1, d-1};
    }

    @Override
    public Integer primaScelta(int[] ps) {
        return 1;
    }

    @Override
    public Integer prossimaScelta(Integer s) {
        if(s < d) return s+1;
        else return null;
    }

    @Override
    public Integer ultimaScelta(int[] ps) {
        return d;
    }

    @Override
    public boolean assegnabile(Integer scelta, int[] puntoDiScelta) {
        int row = puntoDiScelta[0], col = puntoDiScelta[1];
        //verifica verticale
        for(int i=0; i<row; i++) if(grid[i][col] == scelta) return false;
        //verifica orizzontale
        for(int j=0; j<col; j++) if(grid[row][j] == scelta) return false;
        //verifica blocco
        return checkBlock(row,col,scelta);
    }

    @Override
    public void assegna(Integer scelta, int[] puntoDiScelta) {
        int row = puntoDiScelta[0],col = puntoDiScelta[1];
        grid[row][col] = scelta;
    }

    @Override
    public void deassegna(Integer scelta, int[] puntoDiScelta) {
        int row = puntoDiScelta[0], col = puntoDiScelta[1];
        grid[row][col] = 0;
    }

    @Override
    public boolean ultimoPuntoDiScelta(int[] puntoDiScelta) {
        return puntoDiScelta[0] == d - 1 && puntoDiScelta[1] == d - 1;
    }

    @Override
    public boolean primoPuntoDiScelta(int[] puntoDiScelta) {
        return puntoDiScelta[0] == 0 && puntoDiScelta[1] == 0;
    }

    @Override
    public int[] precedentePuntoDiScelta(int[] puntoDiScelta) {
        int row = puntoDiScelta[0], col = puntoDiScelta[1];

        if (col > 0) return new int[]{row, col - 1};
        else if (row > 0) return new int[]{row - 1, d - 1};

        return null;//new int[]{0, 0};
    }

    @Override
    public Integer ultimaSceltaAssegnataA(int[] puntoDiScelta) {
        return grid[puntoDiScelta[0]][puntoDiScelta[1]];

    }

    @Override
    public void scriviSoluzione(int nr_sol) {
        nSol++;
        if(maxSolution!=0 && nSol > maxSolution) return;

        int[][] solutionCopy = new int[d][d];
        for (int i = 0; i < d;i++) for (int j = 0; j < d; j++) solutionCopy[i][j] = grid[i][j];

        allSolutions.add(solutionCopy);
    }


    private boolean checkBlock(int row, int col, int num) {

        Block b = getBlock(row,col);
        int[] values = new int[b.getCells().size()];

        int counter=0;
        for(int c : b.getCells()){
            //assegna al vettore values il valore contenuto nel grid
            values[counter]=(grid[c/d][c%d]);

            //se la cella in analisi è quella analizzata dal backtracking si assegna al vettore values il valore assegnabile num
            if(c/d == row && c%d == col){
                if(grid[c/d][c%d] == 0) values[counter] = num;
            }
            if(values[counter]==0)return true;
            counter++;
        }
        return b.isValid(values);
    }


    private Block getBlock(int row, int col){
        for(Block b : blocks ){
            if(b.getCells().contains(row*d+col)){
                return Block.create(b.getCells(),b.getOperation(),b.getResult());
            }
        }
        return null;
    }
}


