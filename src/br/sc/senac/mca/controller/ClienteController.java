package br.sc.senac.mca.controller;

import br.sc.senac.mca.dao.ClienteDao;
import br.sc.senac.mca.dao.Dao;
import br.sc.senac.mca.model.Cliente;

import java.sql.SQLException;

public class ClienteController extends Dao<Cliente> {
    @Override
    public boolean cadastrar(Cliente cliente) throws SQLException {
        boolean resultado = false;
        //criar classe ClienteDao no pacote dao
        ClienteDao clienteDao = new ClienteDao();

        if (clienteDao.cadastrar(cliente)){
            resultado = true;
        }
        return resultado;
    }

    @Override
    public boolean atualizar(Cliente cliente) throws SQLException {
        boolean resultado = false;
        //criar classe ClienteDao no pacote dao
        ClienteDao clienteDao = new ClienteDao();

        if (clienteDao.atualizar(cliente)){
            resultado = true;
        }
        return resultado;
    }

    @Override
    public boolean excluir(Cliente cliente) throws SQLException {
        boolean resultado = false;
        //criar classe ClienteDao no pacote dao
        ClienteDao clienteDao = new ClienteDao();

        if (clienteDao.excluir(cliente)){
            resultado = true;
        }
        return resultado;
    }

    @Override
    public Integer getCodigo(Cliente pojo) throws SQLException {
        return null;
    }
}
