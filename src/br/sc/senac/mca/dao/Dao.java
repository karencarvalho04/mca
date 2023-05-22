package br.sc.senac.mca.dao;

import br.sc.senac.mca.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class Dao <Entity>{

    protected Connection obterConexao() throws SQLException{
        return ConnectionFactory.getConexao();
    }

    public abstract boolean cadastrar (Entity pojo) throws SQLException;
    public abstract boolean atualizar (Entity pojo) throws SQLException;
    public abstract boolean excluir (Entity pojo) throws SQLException;
    public abstract Integer getCodigo(Entity pojo) throws SQLException;


}
