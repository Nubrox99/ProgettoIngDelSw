package model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;

public class Block implements Serializable {
    private LinkedList<Integer> cells;
    private int operation; //  0(+) | 1(-) | 2(*) | 3(/)
    private int result;

    private Block(LinkedList<Integer> c, int o, int r) {
        cells = c;
        operation = o;
        result = r;
    }

    public static Block create(LinkedList<Integer> c, int o, int r) {
        if (c.size() != 2 && (o == 1 || o == 3)) {
            return null;
        }
        return new Block(c, o, r);
    }

    public Block(String str){
        String[] parts = str.split(";");

        LinkedList<Integer> cells = new LinkedList<>();
        for (String c : parts[0].split(",")) {
            cells.add(Integer.parseInt(c.trim()));
        }

        int o = Integer.parseInt(parts[1].trim());
        int r = Integer.parseInt(parts[2].trim());

        this.cells = cells;
        operation=o;
        result=r;
    }

    public LinkedList<Integer> getCells() {
        return cells;
    }

    public int getOperation() {
        return operation;
    }

    public String getStringOperation(){
        switch(operation){
            case 0 : return "+";
            case 1 : return "-";
            case 2 : return "×";
            case 3 : return "÷";
            default: return "";
        }
    }

    public int getResult() {
        return result;
    }

    @Override
    public String toString(){
        String msg = "";
        for(int c : cells){
            msg += c + ",";
        }
        msg = msg.substring(0, msg.length() - 1);
        msg += ";" + operation + ";" + result;
        return msg;
    }


    public boolean isValid(int[] values){
        switch (operation){
            case 0:
                return Arrays.stream(values).sum() == result;
            case 1:
                Arrays.sort(values);
                return values[1] - values[0] == result;
            case 2:
                int p = 1;
                for(int v : values) p*=v;
                return p==result;
            case 3:
                Arrays.sort(values);
                if(values[0]==0) return false;
                return values[1] / values[0] == result;

            default: return false;
        }
    }

    public boolean isValid(int[][] gridValue){
        int[] values = getValueOfCells(gridValue);
        return isValid(values);
    }

    private int[] getValueOfCells(int[][] gridValue){
        int[] values = new int[cells.size()];
        int counter = 0;
        for(int c : cells){
            values[counter] = gridValue[c/gridValue.length][c%gridValue.length];
            counter++;
        }
        return values;
    }
}
