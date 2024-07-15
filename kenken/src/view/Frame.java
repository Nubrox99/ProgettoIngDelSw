package view;

import controller.Configuration;
import factory.AbstractFactory;
import factory.CreationFactory;
import factory.GameFactory;
import model.Block;
import model.CreationGrid;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.LinkedList;


public class Frame extends JFrame {
    final int LARGHEZZA = 1000;
    final int ALTEZZA = 600;
    private IGrid grid;
    private IMenu menuSx, menuDx;
    private AbstractFactory factory;

    /*Singleton*/
    private static Frame instance;

    private Frame(){
        super("KenKen");
        this.setSize(LARGHEZZA,ALTEZZA);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static Frame getInstance(){
        if(instance==null){
            instance = new Frame();
        }
        return instance;
    }
    /*end-Singleton*/


    private void init(){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //creazione e setting titolo
        JLabel titleLabel = new JLabel("KENKEN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 72));

        //setting Constraints e aggiunta titolo al frame
        gbc.insets = new Insets(30, 0, 0, 0); // Margine superiore per distaccare dal bordo alto
        gbc.anchor = GridBagConstraints.NORTH;
        add(titleLabel, gbc);

        //spazio vuoto 40%
        gbc.weighty = .4;
        this.add(Box.createVerticalGlue(), gbc);

        //pannello per i pulsanti
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 80, 0));

        //--pulsanti
        Dimension buttonSize = new Dimension(250, 100);
        //nuova partita
        JButton newButton = new JButton("NUOVO");
        newButton.setPreferredSize(buttonSize);
        newButton.setFont(new Font("Arial",Font.BOLD,26));
        newButton.setBorder(new LineBorder(Color.BLACK, 5));
        panel.add(newButton);
        //carica partita
        JButton loadButton = new JButton("CARICA");
        loadButton.setPreferredSize(buttonSize);
        loadButton.setFont(new Font("Arial",Font.BOLD,26));
        loadButton.setBorder(new LineBorder(Color.BLACK, 5));
        panel.add(loadButton);

        // Constraints e aggiunta pannello pulsanti
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(panel, gbc);

        // spazio vuoto
        gbc.gridy = 3;
        gbc.weighty = 1.0;
        add(Box.createVerticalGlue(), gbc);

        //-----ButtonClick

        //funzione di caricamento
        loadButton.addActionListener(e -> {
            Configuration.getInstance().importFile();
        });

        //funzione creazione partita
        newButton.addActionListener(e -> {
            initCreateGame();
        });

        setVisible(true);
        SwingUtilities.updateComponentTreeUI(this);

    }

    //factory Start
    public void initLoadedGame(int d,LinkedList<Block> blocks){
        factory = new GameFactory();
        initGame(d,blocks);
    }

    public void initCreateGame(){
        factory = new CreationFactory();
        initGame(2,new LinkedList<>());
    }

    public void initGame(int d,LinkedList<Block> blocks){

        getContentPane().removeAll();
        setLayout(new BorderLayout());

        grid = factory.createGrid(d,blocks);
        menuSx = factory.createLeftMenu();
        menuDx = factory.createRightMenu();

        add(menuSx, BorderLayout.WEST);
        add(menuDx, BorderLayout.EAST);
        add(grid, BorderLayout.CENTER);

        Configuration.getInstance().setGame(grid,menuSx,menuDx,blocks);
        setVisible(true);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void initializeGrid(int d){
        if(Configuration.getInstance().getBlocks().size()!=0) {
            int a = JOptionPane.showConfirmDialog(this, "Proseguendo perderai i progressi fatti. Vuoi continuare?", "Attenzione", JOptionPane.YES_NO_OPTION);
            if (a !=0) return;
        }

        remove(grid);
        grid = new CreationGrid(d,new LinkedList<>());
        add(grid,BorderLayout.CENTER);
        Configuration.getInstance().setGame(grid,menuSx,menuDx,new LinkedList<Block>());
        menuDx.updateListBlock(new LinkedList<>());
        SwingUtilities.updateComponentTreeUI(this);
    }

    public static void main(String[] args) {
        Frame.getInstance().init();
    }
}
