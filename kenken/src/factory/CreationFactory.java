package factory;

import view.IMenu;
import view.IGrid;
import model.*;
import java.util.LinkedList;

public class CreationFactory extends AbstractFactory {
    @Override
    public IGrid createGrid(int d, LinkedList<Block> b) {
        return new CreationGrid(d, b);
    }

    @Override
    public IMenu createLeftMenu() {
        return new LeftCreationMenu();
    }

    @Override
    public IMenu createRightMenu() {
        return new RightCreationMenu();
    }
}
