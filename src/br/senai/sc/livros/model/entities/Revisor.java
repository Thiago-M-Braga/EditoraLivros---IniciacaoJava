package br.senai.sc.livros.model.entities;

public class Revisor extends Pessoa {

    public Revisor(String CPF, String nome, String sobrenome, String email, Genero genero, String senha) {
        super(CPF, nome, sobrenome, email, genero, senha,2);
    }

}
