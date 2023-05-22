package br.sc.senac.mca.view;

import javax.swing.*;

public class SearchView extends JFrame{
    private JPanel pnlSearch;

    public SearchView(){
        initComponents();

    }
    public void initComponents(){
        setTitle("Pesquisa de clientes");
        setSize(1290,900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(pnlSearch);
        setVisible(true);

    }
}
