package br.sc.senac.mca.view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import static javax.swing.JFrame.*;

public class SystemView extends JFrame {

    JPanel pnlSystem = new JPanel();
    JMenuBar menuBar = new JMenuBar();
 //   JScrollPane scrollPane = new JScrollPane(pnlSystem);

    //JButton btnPesquisa = new JButton("Pesquisar");
  //  JButton btnCliente = new JButton("Clientes");

    public SystemView(){
        initComponents();
        initMenuBar();
        listeners();
    }
    public void initComponents(){
        setTitle("Tela de sistema");
        setSize(1920,1080);
        //setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(pnlSystem);
        setVisible(true);
        pnlSystem.setLayout(null);
        setJMenuBar(menuBar);
     //   add(btnPesquisa);
      //  add(btnCliente);
        //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
       // scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
       // setContentPane(scrollPane);

    }
    public void initMenuBar(){
        JMenu cadastrosMenu = new JMenu("Cadastros");
        JMenu pesquisarMenu = new JMenu("Pesquisar");
        JMenu sairMenu = new JMenu("Sair");
        JMenuItem clienteItem = new JMenuItem("Cliente");
        JMenuItem pesquisarItem = new JMenuItem("Pesquisar cliente");
        JMenuItem sairItem = new JMenuItem("Sair");

        cadastrosMenu.add(clienteItem);

        pesquisarMenu.add(pesquisarItem);

        sairMenu.add(sairItem);

        menuBar.add(cadastrosMenu);
        menuBar.add(pesquisarMenu);
        menuBar.add(sairMenu);

        clienteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClientView();
                dispose();
            }
        });
        pesquisarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchView();
                dispose();
            }
        });
        sairItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = "Deseja sair do sistema?";

                int opcaoEscolhida = JOptionPane.showConfirmDialog(
                        null,
                        msg,
                        "Sistema",
                        JOptionPane.YES_NO_OPTION);

                if (opcaoEscolhida == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });
    }
    public void listeners(){

        ImageIcon user = new ImageIcon("C:\\Users\\Karen\\IdeaProjects\\mca\\src\\br\\sc\\senac\\mca\\img\\add-user.png");
        JButton btnCliente = new JButton("Clientes", user);
        btnCliente.setBounds(450,300,150,100);
      // btnCliente.setSize(100,100);
      // btnCliente.setLocation(640,400);

        pnlSystem.add(btnCliente);

        btnCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientView clientView = new ClientView();
                dispose();
            }
        });

        ImageIcon search = new ImageIcon("C:\\Users\\Karen\\IdeaProjects\\mca\\src\\br\\sc\\senac\\mca\\img\\people.png");
        JButton btnPesquisar = new JButton("Pesquisar", search);
        btnPesquisar.setBounds(650,300,150,100);
        pnlSystem.add(btnPesquisar);

        btnPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchView searchView = new SearchView();
                dispose();
            }
        });
    }
    public static void main(String[] args) {
        SystemView sv = new SystemView();
    }
}
