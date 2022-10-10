package br.senai.sc.livros.model.entities;

import br.senai.sc.livros.model.factory.PessoaFactory;
import br.senai.sc.livros.view.Main;

import java.util.ArrayList;

public class Pessoa {
    private String CPF, nome, sobrenome, email, senha;
    private Genero genero;
    private int tipoUsuario;

    @Override
    public String toString() {
        return "\n--Nome: " + nome +
                "\nSobrenome: " + sobrenome +
                "\nCPF: " + CPF +
                "\nEmail: " + email +
                "\nSenha: " + senha +
                "\nGenero: " + genero;
    }

    public Pessoa(String CPF, String nome, String sobrenome, String email, Genero genero, String senha, int tipoUsuario) {
        this.CPF = CPF;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.genero = genero;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public boolean equals(Object o) {
        Pessoa outraPessoa = (Pessoa) o;
        return CPF.equals(outraPessoa.CPF);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (char l : CPF.toCharArray()) {
            hash += l;
        }
        return hash;
    }

    public String getCPF() {
        return CPF;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getSenha() {
        return senha;
    }

    public Pessoa validaLogin(String senha) {
        if (this.getSenha().equals(senha)) {
            return this;
        };
        throw new RuntimeException("Senha incorreta!");
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public static Pessoa cadastrar(String nome, String sobrenome, String email, Genero genero, String senha, String cpf, String confSenha, int tipoUsuario) {
        if (senha.equals(confSenha)) {
            if (email.contains("@")) {
                int tipo = tipoUsuario;
                return new PessoaFactory().getPessoa(cpf, nome, sobrenome, email, senha, genero.ordinal(), tipo);
            } else {
                throw new RuntimeException("Email inválido!");
            }
        } else {
            throw new RuntimeException("Senhas não conferem!");
        }
    };

    public int getTipo() {
        return tipoUsuario;
    }
}
