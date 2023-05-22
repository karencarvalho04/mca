package br.sc.senac.mca.dao;

import br.sc.senac.mca.model.Login;
import br.sc.senac.mca.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class LoginDao {

    public static ArrayList<Login> buscarLogin() throws SQLException{
        ArrayList<Login> loginAux = new ArrayList<>();
        String sql = "select log_nome, log_senha from login";

        Connection conn = ConnectionFactory.getConexao();
        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Login login = new Login();
            login.setLogin(rs.getString("log_nome"));
            login.setSenha(rs.getString("log_senha"));

            loginAux.add(login);
        }

        return loginAux;
    }

}
