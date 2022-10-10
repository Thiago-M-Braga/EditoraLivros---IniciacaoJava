package br.senai.sc.livros.controller;

import br.senai.sc.livros.model.entities.Genero;
import br.senai.sc.livros.model.entities.Pessoa;
import br.senai.sc.livros.model.service.PessoaService;

import java.sql.SQLException;
import java.util.Collection;

public class PessoaController {

    public static Pessoa ValidaLogin(String email, String senha) {
        return new PessoaService().ValidaLogin(email, senha);
    }

    public void cadastrar(String nome, String sobrenome, String email, Object genero, String senha, String cpf, String confSenha, int tipoUsuario) throws SQLException {
        PessoaService service = new PessoaService();
        Pessoa pessoa = Pessoa.cadastrar(nome, sobrenome, email, (Genero)genero, senha, cpf, confSenha, tipoUsuario);
        service.inserir(pessoa);
    }

    public int BuscarTipoUsuario(String email, String senha) {
        return new PessoaService().BuscarTipoUsuario(email, senha);
    }
}
