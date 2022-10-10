package br.senai.sc.livros.model.dao;

import br.senai.sc.livros.model.entities.*;
import br.senai.sc.livros.model.factory.ConexaoFactory;
import br.senai.sc.livros.model.factory.LivroFactory;
import br.senai.sc.livros.model.factory.PessoaFactory;
import br.senai.sc.livros.model.factory.StatusFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class LivroDAO {

    private static Collection<Livro> listaLivros = new ArrayList<>();
    private Connection connection;

    //Faz a conexão/ligação com o banco de dados
    public LivroDAO() {
        this.connection = new ConexaoFactory().connectDB();
    }

    //Método que retorna a lista de livros daquele autor
    public  Collection<Livro> listarLivros(Pessoa autor) {
        String sqlCommand = "select * from livros where AUTOR_cpf = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
            preparedStatement.setString(1, autor.getCPF());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet != null && resultSet.next()) {
                    Livro livro = new Livro(
                            (Autor) new PessoaDAO().selecionarPorCPF(resultSet.getString("AUTOR_cpf")),
                            resultSet.getString("tituloLivro"),
                            new StatusFactory().getStatus(),
                            resultSet.getInt("qtdPaginasLivro"),
                            resultSet.getInt("isbnLivro")
                    );
                    listaLivros.add(livro);
                }
                return listaLivros;
            } catch (Exception e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na preparação do comando SQL"+e.getMessage());
        }
    }

    //Método que irá remover um livro da lista de livros e do Banco de dados
    public void removerLivro(int isbn) {
        String sqlCommand = "delete from livros where isbnLivro = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
            preparedStatement.setInt(1, isbn);
            preparedStatement.execute();
            listaLivros.remove(LivroFactory.pegarLivroPeloISBN(isbn, listaLivros));
        } catch (Exception e) {
            throw new RuntimeException("Erro na execução do comando SQL");
        }
    }

    //Método que irá adicionar um livro no Banco de dados
    public void inserir(Livro livro) {
        String sqlCommand = "insert into livros (isbnLivro, tituloLivro, statusLivro, qtdPaginasLivro, AUTOR_cpf) values (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
            preparedStatement.setInt(1, livro.getISBN());
            preparedStatement.setString(2, livro.getTitulo());
            preparedStatement.setInt(3, livro.getStatus().ordinal());
            preparedStatement.setInt(4, livro.getQntdPaginas());
            preparedStatement.setString(5, livro.getAutor().getCPF());
            try {
                preparedStatement.execute();
            } catch (Exception e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na preparação do comando SQL");
        }
    }

    //Método que irá retornar um livro do banco de dados conforme o status
    public Collection<Livro> selecionarPorStatus(Status status) {
        Collection<Livro> livrosStatus = new ArrayList<>();
        String sqlCommand = "select * from livros where statusLivro = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
            preparedStatement.setInt(1, status.ordinal());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet != null && resultSet.next()) {
                    Livro livro = new Livro(
                            (Autor) new PessoaDAO().selecionarPorCPF(resultSet.getString("AUTOR_cpf")),
                            resultSet.getString("tituloLivro"),
                            new StatusFactory().getStatus(),
                            resultSet.getInt("qtdPaginasLivro"),
                            resultSet.getInt("isbnLivro"));
                            livrosStatus.add(livro);
                }
                return livrosStatus;
            } catch (Exception e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na preparação do comando SQL");
        }
    }

    //Método que irá retornar livros do banco de dados conforme as atividades de uma pessoa
    public Collection<Livro> selecionarAtividadesAutor(Pessoa pessoa) {
        Collection<Livro> livrosStatus = new ArrayList<>();
        String sqlCommand = "select * from livros where statusLivro = 3 and AUTOR_cpf = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
            preparedStatement.setString(1, pessoa.getCPF());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet != null && resultSet.next()) {
                    Livro livro = new Livro(
                            (Autor) new PessoaDAO().selecionarPorCPF(resultSet.getString("AUTOR_cpf")),
                            resultSet.getString("tituloLivro"),
                            new StatusFactory().getStatus(),
                            resultSet.getInt("qtdPaginasLivro"),
                            resultSet.getInt("isbnLivro"));
                    livrosStatus.add(livro);
                }
                return livrosStatus;
            } catch (Exception e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na preparação do comando SQL");
        }
    }

    //Método que irá atualizar um livro do banco de dados conforme o ISBN
    public void atualizarLivro(String titulo, int isbn) {
        String sqlCommand = "update livros set tituloLivro = ?, statusLivro = ? where isbnLivro = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
            preparedStatement.setString(1, titulo);
            preparedStatement.setInt(2, Status.AGUARDANDO_REVISAO.ordinal());
            preparedStatement.setInt(3, isbn);
            try {
                preparedStatement.execute();
            } catch (Exception e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na preparação do comando SQL");
        }
    }

    //Método que irá atualizar o status de um livro do banco de dados conforme o ISBN
    public void revisarLivro(int isbn, Pessoa pessoa, int status) {
        String sqlCommand = "update livros set statusLivro = ?, REVISOR_cpf = ? where isbnLivro = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
            switch (status) {
                case 1:
                    preparedStatement.setInt(1, Status.EM_REVISAO.ordinal());
                    break;
                case 2:
                    preparedStatement.setInt(1, Status.AGUARDANDO_EDICAO.ordinal());
                    break;
                case 3:
                    preparedStatement.setInt(1, Status.PUBLICADO.ordinal());
                    break;
                default:
                    preparedStatement.setInt(1, Status.REPROVADO.ordinal());
                    break;
            }
            preparedStatement.setString(2, pessoa.getCPF());
            preparedStatement.setInt(3, isbn);
            try {
                preparedStatement.execute();
            } catch (Exception e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na preparação do comando SQL");
        }
    }
}
