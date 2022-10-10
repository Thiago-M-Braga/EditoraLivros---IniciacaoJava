package br.senai.sc.livros.controller;

import br.senai.sc.livros.model.entities.*;
import br.senai.sc.livros.model.service.LivroService;

import java.util.Collection;

public class LivrosController {

    public static Collection<Livro> listarLivros(Pessoa pessoa) {
        return LivroService.listarLivros(pessoa);
    }

    public static void cadastrarLivro(String titulo, int isbn, int qtdPag, Pessoa autor){
        new LivroService().inserir(Livro.cadastrar(titulo, isbn, qtdPag, (Autor)autor));
    }

    public static Collection<Livro> listarAtividades(Pessoa pessoa) {
        return LivroService.listarAtividades(pessoa);
    }

    public static void removerLivro(int isbn) {
        new LivroService().removerLivro(isbn);
    }

    public static void atualizarLivro(String titulo, int isbn) {
        new LivroService().atualizarLivro(titulo, isbn);
    }

    public static void revisarLivro(int isbn, Pessoa pessoa, int status) {
        LivroService.revisarLivro(isbn, pessoa, status);
    }
}
