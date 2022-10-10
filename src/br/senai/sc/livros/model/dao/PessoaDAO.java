package br.senai.sc.livros.model.dao;

import br.senai.sc.livros.model.factory.ConexaoFactory;
import br.senai.sc.livros.model.entities.*;
import br.senai.sc.livros.model.factory.PessoaFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PessoaDAO {
    private Connection conn;

    public PessoaDAO() {
        this.conn = new ConexaoFactory().connectDB();
    }

    // Método para inserir uma pessoa no banco de dados
    public void inserir(Pessoa pessoa) {
        String sqlCommand = "INSERT INTO PESSOAS (cpfPessoa, nomePessoa, sobrenomePessoa, emailPessoa, generoPessoa, senhaPessoa, tipoPessoa) values (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstm = conn.prepareStatement(sqlCommand)) {
            pstm.setString(1, pessoa.getCPF());
            pstm.setString(2, pessoa.getNome());
            pstm.setString(3, pessoa.getSobrenome());
            pstm.setString(4, pessoa.getEmail());
            pstm.setInt(5, pessoa.getGenero().ordinal());
            pstm.setString(6, pessoa.getSenha());
            pstm.setInt(7, ((pessoa instanceof Autor) ? 1 : (pessoa instanceof Revisor) ? 2 : 3));
            try {
                pstm.execute();
            } catch (Exception e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na preparação do comando SQL");
        }
        System.out.println("Cadastro chegou ao fim");
    }

    // Método para buscar uma pessoa no banco de dados pelo seu CPF
    public Pessoa selecionarPorCPF(String cpf) {
        String sqlCommand = "select * from pessoas where cpfPessoa = ?";
        try (PreparedStatement pstm = conn.prepareStatement(sqlCommand)) {
            pstm.setString(1, cpf);
            try (ResultSet resultSet = pstm.executeQuery()) {
                if (resultSet.next()) {
                    return extrairObjeto(resultSet);
                }
            } catch (Exception e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na preparação do comando SQL");
        }
        throw new RuntimeException("Algo deu ruim");
    }

    // Método para extrair os dados de uma pessoa
    private Pessoa extrairObjeto(ResultSet resultSet) {
        try {
            return new PessoaFactory().getPessoa(
                    resultSet.getString("cpfPessoa"),
                    resultSet.getString("nomePessoa"),
                    resultSet.getString("sobrenomePessoa"),
                    resultSet.getString("emailPessoa"),
                    resultSet.getString("senhaPessoa"),
                    resultSet.getInt("generoPessoa"),
                    resultSet.getInt("tipoPessoa"));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao extrair o objeto!");
        }
    }

    // Método para buscar todas uma pessoa do banco de dados através do seu email e senha
    public Pessoa ValidaLogin(String email, String senha) {
        String sqlCommand = "select * from pessoas where emailPessoa = ? and senhaPessoa = ?";
        try (PreparedStatement pstm = conn.prepareStatement(sqlCommand)) {
            pstm.setString(1, email);
            pstm.setString(2, senha);
            try (ResultSet resultSet = pstm.executeQuery()) {
                if (resultSet.next()) {
                    return extrairObjeto(resultSet);
                }
            } catch (Exception e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na preparação do comando SQL");
        }
        throw new RuntimeException("Algo deu ruim");
    }

    // Método para buscar um tipo de usuário no banco de dados pelo email e senha
    public int BuscarTipoUsuario(String email, String senha) {
        String sqlCommand = "select * from pessoas where emailPessoa = ? and senhaPessoa = ?";
        try (PreparedStatement pstm = conn.prepareStatement(sqlCommand)) {
            pstm.setString(1, email);
            pstm.setString(2, senha);
            try (ResultSet resultSet = pstm.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("tipoPessoa");
                }
            } catch (Exception e) {
                throw new RuntimeException("Erro na execução do comando SQL");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro na preparação do comando SQL");
        }
        return 0;
    }
}
