package br.senai.sc.livros.model.entities;

public class Autor extends Pessoa {

    public Autor(String CPF, String nome, String sobrenome, String email, Genero genero, String senha) {
        super(CPF, nome, sobrenome, email, genero, senha, 1);
    }


}
