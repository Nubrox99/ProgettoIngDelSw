package model;


import view.IMenu;
import controller.Configuration;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LeftGameMenu extends IMenu {
    private boolean liveCheck = false;
    public LeftGameMenu(){
        super();
        init();
    }
    private void init(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(5, 5, 5, 5));

        Panel emptyPanel = new Panel();
        emptyPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        add(emptyPanel);

        //pulsante live auto correzione
        JPanel panel0 = new JPanel();
        panel0.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel0.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        JButton liveBtn = new JButton("Live");
        liveBtn.setForeground(Color.white);
        liveBtn.setBackground(Color.red);
        panel0.add(liveBtn);
        add(panel0);


        //pulsante check correzione manuale
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        JButton checkBtn = new JButton("Check");
        panel1.add(checkBtn);
        add(panel1);


        checkBtn.addActionListener(e -> {
            Configuration.getInstance().checkSolution();
        });
        liveBtn.addActionListener(e -> {
            if(liveCheck) {
                liveCheck=false;
                liveBtn.setBackground(Color.RED);
                liveBtn.setForeground(Color.white);
            }
            else {
                liveCheck=true;
                liveBtn.setBackground(Color.green);
                liveBtn.setForeground(Color.white);
            }

            Configuration.getInstance().setLiveCheck(liveCheck);
        });

    }
}
