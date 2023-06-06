package br.sc.senac.mca.view;

import br.sc.senac.mca.controller.ClienteController;
import br.sc.senac.mca.model.Cliente;
import br.sc.senac.mca.util.OperacoesCrud;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.demo.DateChooserPanel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private JPanel pnlDados;
    private JPanel pnl;
    private JPanel pnlTabela;
    public Integer operacao;
    private String sexo;

    public ClientView(){
        initComponents();
        listeners();
    }
    public void initComponents(){
        setTitle("Cadastro de Clientes");
        setSize(900,600);
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
                btnAtualizar.setEnabled(true);
                btnExcluir.setEnabled(false);

                pnlBotoesAcao.setVisible(true);

                abrirCampos();
                LimparCampos();
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

       rbFeminino.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               rbMasculino.setSelected(false);
               sexo = rbFeminino.getText();
           }
       });
        rbMasculino.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rbFeminino.setSelected(false);
                sexo = rbMasculino.getText();
            }
        });
    }
    private void gravarAtualizarDados() {

        //recupera dados da tela
        String nome = txtNome.getText();
        String cpf = txtCpf.getText();
        String fone = txtTelefone.getText();

        //atribui dados da tela para o objeto cliente
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setFone(fone);
        cliente.setSexo(formatarCampoSexo(sexo));

        ClienteController clienteController = new ClienteController();

        try{
            if (operacao == OperacoesCrud.NOVO.getOperacao()){
                clienteController.cadastrar(cliente);
                JOptionPane.showMessageDialog(null, "O cliente " +
                        cliente.getNome()+ " foi criado com sucesso!");
                        LimparCampos();
            } else if (operacao == OperacoesCrud.EDITAR.getOperacao()){
            }
        } catch (SQLException ex){
            Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void abrirCampos(){
        txtNome.setEditable(true);
        txtCpf.setEditable(true);
        txtEndereco.setEditable(true);
        txtTelefone.setEditable(true);
    }
    private String formatarCampoSexo(String sexo){
        if (sexo.equals("Masculino")){
            sexo = "M";
        } else {
            sexo = "F";
        }
        return sexo;
    }
    private void LimparCampos(){
        txtNome.setText(" ");
        txtCpf.setText(" ");
        txtEndereco.setText(" ");
        txtTelefone.setText(" ");
    }
    public static void main(String[] args) {
        ClientView clientView = new ClientView();
    }
}
