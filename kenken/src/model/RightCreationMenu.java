package model;

import controller.Configuration;
import view.IMenu;
import view.Frame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.LinkedList;

public class RightCreationMenu extends IMenu {

    private JPanel panelBlocks;
    private JButton sendConfBtn;

    public RightCreationMenu(){
        super();
        init();
    }
    private void init(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(5, 5, 5, 5));

        Panel emptyPanel = new Panel();
        emptyPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        add(emptyPanel);


        JPanel panelMenu = new JPanel();;
        panelMenu.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JButton importBtn = new JButton("Load grid");
        importBtn.addActionListener(e -> {
            int a=JOptionPane.showConfirmDialog(Frame.getInstance(),"Proseguendo perderai i progressi fatti. Vuoi continuare?", "Attenzione", JOptionPane.YES_NO_OPTION );
            if(a==JOptionPane.YES_OPTION){
                Configuration.getInstance().importFile();
            }
        });

        panelMenu.add(importBtn);

        add(panelMenu);


        // Dropdown dimensioni disponibili
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));


        sendConfBtn = new JButton("Salva configurazione");
        sendConfBtn.setEnabled(false);
        sendConfBtn.addActionListener(e -> {
            Configuration.getInstance().sendConfiguration();

        });
        panel1.add(sendConfBtn);
        add(panel1);

        JPanel panel2 = new JPanel();;
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        panel2.add(new JLabel("Blocchi creati : "));
        add(panel2);


        //pannello Lista Blocchi
        panelBlocks = new JPanel();;
        JScrollPane scroller = new JScrollPane(panelBlocks);

        panelBlocks.setLayout(new BoxLayout(panelBlocks, BoxLayout.Y_AXIS));

//        panelBlocks.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBlocks.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        add(scroller);
//        add(panelBlocks);

    }

    //funzioni menuDx
    public void updateListBlock(LinkedList<Block> blocks){
        panelBlocks.removeAll();
        panelBlocks.revalidate();
        panelBlocks.repaint();

        for(Block b:blocks){
            JPanel tempPanel = new JPanel();;
            tempPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
            tempPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

            JButton removeBlock = new JButton("del");
            removeBlock.addActionListener(e -> {
                Configuration.getInstance().removeBlock(b);
            });

            tempPanel.add(new JLabel(b.getCells() + " : " + b.getResult() + b.getStringOperation()));
            tempPanel.add(removeBlock);


            panelBlocks.add(tempPanel);


        }
        sendConfBtn.setEnabled(Configuration.getInstance().checkAllBlocks());
    }
}

