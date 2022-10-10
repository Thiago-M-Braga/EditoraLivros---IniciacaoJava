package br.senai.sc.livros.view;

import br.senai.sc.livros.controller.LivrosController;
import br.senai.sc.livros.controller.PessoaController;
import br.senai.sc.livros.model.entities.Pessoa;
import br.senai.sc.livros.model.factory.GeneroFactory;

import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static String email = "";
    private static String senha = "";

    public static void main(String[] args) {
        login(false);
    }

    //Método para fazer login ou ir para o cadastro
    private static void login(Boolean cadastro) {
        int opcao = 1;
        if(cadastro != true) {
            System.out.println("Já é cadastrado? \n1 - Sim \n2 - Não");
            opcao = sc.nextInt();
        }
        if(opcao == 1) {
            System.out.println("=-=-=-=-=-=-=-= Login =-=-=-=-=-=-=-=");
            System.out.println("Digite seu email: ");
            email = sc.next();
            System.out.println("Digite sua senha: ");
            senha = sc.next();
            Pessoa pessoa = PessoaController.ValidaLogin(email, senha);
            if(pessoa.getCPF() != null) {
                System.out.println("\nLogin efetuado com sucesso!\n");
                menu(pessoa);
            } else {
                System.out.println("Email ou senha incorreto!");
                login(false);
            }
        } else {
            cadastro(1);
        }
    }

    //Menu principal, onde irá aparecer todas as opções do sistema
    private static void menu(Pessoa pessoa) {
        System.out.println("=-=-=-=-=-=-=-= Menu =-=-=-=-=-=-=-=");
        if (pessoa.getTipo() == 3) {
            System.out.println("0 - Cadastrar Revisor");
        }
        System.out.println("1 - Cadastrar livro \n2 - Listar livros \n3 - Listar atividades \n4 - Remover livro \n5 - Sair");
        int opcao = sc.nextInt();
        switch (opcao) {
            case 0 -> {
                if (pessoa.getTipo() == 3) {
                    cadastro(2);
                    menu(pessoa);
                } else {
                    System.out.println("Você não tem permissão para acessar essa opção!");
                    menu(pessoa);
                }
            }
            case 1 -> {
                cadastrarLivro(pessoa);
            }
            case 2 -> {
                listarLivros(pessoa);
            }
            case 3 -> {
                listarAtividades(pessoa);
            }
            case 4 -> {
                removerLivro();
            }
            case 5 -> {
                System.out.println("\nSaindo...\n");
                login(false);
            }
            default -> {
                System.out.println("Opção inválida! Burro!");
            }
        }
        menu(pessoa);
    }

    //Método para remover um livro
    private static void removerLivro() {
        System.out.println("=-=-=-=-=-=-=-= Remover livro =-=-=-=-=-=-=-=");
        System.out.println("Digite o ISBN do livro que deseja remover: ");
        int isbn = sc.nextInt();
        LivrosController.removerLivro(isbn);
        System.out.println("\nLivro removido com sucesso!\n");
    }

    //Método para listar as atividades de um usuário
    private static void listarAtividades(Pessoa pessoa) {
        System.out.println("=-=-=-=-=-=-=-= Atividades =-=-=-=-=-=-=-=");

        if (LivrosController.listarAtividades(pessoa).isEmpty()) {
            System.out.println("Nenhuma atividade encontrada!");
        } else if(pessoa.getTipo() == 1) {
            System.out.println(LivrosController.listarAtividades(pessoa));
            System.out.println("\nDeseja Editar algum livro? \n1 - Sim \n2 - Não");
            int opcao = sc.nextInt();
            if(opcao == 1) {
                editarLivro();
            }
        } else if(pessoa.getTipo() == 2) {
            System.out.println(LivrosController.listarAtividades(pessoa));
            System.out.println("\nDeseja Revisar algum livro? \n1 - Sim \n2 - Não");
            int opcao = sc.nextInt();
            if(opcao == 1) {
                revisarLivro(pessoa);
            }
        }
    }

    //Método para o revisor revisar um livro
    private static void revisarLivro(Pessoa pessoa) {
        System.out.println("=-=-=-=-=-=-=-= Revisar livro =-=-=-=-=-=-=-=");
        System.out.println("Digite o ISBN do livro que deseja revisar: ");
        int isbn = sc.nextInt();
        System.out.println("Digite o status do livro: \n1 - Em revisão \n2 - Aguardando edição \n3 - Aprovado \n4 - Reprovado");
        int status = sc.nextInt();
        LivrosController.revisarLivro(isbn, pessoa, status);
        System.out.println("\nLivro revisado com sucesso!\n");
    }

    //Método para o autor editar um livro
    private static void editarLivro() {
        System.out.println("=-=-=-=-=-=-=-= Editar livro =-=-=-=-=-=-=-=");
        System.out.println("Digite o ISBN do livro que deseja editar: ");
        int isbn = sc.nextInt();
        System.out.println("Digite o novo titulo: ");
        String titulo = sc.next();
        LivrosController.atualizarLivro(titulo, isbn);
        System.out.println("\nLivro editado com sucesso!\n");
    }

    //Método para listar os livros da pessoa
    private static void listarLivros(Pessoa pessoa) {
        System.out.println("=-=-=-=-=-=-=-= Listar livros =-=-=-=-=-=-=-=");
        System.out.println(LivrosController.listarLivros(pessoa));
    }

    //Método para cadastrar um livro
    private static void cadastrarLivro(Pessoa pessoa) {
        System.out.println("=-=-=-=-=-=-=-= Cadastrar livro =-=-=-=-=-=-=-=");
        System.out.println("Digite o isbn do livro: ");
        int isbn = sc.nextInt();
        System.out.println("Digite o título do livro: ");
        String titulo = sc.next();
        System.out.println("Digite a quantidade de páginas: ");
        int qtdPaginas = sc.nextInt();
        LivrosController.cadastrarLivro(titulo, isbn, qtdPaginas, pessoa);
        System.out.println("\nLivro cadastrado com sucesso!\n");
    }

    //Método para cadastrar um usuário
    private static void cadastro(int tipoUsuario) {
        System.out.println("=-=-=-=-=-=-=-= Cadastro =-=-=-=-=-=-=-=");
        System.out.println("Digite o CPF: ");
        String cpf = sc.next();
        System.out.println("Digite o nome: ");
        String nome = sc.next();
        System.out.println("Digite o sobrenome: ");
        String sobrenome = sc.next();
        System.out.println("Digite o Genero; \n1 - Masculino \n2 - Feminino \n3 - Outro");
        int genero = sc.nextInt();
        System.out.println("Digite o email: ");
        String email = sc.next();
        System.out.println("Digite a senha: ");
        String senha = sc.next();
        System.out.println("Digite a confirmação de senha: ");
        String confSenha = sc.next();
        PessoaController controller = new PessoaController();
        try {
            controller.cadastrar(nome, sobrenome, email, new GeneroFactory().getGenero(genero), senha, cpf, confSenha, tipoUsuario);
            login(true);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
        }
    }
}
