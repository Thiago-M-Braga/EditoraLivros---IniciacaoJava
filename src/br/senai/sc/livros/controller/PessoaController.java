package br.senai.sc.livros.controller;

import br.senai.sc.livros.model.entities.Genero;
import br.senai.sc.livros.model.entities.Livro;
import br.senai.sc.livros.model.entities.Pessoa;
import br.senai.sc.livros.model.service.PessoaService;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Collection;

public class PessoaController {
    Pessoa model;

    public static void removePessoaPorID(int id) {
        PessoaService.removePessoaPorID(id);
    }

    public static Collection<Livro> listarPessoas() {
        return PessoaService.listarPessoas();
    }

    public Pessoa validaLogin(String email, String senha) {
        PessoaService service = new PessoaService();
        model = service.selecionarPorEmail(email);
        return model.validaLogin(senha);
    }

    public void cadastrar(String nome, String sobrenome, String email, Object genero, String senha, String cpf, String confSenha) throws SQLException {
        PessoaService service = new PessoaService();
        Pessoa pessoa = Pessoa.cadastrar(nome, sobrenome, email, (Genero)genero, senha, cpf, confSenha);
        service.inserir(pessoa);

    }
}
