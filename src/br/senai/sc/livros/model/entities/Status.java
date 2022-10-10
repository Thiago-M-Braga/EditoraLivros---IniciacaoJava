package br.senai.sc.livros.model.entities;

public enum Status {
    AGUARDANDO_REVISAO("Aguardando revisão", new int[]{1, 3}),
    EM_REVISAO("Em revisão", new int[]{2}),
    APROVADO("Aprovado", new int[]{2}),
    AGUARDANDO_EDICAO("Aguardando edição", new int[]{2}),
    REPROVADO("Reprovado", new int[]{2, 3}),
    PUBLICADO("Publicado", new int[]{3});

    private String nome;
    private int[] permissao;

    Status(String nome, int[] permissao) {
        this.nome = nome;
        this.permissao = permissao;
    }
    public String getNome() {
        return nome;
    }
}
