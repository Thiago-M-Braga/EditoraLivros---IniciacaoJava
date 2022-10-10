package br.senai.sc.livros.model.service;

import br.senai.sc.livros.model.dao.PessoaDAO;
import br.senai.sc.livros.model.entities.Pessoa;

import java.util.Collection;

public class PessoaService {
    public void inserir(Pessoa pessoa) {
        new PessoaDAO().inserir(pessoa);
    }

    public Pessoa ValidaLogin(String email, String senha) {
        return new PessoaDAO().ValidaLogin(email, senha);
    }

    public int BuscarTipoUsuario(String email, String senha) {
        return new PessoaDAO().BuscarTipoUsuario(email, senha);
    }
}
