package br.sc.senac.mca.view;

import br.sc.senac.mca.controller.ClienteController;
import br.sc.senac.mca.model.Cliente;
import br.sc.senac.mca.util.ConnectionFactory;
import br.sc.senac.mca.util.OperacoesCrud;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    private JTable tblCliente;
    private JPanel pnlBotoesAcao;
    private JPanel pnlDados;
    private JPanel pnl;
    private JScrollPane pnlTabela;
    public Integer operacao;
    private String sexo;
    private Integer codigo;
    private String nomeCliente;

    public ClientView(){
        initComponents();
        listeners();
        CarregarDadosTabela();
    }
    public void initComponents(){
        setTitle("Cadastro de Clientes");
        setSize(900,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(pnlClientView);
        setVisible(true);


        pnlBotoesAcao.setVisible(false);
    }
    public void listeners() {
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

   /* private void tblClienteMouseClicked(){
        ListSelectionModel tableSelectionModel = tblCliente.getSelectionModel();
        tblCliente.setSelectionMode(tableSelectionModel);
        setCodigo(tblCliente.getValueAt(tblCliente.getSelectedRow(),0).toString());
        setNomeCliente();
    }*/
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

    private void CarregarDadosTabela() {
        String sql = "select cli_cod, cli_nome, cli_cpf, cli_sexo, cli_fone  from cliente order by cli_cod;";

        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, new String[] {"Código", "Nome", "CPF", "Sexo", "Fone"});
        tblCliente.setModel(model);

        try {
            Connection connection = ConnectionFactory.getConexao();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            tblCliente.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            tblCliente.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblCliente.getColumnModel().getColumn(1).setPreferredWidth(180);
            tblCliente.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblCliente.getColumnModel().getColumn(3).setPreferredWidth(100);
            tblCliente.getColumnModel().getColumn(4).setPreferredWidth(100);


            while (rs.next()) {
                Integer rsCodigo = rs.getInt("cli_cod");
                String rsNome = rs.getString("cli_nome");
                String rsCpf = rs.getString("cli_cpf");
                String rsSexo = rs.getString("cli_sexo");
                if (rs.equals("M")) {
                    rsSexo = "Masculino";
                } else {
                    rsSexo = "Feminino";
                }
                String rsFone = rs.getString("cli_fone");
                model.addRow(new Object[]{rsCodigo, rsNome, rsCpf, rsSexo, rsFone});
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        public Integer getCodigo() {
            return codigo;
        }
        public void setCodigo(Integer codigo) {
            this.codigo = codigo;
        }
        public String getNomeCliente() {
            return nomeCliente;
        }

        public void setNomeCliente(String nomeCliente) {
            this.nomeCliente = nomeCliente;
        }

    public static void main(String[] args) {
        ClientView clientView = new ClientView();
    }
}
