package factory;

import view.IMenu;
import view.IGrid;
import model.*;
import java.util.LinkedList;

public class GameFactory extends AbstractFactory {
    @Override
    public IGrid createGrid(int d, LinkedList<Block> b) {
        return new GameGrid(d, b);
    }

    @Override
    public IMenu createLeftMenu() {
        return new LeftGameMenu();
    }

    @Override
    public IMenu createRightMenu() {
        return new RightGameMenu();
    }
}
