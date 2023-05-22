package br.sc.senac.mca.view;

import br.sc.senac.mca.util.OperacoesCrud;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientView extends JFrame{
    private JPanel pnlClientView;
    private JButton btnNovo;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnSair;
    private JTextField txtNome;
    private JTextField txtCpf;
    private JRadioButton rbMasculino;
    private JRadioButton rbFeminino;
    private JTextField txtEndereco;
    private JTextField txtTelefone;
    private JLabel lblEndereco;
    private JLabel lblTelefone;
    private JLabel lblNome;
    private JLabel lblCpf;
    private JButton btnSalvar;
    private JButton btnAtualizar;
    private JButton btnCancelar;
    private JTable table1;
    private JPanel pnlBotoesAcao;
    public Integer operacao;

    public ClientView(){
        initComponents();
        listeners();
    }
    public void initComponents(){
        setTitle("Cadastro de Clientes");
        setSize(1290,900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(pnlClientView);
        setVisible(true);

        pnlBotoesAcao.setVisible(false);
    }
    public void listeners(){
        btnNovo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operacao = OperacoesCrud.NOVO.getOperacao();

                btnEditar.setEnabled(false);
                btnAtualizar.setVisible(false);
                btnExcluir.setVisible(false);

                pnlBotoesAcao.setVisible(true);

                abrirCampos();
            }
        });
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operacao = OperacoesCrud.EDITAR.getOperacao();

            }
        });
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gravarAtualizarDados();
            }
        });
        btnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gravarAtualizarDados();
            }
        });
    }
    private void gravarAtualizarDados() {
        if (operacao == OperacoesCrud.NOVO.getOperacao()){

        } else if (operacao == OperacoesCrud.EDITAR.getOperacao()){

        }
    }
    private void abrirCampos(){
        txtNome.setEditable(true);
        txtCpf.setEditable(true);
        txtEndereco.setEditable(true);
        txtTelefone.setEditable(true);
    }
    public static void main(String[] args) {
        ClientView clientView = new ClientView();
    }
}
