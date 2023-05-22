package br.sc.senac.mca.util;


import javax.swing.JOptionPane;

public class TesteJOptionP {

    public static void main(String[] args){
        /*String nome = JOptionPane.showInputDialog("Digite o nome:");
        String sobreNome = JOptionPane.showInputDialog("Digite o sobrenome:");
        int idade = Integer.parseInt(JOptionPane.showInputDialog("Digite a sua idade: "));

        String nomeCompleto = nome + " " + sobreNome;

        JOptionPane.showMessageDialog(null, "Nome Completo: "+nomeCompleto+"\n Idade: "+idade,
                "Informação", JOptionPane.INFORMATION_MESSAGE);*/

        ConnectionFactory.getConexao();
    }

}