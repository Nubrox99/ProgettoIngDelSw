import backtracking.Backtracking;
import controller.Configuration;
import model.Block;
import model.CreationGrid;
import model.LeftCreationMenu;
import model.RightCreationMenu;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import view.IGrid;
import view.IMenu;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class TestClass {
    private Configuration config;
    private IGrid grid;
    private IMenu menuSx, menuDx;
    private LinkedList<Block> blocks;

    @BeforeEach //funzione setup chiamata prima di ogni @Test
    void setUp() {;
        config = Configuration.getInstance();

        blocks = new LinkedList<>();

        blocks.add(Block.create(new LinkedList<>(Arrays.asList(0, 1, 2)), 0, 6));
        blocks.add(Block.create(new LinkedList<>(Arrays.asList(3, 6)), 1, 1));
        blocks.add(Block.create(new LinkedList<>(Arrays.asList(4, 7)), 2, 3));
//        blocks.add(Block.create(new LinkedList<>(Arrays.asList(5,8)), 3, 2));

        grid = new CreationGrid(3,blocks);
        menuSx = new LeftCreationMenu();
        menuDx = new RightCreationMenu();
        config.setGame(grid, menuSx, menuDx, blocks);
    }

    @Test //simulazione della selezione dei blocchi azzurri in fase di creazione
    public void testAddTemporaryButton() {
        config.addTemporaryButton(1);
        assertTrue(config.getTempBtn().contains(1));

        config.addTemporaryButton(1);
        assertFalse(config.getTempBtn().contains(1));
    }

    @Test //simulazione della creazione di un blocco dopo la selezione
    public void testCreateBlock() {
        config.resetGrid();
        config.addTemporaryButton(5);
        config.addTemporaryButton(8);
        config.createBlock(3, 2);

        assertEquals(4, config.getBlocks().size());
        assertEquals(2, config.getBlocks().getLast().getResult());
        assertEquals(3, config.getBlocks().getLast().getOperation());
    }

    @Test //controllo la funzione di checkAllBlocks utilizzata per verificare che tutte le celle siano in un blocco
    public void testCheckAllBlocks() {
        config.addTemporaryButton(5);
        config.addTemporaryButton(8);
        config.createBlock(3, 2);
        assertTrue(config.checkAllBlocks());
    }

    @Test //controllo che l'algoritmo di backtracking funzioni correttamente
    public void testBacktrackingSolve() {
        config.addTemporaryButton(5);
        config.addTemporaryButton(8);
        config.createBlock(3, 2);
        Backtracking backtracking = new Backtracking(3, blocks);
        LinkedList<int[][]> solutions = backtracking.solve(0);

        assertNotNull(solutions);
        assertFalse(solutions.isEmpty());
    }

    @Test//controllo se il backtracking si limita ad un numero massimo di soluzioni
    public void testMaxSolutionBacktrackingSolve() {
        //blocks contiene una configurazione con ben 12 soluzioni possibili
        blocks = new LinkedList<Block>( Arrays.asList( Block.create( new LinkedList<>(Arrays.asList(0,1,2,3,4,5,6,7,8)),0,18)));

        //prima controllo che calcoli bene le soluzioni e che siano 12
        Backtracking backtracking = new Backtracking(3, blocks);
        LinkedList<int[][]> solutions = backtracking.solve(0);
        assertNotNull(solutions);
        assertFalse(solutions.isEmpty());
        assertEquals(12,solutions.size());

        //Controllo che si limiti a 5
        Backtracking backtrackingLim = new Backtracking(3, blocks);
        LinkedList<int[][]> solutionsLimited = backtrackingLim.solve(5);
        assertEquals(5,solutionsLimited.size());
    }

}
