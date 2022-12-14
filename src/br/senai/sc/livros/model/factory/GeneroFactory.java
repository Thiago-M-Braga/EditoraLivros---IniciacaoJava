package br.senai.sc.livros.model.factory;

import br.senai.sc.livros.model.entities.Genero;

import java.sql.ResultSet;

public class GeneroFactory {

    public static Genero getGenero(Integer ordinal) {
        switch (ordinal) {
            case 1 -> {
                return Genero.MASCULINO;
            }
            case 2 -> {
                return Genero.FEMININO;
            }
            default -> {
                return Genero.OUTRO;
            }
        }
    }
}
