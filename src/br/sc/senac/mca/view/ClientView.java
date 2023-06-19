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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
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

    //variáveis em tempo de execução
    private Integer codigo;
    private String nomeCliente;
    DefaultTableModel model = new DefaultTableModel(new Object[][]{}, new String[] {"Código", "Nome", "CPF", "Sexo", "Fone"});



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
                btnNovo.setEnabled(false);
                pnlBotoesAcao.setVisible(true);

                abrirCampos();
                LimparCampos();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnNovo.setEnabled(true);
                btnAtualizar.setEnabled(true);
                btnExcluir.setEnabled(true);
                btnEditar.setEnabled(true);

                fecharCampos();
            }
        });

        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                operacao = OperacoesCrud.EDITAR.getOperacao();

                if (tblCliente.getSelectedRow() == -1){
                    if (tblCliente.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "A tabela está vazia");
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Deve ser selecionado um cliente");
                    }
                } else {
                    btnExcluir.setEnabled(false);
                    btnNovo.setEnabled(false);
                    pnlBotoesAcao.setVisible(true);
                    btnSalvar.setEnabled(false);
                    btnAtualizar.setEnabled(true);

                    abrirCampos();
                }

            }
        });
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gravarAtualizarDados();
                CarregarDadosTabela();
            }
        });
        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                operacao = OperacoesCrud.EXCLUIR.getOperacao();

                if (tblCliente.getSelectedRow() == -1){
                    if (tblCliente.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "A tabela está vazia");
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Deve ser selecionado um cliente");
                    }
                } else {
                    btnExcluir.setEnabled(false);
                    btnNovo.setEnabled(true);
                    pnlBotoesAcao.setVisible(true);
                    btnSalvar.setEnabled(false);
                    btnAtualizar.setEnabled(true);
                    excluirDados();
                    abrirCampos();
                }

            }
        });
        btnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gravarAtualizarDados();
                CarregarDadosTabela();
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
        tblCliente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                //recupera as informações na tabela
                ListSelectionModel tableSelectionModel = tblCliente.getSelectionModel();

                //refresh - limpeza de cache da tabela
                tblCliente.setSelectionModel(tableSelectionModel);

                //armazenar a linha selecionada nas variáveis instância para utilizar nas operações de atualização e exclusão
                setCodigo(Integer.parseInt(tblCliente.getValueAt(tblCliente.getSelectedRow(), 0).toString()));
                setNomeCliente(tblCliente.getValueAt(tblCliente.getSelectedRow(), 1).toString());


                String rowCpf = tblCliente.getValueAt(tblCliente.getSelectedRow(), 2).toString();
                String rowSexo = tblCliente.getValueAt(tblCliente.getSelectedRow(), 3).toString();
                String rowFone = tblCliente.getValueAt(tblCliente.getSelectedRow(), 4).toString();

                txtNome.setText(getNomeCliente());
                txtCpf.setText(rowCpf);
                getSexoSelecionado(rowSexo);
                txtTelefone.setText(rowFone);
        }
        });
    }
    private void getSexoSelecionado(String rowSexo) {
        if(rowSexo.equals("Masculino")){
            rbMasculino.setSelected(true);
            rbFeminino.setSelected(false);
        } else {
            rbMasculino.setSelected(false);
            rbFeminino.setSelected(true);
        }
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
                model.addRow(new Object[]{clienteController.getCodigo(cliente), nome, cpf, sexo, fone});

                JOptionPane.showMessageDialog(null, "O cliente " +
                        cliente.getNome()+ " foi criado com sucesso!");
                        LimparCampos();
            } else if (operacao == OperacoesCrud.EDITAR.getOperacao()){
                cliente.setCodigo(getCodigo());
                clienteController.atualizar(cliente);
                model.setValueAt(nome, tblCliente.getSelectedRow(), 1);
                model.setValueAt(cpf, tblCliente.getSelectedRow(), 2);
                model.setValueAt(sexo, tblCliente.getSelectedRow(), 3);
                model.setValueAt(fone, tblCliente.getSelectedRow(), 4);

                JOptionPane.showMessageDialog(null, "O cliente " +
                        cliente.getNome()+ " foi atualizado com sucesso!");

            }
        } catch (SQLException ex){
            Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void excluirDados() {
        String msg = "Deletar o cliente " + getNomeCliente() + "?";
        int opcaoEscolhida = JOptionPane.showConfirmDialog(null, msg, "Exclusão", JOptionPane.YES_NO_OPTION);
        if (opcaoEscolhida == JOptionPane.YES_OPTION) {
            Cliente cliente = new Cliente();
            cliente.setCodigo(getCodigo());

            try {
                ClienteController clienteController = new ClienteController();
                clienteController.excluir(cliente);
                model.removeRow(tblCliente.getSelectedRow());

                JOptionPane.showMessageDialog(null, "O cliente " + getCodigo() + getNomeCliente() +
                        " foi excluído com sucesso! ", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void fecharCampos(){
        txtNome.setEnabled(false);
        txtCpf.setEnabled(false);
        txtEndereco.setEnabled(false);
        txtTelefone.setEnabled(false);
        rbFeminino.setEnabled(false);
        rbMasculino.setEnabled(false);
    }
    private void abrirCampos(){
        txtNome.setEditable(true);
        txtNome.setEnabled(true);
        txtCpf.setEditable(true);
        txtCpf.setEnabled(true);
        txtEndereco.setEditable(true);
        txtEndereco.setEnabled(true);
        txtTelefone.setEditable(true);
        txtTelefone.setEnabled(true);
        rbFeminino.setEnabled(true);
        rbMasculino.setEnabled(true);
    }
    private String formatarCampoSexo(String sexo){
        if (Objects.equals(sexo, "Masculino")){
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

       // DefaultTableModel model = new DefaultTableModel(new Object[][]{}, new String[] {"Código", "Nome", "CPF", "Sexo", "Fone"});
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

            while(rs.next()){
                Integer rsCodigo = rs.getInt("cli_cod");
                String rsNome = rs.getString("cli_nome");
                String rsCpf = rs.getString("cli_cpf");
                String rsSexo = rs.getString("cli_sexo");
                if(rsSexo.equals("M") || rsSexo.equals("m")){
                    rsSexo = "Masculino";
                }else{
                    rsSexo = "Feminino";
                }
                String rsFone = rs.getString("cli_fone");
                model.addRow(new Object[]{rsCodigo, rsNome, rsCpf, rsSexo, rsFone });
            }

            // ps.executeUpdate();
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
