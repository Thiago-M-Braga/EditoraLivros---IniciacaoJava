package br.senai.sc.livros.model.service;

import br.senai.sc.livros.model.dao.LivroDAO;
import br.senai.sc.livros.model.entities.*;

import java.util.Collection;

public class LivroService {

    public static Collection<Livro> listarLivros(Pessoa pessoa) {
        return new LivroDAO().listarLivros(pessoa);
    }

    public static void revisarLivro(int isbn, Pessoa pessoa, int status) {
        new LivroDAO().revisarLivro(isbn, pessoa, status);
    }

    public void removerLivro(int isbn) {
        new LivroDAO().removerLivro(isbn);
    }

    public void inserir(Livro livro) {
        new LivroDAO().inserir(livro);
    }
    public static Collection<Livro> selecionarPorStatus(Status status) {
        return new LivroDAO().selecionarPorStatus(status);
    }

    public static Collection<Livro> selecionarAtividadesAutor(Pessoa pessoa) {
        return new LivroDAO().selecionarAtividadesAutor(pessoa);
    }

    public static Collection<Livro> listarAtividades(Pessoa pessoa){
        if(pessoa instanceof Autor){
            return selecionarAtividadesAutor(pessoa);
        } else if(pessoa instanceof Revisor){
            Collection<Livro> livros = new LivroDAO().selecionarPorStatus(Status.AGUARDANDO_REVISAO);
            for(Livro livro : new LivroDAO().selecionarPorStatus(Status.EM_REVISAO)){
                if(livro.getRevisor() == pessoa){
                    livros.add(livro);
                }
            }
            return livros;
        } else {
            return selecionarPorStatus(Status.APROVADO);
        }
    }

    public void atualizarLivro(String titulo, int isbn) {
        new LivroDAO().atualizarLivro(titulo, isbn);
    }
}
