package br.sc.senac.mca.view;

import br.sc.senac.mca.controller.LoginController;
import br.sc.senac.mca.model.Login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import static javax.swing.JFrame.*;

public class LoginView extends JFrame {
    private JPanel panel;
    private JLabel lblUsuario;
    private JLabel lblSenha;


    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnLimpar;
    private JButton btnLogin;

    public LoginView() {
        initComponents();
        listeners();
    }
    public void initComponents() {
        setTitle("Tela de login");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        setVisible(true);
    }
    public void listeners() {

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String login = txtLogin.getText();
                char[] senha = txtSenha.getPassword();

                try {
                    LoginController lc = new LoginController();
                    ArrayList<Login> loginList = lc.buscarLoginSenha();

                    for (Login l : loginList) {
                        if (l.getLogin().equalsIgnoreCase(login) &&
                                l.getSenha().equalsIgnoreCase(new String(senha))) {
                            System.out.println("Login validado");

                            SystemView s = new SystemView();
                            dispose();

                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Verifique usuário e senha",
                                    "Erro",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    System.out.println("Classe não encontrada");
                }
            }
        });
    }
    public static void main(String[] args) {
        LoginView lv = new LoginView();
    }
}
