package br.senai.sc.livros.model.factory;

import br.senai.sc.livros.model.entities.*;

import java.sql.ResultSet;

public class PessoaFactory {
    public static Pessoa criarPessoa(ResultSet resultSet) {
        Pessoa pessoa = new Pessoa();
        try {
            pessoa.setNome(resultSet.getString("nome"));
            pessoa.setSobrenome(resultSet.getString("sobrenome"));
            pessoa.setEmail(resultSet.getString("email"));
            pessoa.setSenha(resultSet.getString("senha"));
            pessoa.setCPF(resultSet.getString("cpf"));
            pessoa.setGenero(GeneroFactory.criarGenero(resultSet));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pessoa;
    }

    public Pessoa getPessoa(String cpf, String nome, String sobrenome, String email,
                            String senha, Integer genero, Integer tipo) {
        switch (tipo) {
            case 1 -> {
                return new Autor(cpf, nome, sobrenome, email, new GeneroFactory().getGenero(genero), senha);
            }
            case 2 -> {
                return new Revisor(cpf, nome, sobrenome, email, new GeneroFactory().getGenero(genero), senha);
            }
            default -> {
                return new Diretor(cpf, nome, sobrenome, email, new GeneroFactory().getGenero(genero), senha);
            }
        }
    }
}
