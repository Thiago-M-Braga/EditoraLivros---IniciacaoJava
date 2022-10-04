package br.senai.sc.livros.model.service;

import br.senai.sc.livros.model.dao.PessoaDAO;
import br.senai.sc.livros.model.entities.Livro;
import br.senai.sc.livros.model.entities.Pessoa;
import br.senai.sc.livros.model.factory.ConexaoFactory;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class PessoaService {
    PessoaDAO bdPessoa = new PessoaDAO();

    public static void removePessoaPorID(int id) {
        PessoaDAO.removePessoaPorID(id);
    }

    public static Collection<Livro> listarPessoas() {
        return PessoaDAO.listarPessoas();
    }

    public void inserir(Pessoa pessoa) {
        new PessoaDAO().inserir(pessoa);
    }

    public void remover(Pessoa pessoa) {
        new PessoaDAO().remover(pessoa);
    }

    public Pessoa selecionarPorCPF(String CPF) {
        return  new PessoaDAO().selecionarPorCPF(CPF);
    }

    public Pessoa selecionarPorEmail(String email) {
        return bdPessoa.selecionarPorEmail(email);
    }
}
