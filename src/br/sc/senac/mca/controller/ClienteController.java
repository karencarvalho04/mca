package br.sc.senac.mca.controller;

import br.sc.senac.mca.dao.ClienteDao;
import br.sc.senac.mca.dao.Dao;
import br.sc.senac.mca.model.Cliente;

import java.sql.SQLException;

public class ClienteController extends Dao<Cliente> {
    @Override
    public boolean cadastrar(Cliente cliente) throws SQLException {
        boolean resultado = false;

        ClienteDao clienteDao = new ClienteDao();

        if (clienteDao.cadastrar(cliente)){
            resultado = true;
        }
        return resultado;
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
