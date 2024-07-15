package model;

import controller.Configuration;
import view.IMenu;
import view.Frame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.LinkedList;

public class LeftCreationMenu extends IMenu {

    private JTextArea selectedButtonLbl;
    private JButton insertBlockBtn;
    private JTextField risTxtBox;


    public LeftCreationMenu(){
        super();
        init();
    }
    private void init(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(5, 5, 5, 5));

        Panel emptyPanel = new Panel();
        emptyPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        add(emptyPanel);

        //label "Dimensione Griglia"
        JPanel panel0 = new JPanel();
        panel0.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel0.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel0.add(new JLabel("Dimensione Griglia"));
        add(panel0);


        // Dropdown dimensioni disponibili
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));


        JComboBox<Integer> dimensionDrop = new JComboBox<Integer>(new Integer[]{2,3,4,5,6,7,8,9});
        dimensionDrop.setPreferredSize(new Dimension(60, 30));
        JButton updateBtn = new JButton("CARICA");


        panel1.add(dimensionDrop);
        panel1.add(updateBtn);
        add(panel1);

        JPanel panel2 = new JPanel();;
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JPanel panel2bis = new JPanel();;
        panel2bis.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel2bis.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        //label "Pulsanti selezionati"
        panel2.add(new JLabel("Pulsanti selezionati:"));

        //label aggiornabile che riporta pulsanti selezionati
        selectedButtonLbl = new JTextArea("");
        selectedButtonLbl.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        selectedButtonLbl.setPreferredSize(new Dimension(200, 60));
        selectedButtonLbl.setLineWrap(true);
        selectedButtonLbl.setWrapStyleWord(true);
        selectedButtonLbl.setEditable(false);
        selectedButtonLbl.setEnabled(false);
        selectedButtonLbl.setOpaque(false);
        selectedButtonLbl.setDisabledTextColor(Color.BLACK);

        panel2bis.add(selectedButtonLbl);

        add(panel2);
        add(panel2bis);

        //pannello selezione operazione
        JPanel panel3 = new JPanel();;
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel3.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JComboBox<String> operationDrop = new JComboBox<>(new String[]{"+", "-", "×","÷"});
        operationDrop.setPreferredSize(new Dimension(60, 30));


        panel3.add(new JLabel("Operazione : "));
        panel3.add(operationDrop);


        add(panel3);

        // pannello selezione risultato
        JPanel panel4 = new JPanel();;
        panel4.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel4.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        risTxtBox = new JTextField(15);
        risTxtBox.setText("");
        risTxtBox.setMaximumSize(new Dimension(30, 60));

        panel4.add(new JLabel("Risultato : "));
        panel4.add(risTxtBox);

        add(panel4);

        JPanel panel5 = new JPanel();;
        panel5.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel5.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        insertBlockBtn = new JButton("Salva Blocco");
        insertBlockBtn.setEnabled(false);

        panel5.add(insertBlockBtn);
        add(panel5);

        updateBtn.addActionListener(e -> {
            Frame.getInstance().initializeGrid((int)dimensionDrop.getSelectedItem());
        });

        risTxtBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                insertBlockBtn.setEnabled(!selectedButtonLbl.getText().equals("") && !risTxtBox.getText().equals(""));
            }
            public void removeUpdate(DocumentEvent e) {
                insertBlockBtn.setEnabled(!selectedButtonLbl.getText().equals("") && !risTxtBox.getText().equals(""));
            }
            public void changedUpdate(DocumentEvent e) {}
        });

        //controllo inserimento solo numeri
        risTxtBox.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent k){
                if ((k.getKeyChar() >= '0' && k.getKeyChar() <= '9') || (k.getKeyCode()==KeyEvent.VK_BACK_SPACE || k.getKeyCode()==KeyEvent.VK_DELETE)) {
                    risTxtBox.setEditable(true);
                } else {
                    risTxtBox.setEditable(false);
                }
            }
        });

        insertBlockBtn.addActionListener(e -> {
            Configuration.getInstance().createBlock(operationDrop.getSelectedIndex(),Integer.parseInt(risTxtBox.getText()));
            selectedButtonLbl.setText("");
            risTxtBox.setText("");
        });
    }

    public void updateTempButton(LinkedList<Integer> tempBtn){
        String txt = "";
        for(int t : tempBtn){
            txt += t + " - ";
        }
        if(txt.length()>2) selectedButtonLbl.setText(txt.substring(0, txt.length() - 2));
        else selectedButtonLbl.setText(txt);

        insertBlockBtn.setEnabled(tempBtn.size()!=0 && !risTxtBox.getText().equals(""));
    }
}
