package br.sc.senac.mca.view;

import br.sc.senac.mca.util.ConnectionFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchView extends JFrame{
    private JPanel pnlSearch;
    private JTextField txtPesquisaNome;
    private JButton btnLimpar;
    private JButton btnPesquisar;
    private JTable tblCliente;
    private JLabel lblNome;
    private JScrollPane pnlTabela;
    private JPanel pnlPesquisa;
    DefaultTableModel model = new DefaultTableModel(new Object[][]{}, new String[] {"Código", "Nome", "CPF", "Sexo", "Fone"});

    private String nomeCliente;

    public SearchView(){
        initComponents();
        listeners();
        carregarTabela();

    }

    private void carregarTabela() {
        String sql = "select cli_cod, cli_nome, cli_cpf, cli_sexo, cli_fone  from cliente order by cli_cod;";

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
    public void listeners(){
        tblCliente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                //recupera as informações na tabela
                ListSelectionModel tableSelectionModel = tblCliente.getSelectionModel();

                //refresh - limpeza de cache da tabela
                tblCliente.setSelectionModel(tableSelectionModel);

                //armazenar a linha selecionada nas variáveis instância para utilizar nas operações de atualização e exclusão
                setNomeCliente(tblCliente.getValueAt(tblCliente.getSelectedRow(), 1).toString());

                txtPesquisaNome.setText(getNomeCliente());
              }
        });
        btnPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model = (DefaultTableModel) tblCliente.getModel();
                TableRowSorter sorter = new TableRowSorter<TableModel>(model);
                tblCliente.setRowSorter(sorter);

                String nome = txtPesquisaNome.getText();
                if (nome != null){
                    sorter.setRowFilter(RowFilter.regexFilter(("(?i)" + nome)));
                } else {
                    sorter.setRowFilter(null);
                }
            }
        });

        btnLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtPesquisaNome.setText("");
            }
        });
    }
    public void initComponents(){
        setTitle("Pesquisa de clientes");
        setSize(900,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(pnlSearch);
        setVisible(true);

    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public static void main(String[] args) {
        SearchView searchView = new SearchView();
    }
}
