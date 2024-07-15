package model;

import controller.Configuration;
import view.IMenu;
import view.Frame;
import model.Block;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class RightGameMenu extends IMenu {

    private LinkedList<int[][]> allSolution;
    private int indexSolution = 0;

    public RightGameMenu(){
        super();
        init();
    }
    private void init(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(5, 5, 5, 5));

        Panel emptyPanel = new Panel();
        emptyPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        add(emptyPanel);

        JPanel panelMenu = new JPanel();;
        panelMenu.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JButton importBtn = new JButton("Load new");
        JButton createBtn = new JButton("Create new");

        panelMenu.add(importBtn);
        panelMenu.add(createBtn);

        add(panelMenu);


        //label "numero soluzioni"
        JPanel panel0 = new JPanel();
        panel0.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel0.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        JLabel lblNumSol = new JLabel("Numero soluzioni : ");
        lblNumSol.setFont(new Font("Arial",Font.BOLD,20));
        panel0.add(lblNumSol);
        add(panel0);

        JPanel panel0bis = new JPanel();
        panel0bis.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel0bis.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        panel0bis.add(new JLabel("(0 = tutte le soluzioni)"));
        add(panel0bis);


        //pulsante live auto correzione
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        JTextField nSolTxtBox= new JTextField(5);
        nSolTxtBox.setText("0");
        nSolTxtBox.setMaximumSize(new Dimension(30, 60));
        panel1.add(nSolTxtBox);
        add(panel1);


        // Dropdown dimensioni disponibili
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));


        JButton calcSolBtn = new JButton("Calcola");
        panel2.add(calcSolBtn);
        add(panel2);

        JPanel panel3 = new JPanel();;
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel3.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JButton prevSolBtn = new JButton("<-");
        prevSolBtn.setEnabled(false);

        JButton nextSolBtn = new JButton("->");
        nextSolBtn.setEnabled(false);
        JLabel solLbl =new JLabel("");

        panel3.add(prevSolBtn);
        panel3.add(solLbl);
        panel3.add(nextSolBtn);

        add(panel3);

        JPanel panel4 = new JPanel();;
        panel4.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel4.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JButton resetBtn = new JButton("reset");

        panel4.add(resetBtn);

        add(panel4);

        //controllo inserimento solo numeri
        nSolTxtBox.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent k){
                if ((k.getKeyChar() >= '0' && k.getKeyChar() <= '9') || (k.getKeyCode()==KeyEvent.VK_BACK_SPACE || k.getKeyCode()==KeyEvent.VK_DELETE)) {
                    nSolTxtBox.setEditable(true);
                } else {
                    nSolTxtBox.setEditable(false);
                }
            }
        });
        resetBtn.addActionListener(e -> {
            Configuration.getInstance().resetGrid();
            solLbl.setText("");
            prevSolBtn.setEnabled(false);
            nextSolBtn.setEnabled(false);
        });

        calcSolBtn.addActionListener(e -> {
            int nsol=0;
            if(!nSolTxtBox.getText().equals("")) nsol=Integer.parseInt(nSolTxtBox.getText());

            allSolution = Configuration.getInstance().calculateSolution(nsol);
            resetBtn.setEnabled(true);
            if(allSolution.size()==0) {
                indexSolution=0;
                solLbl.setText("0 / 0");
                prevSolBtn.setEnabled(false);
                nextSolBtn.setEnabled(false);
            }
            else {
                indexSolution=1;
                solLbl.setText(indexSolution+" / " + allSolution.size());

                if(indexSolution<allSolution.size()) nextSolBtn.setEnabled(true);
                else nextSolBtn.setEnabled(false);

                if(indexSolution>1) prevSolBtn.setEnabled(true);
                else prevSolBtn.setEnabled(false);
                Configuration.getInstance().fillGrid(allSolution.get(indexSolution-1));
            }
        });

        prevSolBtn.addActionListener(e -> {
            indexSolution--;
            if(indexSolution>1) prevSolBtn.setEnabled(true);
            else prevSolBtn.setEnabled(false);
            if(indexSolution<allSolution.size()) nextSolBtn.setEnabled(true);
            else nextSolBtn.setEnabled(false);

            solLbl.setText(indexSolution+" / " + allSolution.size());
            Configuration.getInstance().fillGrid(allSolution.get(indexSolution-1));

        });

        nextSolBtn.addActionListener(e -> {
            indexSolution++;
            if(indexSolution<allSolution.size()) nextSolBtn.setEnabled(true);
            else nextSolBtn.setEnabled(false);
            if(indexSolution>1) prevSolBtn.setEnabled(true);
            else prevSolBtn.setEnabled(false);

            solLbl.setText(indexSolution+" / " + allSolution.size());
            Configuration.getInstance().fillGrid(allSolution.get(indexSolution-1));
        });

        importBtn.addActionListener(e -> {
            int a=JOptionPane.showConfirmDialog(Frame.getInstance(),"Proseguendo perderai i progressi fatti. Vuoi continuare?", "Attenzione", JOptionPane.YES_NO_OPTION );
            if(a==JOptionPane.YES_OPTION){
                Configuration.getInstance().importFile();
            }
        });
        createBtn.addActionListener(e -> {
            int a=JOptionPane.showConfirmDialog(Frame.getInstance(),"Proseguendo perderai i progressi fatti. Vuoi continuare?", "Attenzione", JOptionPane.YES_NO_OPTION );
            if(a==JOptionPane.YES_OPTION){
                Configuration.getInstance().createNewGame();
            }
        });

    }
}
