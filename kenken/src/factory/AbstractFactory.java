package factory;
import view.IMenu;
import view.IGrid;
import model.Block;

import java.util.LinkedList;

public abstract class AbstractFactory {
    public abstract IGrid createGrid(int d, LinkedList<Block> b);
    public abstract IMenu createLeftMenu();
    public abstract IMenu createRightMenu();
}

