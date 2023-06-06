package br.sc.senac.mca.dao;

import br.sc.senac.mca.model.Cliente;
import br.sc.senac.mca.util.ConnectionFactory;
import br.sc.senac.mca.util.StandardException;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDao extends Dao<Cliente>{
    @Override
    public boolean cadastrar(Cliente cliente) throws SQLException {
        PreparedStatement ps = null;
        String sql = "INSERT INTO cliente (cli_nome, cli_cpf, cli_sexo, cli_fone) " +
                "VALUES (?, ?, ?, ?);";
        try{
            Connection conn = this.obterConexao();

                try {
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, cliente.getNome());
                    ps.setString(2, cliente.getCpf());
                    ps.setString(3, cliente.getSexo());
                    ps.setString(4, cliente.getFone());

                    ps.executeUpdate();

                } finally{
                    try{
                        ConnectionFactory.closeConnection(conn, ps);
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                }

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Cliente pojo) throws SQLException {
        return false;
    }

    @Override
    public boolean excluir(Cliente pojo) throws SQLException {
        return false;
    }

    @Override
    public Integer getCodigo(Cliente pojo) throws SQLException {
        return null;
    }
}
