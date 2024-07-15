package view;

import model.Block;

import javax.swing.*;
import java.util.LinkedList;

public abstract class IMenu extends JPanel {

    public IMenu(){}

    //metodi di comunicazione tra menusx e menudx durante la creazione
    public void updateTempButton(LinkedList<Integer> tempBtn){return;}
    public void updateListBlock(LinkedList<Block> blocks){return;}


}
