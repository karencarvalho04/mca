package br.sc.senac.mca.controller;

import br.sc.senac.mca.dao.LoginDao;
import br.sc.senac.mca.model.Login;

import java.sql.SQLException;
import java.util.ArrayList;

public class LoginController {

    public ArrayList<Login>buscarLoginSenha()
            throws SQLException, ClassNotFoundException{
        return LoginDao.buscarLogin();
    }
}
